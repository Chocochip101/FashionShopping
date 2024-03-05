package com.musinsa.fashionshopping.product.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.musinsa.fashionshopping.global.PriceFormatter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BrandPrice {
    @JsonProperty("브랜드")
    private String brandName;
    @JsonProperty("가격")
    private String price;

    public BrandPrice(final String brandName, final Long price) {
        this.brandName = brandName;
        this.price = PriceFormatter.convert(price);
    }
}
