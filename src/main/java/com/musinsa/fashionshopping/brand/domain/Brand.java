package com.musinsa.fashionshopping.brand.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    private Long id;

    @Embedded
    private BrandName brandName;

    @Builder
    public Brand(final Long id, final BrandName brandName) {
        this.id = id;
        this.brandName = brandName;
    }

    public void updateBrandName(BrandName brandName) {
        this.brandName = brandName;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Brand brand = (Brand) o;
        return Objects.equals(getId(), brand.getId()) && Objects.equals(getBrandName(),
                brand.getBrandName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getBrandName());
    }
}
