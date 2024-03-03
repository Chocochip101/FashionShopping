package com.musinsa.fashionshopping.brand.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

import com.musinsa.fashionshopping.ControllerTest;
import com.musinsa.fashionshopping.brand.controller.dto.BrandNameUpdateRequest;
import com.musinsa.fashionshopping.brand.controller.dto.NewBrandRequest;
import com.musinsa.fashionshopping.brand.exception.BrandNotFoundException;
import com.musinsa.fashionshopping.brand.exception.DuplicateBrandNameException;
import com.musinsa.fashionshopping.brand.exception.InvalidBrandNameException;
import com.musinsa.fashionshopping.brand.exception.ProductInsufficientException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

class BrandControllerTest extends ControllerTest {

    @DisplayName("브랜드를 생성하면 201을 반환한다.")
    @Test
    void createBrand() {
        //given
        NewBrandRequest newBrandRequest = new NewBrandRequest("nike");

        //when
        doNothing()
                .when(brandService)
                .createBrand(newBrandRequest);

        //then
        restDocs
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(newBrandRequest)
                .when().post("/brands")
                .then().log().all()
                .apply(document("brands/create/success"))
                .statusCode(HttpStatus.CREATED.value());
    }


    @DisplayName("브랜드 생성 요청에 빈 이름에 대해 400을 반환한다.")
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "  "})
    void createBrand_Exception_NoName(final String name) {
        //given
        NewBrandRequest newBrandRequest = new NewBrandRequest(name);

        //then
        restDocs
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(newBrandRequest)
                .when().post("/brands")
                .then().log().all()
                .apply(document("brands/create/fail"))
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("브랜드 이름 등록 시 잘못된 형식이면 400 반환한다.")
    @ParameterizedTest
    @ValueSource(strings = {"ㄱㄴㄷ", "abdf123ㅏㅇ", "11112222333344445"})
    void createBrand_Exception_InvalidFormat(final String invalidBrandName) {
        //given
        NewBrandRequest newBrandRequest = new NewBrandRequest(invalidBrandName);

        //when
        doThrow(new InvalidBrandNameException(invalidBrandName))
                .when(brandService)
                .createBrand(newBrandRequest);

        //then
        restDocs
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(newBrandRequest)
                .when().post("/brands")
                .then().log().all()
                .apply(document("brands/create/fail/invalidFormat"))
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("중복된 브랜드 이름 등록 시 400 반환한다.")
    @Test
    void createBrand_Exception_Duplicated() {
        //given
        final String duplicatedName = "nike";
        NewBrandRequest newBrandRequest = new NewBrandRequest(duplicatedName);

        //when
        doThrow(new DuplicateBrandNameException(duplicatedName))
                .when(brandService)
                .createBrand(newBrandRequest);

        //then
        restDocs
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(newBrandRequest)
                .when().post("/brands")
                .then().log().all()
                .apply(document("brands/create/fail/duplicateBrandName"))
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("닉네임 변경 시 204 반환한다.")
    @Test
    void editBrandName() {
        //given
        BrandNameUpdateRequest brandNameUpdateRequest = new BrandNameUpdateRequest(1L, "musinsa");

        //when
        doNothing()
                .when(brandService)
                .editBrandName(brandNameUpdateRequest);

        //then
        restDocs
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(brandNameUpdateRequest)
                .when().patch("/brands")
                .then().log().all()
                .assertThat()
                .apply(document("brands/patch/success"))
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("중복된 닉네임에 대해 400 반환한다.")
    @Test
    void editNickname_Exception_Duplicate() {
        //given
        final String duplicatedName = "musinsa";
        BrandNameUpdateRequest brandNameUpdateRequest = new BrandNameUpdateRequest(1L, duplicatedName);

        //when
        doThrow(new DuplicateBrandNameException(duplicatedName))
                .when(brandService)
                .editBrandName(brandNameUpdateRequest);

        //then
        restDocs
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(brandNameUpdateRequest)
                .when().patch("/brands")
                .then().log().all()
                .assertThat()
                .apply(document("brands/patch/fail/duplicateBrandName"))
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("닉네임 변경 시 잘못된 형식이면 400 반환한다.")
    @Test
    void editNickname_Exception_InvalidFormat() {
        //given
        final String invalidName = " ";
        BrandNameUpdateRequest brandNameUpdateRequest = new BrandNameUpdateRequest(1L, invalidName);

        //when
        doThrow(new InvalidBrandNameException(invalidName))
                .when(brandService)
                .editBrandName(brandNameUpdateRequest);

        //then
        restDocs
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(brandNameUpdateRequest)
                .when().patch("/brands")
                .then().log().all()
                .assertThat()
                .apply(document("brands/patch/fail/invalidFormat"))
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("브랜드 삭제 시 204를 반환한다.")
    @Test
    void deleteBrand() {
        //given
        Long brandId = 1L;

        //when
        doNothing()
                .when(brandService)
                .deleteBrand(brandId);

        //then
        restDocs
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/brands/{id}", brandId)
                .then().log().all()
                .assertThat()
                .apply(document("brands/delete/success"))
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("존재하지 않는 브랜드 삭제 시 404를 반환한다.")
    @Test
    void deleteBrand_Exception_InvalidBrand() {
        //given
        Long invalidBrandId = 4L;

        //when
        doThrow(new BrandNotFoundException(invalidBrandId))
                .when(brandService)
                .deleteBrand(invalidBrandId);

        //then
        restDocs
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/brands/{id}", invalidBrandId)
                .then().log().all()
                .assertThat()
                .apply(document("brands/delete/fail/invalidBrand"))
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @DisplayName("최저 가격인 단일 브랜드의 카테고리 상품 조회 시 200을 반환한다.")
    @Test
    void findMinPriceBrandCategory() {
        //given & when & then
        restDocs
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/brands/min-price-category")
                .then().log().all()
                .assertThat()
                .apply(document("brands/find/success/minPrice"))
                .statusCode(HttpStatus.OK.value());
    }

    @DisplayName("상품 부족으로 최저 가격의 브랜드를 계산 불가 시 400을 반환한다.")
    @Test
    void findMinPriceBrandCategory_Exception_InsufficientProduct() {
        //given & when
        doThrow(new ProductInsufficientException())
                .when(brandService)
                .getMinPriceCategoryAndTotal();

        //then
        restDocs
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/brands/min-price-category")
                .then().log().all()
                .assertThat()
                .apply(document("brands/find/fail/minPrice"))
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}
