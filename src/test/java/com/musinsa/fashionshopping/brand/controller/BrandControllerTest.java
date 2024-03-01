package com.musinsa.fashionshopping.brand.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

import com.musinsa.fashionshopping.ControllerTest;
import com.musinsa.fashionshopping.brand.controller.dto.BrandNameUpdateRequest;
import com.musinsa.fashionshopping.brand.controller.dto.NewBrandRequest;
import com.musinsa.fashionshopping.brand.exception.DuplicateBrandNameException;
import com.musinsa.fashionshopping.brand.exception.InvalidBrandNameException;
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
        doThrow(new InvalidBrandNameException())
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
    void createBrand_Exception_Duplicated() throws Exception {
        //given
        NewBrandRequest newBrandRequest = new NewBrandRequest("nike");

        //when
        doThrow(new DuplicateBrandNameException())
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
        BrandNameUpdateRequest brandNameUpdateRequest = new BrandNameUpdateRequest(1L, "musinsa");

        //when
        doThrow(new DuplicateBrandNameException())
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
        BrandNameUpdateRequest brandNameUpdateRequest = new BrandNameUpdateRequest(1L, " ");

        //when
        doThrow(new InvalidBrandNameException())
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
}
