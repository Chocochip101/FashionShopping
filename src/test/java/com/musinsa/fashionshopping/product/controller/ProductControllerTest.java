package com.musinsa.fashionshopping.product.controller;

import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

import com.musinsa.fashionshopping.ControllerTest;
import com.musinsa.fashionshopping.product.controller.dto.NewProductRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
}