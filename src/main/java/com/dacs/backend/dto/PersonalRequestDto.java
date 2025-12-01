package com.dacs.backend.dto;

import io.micrometer.common.lang.Nullable;
import lombok.Data;

@Data
public class PersonalRequestDto {

    @Nullable
    Long id;

    String legajo;
    String nombre;
    String especialidad;
    String rol;
    String estado;
    String telefono;
}


