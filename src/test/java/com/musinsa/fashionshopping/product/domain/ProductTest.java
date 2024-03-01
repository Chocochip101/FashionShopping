package com.musinsa.fashionshopping.product.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.musinsa.fashionshopping.brand.domain.Brand;
import com.musinsa.fashionshopping.brand.domain.BrandName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTest {
    @DisplayName("빌더 패턴을 사용하면, 상품을 생성할 수 있다.")
    @Test
    void createProduct() {
        //given
        Long id = 1L;
        Long price = 10_000L;
        Category category = Category.TOP;
        Brand brand = Brand.builder()
                .id(id)
                .brandName(new BrandName("nike"))
                .build();

        //when
        Product product = Product.builder()
                .id(id)
                .productPrice(new ProductPrice(price))
                .category(category)
                .brand(brand)
                .build();

        //then
        assertThat(product.getId()).isEqualTo(id);
        assertThat(product.getProductPrice()).isEqualTo(new ProductPrice(price));
        assertThat(product.getCategory()).isEqualTo(category);
        assertThat(product.getBrand()).isEqualTo(brand);
    }
}
