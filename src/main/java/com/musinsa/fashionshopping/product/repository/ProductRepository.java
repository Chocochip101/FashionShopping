package com.musinsa.fashionshopping.product.repository;

import com.musinsa.fashionshopping.product.controller.dto.BrandPrice;
import com.musinsa.fashionshopping.product.domain.Category;
import com.musinsa.fashionshopping.product.domain.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT new com.musinsa.fashionshopping.product.controller.dto.BrandPrice(b.brandName.value, p.productPrice.price) " +
            "FROM Product p " +
            "JOIN fetch Brand b ON p.brand.id = b.id " +
            "WHERE p.category = :category " +
            "AND p.productPrice.price = (SELECT MIN(p2.productPrice.price) FROM Product p2 WHERE p2.category = :category)")
    List<BrandPrice> findMinPriceProductsByCategory(@Param("category") Category category);

    @Query("SELECT new com.musinsa.fashionshopping.product.controller.dto.BrandPrice(b.brandName.value, p.productPrice.price) " +
            "FROM Product p " +
            "JOIN fetch Brand b ON p.brand.id = b.id " +
            "WHERE p.category = :category " +
            "AND p.productPrice.price = (SELECT MAX(p2.productPrice.price) FROM Product p2 WHERE p2.category = :category)")
    List<BrandPrice> findMaxPriceProductsByCategory(@Param("category") Category category);
}
