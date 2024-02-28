package com.musinsa.fashionshopping.brand.service;

import com.musinsa.fashionshopping.brand.domain.Brand;
import com.musinsa.fashionshopping.brand.repository.BrandRepository;
import com.musinsa.fashionshopping.brand.controller.dto.NewBrandRequest;
import com.musinsa.fashionshopping.brand.controller.dto.NewBrandResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BrandService {
    private final BrandRepository brandRepository;

    @Transactional
    public NewBrandResponse createBrand(NewBrandRequest newBrandRequest) {
        Brand brand = Brand.builder()
                .name(newBrandRequest.getName())
                .build();
        final Brand savedBrand = brandRepository.save(brand);
        return new NewBrandResponse(savedBrand);
    }
}
