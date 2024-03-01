package com.musinsa.fashionshopping.product.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.musinsa.fashionshopping.brand.domain.Brand;
import com.musinsa.fashionshopping.brand.domain.BrandName;
import com.musinsa.fashionshopping.brand.exception.BrandNotFoundException;
import com.musinsa.fashionshopping.brand.repository.BrandRepository;
import com.musinsa.fashionshopping.product.controller.dto.NewProductRequest;
import com.musinsa.fashionshopping.product.controller.dto.PriceUpdateRequest;
import com.musinsa.fashionshopping.product.domain.Category;
import com.musinsa.fashionshopping.product.domain.Product;
import com.musinsa.fashionshopping.product.domain.ProductPrice;
import com.musinsa.fashionshopping.product.exception.CategoryNotFoundException;
import com.musinsa.fashionshopping.product.exception.InvalidProductPriceException;
import com.musinsa.fashionshopping.product.repository.ProductRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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
        assertThat(products.get(0).getProductPrice()).isEqualTo(new ProductPrice(price));
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

    @DisplayName("존재하지 않는 카테고리로 생성 시 예외가 발생한다.")
    @Test
    void createProduct_Exception_InvalidCategory() {
        //given
        Long price = 10_000L;
        String invalidCategory = "MIDDLE";
        NewProductRequest newProductRequest = new NewProductRequest(price, invalidCategory);

        //when & then
        assertThatThrownBy(() -> productService.createProduct(musinsa.getId(), newProductRequest))
                .isInstanceOf(CategoryNotFoundException.class);
    }

    @DisplayName("상품 금액 수정에 성공한다.")
    @Test
    void editProductPrice() {
        //given
        Long price = 10_000L;
        Category category = Category.TOP;
        Product product = Product.builder()
                .productPrice(new ProductPrice(price))
                .category(category)
                .build();

        productRepository.save(product);
        Long toChangePrice = 100_000L;
        PriceUpdateRequest priceUpdateRequest = new PriceUpdateRequest(toChangePrice);

        //when
        productService.editPrice(product.getId(), priceUpdateRequest);

        //then
        Product foundProduct = productRepository.findById(product.getId()).get();

        assertThat(foundProduct.getProductPrice()).isEqualTo(new ProductPrice(toChangePrice));
        assertThat(foundProduct.getCategory()).isEqualTo(category);
    }

    @DisplayName("범위에 벗어난 가격으로 상품 가격 수정 시 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(longs = {-1, 100_000_000_000L, 1})
    void editProductPrice_Exception_InvalidPrice(Long invalidPrice) {
        //given
        Long price = 10_000L;
        Category category = Category.TOP;
        Product product = Product.builder()
                .productPrice(new ProductPrice(price))
                .category(category)
                .build();

        productRepository.save(product);
        PriceUpdateRequest priceUpdateRequest = new PriceUpdateRequest(invalidPrice);

        //when & then
        assertThatThrownBy(() -> productService.editPrice(product.getId(), priceUpdateRequest))
                .isInstanceOf(InvalidProductPriceException.class);
    }
}
