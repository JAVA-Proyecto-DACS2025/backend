package com.dacs.backend.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dacs.backend.model.entity.Paciente;
import com.dacs.backend.model.repository.PacienteRepository;

@Service
public class PacienteServiceImpl implements PacienteService {

    @Autowired PacienteRepository pacienteRepository;

    @Override
    public Optional<Paciente> getById(Long id) {
        return pacienteRepository.findById(id);
    }

    @Override
    public Paciente save(Paciente entity) {
        return pacienteRepository.save(entity);
    }

    @Override
    public Boolean existById(Long id) {
        return pacienteRepository.existsById(id);
    }

    @Override
    public java.util.List<Paciente> getAll() {
        return pacienteRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        Optional<Paciente> paciente = getById(id);
        pacienteRepository.delete(paciente.get());
    }

    @Override
    public java.util.List<Paciente> find(Map<String, Object> filter) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public Paciente getBy(Map<String, Object> filter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public java.util.List<Paciente> getByDni(String dni) {
        return pacienteRepository.findByDni(dni);
    }
}
