package com.musinsa.fashionshopping.product.repository.dto;

import com.musinsa.fashionshopping.brand.domain.BrandName;
import com.musinsa.fashionshopping.product.domain.Category;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryMinPrice {
    private Category category;
    private BrandName brandName;
    private Long price;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CategoryMinPrice that = (CategoryMinPrice) o;
        return Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category);
    }
}
