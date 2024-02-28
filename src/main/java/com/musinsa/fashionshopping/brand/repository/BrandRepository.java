package com.musinsa.fashionshopping.brand.repository;

import com.musinsa.fashionshopping.brand.domain.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
}
