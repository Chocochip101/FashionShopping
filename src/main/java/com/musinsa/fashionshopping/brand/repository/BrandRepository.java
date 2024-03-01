package com.musinsa.fashionshopping.brand.repository;

import com.musinsa.fashionshopping.brand.controller.dto.CategoryInfo;
import com.musinsa.fashionshopping.brand.domain.Brand;
import com.musinsa.fashionshopping.brand.repository.dto.MinBrandPrice;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    boolean existsBrandByBrandNameValue(String brandName);

    @Query("SELECT new com.musinsa.fashionshopping.brand.repository.dto.MinBrandPrice(b.id, b.brandName.value, SUM(p.productPrice.price)) "
            +
            "FROM Brand b " +
            "JOIN Product p ON b.id = p.brand.id " +
            "GROUP BY b.id " +
            "ORDER BY SUM(p.productPrice.price) ASC ")
    List<MinBrandPrice> findBrandByPrices(Pageable pageable);

    @Query("SELECT new com.musinsa.fashionshopping.brand.controller.dto.CategoryInfo(p.category, p.productPrice.price)"
            +
            "FROM Product p " +
            "WHERE p.brand.id = :brandId")
    List<CategoryInfo> findCategoryInfos(@Param("brandId") Long brandId);
}
