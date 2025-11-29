package com.dacs.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dacs.backend.dto.CirugiaResponseDTO;
import com.dacs.backend.dto.CirugiaRequestDto;
import com.dacs.backend.dto.PacienteDTO;
import com.dacs.backend.service.CirugiaService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.dacs.backend.dto.PageResponse;
import com.dacs.backend.model.entity.Cirugia;


import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/cirugia")
public class CirugiaController {

    @Autowired
    private CirugiaService cirugiaService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private com.dacs.backend.model.repository.CirugiaRepository cirugiaRepository;

    @Autowired
    private com.dacs.backend.model.repository.PacienteRepository pacienteRepository;

    @GetMapping("")
    public PageResponse<CirugiaResponseDTO> list(@RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "16") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Cirugia> p = cirugiaRepository.findAll(pageable);

        // Map entities -> DTOs (without paciente yet)
        List<Cirugia> entidades = p.getContent();
        List<CirugiaResponseDTO> dtos = entidades.stream()
                .map(e -> modelMapper.map(e, CirugiaResponseDTO.class))
                .collect(Collectors.toList());

        // Recolectar pacienteIds de las cirugías
        List<Long> pacienteIds = entidades.stream()
                .map(Cirugia::getPaciente)
                .filter(Objects::nonNull)
                .map(ent -> ent.getId())
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        if (!pacienteIds.isEmpty()) {
            // Cargar pacientes en batch y mapear a DTOs
            List<com.dacs.backend.model.entity.Paciente> pacientes = pacienteRepository.findAllById(pacienteIds);
            Map<Long, com.dacs.backend.model.entity.Paciente> pacientesMap = pacientes.stream()
                    .collect(Collectors.toMap(com.dacs.backend.model.entity.Paciente::getId, pc -> pc));

            for (int i = 0; i < entidades.size(); i++) {
                var entidad = entidades.get(i);
                var dto = dtos.get(i);
                if (entidad.getPaciente() != null && entidad.getPaciente().getId() != null) {
                    var pacienteEntity = pacientesMap.get(entidad.getPaciente().getId());
                    if (pacienteEntity != null) {
                        var pacienteDto = modelMapper.map(pacienteEntity, PacienteDTO.class);
                        dto.setPaciente(pacienteDto); // requiere que CirugiaDTO tenga setPaciente(PacienteDto)
                    }
                }
            }
        }

        PageResponse<CirugiaResponseDTO> resp = new PageResponse<>();
        resp.setContent(dtos);
        resp.setNumber(p.getNumber());
        resp.setSize(p.getSize());
        resp.setTotalElements(p.getTotalElements());
        resp.setTotalPages(p.getTotalPages());
        return resp;
    }

    @PostMapping("")
    public CirugiaResponseDTO save(@RequestBody CirugiaRequestDto cirugiaRequestDto) {
        // delegar al servicio que resuelve relaciones y retorna el DTO de respuesta
        return cirugiaService.create(cirugiaRequestDto);
    }
}
