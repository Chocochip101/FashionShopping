package com.musinsa.fashionshopping.product.exception;

import com.musinsa.fashionshopping.advice.BadRequestException;

public class InvalidProductPriceException extends BadRequestException {

    private static final String MESSAGE = "잘못된 가격 형식입니다.";

    public InvalidProductPriceException() {
        super(MESSAGE);
    }
}
