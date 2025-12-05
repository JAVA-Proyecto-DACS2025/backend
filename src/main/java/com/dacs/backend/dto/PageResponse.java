package com.dacs.backend.dto;

import java.util.List;

import lombok.Data;

@Data
public class PageResponse<T> {
    private List<T> content;
    private long totalElements;
    private int totalPages;
    private int number;
    private int size;
}
