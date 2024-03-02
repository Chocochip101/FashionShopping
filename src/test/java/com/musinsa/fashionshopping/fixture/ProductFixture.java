package com.musinsa.fashionshopping.fixture;

import static com.musinsa.fashionshopping.fixture.BrandFixture.createBrandA;
import static com.musinsa.fashionshopping.fixture.BrandFixture.createBrandB;

import com.musinsa.fashionshopping.product.domain.Category;
import com.musinsa.fashionshopping.product.domain.Product;
import com.musinsa.fashionshopping.product.domain.ProductPrice;

public class ProductFixture {
    private ProductFixture() {
    }

    public static Product createProductATop() {
        return Product.builder()
                .productPrice(new ProductPrice(11_200L))
                .category(Category.TOP)
                .brand(createBrandA())
                .build();

    }

    public static Product createProductBTop() {
        return Product.builder()
                .productPrice(new ProductPrice(10_500L))
                .category(Category.TOP)
                .brand(createBrandB())
                .build();
    }

    public static Product createProductAPants() {
        return Product.builder()
                .productPrice(new ProductPrice(100_000L))
                .category(Category.PANTS)
                .brand(createBrandA())
                .build();
    }
}
