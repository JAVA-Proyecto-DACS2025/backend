package com.dacs.backend.service;

import java.util.List;

import com.dacs.backend.dto.PacienteDTO;
import com.dacs.backend.model.entity.Paciente;

public interface PacienteService extends CommonService<Paciente>{

    List<Paciente> getByDni(String dni);

    List<PacienteDTO> getPacientesByIds(List<Long> ids);
}
