package com.musinsa.fashionshopping.brand.exception;

import com.musinsa.fashionshopping.advice.BadRequestException;

public class ProductInsufficientException extends BadRequestException {

    private static final String MESSAGE = "상품 부족으로 최저 가격의 브랜드를 계산할 수 없습니다.";

    public ProductInsufficientException() {
        super(MESSAGE);
    }
}
