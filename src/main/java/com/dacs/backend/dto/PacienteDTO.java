package com.dacs.backend.dto;

import lombok.Data;

@Data
public class PacienteDTO {

    private Long id;
    private String nombre;
    private String dni;
    private String edad;
    private String altura;
    private String peso;
    private String direccion;
    private String telefono;
}
