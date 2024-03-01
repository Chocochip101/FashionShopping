package com.musinsa.fashionshopping.product.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

import com.musinsa.fashionshopping.ControllerTest;
import com.musinsa.fashionshopping.brand.exception.BrandNotFoundException;
import com.musinsa.fashionshopping.product.controller.dto.NewProductRequest;
import com.musinsa.fashionshopping.product.exception.CategoryNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

class ProductControllerTest extends ControllerTest {
    @DisplayName("특정 브랜드의 상품을 생성하면 201을 반환한다.")
    @Test
    void addProduct() {
        //given
        Long brandId = 1L;
        NewProductRequest newProductRequest = new NewProductRequest(10_000L, "TOP");

        //when
        doNothing()
                .when(productService)
                .createProduct(brandId, newProductRequest);

        //then
        restDocs
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(newProductRequest)
                .when().post("/brands/{id}/products", brandId)
                .then().log().all()
                .apply(document("products/create/success"))
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("존재하지 않는 브랜드의 상품 생성 시 404 반환한다.")
    @Test
    void addProduct_Exception_InvalidBrand() {
        //given
        Long invalidBrandId = 3L;
        NewProductRequest newProductRequest = new NewProductRequest(10_000L, "TOP");

        //when
        doThrow(new BrandNotFoundException())
                .when(productService)
                .createProduct(invalidBrandId, newProductRequest);

        //then
        restDocs
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(newProductRequest)
                .when().post("/brands/{id}/products", invalidBrandId)
                .then().log().all()
                .apply(document("products/create/fail/invalidBrandId"))
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @DisplayName("존재하지 않는 카테고리로 생성 시 404을 반환한다.")
    @Test
    void addProduct_Exception_InvalidCategory() {
        //given
        Long brandId = 1L;
        Long price = 10_000L;
        String invalidCategory = "MIDDLE";
        NewProductRequest newProductRequest = new NewProductRequest(price, invalidCategory);

        //when
        doThrow(new CategoryNotFoundException())
                .when(productService)
                .createProduct(brandId, newProductRequest);

        //then
        restDocs
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(newProductRequest)
                .when().post("/brands/{id}/products", brandId)
                .then().log().all()
                .apply(document("products/create/fail/invalidCategory"))
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @DisplayName("상품 생성 요청에 빈 카테고리에 대해 400을 반환한다.")
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "  "})
    void addProduct_Exception_NoCategory(String invalidCategory) {
        //given
        Long brandId = 1L;
        Long price = 10_000L;
        NewProductRequest newProductRequest = new NewProductRequest(price, invalidCategory);

        //when & then
        restDocs
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(newProductRequest)
                .when().post("/brands/{id}/products", brandId)
                .then().log().all()
                .apply(document("products/create/noCategory"))
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("상품 생성 요청에 빈 가격에 대해 400을 반환한다.")
    @Test
    void addProduct_Exception_InvalidPrice() {
        //given
        Long brandId = 1L;
        String category = "TOP";
        NewProductRequest newProductRequest = new NewProductRequest(null, category);

        //when & then
        restDocs
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(newProductRequest)
                .when().post("/brands/{id}/products", brandId)
                .then().log().all()
                .apply(document("products/create/invalidPrice"))
                .statusCode(HttpStatus.BAD_REQUEST.value());

    }
}
