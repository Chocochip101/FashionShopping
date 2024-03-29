package com.musinsa.fashionshopping.brand.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

import com.musinsa.fashionshopping.ControllerTest;
import com.musinsa.fashionshopping.brand.controller.dto.BrandMinPriceResponse;
import com.musinsa.fashionshopping.brand.controller.dto.BrandNameUpdateRequest;
import com.musinsa.fashionshopping.brand.controller.dto.BrandResponse;
import com.musinsa.fashionshopping.brand.controller.dto.LowestPriceInfo;
import com.musinsa.fashionshopping.brand.controller.dto.NewBrandRequest;
import com.musinsa.fashionshopping.brand.exception.BrandNotFoundException;
import com.musinsa.fashionshopping.brand.exception.DuplicateBrandNameException;
import com.musinsa.fashionshopping.brand.exception.InvalidBrandNameException;
import com.musinsa.fashionshopping.brand.exception.ProductInsufficientException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

class BrandControllerTest extends ControllerTest {
    @DisplayName("브랜드를 조회하면 200을 반환한다.")
    @Test
    void findBrands() {
        //given
        doReturn(List.of(new BrandResponse()))
                .when(brandService)
                .findBrands();

        //when & then
        restDocs
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/brands")
                .then().log().all()
                .apply(document("brands/find/success",
                        responseFields(
                                fieldWithPath("[].브랜드 ID").description("브랜드의 식별자"),
                                fieldWithPath("[].브랜드명").description("브랜드명")
                        )))
                .statusCode(HttpStatus.OK.value());
    }

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
        String duplicatedName = "nike";
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

    @DisplayName("브랜드명 변경 시 204 반환한다.")
    @Test
    void editBrandName() {
        //given
        Long brandId = 1L;
        BrandNameUpdateRequest brandNameUpdateRequest = new BrandNameUpdateRequest("musinsa");

        //when
        doNothing()
                .when(brandService)
                .editBrandName(brandId, brandNameUpdateRequest);

        //then
        restDocs
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(brandNameUpdateRequest)
                .when().patch("/brands/{brandId}", brandId)
                .then().log().all()
                .assertThat()
                .apply(document("brands/patch/success"))
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("중복된 브랜드명에 대해 400 반환한다.")
    @Test
    void editBrandName_Exception_Duplicate() {
        //given
        String duplicatedName = "musinsa";
        Long brandId = 1L;
        BrandNameUpdateRequest brandNameUpdateRequest = new BrandNameUpdateRequest(duplicatedName);

        //when
        doThrow(new DuplicateBrandNameException(duplicatedName))
                .when(brandService)
                .editBrandName(brandId, brandNameUpdateRequest);

        //then
        restDocs
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(brandNameUpdateRequest)
                .when().patch("/brands/{brandId}", brandId)
                .then().log().all()
                .assertThat()
                .apply(document("brands/patch/fail/duplicateBrandName"))
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("브랜드명 변경 시 잘못된 형식이면 400 반환한다.")
    @Test
    void editBrandName_Exception_InvalidFormat() {
        //given
        String invalidName = " ";
        Long brandId = 1L;
        BrandNameUpdateRequest brandNameUpdateRequest = new BrandNameUpdateRequest(invalidName);

        //when
        doThrow(new InvalidBrandNameException(invalidName))
                .when(brandService)
                .editBrandName(brandId, brandNameUpdateRequest);

        //then
        restDocs
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(brandNameUpdateRequest)
                .when().patch("/brands/{brandId}", brandId)
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
        //given
        doReturn(new BrandMinPriceResponse(new LowestPriceInfo()))
                .when(brandService)
                .getMinPriceCategoryAndTotal();

        //when & then
        restDocs
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/brands/min-price-category")
                .then().log().all()
                .assertThat()
                .apply(document("brands/find/success/minPrice",
                        responseFields(
                                fieldWithPath("최저가").description("최저가 브랜드가 속한 데이터 집합"),
                                fieldWithPath("최저가.브랜드").description("최저가 브랜드명"),
                                fieldWithPath("최저가.카테고리").description("최저가 브랜드의 상품들"),
                                fieldWithPath("최저가.총액").description("최저가 브랜드의 총액")
                        )))
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
