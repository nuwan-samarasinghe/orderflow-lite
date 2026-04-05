package com.interviewprep.orderflow_lite.exception;
import org.springframework.http.HttpStatus;

public class InvalidOrderStateException extends BaseBusinessException {

    public InvalidOrderStateException(String currentStatus, String targetAction) {
        super(
                "INVALID_ORDER_STATE",
                "Cannot perform action '" + targetAction + "' when order is in status '" + currentStatus + "'",
                HttpStatus.UNPROCESSABLE_CONTENT
        );
    }
}
