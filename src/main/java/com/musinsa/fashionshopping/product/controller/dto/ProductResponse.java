package com.musinsa.fashionshopping.product.controller.dto;

import com.musinsa.fashionshopping.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private Long id;
    private Long price;
    private String category;
    private String brandName;

    public ProductResponse(Product product) {
        this.id = product.getId();
        this.price = product.getProductPrice().getPrice();
        this.category = product.getCategory().getName();
        this.brandName = product.getBrand().getBrandName().getValue();
    }
}
