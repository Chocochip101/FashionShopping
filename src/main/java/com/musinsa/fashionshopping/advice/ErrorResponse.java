package com.musinsa.fashionshopping.advice;

import lombok.Getter;

@Getter
public class ErrorResponse {

    private final String message;
    private final String failureValue;

    public ErrorResponse(String message, String failureValue) {
        this.message = message;
        this.failureValue = failureValue;
    }
}
