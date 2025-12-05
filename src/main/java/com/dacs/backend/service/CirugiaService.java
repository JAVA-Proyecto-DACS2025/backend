package com.dacs.backend.service;

import com.dacs.backend.model.entity.Cirugia;

import java.util.List;

import com.dacs.backend.dto.CirugiaDTO;
import com.dacs.backend.dto.MiembroEquipoMedicoDto;
import com.dacs.backend.dto.PageResponse;

public interface CirugiaService extends CommonService<Cirugia> {
    CirugiaDTO.Response create(CirugiaDTO.Create request);

    List<MiembroEquipoMedicoDto.Response> saveEquipoMedico(Long id, List<MiembroEquipoMedicoDto.Create> entity);
    
    List<MiembroEquipoMedicoDto.Response> getEquipoMedico(Long cirugiaId);

    PageResponse<CirugiaDTO.Response> get(int page, int size);

}
