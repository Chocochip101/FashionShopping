package com.musinsa.fashionshopping.brand.controller;

import com.musinsa.fashionshopping.brand.controller.dto.BrandMinPriceResponse;
import com.musinsa.fashionshopping.brand.controller.dto.BrandNameUpdateRequest;
import com.musinsa.fashionshopping.brand.controller.dto.BrandResponse;
import com.musinsa.fashionshopping.brand.controller.dto.NewBrandRequest;
import com.musinsa.fashionshopping.brand.service.BrandService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/brands")
@RequiredArgsConstructor
public class BrandController {
    private final BrandService brandService;

    @GetMapping
    public ResponseEntity<List<BrandResponse>> findBrand() {
        List<BrandResponse> brands = brandService.findBrands();
        return ResponseEntity.ok(brands);
    }

    @PostMapping
    public ResponseEntity<Void> createBrand(@Valid @RequestBody NewBrandRequest newBrandRequest) {
        brandService.createBrand(newBrandRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> editBrandName(@PathVariable(name = "id") Long brandId,
                                              @Valid @RequestBody BrandNameUpdateRequest brandNameUpdateRequest) {
        brandService.editBrandName(brandId, brandNameUpdateRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBrand(@PathVariable(name = "id") Long brandId) {
        brandService.deleteBrand(brandId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/min-price-category")
    public ResponseEntity<BrandMinPriceResponse> findMinPriceCategory() {
        BrandMinPriceResponse minPriceCategory = brandService.getMinPriceCategoryAndTotal();
        return ResponseEntity.ok(minPriceCategory);
    }
}
