package com.dacs.backend.model.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class EquipoMedico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación hacia la cirugía
    @ManyToOne
    @JoinColumn(name = "id_cirugia", nullable = false)
    private Cirugia cirugia;

    // Relación hacia el personal
    @ManyToOne
    @JoinColumn(name = "id_personal", nullable = false)
    private Personal personal;  

    @CreationTimestamp
    private LocalDateTime fechaAsignacion;

    private String rol;
}
  