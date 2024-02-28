package com.musinsa.fashionshopping.brand.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.musinsa.fashionshopping.brand.controller.dto.NewBrandRequest;
import com.musinsa.fashionshopping.brand.domain.Brand;
import com.musinsa.fashionshopping.brand.repository.BrandRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

    @Test
    @DisplayName("브랜드를 생성할 수 있다.")
    void createBrand() {
        //given
        String brandName = "nike";
        NewBrandRequest newBrandRequest = new NewBrandRequest(brandName);

        //when
        brandService.createBrand(newBrandRequest);
        final List<Brand> brands = brandRepository.findAll();

        //then
        assertThat(brands).isNotNull();
        assertThat(brands.get(0).getName()).isEqualTo(brandName);
    }
}
