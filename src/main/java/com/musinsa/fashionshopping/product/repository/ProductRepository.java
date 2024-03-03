package com.musinsa.fashionshopping.product.repository;

import com.musinsa.fashionshopping.product.controller.dto.BrandPrice;
import com.musinsa.fashionshopping.product.domain.Category;
import com.musinsa.fashionshopping.product.domain.Product;
import com.musinsa.fashionshopping.product.repository.dto.CategoryMinPrice;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT NEW com.musinsa.fashionshopping.product.controller.dto.BrandPrice(b.brandName.value, p.productPrice.price) " +
            "FROM Product p " +
            "JOIN FETCH Brand b ON p.brand.id = b.id " +
            "WHERE p.category = :category " +
            "AND p.productPrice.price = (SELECT MIN(p2.productPrice.price) FROM Product p2 WHERE p2.category = :category)")
    List<BrandPrice> findMinPriceProductsByCategory(@Param("category") Category category);

    @Query("SELECT NEW com.musinsa.fashionshopping.product.controller.dto.BrandPrice(b.brandName.value, p.productPrice.price) " +
            "FROM Product p " +
            "JOIN FETCH Brand b ON p.brand.id = b.id " +
            "WHERE p.category = :category " +
            "AND p.productPrice.price = (SELECT MAX(p2.productPrice.price) FROM Product p2 WHERE p2.category = :category)")
    List<BrandPrice> findMaxPriceProductsByCategory(@Param("category") Category category);

    @Query("SELECT NEW com.musinsa.fashionshopping.product.repository.dto.CategoryMinPrice(p.category, b.brandName, p.productPrice.price) " +
            "FROM Product p " +
            "JOIN FETCH Brand b on p.brand.id = b.id " +
            "WHERE p.productPrice.price = " +
            "(SELECT MIN(p2.productPrice.price) FROM Product p2 WHERE p2.category = p.category GROUP BY p2.category) ")
    List<CategoryMinPrice> findCategoryMinPrice();
}
