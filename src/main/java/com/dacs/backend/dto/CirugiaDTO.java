package com.dacs.backend.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class CirugiaDTO {

    @Data
    static public class Response {
        private Long id;
        private String servicio;
        private String prioridad;
        private LocalDateTime fecha_hora_inicio;
        private String estado;
        private String anestesia;
        private String tipo;
        private PacienteDTO.Response paciente;
        private QuirofanoDto quirofano;
    }

    @Data
    static public class Create {
        private String servicio;
        private String prioridad;
        private LocalDateTime fecha_hora_inicio;
        private String estado;
        private String anestesia;
        private String tipo;
        private Long paciente;
        private Long quirofano;
    }

    @Data
    static public class Update extends Create {
        private Long id;
    }
}
