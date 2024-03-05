package com.musinsa.fashionshopping.advice;

public class NotFoundException extends BusinessException {

    public NotFoundException(final String message, final String failureValue) {
        super(message, failureValue);
    }

    public NotFoundException(final String message, final Long failureValue) {
        super(message, String.valueOf(failureValue));
    }
}
