package com.musinsa.fashionshopping.brand.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BrandTest {
    @Test
    @DisplayName("빌더 패턴을 사용하면, 브랜드를 생성할 수 있다.")
    void createBrand() {
        //given
        Long id = 1L;
        String name = "nike";

        //when
        Brand nike = Brand.builder()
                .id(id)
                .name(name)
                .build();

        //then
        assertThat(nike.getId()).isEqualTo(id);
        assertThat(nike.getName()).isEqualTo(name);
    }
}
