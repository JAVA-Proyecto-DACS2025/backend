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
import java.util.stream.Collectors;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping(value = "/pacient")
public class PacienteController {

	@Autowired
	private PacienteService pacienteService;

	@Autowired
	private ModelMapper modelMapper;

        @GetMapping("")
        public ResponseEntity<List<PacienteDTO>> getAll(@RequestParam(name = "search", required = false) String search) {
        List<Paciente> data = pacienteService.getAll();
        if (search != null && !search.isBlank()) {
            String s = search.toLowerCase();
            data = data.stream()
                .filter(p -> (p.getNombre() != null && p.getNombre().toLowerCase().contains(s))
                    || (p.getDni() != null && p.getDni().toLowerCase().contains(s)))
                .collect(Collectors.toList());
        }
        List<PacienteDTO> dtoList = data.stream()
            .map(p -> modelMapper.map(p, PacienteDTO.class))
            .collect(Collectors.toList());
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
        }

    @PostMapping("")
    public ResponseEntity<PacienteDTO> create(@RequestBody PacienteDTO PacienteDTO) {
        Paciente paciente = modelMapper.map(PacienteDTO, Paciente.class);
        PacienteDTO data = modelMapper.map(pacienteService.save(paciente), PacienteDTO.class);
        return new ResponseEntity<PacienteDTO>(data, HttpStatus.OK);
    }
}
