package com.musinsa.fashionshopping.product.controller.dto;

import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NewProductRequest {
    @NotNull
    private Long price;

    @NotBlank
    private String category;

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final NewProductRequest that = (NewProductRequest) o;
        return Objects.equals(getPrice(), that.getPrice()) && Objects.equals(getCategory(),
                that.getCategory());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPrice(), getCategory());
    }
}
