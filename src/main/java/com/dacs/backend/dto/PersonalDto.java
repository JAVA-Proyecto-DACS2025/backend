package com.dacs.backend.dto;

import lombok.Data;

@Data
public class PersonalDto {

    @Data
    public static class Response {
        Long id;
        String legajo;
        String nombre;
        String apellido;
        String dni;
        String especialidad;
        String rol;
        String estado;
        String telefono;
    }

    @Data
    public static class Create {
        String legajo;
        String nombre;
        String apellido;
        String especialidad;
        String dni;
        String rol;
        String estado;
        String telefono;
    }

    @Data
    public static class Update {
        Long id;
        String legajo;
        String nombre;
        String apellido;
        String especialidad;
        String dni;
        String rol;
        String estado;
        String telefono;
    }
}
