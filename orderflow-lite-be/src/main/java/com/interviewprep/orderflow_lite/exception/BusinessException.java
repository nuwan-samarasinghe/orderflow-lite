package com.interviewprep.orderflow_lite.exception;

import org.springframework.http.HttpStatus;

public class BusinessException extends BaseBusinessException {

    public BusinessException(String code, String message) {
        super(code, message, HttpStatus.UNPROCESSABLE_CONTENT);
    }

    public BusinessException(String code, String message, HttpStatus status) {
        super(code, message, status);
    }
}
