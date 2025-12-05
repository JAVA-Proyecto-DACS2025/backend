package com.dacs.backend.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dacs.backend.dto.PersonalDto;
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
    public PersonalDto.Response create(PersonalDto.Create request) {
        Personal entity = modelMapper.map(request, Personal.class);
        Personal saved = personalRepository.save(entity);
        return modelMapper.map(saved, PersonalDto.Response.class);
    }

    @Override
    @Transactional
    public PersonalDto.Response update(Long id, PersonalDto.Update request) {
        Personal existingPersonal = personalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Personal not found with id: " + id));

        modelMapper.map(request, existingPersonal);
        Personal updatedPersonal = personalRepository.save(existingPersonal);
        return modelMapper.map(updatedPersonal, PersonalDto.Response.class);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Personal existingPersonal = personalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Personal not found with id: " + id));
        personalRepository.delete(existingPersonal);
    }

    @Override
    public List<PersonalDto.Response> searchByNombreOrDni(String param) {
        List<Personal> personals = personalRepository.findByNombreContainingIgnoreCaseOrDniContainingIgnoreCase(param, param);
        return personals.stream()
                .map(p -> modelMapper.map(p, PersonalDto.Response.class))
                .collect(java.util.stream.Collectors.toList());
    }
}