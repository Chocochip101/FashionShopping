package com.musinsa.fashionshopping.product.exception;

import com.musinsa.fashionshopping.advice.NotFoundException;

public class ProductNotFoundException extends NotFoundException {

    private static final String MESSAGE = "해당 상품을 찾을 수 없습니다.";

    public ProductNotFoundException(final Long failureValue) {
        super(MESSAGE, failureValue);
    }
}
