package com.musinsa.fashionshopping.brand.exception;

import com.musinsa.fashionshopping.advice.BadRequestException;

public class ProductInsufficientException extends BadRequestException {

    private static final String MESSAGE = "현재 상품 수량이 부족하여 최저 가격의 브랜드를 계산할 수 없습니다. 더 많은 상품을 추가해보세요.";

    public ProductInsufficientException() {
        super(MESSAGE, null);
    }
}
