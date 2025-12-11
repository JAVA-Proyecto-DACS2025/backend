package com.dacs.backend.model.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String nombre;

    @Column(length = 50, nullable = false)
    private String apellido;
    
    @Column(length = 25, nullable = false)
    private String dni;

    @Column(length = 15, nullable = false)
    private Date fecha_nacimiento;

    @Column(length = 10, nullable = false)
    private String altura;

    @Column(length = 10, nullable = false)
    private String peso;

    @Column(length = 100, nullable = false)
    private String direccion;

    @Column(length = 20, nullable = false)
    private String telefono;
}
