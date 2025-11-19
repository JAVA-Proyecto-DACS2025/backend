package com.dacs.backend.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dacs.backend.dto.QuirofanoDTO;
import com.dacs.backend.model.entity.Quirofano;
import com.dacs.backend.service.QuirofanoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(value = "/quirofano")
public class QuirofanoController {

    @Autowired
    private QuirofanoService quirofanoService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("")
    public ResponseEntity<QuirofanoDTO> postMethodName(@RequestBody QuirofanoDTO QuirofanoDTO) {
        Quirofano quirofano = modelMapper.map(QuirofanoDTO, Quirofano.class);
        QuirofanoDTO data = modelMapper.map(quirofanoService.save(quirofano), QuirofanoDTO.class);
        return new ResponseEntity<QuirofanoDTO>(data, HttpStatus.OK);
    }
}
