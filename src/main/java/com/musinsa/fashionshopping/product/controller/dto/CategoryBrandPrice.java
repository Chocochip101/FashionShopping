package com.musinsa.fashionshopping.product.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.musinsa.fashionshopping.global.PriceFormatter;
import com.musinsa.fashionshopping.product.repository.dto.CategoryMinPrice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryBrandPrice {
    @JsonProperty("카테고리")
    private String category;
    @JsonProperty("브랜드")
    private String brandName;
    @JsonProperty("가격")
    private String price;

    public CategoryBrandPrice(final CategoryMinPrice categoryMinPrice) {
        this.category = categoryMinPrice.getCategory().getName();
        this.brandName = categoryMinPrice.getBrandName().getValue();
        this.price = PriceFormatter.convert(categoryMinPrice.getPrice());
    }
}
