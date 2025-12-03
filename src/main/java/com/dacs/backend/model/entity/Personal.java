package com.dacs.backend.model.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Personal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String legajo;

    @Column(length = 100, nullable = false)
    private String nombre;

    @Column(length = 15, nullable = false)
    private String dni;

    @Column(length = 100, nullable = false)
    private String especialidad;

    @Column(length = 100, nullable = false)
    private String rol;

    @Column(length = 100, nullable = false)
    private String estado;

    @Column(length = 15, nullable = false)
    private String telefono;
}
