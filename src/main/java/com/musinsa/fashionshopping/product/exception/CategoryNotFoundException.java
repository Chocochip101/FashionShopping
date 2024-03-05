package com.musinsa.fashionshopping.product.exception;

import com.musinsa.fashionshopping.advice.NotFoundException;

public class CategoryNotFoundException extends NotFoundException {

    private static final String MESSAGE = "해당 카테고리를 찾을 수 없습니다.";

    public CategoryNotFoundException(final String failureValue) {
        super(MESSAGE, failureValue);
    }
}
