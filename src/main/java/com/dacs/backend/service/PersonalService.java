package com.dacs.backend.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.dacs.backend.dto.PersonalRequestDto;
import com.dacs.backend.dto.PersonalDto;

public interface PersonalService {
    PersonalDto create(PersonalRequestDto request);

    PersonalDto update(Long id, PersonalRequestDto request);

    void delete(Long id);

    List<PersonalDto> searchByNombreOrDni(String param);


}
