package com.musinsa.fashionshopping.product.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.musinsa.fashionshopping.brand.domain.Brand;
import com.musinsa.fashionshopping.brand.domain.BrandName;
import com.musinsa.fashionshopping.product.domain.Category;
import com.musinsa.fashionshopping.product.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ProductRepositoryTest {
    @Autowired
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
    }

    @DisplayName("id가 없는 Product 엔티티를 저장하면 순차적으로 ID를 부여하여 저장한다.")
    @Test
    void saveNoIdProduct() {
        //given
        Brand brand = Brand.builder()
                .brandName(new BrandName("nike"))
                .build();

        Product product1 = Product.builder()
                .price(10_000L)
                .category(Category.ACCESSORY)
                .brand(brand)
                .build();

        Product product2 = Product.builder()
                .price(20_000L)
                .category(Category.TOP)
                .brand(brand)
                .build();

        //when
        productRepository.save(product1);
        productRepository.save(product2);

        //then
        long isDiff = product2.getId() - product1.getId();
        assertThat(isDiff).isEqualTo(1L);
    }

}
