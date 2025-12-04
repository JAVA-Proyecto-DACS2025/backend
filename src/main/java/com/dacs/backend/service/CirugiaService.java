package com.dacs.backend.service;

import com.dacs.backend.model.entity.Cirugia;
import com.dacs.backend.model.entity.EquipoMedico;

import java.util.List;

import com.dacs.backend.dto.CirugiaRequestDto;
import com.dacs.backend.dto.CirugiaResponseDTO;
import com.dacs.backend.dto.MiembroEquipoMedicoDto;

public interface CirugiaService extends CommonService<Cirugia> {
    CirugiaResponseDTO create(CirugiaRequestDto request);

    List<MiembroEquipoMedicoDto> saveEquipoMedico(Long id, List<MiembroEquipoMedicoDto.Create> entity);

    List<MiembroEquipoMedicoDto> getEquipoMedico(Long cirugiaId);

}
