package com.musinsa.fashionshopping.product.domain;

import com.musinsa.fashionshopping.brand.domain.Brand;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    private Long price;

    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToOne
    private Brand brand;

    @Builder
    public Product(final Long id, final Long price, final Category category, final Brand brand) {
        this.id = id;
        this.price = price;
        this.category = category;
        this.brand = brand;
    }
}
