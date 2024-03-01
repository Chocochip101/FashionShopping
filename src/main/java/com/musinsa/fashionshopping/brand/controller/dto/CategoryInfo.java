package com.musinsa.fashionshopping.brand.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.musinsa.fashionshopping.product.domain.Category;
import java.text.DecimalFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryInfo {
    private static final DecimalFormat decimalFormat = new DecimalFormat("#,###");
    @JsonProperty("카테고리")
    private String category;
    @JsonProperty("가격")
    private String price;

    public CategoryInfo(final Category category, final Long price) {
        this.category = category.getName();
        this.price = formatPrice(price);
    }

    private String formatPrice(Long price) {
        return decimalFormat.format(price);
    }
}
