package com.musinsa.fashionshopping.advice;

public class NotFoundException extends BusinessException {

    public NotFoundException(String message, String failureValue) {
        super(message, failureValue);
    }

    public NotFoundException(String message, Long failureValue) {
        super(message, String.valueOf(failureValue));
    }
}
