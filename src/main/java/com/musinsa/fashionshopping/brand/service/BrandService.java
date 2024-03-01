package com.musinsa.fashionshopping.brand.service;

import com.musinsa.fashionshopping.brand.controller.dto.BrandNameUpdateRequest;
import com.musinsa.fashionshopping.brand.controller.dto.NewBrandRequest;
import com.musinsa.fashionshopping.brand.domain.Brand;
import com.musinsa.fashionshopping.brand.domain.BrandName;
import com.musinsa.fashionshopping.brand.exception.BrandNotFoundException;
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

    @Transactional
    public void editBrandName(BrandNameUpdateRequest brandNameUpdateRequest) {
        validate(brandNameUpdateRequest);

        Brand brand = brandRepository.findById(brandNameUpdateRequest.getId())
                .orElseThrow(BrandNotFoundException::new);
        BrandName validBrandName = new BrandName(brandNameUpdateRequest.getName());
        brand.updateBrandName(validBrandName);
    }

    private void validate(NewBrandRequest newBrandRequest) {
        validateUniqueBrandName(newBrandRequest.getName());
    }

    private void validate(BrandNameUpdateRequest brandNameUpdateRequest) {
        validateUniqueBrandName(brandNameUpdateRequest.getName());
    }

    private void validateUniqueBrandName(String brandName) {
        boolean isDuplicatedBrandName = brandRepository
                .existsBrandByBrandNameValue(brandName);
        if (isDuplicatedBrandName) {
            throw new DuplicateBrandNameException();
        }
    }

    @Transactional
    public void deleteBrand(Long brandId) {
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(BrandNotFoundException::new);

        brandRepository.delete(brand);
    }
}
