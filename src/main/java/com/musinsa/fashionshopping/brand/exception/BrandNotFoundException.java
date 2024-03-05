package com.musinsa.fashionshopping.brand.exception;

import com.musinsa.fashionshopping.advice.NotFoundException;

public class BrandNotFoundException extends NotFoundException {

    private static final String MESSAGE = "브랜드가 존재하지 않습니다.";

    public BrandNotFoundException(final String failureValue) {
        super(MESSAGE, failureValue);
    }

    public BrandNotFoundException(final Long failureValue) {
        super(MESSAGE, failureValue);
    }
}
