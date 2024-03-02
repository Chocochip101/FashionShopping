package com.musinsa.fashionshopping.product.service;

import com.musinsa.fashionshopping.brand.domain.Brand;
import com.musinsa.fashionshopping.brand.exception.BrandNotFoundException;
import com.musinsa.fashionshopping.brand.repository.BrandRepository;
import com.musinsa.fashionshopping.product.controller.dto.BrandPrice;
import com.musinsa.fashionshopping.product.controller.dto.CategoryPriceResponse;
import com.musinsa.fashionshopping.product.controller.dto.CategoryUpdateRequest;
import com.musinsa.fashionshopping.product.controller.dto.NewProductRequest;
import com.musinsa.fashionshopping.product.controller.dto.PriceUpdateRequest;
import com.musinsa.fashionshopping.product.domain.Category;
import com.musinsa.fashionshopping.product.domain.Product;
import com.musinsa.fashionshopping.product.domain.ProductPrice;
import com.musinsa.fashionshopping.product.exception.ProductNotFoundException;
import com.musinsa.fashionshopping.product.repository.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;

    @Transactional
    public void createProduct(Long brandId, NewProductRequest newProductRequest) {
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(BrandNotFoundException::new);

        Product product = Product.builder()
                .productPrice(new ProductPrice(newProductRequest.getPrice()))
                .category(Category.from(newProductRequest.getCategory()))
                .brand(brand)
                .build();

        product.addBrand(brand);

        productRepository.save(product);
    }

    @Transactional
    public void editPrice(Long productId, PriceUpdateRequest priceUpdateRequest) {
        Product product = productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);

        ProductPrice validPrice = new ProductPrice(priceUpdateRequest.getPrice());

        product.updateProductPrice(validPrice);
    }

    @Transactional
    public void editCategory(Long productId, CategoryUpdateRequest categoryUpdateRequest) {
        Product product = productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);

        Category validCategory = Category.from(categoryUpdateRequest.getCategory());

        product.updateCategory(validCategory);
    }

    @Transactional
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);

        product.deleteBrand();

        productRepository.delete(product);
    }

    public CategoryPriceResponse getPriceBrandByCategory(String category) {
        Category foundCategory = Category.from(category);

        final List<BrandPrice> minPriceProducts = productRepository.findMinPriceProductsByCategory(foundCategory);
        final List<BrandPrice> maxPriceProducts = productRepository.findMaxPriceProductsByCategory(foundCategory);

        return new CategoryPriceResponse(foundCategory.getName(), minPriceProducts, maxPriceProducts);
    }
}
