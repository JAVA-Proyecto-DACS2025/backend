package com.dacs.backend.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CirugiaRequestDto {
    private String servicio;
    private String prioridad;
    private LocalDateTime fecha_hora_inicio;
    private String estado;
    private String anestesia;
    private String tipo;
    private Long paciente;
    private Long quirofano;
}

class CirugiaUpdateDTO extends CirugiaRequestDto {
    private Long id;
}