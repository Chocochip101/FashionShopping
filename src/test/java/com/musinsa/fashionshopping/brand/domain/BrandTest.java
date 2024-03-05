package com.musinsa.fashionshopping.brand.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BrandTest {
    @DisplayName("빌더 패턴을 사용하면, 브랜드를 생성할 수 있다.")
    @Test
    void createBrand() {
        //given
        Long id = 1L;
        BrandName name = new BrandName("nike");

        //when
        Brand nike = Brand.builder()
                .id(id)
                .brandName(name)
                .build();

        //then
        assertThat(nike.getId()).isEqualTo(id);
        assertThat(nike.getBrandName()).isEqualTo(name);
    }
}
