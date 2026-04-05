package com.interviewprep.orderflow_lite.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends BaseBusinessException {

    public ResourceNotFoundException(String resourceName, Object identifier) {
        super(
                "RESOURCE_NOT_FOUND",
                resourceName + " not found with identifier: " + identifier,
                HttpStatus.NOT_FOUND
        );
    }
}
