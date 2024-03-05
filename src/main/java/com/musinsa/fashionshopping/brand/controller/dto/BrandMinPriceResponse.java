package com.musinsa.fashionshopping.brand.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BrandMinPriceResponse {
    @JsonProperty("최저가")
    private LowestPriceInfo lowestPrice;
}
