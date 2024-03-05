package com.musinsa.fashionshopping.brand.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.musinsa.fashionshopping.brand.repository.dto.MinBrandPrice;
import com.musinsa.fashionshopping.global.PriceFormatter;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LowestPriceInfo {
    @JsonProperty("브랜드")
    private String brand;
    @JsonProperty("카테고리")
    private List<CategoryInfo> categoryInfos;
    @JsonProperty("총액")
    private String totalPrice;

    public static LowestPriceInfo from(final MinBrandPrice minBrandPrice,
                                       final List<CategoryInfo> categoryInfos) {
        return new LowestPriceInfo(
                minBrandPrice.getBrandName(),
                categoryInfos,
                PriceFormatter.convert(minBrandPrice.getTotalPrice())
        );
    }
}
