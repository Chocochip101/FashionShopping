package com.musinsa.fashionshopping.brand.exception;

import com.musinsa.fashionshopping.advice.BadRequestException;

public class DuplicateBrandNameException extends BadRequestException {

    private static final String MESSAGE = "이미 존재하는 브랜드 이름입니다.";

    public DuplicateBrandNameException(String failureValue) {
        super(MESSAGE, failureValue);
    }
}
