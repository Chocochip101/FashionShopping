package com.musinsa.fashionshopping.brand.domain;

import com.musinsa.fashionshopping.global.BaseEntity;
import com.musinsa.fashionshopping.product.domain.Product;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Brand extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    private Long id;

    @Embedded
    private BrandName brandName;

    @OneToMany(mappedBy = "brand", cascade = CascadeType.REMOVE)
    private List<Product> products = new ArrayList<>();

    @Builder
    public Brand(final Long id, final BrandName brandName, final List<Product> products) {
        this.id = id;
        this.brandName = brandName;
        this.products = (products != null) ? products : new ArrayList<>();
    }

    public void updateBrandName(final BrandName brandName) {
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
