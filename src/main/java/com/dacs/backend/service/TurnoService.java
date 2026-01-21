package com.dacs.backend.service;

import java.time.LocalDate;
import java.util.List;

import com.dacs.backend.model.entity.Turno;


public interface TurnoService  {

    List<Turno> getTurnosDisponibles(int pagi, int size, LocalDate fechaInicio, LocalDate fechaFin, Long quirofanoId);

    Boolean verificarDisponibilidadTurno(Long quirofanoId, LocalDate fechaHoraInicio, LocalDate fechaHoraFin);

    Turno asignarTurno(Long cirugiaId, Long quirofanoId, LocalDate fechaHoraInicio, LocalDate fechaHoraFin);

    void borrarTurno(Long cirugiaId);
}
