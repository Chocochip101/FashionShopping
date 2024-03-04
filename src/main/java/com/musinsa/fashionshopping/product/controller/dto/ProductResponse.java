package com.musinsa.fashionshopping.product.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.musinsa.fashionshopping.product.domain.Product;
import java.text.DecimalFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private static final DecimalFormat decimalFormat = new DecimalFormat("#,###");
    @JsonProperty("상품 ID")
    private Long id;
    @JsonProperty("상품 가격")
    private String price;
    @JsonProperty("카테고리")
    private String category;
    @JsonProperty("브랜드명")
    private String brandName;

    public ProductResponse(Product product) {
        this.id = product.getId();
        this.price = formatPrice(product.getProductPrice().getPrice());
        this.category = product.getCategory().getName();
        this.brandName = product.getBrand().getBrandName().getValue();
    }

    private String formatPrice(Long price) {
        return decimalFormat.format(price);
    }
}
