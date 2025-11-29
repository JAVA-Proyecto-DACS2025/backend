package com.dacs.backend.service;

import java.util.Map;
import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dacs.backend.dto.PacienteDTO;
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
    public List<Paciente> getAll() {
        return pacienteRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        Optional<Paciente> paciente = getById(id);
        pacienteRepository.delete(paciente.get());
    }

    @Override
    public List<Paciente> find(Map<String, Object> filter) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public Paciente getBy(Map<String, Object> filter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Paciente> getByDni(String dni) {
        return pacienteRepository.findByDni(dni);
    }

    @Override
    public List<PacienteDTO> getPacientesByIds(List<Long> ids) {
        List<Paciente> pacientes = pacienteRepository.findAllById(ids);
        List<PacienteDTO> pacientesDTO = pacientes.stream().map(p -> {
            PacienteDTO dto = new PacienteDTO();
            dto.setId(p.getId());
            dto.setNombre(p.getNombre());
            dto.setDni(p.getDni());
            dto.setEdad((p.getEdad()));
            dto.setTelefono(p.getTelefono());
            dto.setPeso(p.getPeso());
            dto.setDireccion(p.getDireccion());    
            return dto;
        }).toList();
        return pacientesDTO;
    }
}
