package com.dacs.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dacs.backend.dto.CirugiaDTO;
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

import com.dacs.backend.dto.CirugiaPageResponse;
import com.dacs.backend.model.entity.Cirugia;



@RestController
@RequestMapping(value = "/cirugia")
public class CirugiaController {

    @Autowired
    private CirugiaService cirugiaService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private com.dacs.backend.model.repository.CirugiaRepository cirugiaRepository;

    @GetMapping("")
    public CirugiaPageResponse list(@RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "16") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Cirugia> p = cirugiaRepository.findAll(pageable);
        CirugiaPageResponse resp = new CirugiaPageResponse();
        resp.setContent(p.getContent().stream().map(e -> modelMapper.map(e, CirugiaDTO.class)).toList());
        resp.setNumber(p.getNumber());
        resp.setSize(p.getSize());
        resp.setTotalElements(p.getTotalElements());
        resp.setTotalPages(p.getTotalPages());
        return resp;
    }

    @PostMapping("")
    public CirugiaDTO save(@RequestBody CirugiaDTO cirugiaDTO) {
        com.dacs.backend.model.entity.Cirugia toSave = modelMapper.map(cirugiaDTO, com.dacs.backend.model.entity.Cirugia.class);
        com.dacs.backend.model.entity.Cirugia saved = cirugiaService.save(toSave);
        cirugiaDTO.setId(saved.getId());
        return cirugiaDTO;
    }
}
