package com.dacs.backend.dto;

import java.time.LocalDateTime;


import lombok.Data;

@Data
public class MiembroEquipoMedicoDto {

    @Data
    public static class Response {
    private PersonalDto.Response personal;
    private String rol;
    private Long cirugiaId;
    private LocalDateTime fechaAsignacion;
    }

    @Data
    public static class Create {
        private Long cirugiaId;
        private Long personalId;
        private String rol;
    }
}


