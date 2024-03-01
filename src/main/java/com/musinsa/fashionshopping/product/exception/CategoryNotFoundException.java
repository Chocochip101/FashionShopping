package com.musinsa.fashionshopping.product.exception;

import com.musinsa.fashionshopping.advice.NotFoundException;

public class CategoryNotFoundException extends NotFoundException {

    private static final String MESSAGE = "카테고리가 존재하지 않습니다.";

    public CategoryNotFoundException() {
        super(MESSAGE);
    }
}
