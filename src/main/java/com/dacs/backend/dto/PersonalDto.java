package com.dacs.backend.dto;

import lombok.Data;

@Data
public class PersonalDto {
    Long id;
    String legajo;
    String nombre;
    String especialidad;
    String rol;
    String estado;
    String telefono;

    @Data
    public static class Lite {
        Long id;
        String legajo;
        String rol;
        String nombre;
    }
}
