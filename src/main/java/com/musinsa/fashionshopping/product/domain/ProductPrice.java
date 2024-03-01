package com.musinsa.fashionshopping.product.domain;

import com.musinsa.fashionshopping.product.exception.InvalidProductPriceException;
import java.util.Objects;
import javax.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class ProductPrice {
    private static final long MAX_PRICE = 10_000_000_000L;
    private static final int MIN_PRICE = 10;
    private Long price;

    protected ProductPrice() {

    }

    public ProductPrice(final Long price) {
        validate(price);
        this.price = price;
    }

    private void validate(Long price) {
        if (price < MIN_PRICE || price > MAX_PRICE) {
            throw new InvalidProductPriceException();
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductPrice productPrice1 = (ProductPrice) o;
        return Objects.equals(getPrice(), productPrice1.getPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPrice());
    }
}