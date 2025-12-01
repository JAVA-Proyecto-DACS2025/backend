package com.dacs.backend.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.query.Page;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable; 
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dacs.backend.dto.PageResponse;
import com.dacs.backend.dto.PersonalRequestDto;
import com.dacs.backend.dto.PersonalResponseDto;
import com.dacs.backend.model.entity.Personal;
import com.dacs.backend.model.repository.PersonalRepository;
import com.dacs.backend.service.PersonalService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping(value = "/personal")
public class PersonalController {

    @Autowired
    private PersonalService personalService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PersonalRepository personalRepository;

    @GetMapping("")
    public PageResponse<PersonalResponseDto> getPage(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "16") int size) {
        Pageable pageable = PageRequest.of(page, size);
        org.springframework.data.domain.Page<Personal> p = personalRepository.findAll(pageable);
        
        // Map entities -> DTOs     //Implementar en un helper
        List<PersonalResponseDto> dtos = p.getContent().stream()
                .map(e -> modelMapper.map(e, PersonalResponseDto.class))
                .collect(Collectors.toList());

        PageResponse<PersonalResponseDto> resp = new PageResponse<>();
        resp.setContent(dtos);
        resp.setNumber(p.getNumber());
        resp.setSize(p.getSize());
        resp.setTotalElements(p.getTotalElements());
        resp.setTotalPages(p.getTotalPages());
        return resp;
    }

    @PostMapping("")  
    public ResponseEntity<PersonalResponseDto> create(@RequestBody PersonalRequestDto data) {
        PersonalResponseDto out = personalService.create(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(out);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<PersonalResponseDto> update(@PathVariable Long id, @RequestBody PersonalRequestDto data) {
        PersonalResponseDto out = personalService.update(id, data);
        return ResponseEntity.status(HttpStatus.OK).body(out);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        personalService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}