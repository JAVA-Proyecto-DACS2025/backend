package com.dacs.backend.dto;
import lombok.Data;

@Data
public class PersonalDto {

    @Data
    public static class Response {
        Long id;
        String legajo;
        String nombre;
        String dni;
        String especialidad;
        String rol;
        String estado;
        String telefono;
    }

    @Data         //Borrar, esto l ogestiona el bff
    public static class Lite {
        Long id;
        String legajo;
        String rol;
        String nombre;
    }

    @Data
    public static class Create {
        String legajo;
        String nombre;
        String especialidad;
        String dni;
        String rol;
        String estado;
        String telefono;
    }

    @Data
    public static class Update extends Create {
        Long id;
    }

}
