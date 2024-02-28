package com.musinsa.fashionshopping.brand.controller.dto;

import com.musinsa.fashionshopping.brand.domain.Brand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NewBrandResponse {
    private Long id;

    public NewBrandResponse(Brand brand) {
        this.id = brand.getId();
    }
}
