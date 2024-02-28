package com.musinsa.fashionshopping.brand.exception;

import com.musinsa.fashionshopping.advice.BadRequestException;

public class InvalidBrandNameException extends BadRequestException {

    private static final String MESSAGE = "잘못된 이름 형식입니다.";

    public InvalidBrandNameException() {
        super(MESSAGE);
    }
}
