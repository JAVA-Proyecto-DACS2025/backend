package com.dacs.backend.dto;

import java.sql.Date;
import java.time.LocalDateTime;

import com.dacs.backend.model.entity.Personal;

import lombok.Data;

@Data
public class MiembroEquipoMedicoDto {

    private PersonalDto personal;
    private String rol;
    private Long cirugiaId;
    private LocalDateTime fechaAsignacion;

    @Data
    public static class Create {
        private Long cirugiaId;
        private Long personalId;
        private String rol;
    }
}


