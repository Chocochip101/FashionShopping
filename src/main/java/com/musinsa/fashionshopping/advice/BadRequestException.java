package com.musinsa.fashionshopping.advice;

public class BadRequestException extends BusinessException {

    public BadRequestException(String message, String failureValue) {
        super(message, failureValue);
    }
}
