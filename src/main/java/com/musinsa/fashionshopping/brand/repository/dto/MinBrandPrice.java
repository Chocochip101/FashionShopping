package com.musinsa.fashionshopping.brand.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MinBrandPrice {
    private Long brandId;
    private String brandName;
    private Long totalPrice;
}
