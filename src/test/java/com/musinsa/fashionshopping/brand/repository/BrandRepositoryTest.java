package com.musinsa.fashionshopping.brand.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.musinsa.fashionshopping.brand.domain.Brand;
import com.musinsa.fashionshopping.brand.domain.BrandName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class BrandRepositoryTest {
    @Autowired
    BrandRepository brandRepository;

    @BeforeEach
    void setUp() {
        brandRepository.deleteAll();
    }

    @DisplayName("id가 없는 Brand 엔티티를 저장하면 순차적으로 ID를 부여하여 저장한다.")
    @Test
    void saveNoIdBrand() {
        //given
        Brand brand1 = Brand.builder().brandName(new BrandName("brand1")).build();
        Brand brand2 = Brand.builder().brandName(new BrandName("brand2")).build();

        //when
        brandRepository.save(brand1);
        brandRepository.save(brand2);

        //then
        long isDiff = brand2.getId() - brand1.getId();
        assertThat(isDiff).isEqualTo(1L);
    }
}
