package com.musinsa.fashionshopping.brand.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.musinsa.fashionshopping.brand.exception.InvalidBrandNameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class BrandNameTest {
    @DisplayName("브랜드 이름 생성에 성공한다.")
    @ParameterizedTest
    @ValueSource(strings = {"나이키", "폴로 랄프 로렌", "무신사 스탠다드"})
    void createBrandName(String validBrandName) {
        //given
        BrandName brandName = new BrandName(validBrandName);

        //when & then
        assertThat(brandName.getValue()).isEqualTo(validBrandName);
    }

    @DisplayName("숫자와 영문, 한글음절을 포함한 1자 이상 16자가 아니면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "ㄱㄴㄷ", "abdf123ㅏㅇ", "11112222333344445"})
    void create_Exception_Format(String invalidBrandName) {
        assertThatThrownBy(() -> new BrandName(invalidBrandName))
                .isInstanceOf(InvalidBrandNameException.class);
    }
}
