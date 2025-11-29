package com.dacs.backend.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class CirugiaResponseDTO {

    private Long id;
    private String servicio;
    private String prioridad;
    private LocalDateTime fecha_hora_inicio;
    private String estado;
    private String anestesia;
    private String tipo;
    private PacienteDTO paciente;
    private QuirofanoDto quirofano;
}
