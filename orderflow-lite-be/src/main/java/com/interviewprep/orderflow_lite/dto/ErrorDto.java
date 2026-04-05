package com.interviewprep.orderflow_lite.dto;

import java.time.Instant;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorDto {
    private Instant timestamp;
    private int status;
    private String error;
    private String code;
    private String message;
    private String path;
    private String correlationId;
    private List<FieldErrorDto> fieldErrors;
}
