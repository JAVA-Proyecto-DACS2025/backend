package com.dacs.backend.service;

import com.dacs.backend.model.entity.Cirugia;
import com.dacs.backend.dto.CirugiaRequestDto;
import com.dacs.backend.dto.CirugiaResponseDTO;

public interface CirugiaService extends CommonService<Cirugia> {
    CirugiaResponseDTO create(CirugiaRequestDto request);
}
