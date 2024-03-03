package com.musinsa.fashionshopping.product.exception;

import com.musinsa.fashionshopping.advice.NotFoundException;

public class ProductNotFoundException extends NotFoundException {

    private static final String MESSAGE = "상품이 존재하지 않습니다.";

    public ProductNotFoundException(String failureValue) {
        super(MESSAGE, failureValue);
    }

    public ProductNotFoundException(Long failureValue) {
        super(MESSAGE, failureValue);
    }
}
