package com.musinsa.fashionshopping.brand.controller.dto;

import com.musinsa.fashionshopping.brand.domain.Brand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BrandResponse {
    private Long id;
    private String name;

    public BrandResponse(Brand brand) {
        this.id = brand.getId();
        this.name = brand.getBrandName().getValue();
    }
}
