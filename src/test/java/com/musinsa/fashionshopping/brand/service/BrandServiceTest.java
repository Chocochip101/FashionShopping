package com.musinsa.fashionshopping.brand.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.musinsa.fashionshopping.brand.controller.dto.NewBrandRequest;
import com.musinsa.fashionshopping.brand.domain.Brand;
import com.musinsa.fashionshopping.brand.exception.InvalidBrandNameException;
import com.musinsa.fashionshopping.brand.repository.BrandRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BrandServiceTest {
    @Autowired
    BrandRepository brandRepository;

    @Autowired
    BrandService brandService;

    @BeforeEach
    void setUp() {
        brandRepository.deleteAll();
    }

    @DisplayName("브랜드를 생성할 수 있다.")
    @Test
    void createBrand() {
        //given
        String brandName = "nike";
        NewBrandRequest newBrandRequest = new NewBrandRequest(brandName);

        //when
        brandService.createBrand(newBrandRequest);
        final List<Brand> brands = brandRepository.findAll();

        //then
        assertThat(brands).isNotNull();
        assertThat(brands.get(0).getBrandName().getValue()).isEqualTo(brandName);
    }

    @DisplayName("숫자와 영문, 한글음절을 포함한 1자 이상 16자이하가 아닌 잘못된 형식의 브랜드 이름으로 등록할 시 예외 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "ㄱㄴㄷ", "abdf123ㅏㅇ", "11112222333344445"})
    void createBrandName_Exception_InvalidFormat(String invalidBrandName) {
        //given
        NewBrandRequest newBrandRequest = new NewBrandRequest(invalidBrandName);

        //when & then
        assertThatThrownBy(() -> brandService.createBrand(newBrandRequest))
                .isInstanceOf(InvalidBrandNameException.class);
    }
}
