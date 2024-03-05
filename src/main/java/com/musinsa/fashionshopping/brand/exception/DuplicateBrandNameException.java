package com.musinsa.fashionshopping.brand.exception;

import com.musinsa.fashionshopping.advice.BadRequestException;

public class DuplicateBrandNameException extends BadRequestException {

    private static final String MESSAGE = "이미 사용 중인 브랜드 이름입니다. 다른 이름을 입력해주세요.";

    public DuplicateBrandNameException(final String failureValue) {
        super(MESSAGE, failureValue);
    }
}
