package com.dacs.backend.model.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dacs.backend.model.entity.Paciente;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    
    List<Paciente> findByDni(String dni);
    
    @Query("SELECT p FROM Paciente p WHERE " +
           "LOWER(p.nombre) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Paciente> findBySearch(@Param("search") String search, Pageable pageable);
}