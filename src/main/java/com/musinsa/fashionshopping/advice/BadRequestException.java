package com.musinsa.fashionshopping.advice;

public class BadRequestException extends BusinessException {

    public BadRequestException(final String message, final String failureValue) {
        super(message, failureValue);
    }
}
