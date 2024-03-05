package com.musinsa.fashionshopping.product.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryPriceResponse {
    @JsonProperty("카테고리")
    private String category;
    @JsonProperty("최저가")
    private List<BrandPrice> minBrandPrice;
    @JsonProperty("최고가")
    private List<BrandPrice> maxBrandPrice;
}
