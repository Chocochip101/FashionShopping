package com.musinsa.fashionshopping.product.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.musinsa.fashionshopping.product.repository.dto.CategoryMinPrice;
import java.text.DecimalFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryBrandPrice {
    private static final DecimalFormat decimalFormat = new DecimalFormat("#,###");
    @JsonProperty("카테고리")
    private String category;
    @JsonProperty("브랜드")
    private String brandName;
    @JsonProperty("가격")
    private String price;

    public CategoryBrandPrice(CategoryMinPrice categoryMinPrice) {
        this.category = categoryMinPrice.getCategory().getName();
        this.brandName = categoryMinPrice.getBrandName().getValue();
        this.price = formatPrice(categoryMinPrice.getPrice());
    }

    private String formatPrice(Long price) {
        return decimalFormat.format(price);
    }
}