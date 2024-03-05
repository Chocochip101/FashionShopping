package com.musinsa.fashionshopping.product.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.text.DecimalFormat;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryMinPriceResponse {
    private static final DecimalFormat decimalFormat = new DecimalFormat("#,###");
    @JsonProperty("카테고리")
    private List<CategoryBrandPrice> categories;
    @JsonProperty("총액")
    private String totalPrice;

    public CategoryMinPriceResponse(final List<CategoryBrandPrice> categoryBrandPrices, final Long totalPrice) {
        this.categories = categoryBrandPrices;
        this.totalPrice = formatPrice(totalPrice);
    }

    private String formatPrice(final Long price) {
        return decimalFormat.format(price);
    }
}
