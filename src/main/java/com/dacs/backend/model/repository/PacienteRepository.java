package com.dacs.backend.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dacs.backend.model.entity.Paciente;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long>{

    List<Paciente> findByDni(String dni);
}