package com.musinsa.fashionshopping.advice;

public class BusinessException extends RuntimeException {
    private final String failureValue;

    public BusinessException(final String message, final String failureValue) {
        super(message);
        this.failureValue = failureValue;
    }

    public String getFailureValue() {
        return failureValue;
    }
}
