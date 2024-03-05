package com.musinsa.fashionshopping.product.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.musinsa.fashionshopping.global.PriceFormatter;
import com.musinsa.fashionshopping.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    @JsonProperty("상품 ID")
    private Long id;
    @JsonProperty("상품 가격")
    private String price;
    @JsonProperty("카테고리")
    private String category;
    @JsonProperty("브랜드명")
    private String brandName;

    public ProductResponse(final Product product) {
        this.id = product.getId();
        this.price = PriceFormatter.convert(product.getProductPrice().getPrice());
        this.category = product.getCategory().getName();
        this.brandName = product.getBrandName();
    }
}
