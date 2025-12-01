package com.dacs.backend.dto;

import lombok.Data;

@Data
public class PersonalResponseDto {
    int id;
    String legajo;
    String nombre;
    String especialidad;
    String rol;
    String estado;
    String telefono;
}
