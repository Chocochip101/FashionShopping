package com.musinsa.fashionshopping.brand.service;

import com.musinsa.fashionshopping.brand.controller.dto.NewBrandRequest;
import com.musinsa.fashionshopping.brand.domain.Brand;
import com.musinsa.fashionshopping.brand.domain.BrandName;
import com.musinsa.fashionshopping.brand.exception.DuplicateBrandNameException;
import com.musinsa.fashionshopping.brand.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BrandService {
    private final BrandRepository brandRepository;

    @Transactional
    public void createBrand(NewBrandRequest newBrandRequest) {
        validate(newBrandRequest);

        Brand brand = Brand.builder()
                .brandName(new BrandName(newBrandRequest.getName()))
                .build();
        brandRepository.save(brand);
    }

    private void validate(NewBrandRequest newBrandRequest) {
        validateUniqueBrandName(newBrandRequest);
    }

    private void validateUniqueBrandName(NewBrandRequest newBrandRequest) {
        boolean isDuplicatedBrandName = brandRepository
                .existsBrandByBrandNameValue(newBrandRequest.getName());
        if (isDuplicatedBrandName) {
            throw new DuplicateBrandNameException();
        }
    }
}
