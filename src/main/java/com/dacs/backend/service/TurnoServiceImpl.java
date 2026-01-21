package com.dacs.backend.service;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.dacs.backend.model.entity.Turno;
import com.dacs.backend.model.entity.Cirugia;
import com.dacs.backend.model.repository.TurnoRepository;
import com.dacs.backend.model.repository.CirugiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TurnoServiceImpl implements TurnoService {

    @Autowired
    private TurnoRepository turnoRepository;

    @Autowired
    private CirugiaRepository cirugiaRepository;

    @Override
    public List<Turno> getTurnosDisponibles(int pagi, int size, LocalDate fechaInicio, LocalDate fechaFin,
            Long quirofanoId) {

        List<Turno> turnosDisponibles = new ArrayList<>();

        turnoRepository.findAllByFechaHoraInicioBetween(fechaInicio.atStartOfDay(), fechaFin.atTime(LocalTime.MAX))
                .forEach(turno -> {
                    if (turno.getEstado().equals("DISPONIBLE") && turno.getQuirofano().getId().equals(quirofanoId)) {
                        turnosDisponibles.add(turno);
                    }
                });

        return turnosDisponibles;
    }

    @Override
    public Boolean verificarDisponibilidadTurno(Long quirofanoId, LocalDate fechaHoraInicio, LocalDate fechaHoraFin) {
        List<Turno> turnos = turnoRepository.findAllByFechaHoraInicioBetween(fechaHoraInicio.atStartOfDay(),
                fechaHoraFin.atTime(LocalTime.MAX));
        for (Turno turno : turnos) {
            if (turno.getQuirofano().getId().equals(quirofanoId) && turno.getEstado().equals("DISPONIBLE")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Turno asignarTurno(Long cirugiaId, Long quirofanoId, LocalDate fechaHoraInicio, LocalDate fechaHoraFin) {
        List<Turno> turnos = turnoRepository.findAllByFechaHoraInicioBetween(fechaHoraInicio.atStartOfDay(),
                fechaHoraFin.atTime(LocalTime.MAX));
        for (Turno turno : turnos) {
            if (turno.getQuirofano().getId().equals(quirofanoId) && turno.getEstado().equals("DISPONIBLE")) {
                turno.setEstado("ASIGNADO");
                Cirugia cirugia = cirugiaRepository.findById(cirugiaId).orElse(null);
                if (cirugia != null) {
                    turno.setCirugia(cirugia);
                    return turnoRepository.save(turno);
                }
            }
        }
        return null;
    }

    @Override
    public void borrarTurno(Long cirugiaId) {
        List<Turno> turnos = turnoRepository.findAllByCirugiaId(cirugiaId);
        for (Turno turno : turnos) {
            turno.setEstado("DISPONIBLE");
            turno.setCirugia(null);
            turnoRepository.save(turno);
        }
    }
}
