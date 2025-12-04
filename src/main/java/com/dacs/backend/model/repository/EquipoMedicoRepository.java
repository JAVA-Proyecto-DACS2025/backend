package com.dacs.backend.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dacs.backend.model.entity.EquipoMedico;

public interface EquipoMedicoRepository extends JpaRepository<EquipoMedico, Long> {
    List<EquipoMedico> findByCirugiaId(Long cirugiaId);
    // permite borrar todos los miembros de una cirugía en una sola llamada
    void deleteByCirugiaId(Long cirugiaId);
}
