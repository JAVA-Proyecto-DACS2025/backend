package com.dacs.backend.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class CirugiaDTO {

    private Long id;
    private String servicio;
    private String prioridad;
    private LocalDateTime fecha_hora_inicio;
    private String estado;
    private String anestesia;
    private String tipo;

    // Referencias a entidades relacionadas como solo IDs
    private Long pacienteId;
    private Long quirofanoId;
}