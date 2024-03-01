package com.musinsa.fashionshopping.product.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.musinsa.fashionshopping.brand.domain.Brand;
import com.musinsa.fashionshopping.brand.domain.BrandName;
import com.musinsa.fashionshopping.brand.exception.BrandNotFoundException;
import com.musinsa.fashionshopping.brand.repository.BrandRepository;
import com.musinsa.fashionshopping.product.controller.dto.NewProductRequest;
import com.musinsa.fashionshopping.product.domain.Category;
import com.musinsa.fashionshopping.product.domain.Product;
import com.musinsa.fashionshopping.product.repository.ProductRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    BrandRepository brandRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductService productService;

    Brand musinsa = Brand.builder()
            .brandName(new BrandName("musinsa"))
            .build();

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
        brandRepository.deleteAll();

        brandRepository.save(musinsa);
    }

    @DisplayName("상품을 생성할 수 있다.")
    @Test
    void createProduct() {
        //given
        Long price = 10_000L;
        Category category = Category.TOP;
        NewProductRequest newProductRequest = new NewProductRequest(price, "TOP");

        //when
        productService.createProduct(musinsa.getId(), newProductRequest);
        final List<Product> products = productRepository.findAll();

        //then
        assertThat(products).isNotNull();
        assertThat(products.get(0).getPrice()).isEqualTo(price);
        assertThat(products.get(0).getCategory()).isEqualTo(category);
        assertThat(products.get(0).getBrand()).isEqualTo(musinsa);
    }

    @DisplayName("존재하지 않는 브랜드의 상품 생성 시 예외가 발생한다.")
    @Test
    void createProduct_Exception_InvalidBrand() {
        //given
        Long price = 10_000L;
        String category = "TOP";
        NewProductRequest newProductRequest = new NewProductRequest(price, category);

        //when & then
        assertThatThrownBy(() -> productService.createProduct(3L, newProductRequest))
                .isInstanceOf(BrandNotFoundException.class);
    }
}
