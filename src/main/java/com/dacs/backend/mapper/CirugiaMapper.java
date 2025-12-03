package com.dacs.backend.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.modelmapper.ModelMapper;

import com.dacs.backend.dto.CirugiaRequestDto;
import com.dacs.backend.dto.CirugiaResponseDTO;
import com.dacs.backend.dto.PacienteDTO;
import com.dacs.backend.dto.QuirofanoDto;
import com.dacs.backend.model.entity.Cirugia;
import com.dacs.backend.model.entity.Paciente;
import com.dacs.backend.model.entity.Quirofano;
import com.dacs.backend.model.repository.PacienteRepository;
import com.dacs.backend.model.repository.QuirofanoRepository;

@Component
public class CirugiaMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private QuirofanoRepository quirofanoRepository;

    public Cirugia toEntity(CirugiaRequestDto dto) {
        Cirugia entity = new Cirugia();
        // mapear campos simples
        entity.setServicio(dto.getServicio());
        entity.setPrioridad(dto.getPrioridad());
        entity.setFecha_hora_inicio(dto.getFecha_hora_inicio());
        entity.setEstado(dto.getEstado());
        entity.setAnestesia(dto.getAnestesia());
        entity.setTipo(dto.getTipo());

        // resolver relaciones por id (si vienen)
        if (dto.getPaciente() != null) {
            Paciente p = pacienteRepository.findById(dto.getPaciente())
                    .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado id=" + dto.getPaciente()));
            entity.setPaciente(p);
        }
        if (dto.getQuirofano() != null) {
            Quirofano q = quirofanoRepository.findById(dto.getQuirofano())
                    .orElseThrow(() -> new IllegalArgumentException("Quirofano no encontrado id=" + dto.getQuirofano()));
            entity.setQuirofano(q);
        }
        return entity;
    }

    public CirugiaResponseDTO toResponseDto(Cirugia entity) {
        CirugiaResponseDTO dto = modelMapper.map(entity, CirugiaResponseDTO.class);
        if (entity.getPaciente() != null) {
            PacienteDTO pDto = modelMapper.map(entity.getPaciente(), PacienteDTO.class);
            dto.setPaciente(pDto);
        }
        if (entity.getQuirofano() != null) {
            QuirofanoDto qDto = modelMapper.map(entity.getQuirofano(), QuirofanoDto.class);
            dto.setQuirofano(qDto);
        }
        return dto;
    }
}