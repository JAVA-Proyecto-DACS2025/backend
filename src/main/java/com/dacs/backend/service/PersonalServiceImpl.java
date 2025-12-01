package com.dacs.backend.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dacs.backend.dto.PersonalRequestDto;
import com.dacs.backend.dto.PersonalResponseDto;
import com.dacs.backend.model.entity.Personal;
import com.dacs.backend.model.repository.PersonalRepository;

import jakarta.transaction.Transactional;

@Service
public class PersonalServiceImpl implements PersonalService {

    @Autowired
    private PersonalRepository personalRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public PersonalResponseDto create(PersonalRequestDto request) {
        Personal entity = modelMapper.map(request, Personal.class);
        Personal saved = personalRepository.save(entity);
        return modelMapper.map(saved, PersonalResponseDto.class);
    }

    @Override
    @Transactional
    public PersonalResponseDto update(Long id, PersonalRequestDto request) {
        Personal existingPersonal = personalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Personal not found with id: " + id));

        modelMapper.map(request, existingPersonal);
        Personal updatedPersonal = personalRepository.save(existingPersonal);
        return modelMapper.map(updatedPersonal, PersonalResponseDto.class);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Personal existingPersonal = personalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Personal not found with id: " + id));
        personalRepository.delete(existingPersonal);
    }
}