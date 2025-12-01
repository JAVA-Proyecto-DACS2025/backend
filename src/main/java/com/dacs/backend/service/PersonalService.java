package com.dacs.backend.service;

import org.springframework.data.domain.Pageable;

import com.dacs.backend.dto.PersonalRequestDto;
import com.dacs.backend.dto.PersonalResponseDto;

public interface PersonalService {
    PersonalResponseDto create(PersonalRequestDto request);

    PersonalResponseDto update(Long id, PersonalRequestDto request);

    void delete(Long id);
}
