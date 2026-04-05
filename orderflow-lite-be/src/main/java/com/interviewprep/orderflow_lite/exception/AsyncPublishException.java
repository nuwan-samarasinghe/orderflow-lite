package com.interviewprep.orderflow_lite.exception;

import org.springframework.http.HttpStatus;

public class AsyncPublishException extends BaseBusinessException {

    public AsyncPublishException(String eventType, Throwable cause) {
        super(
                "ASYNC_PUBLISH_FAILED",
                "Failed to publish async event: " + eventType,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
        initCause(cause);
    }
}
