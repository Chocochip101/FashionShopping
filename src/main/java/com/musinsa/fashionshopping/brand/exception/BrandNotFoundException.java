package com.musinsa.fashionshopping.brand.exception;

import com.musinsa.fashionshopping.advice.NotFoundException;

public class BrandNotFoundException extends NotFoundException {

    private static final String MESSAGE = "해당 브랜드를 찾을 수 없습니다.";

    public BrandNotFoundException(final String failureValue) {
        super(MESSAGE, failureValue);
    }

    public BrandNotFoundException(final Long failureValue) {
        super(MESSAGE, failureValue);
    }
}
