package com.musinsa.fashionshopping.fixture;

import com.musinsa.fashionshopping.brand.domain.Brand;
import com.musinsa.fashionshopping.brand.domain.BrandName;

public class BrandFixture {
    private BrandFixture() {
    }

    public static Brand createBrandA() {
        return Brand.builder()
                .brandName(new BrandName("A"))
                .build();
    }

    public static Brand createBrandB() {
        return Brand.builder()
                .brandName(new BrandName("B"))
                .build();
    }
}
