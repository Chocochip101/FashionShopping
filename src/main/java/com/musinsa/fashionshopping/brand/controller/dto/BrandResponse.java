package com.musinsa.fashionshopping.brand.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.musinsa.fashionshopping.brand.domain.Brand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BrandResponse {
    @JsonProperty("브랜드 ID")
    private Long id;
    @JsonProperty("브랜드명")
    private String name;

    public BrandResponse(Brand brand) {
        this.id = brand.getId();
        this.name = brand.getBrandName().getValue();
    }
}
