package com.musinsa.fashionshopping.brand.exception;

import com.musinsa.fashionshopping.advice.BadRequestException;
import com.musinsa.fashionshopping.brand.domain.BrandName;

public class InvalidBrandNameException extends BadRequestException {

    private static final String MESSAGE = "브랜드 이름은 %s자 이상, %s자 이하의 글자로 입력할 수 있습니다.";

    public InvalidBrandNameException(final String failureValue) {
        super(String.format(MESSAGE, BrandName.MIN_LENGTH, BrandName.MAX_LENGTH), failureValue);
    }
}
