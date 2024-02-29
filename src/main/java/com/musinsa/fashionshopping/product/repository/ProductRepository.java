package com.musinsa.fashionshopping.product.repository;

import com.musinsa.fashionshopping.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
