package com.interviewprep.orderflow_lite.exception;

import org.springframework.http.HttpStatus;

public class InsufficientStockException extends BaseBusinessException {

    public InsufficientStockException(String sku, int requested, int available) {
        super(
                "INSUFFICIENT_STOCK",
                "Not enough stock for product SKU " + sku +
                        ". Requested: " + requested +
                        ", Available: " + available,
                HttpStatus.CONFLICT
        );
    }
}
