package com.dacs.backend.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dacs.backend.dto.PacienteDTO;
import com.dacs.backend.model.entity.Paciente;
import com.dacs.backend.service.PacienteService;

@RestController
@RequestMapping(value = "/paciente")
public class PacienteController {

	@Autowired
	private PacienteService pacienteService;

	@Autowired
	private ModelMapper modelMapper;

    @PostMapping("")
    public ResponseEntity<PacienteDTO> create(@RequestBody PacienteDTO PacienteDTO) {
        Paciente paciente = modelMapper.map(PacienteDTO, Paciente.class);
        PacienteDTO data = modelMapper.map(pacienteService.save(paciente), PacienteDTO.class);
        return new ResponseEntity<PacienteDTO>(data, HttpStatus.OK);
    }
}
