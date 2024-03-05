package com.musinsa.fashionshopping.brand.service;

import com.musinsa.fashionshopping.brand.controller.dto.BrandMinPriceResponse;
import com.musinsa.fashionshopping.brand.controller.dto.BrandNameUpdateRequest;
import com.musinsa.fashionshopping.brand.controller.dto.BrandResponse;
import com.musinsa.fashionshopping.brand.controller.dto.CategoryInfo;
import com.musinsa.fashionshopping.brand.controller.dto.LowestPriceInfo;
import com.musinsa.fashionshopping.brand.controller.dto.NewBrandRequest;
import com.musinsa.fashionshopping.brand.domain.Brand;
import com.musinsa.fashionshopping.brand.domain.BrandName;
import com.musinsa.fashionshopping.brand.exception.BrandNotFoundException;
import com.musinsa.fashionshopping.brand.exception.DuplicateBrandNameException;
import com.musinsa.fashionshopping.brand.exception.ProductInsufficientException;
import com.musinsa.fashionshopping.brand.repository.BrandRepository;
import com.musinsa.fashionshopping.brand.repository.dto.MinBrandPrice;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BrandService {
    private static final PageRequest firstPageLimit = PageRequest.of(0, 1);
    private final BrandRepository brandRepository;

    public List<BrandResponse> findBrands() {
        return brandRepository.findAll().stream()
                .map(BrandResponse::new)
                .toList();
    }

    @Transactional
    public void createBrand(final NewBrandRequest newBrandRequest) {
        validate(newBrandRequest);

        Brand brand = Brand.builder()
                .brandName(new BrandName(newBrandRequest.getName()))
                .build();
        brandRepository.save(brand);
    }

    @Transactional
    public void editBrandName(final Long brandId, final BrandNameUpdateRequest brandNameUpdateRequest) {
        validate(brandNameUpdateRequest);

        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new BrandNotFoundException(brandNameUpdateRequest.getName()));
        BrandName validBrandName = new BrandName(brandNameUpdateRequest.getName());
        brand.updateBrandName(validBrandName);
    }

    private void validate(final NewBrandRequest newBrandRequest) {
        validateUniqueBrandName(newBrandRequest.getName());
    }

    private void validate(final BrandNameUpdateRequest brandNameUpdateRequest) {
        validateUniqueBrandName(brandNameUpdateRequest.getName());
    }

    private void validateUniqueBrandName(final String brandName) {
        boolean isDuplicatedBrandName = brandRepository
                .existsBrandByBrandNameValue(brandName);
        if (isDuplicatedBrandName) {
            throw new DuplicateBrandNameException(brandName);
        }
    }

    @Transactional
    public void deleteBrand(final Long brandId) {
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new BrandNotFoundException(brandId));

        brandRepository.delete(brand);
    }

    public BrandMinPriceResponse getMinPriceCategoryAndTotal() {
        List<MinBrandPrice> minBrandPrices = brandRepository.findBrandByPrices(firstPageLimit);
        checkMinBrandPricesNotEmpty(minBrandPrices);
        List<CategoryInfo> categoryInfos = brandRepository.findCategoryInfos(minBrandPrices.get(0).getBrandId());

        return new BrandMinPriceResponse(LowestPriceInfo.from(minBrandPrices.get(0), categoryInfos));
    }

    private void checkMinBrandPricesNotEmpty(final List<MinBrandPrice> minBrandPrices) {
        if (minBrandPrices.size() == 0) {
            throw new ProductInsufficientException();
        }
    }
}
