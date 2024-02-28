package com.musinsa.fashionshopping.brand.controller;

import com.musinsa.fashionshopping.brand.service.BrandService;
import com.musinsa.fashionshopping.brand.controller.dto.NewBrandRequest;
import com.musinsa.fashionshopping.brand.controller.dto.NewBrandResponse;
import java.net.URI;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/brands")
@RequiredArgsConstructor
public class BrandController {
    private final BrandService brandService;

    @PostMapping
    public ResponseEntity<NewBrandResponse> createBrand(@Valid @RequestBody NewBrandRequest newBrandRequest) {
        NewBrandResponse brand = brandService.createBrand(newBrandRequest);
        return ResponseEntity.created(URI.create("/brands/" + brand.getId())).build();
    }
}
