package com.interviewprep.orderflow_lite.exception;

import org.springframework.http.HttpStatus;

public class DuplicateResourceException extends BaseBusinessException {

    public DuplicateResourceException(String resourceName, String fieldName, Object value) {
        super(
                "DUPLICATE_RESOURCE",
                resourceName + " already exists with " + fieldName + ": " + value,
                HttpStatus.CONFLICT
        );
    }
}
