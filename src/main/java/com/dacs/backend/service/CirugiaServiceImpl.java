package com.dacs.backend.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dacs.backend.dto.CirugiaRequestDto;
import com.dacs.backend.dto.CirugiaResponseDTO;
import com.dacs.backend.mapper.CirugiaMapper;
import com.dacs.backend.model.entity.Cirugia;
import com.dacs.backend.model.repository.CirugiaRepository;

@Service
public class CirugiaServiceImpl implements CirugiaService {

    @Autowired
    CirugiaRepository cirugiaRepository;
    @Autowired
    private CirugiaMapper cirugiaMapper;

    @Override
    public Optional<Cirugia> getById(Long id) {
        return cirugiaRepository.findById(id);
    }

    @Override
    @Transactional
    public CirugiaResponseDTO create(CirugiaRequestDto request) {
        // mapear request -> entidad (resuelve relaciones dentro del mapper)
        Cirugia entity = cirugiaMapper.toEntity(request);
        Cirugia saved = cirugiaRepository.save(entity);
        // mapear entidad -> response DTO
        return cirugiaMapper.toResponseDto(saved);
    }

    @Override
    public Cirugia save(Cirugia entity) {
        return cirugiaRepository.save(entity);
    }

    @Override
    public Boolean existById(Long id) {
        return cirugiaRepository.existsById(id);
    }

    @Override
    public java.util.List<Cirugia> getAll() {
        return cirugiaRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        Optional<Cirugia> cirugia = getById(id);
        cirugiaRepository.delete(cirugia.get());
    }

    @Override
    public List<Cirugia> find(java.util.Map<String, Object> filter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Cirugia getBy(Map<String, Object> filter) {
        throw new UnsupportedOperationException();
    }
}
