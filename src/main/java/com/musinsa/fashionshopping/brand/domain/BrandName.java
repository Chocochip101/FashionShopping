package com.musinsa.fashionshopping.brand.domain;

import com.musinsa.fashionshopping.brand.exception.InvalidBrandNameException;
import java.util.Objects;
import java.util.regex.Pattern;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class BrandName {
    private static final Pattern PATTERN = Pattern.compile("^[0-9a-zA-Z가-힣]+(?:\\s+[0-9a-zA-Z가-힣]+)*$");
    private static final int MIN_LENGTH = 1;
    private static final int MAX_LENGTH = 16;

    @Column(name = "brandname", unique = true)
    private String value;

    protected BrandName() {
    }

    public BrandName(final String value) {
        validate(value);
        this.value = value;
    }

    private void validate(final String value) {
        if (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH || !PATTERN.matcher(value).matches()) {
            throw new InvalidBrandNameException(value);
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
        final BrandName brandName = (BrandName) o;
        return Objects.equals(getValue(), brandName.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
