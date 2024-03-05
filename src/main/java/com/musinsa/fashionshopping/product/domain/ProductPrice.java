package com.musinsa.fashionshopping.product.domain;

import com.musinsa.fashionshopping.product.exception.InvalidProductPriceException;
import java.util.Objects;
import javax.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class ProductPrice {
    public static final long MAX_PRICE = 10_000_000_000L;
    public static final int MIN_PRICE = 0;
    private Long price;

    protected ProductPrice() {

    }

    public ProductPrice(final Long price) {
        validate(price);
        this.price = price;
    }

    private void validate(final Long price) {
        if (price <= MIN_PRICE || price > MAX_PRICE) {
            throw new InvalidProductPriceException(price);
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
