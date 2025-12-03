package com.dacs.backend.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dacs.backend.dto.CirugiaRequestDto;
import com.dacs.backend.dto.CirugiaResponseDTO;
import com.dacs.backend.dto.MiembroEquipoMedicoDto;
import com.dacs.backend.dto.PersonalDto;
import com.dacs.backend.mapper.CirugiaMapper;
import com.dacs.backend.model.entity.Cirugia;
import com.dacs.backend.model.entity.EquipoMedico;
import com.dacs.backend.model.entity.Personal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.dacs.backend.model.repository.CirugiaRepository;
import com.dacs.backend.model.repository.EquipoMedicoRepository;
import com.dacs.backend.model.repository.PersonalRepository;


@Service
public class CirugiaServiceImpl implements CirugiaService {

    @Autowired
    CirugiaRepository cirugiaRepository;
    @Autowired
    private CirugiaMapper cirugiaMapper;

    @Autowired
    private EquipoMedicoRepository equipoMedicoRepository;
    @Autowired
    private PersonalRepository personalRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Optional<Cirugia> getById(Long id) {
        return cirugiaRepository.findById(id);
    }

    @Override
    @Transactional
    public CirugiaResponseDTO create(CirugiaRequestDto request) {
        // mapear request -> entidad (resuelve relaciones dentro del mapper)
        Cirugia entity = cirugiaMapper.toEntity(request);
        Cirugia saved = cirugiaRepository.save(entity);
        // mapear entidad -> response DTO
        return cirugiaMapper.toResponseDto(saved);
    }

    @Override
    public Cirugia save(Cirugia entity) {
        return cirugiaRepository.save(entity);
    }

    @Override
    public Boolean existById(Long id) {
        return cirugiaRepository.existsById(id);
    }

    @Override
    public java.util.List<Cirugia> getAll() {
        return cirugiaRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        Optional<Cirugia> cirugia = getById(id);
        cirugiaRepository.delete(cirugia.get());
    }

    @Override
    public List<Cirugia> find(java.util.Map<String, Object> filter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Cirugia getBy(Map<String, Object> filter) {
        throw new UnsupportedOperationException();
    }

    // @Override
    // public MiembroEquipoMedicoDto saveEquipoMedico(Long id, MiembroEquipoMedicoDto entity) {
    //     EquipoMedico equipoMedico = modelMapper.map(entity, EquipoMedico.class);
    //     EquipoMedico saved = equipoMedicoRepository.save(equipoMedico);
    //     return modelMapper.map(saved, MiembroEquipoMedicoDto.class);
    // }

    @Override
    public List<MiembroEquipoMedicoDto> getEquipoMedico(Long cirugiaId) {
        List<EquipoMedico> equipo = equipoMedicoRepository.findByCirugiaId(cirugiaId);

        List<Long> personalIds = equipo.stream()
                .map(EquipoMedico::getPersonal)
                .filter(java.util.Objects::nonNull)
                .distinct()
                .map(Personal::getId)
                .collect(Collectors.toList());

        final Map<Long, Personal> personalMap;
        if (!personalIds.isEmpty()) {
            List<Personal> personals = personalRepository.findAllById(personalIds);
            personalMap = personals.stream().collect(Collectors.toMap(Personal::getId, p -> p));
        } else {
            personalMap = Map.of();
        }

        return equipo.stream().map(e -> {
            // mapear de forma explícita igual que en createEquipoMedico
            MiembroEquipoMedicoDto dto = modelMapper.map(e, MiembroEquipoMedicoDto.class);

            // asegurar campos clave
            dto.setRol(e.getRol());
            dto.setCirugiaId(e.getCirugia() != null ? e.getCirugia().getId() : null);
            dto.setFechaAsignacion(e.getFechaAsignacion());

            // rellenar info del personal a partir del personalMap (evita N+1)
            Personal p = (e.getPersonal() != null) ? personalMap.get(e.getPersonal().getId()) : null;
            if (p != null) {
                PersonalDto pDto = modelMapper.map(p, PersonalDto.class);
                dto.setPersonal(pDto);
            } else {
                dto.setPersonal(null);
            }
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<MiembroEquipoMedicoDto> createEquipoMedico(Long cirugiaId, List<MiembroEquipoMedicoDto.Create> req) {
        // validar existencia de la cirugía
        Cirugia cirugia = cirugiaRepository.findById(cirugiaId)
                .orElseThrow(() -> new IllegalArgumentException("Cirugia no encontrada id=" + cirugiaId));

        // obtener ids de personal desde la petición
        List<Long> personalIds = req.stream()
                .map(MiembroEquipoMedicoDto.Create::getPersonalId)
                .filter(java.util.Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        // cargar todos los personals en una sola consulta
        final Map<Long, Personal> personalMap;
        if (!personalIds.isEmpty()) {
            List<Personal> personals = personalRepository.findAllById(personalIds);
            personalMap = personals.stream().collect(Collectors.toMap(Personal::getId, p -> p));
        } else {
            personalMap = Map.of();
        }

        // construir entidades a guardar
        List<EquipoMedico> toSave = new java.util.ArrayList<>();
        for (MiembroEquipoMedicoDto.Create item : req) {
            Personal personal = personalMap.get(item.getPersonalId());
            if (personal == null) {
                throw new IllegalArgumentException("Personal no encontrado id=" + item.getPersonalId());
            }
            EquipoMedico e = new EquipoMedico();
            e.setFechaAsignacion(LocalDateTime.now());
            e.setCirugia(cirugia);
            e.setPersonal(personal);
            e.setRol(item.getRol());
            toSave.add(e);
        }

        // debug rápido para entender por qué podría estar vacío
        System.err.println("createEquipoMedico - req.size=" + (req == null ? 0 : req.size())
                + " personalIds=" + personalIds
                + " toSave.size=" + toSave.size());

        if (toSave.isEmpty()) {
            return java.util.Collections.emptyList();
        }

        // guardar en batch y mapear resultado a DTOs
        List<EquipoMedico> saved = equipoMedicoRepository.saveAll(toSave);
        // opcional: equipoMedicoRepository.flush(); // si tu repo extiende JpaRepository y quieres asegurar flush

        return saved.stream().map(e -> {
            MiembroEquipoMedicoDto dto = modelMapper.map(e, MiembroEquipoMedicoDto.class);
            // asegurar campos explícitos
            dto.setRol(e.getRol());
            if (e.getCirugia() != null) dto.setCirugiaId(e.getCirugia().getId());
            dto.setFechaAsignacion(e.getFechaAsignacion());

            // agregar info del personal (objeto) usando personalMap
            Personal p = e.getPersonal();
            if (p != null) {
                PersonalDto pDto = modelMapper.map(p, PersonalDto.class);
                dto.setPersonal(pDto);
            }
            return dto;
        }).collect(Collectors.toList());
    }
}