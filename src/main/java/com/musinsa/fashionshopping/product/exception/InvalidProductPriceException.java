package com.musinsa.fashionshopping.product.exception;

import com.musinsa.fashionshopping.advice.BadRequestException;
import com.musinsa.fashionshopping.product.domain.ProductPrice;

public class InvalidProductPriceException extends BadRequestException {

    private static final String MESSAGE = "상품 가격은 %s 이상, %s 이하로 입력할 수 있습니다.";

    public InvalidProductPriceException(final Long failureValue) {
        super(String.format(MESSAGE, ProductPrice.MIN_PRICE, ProductPrice.MAX_PRICE), String.valueOf(failureValue));
    }
}
