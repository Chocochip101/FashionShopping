package com.musinsa.fashionshopping.product.service;

import com.musinsa.fashionshopping.brand.domain.Brand;
import com.musinsa.fashionshopping.brand.exception.BrandNotFoundException;
import com.musinsa.fashionshopping.brand.repository.BrandRepository;
import com.musinsa.fashionshopping.product.controller.dto.BrandPrice;
import com.musinsa.fashionshopping.product.controller.dto.CategoryBrandPrice;
import com.musinsa.fashionshopping.product.controller.dto.CategoryMinPriceResponse;
import com.musinsa.fashionshopping.product.controller.dto.CategoryPriceResponse;
import com.musinsa.fashionshopping.product.controller.dto.CategoryUpdateRequest;
import com.musinsa.fashionshopping.product.controller.dto.NewProductRequest;
import com.musinsa.fashionshopping.product.controller.dto.PriceUpdateRequest;
import com.musinsa.fashionshopping.product.controller.dto.ProductResponse;
import com.musinsa.fashionshopping.product.domain.Category;
import com.musinsa.fashionshopping.product.domain.Product;
import com.musinsa.fashionshopping.product.domain.ProductPrice;
import com.musinsa.fashionshopping.product.exception.ProductNotFoundException;
import com.musinsa.fashionshopping.product.repository.ProductRepository;
import com.musinsa.fashionshopping.product.repository.dto.CategoryMinPrice;
import java.util.Comparator;
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

    public List<ProductResponse> findProducts() {
        return productRepository.findAll().stream()
                .map(ProductResponse::new)
                .toList();
    }

    @Transactional
    public void createProduct(final Long brandId, final NewProductRequest newProductRequest) {
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new BrandNotFoundException(brandId));

        Product product = Product.builder()
                .productPrice(new ProductPrice(newProductRequest.getPrice()))
                .category(Category.from(newProductRequest.getCategory()))
                .brand(brand)
                .build();

        product.addBrand(brand);

        productRepository.save(product);
    }

    @Transactional
    public void editPrice(final Long productId, final PriceUpdateRequest priceUpdateRequest) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        ProductPrice validPrice = new ProductPrice(priceUpdateRequest.getPrice());

        product.updateProductPrice(validPrice);
    }

    @Transactional
    public void editCategory(final Long productId, final CategoryUpdateRequest categoryUpdateRequest) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        Category validCategory = Category.from(categoryUpdateRequest.getCategory());

        product.updateCategory(validCategory);
    }

    @Transactional
    public void deleteProduct(final Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        product.deleteBrand();

        productRepository.delete(product);
    }

    public CategoryPriceResponse getPriceBrandByCategory(final String category) {
        Category foundCategory = Category.from(category);

        List<BrandPrice> minPriceProducts = productRepository.findMinPriceProductsByCategory(foundCategory);
        List<BrandPrice> maxPriceProducts = productRepository.findMaxPriceProductsByCategory(foundCategory);

        return new CategoryPriceResponse(foundCategory.getName(), minPriceProducts, maxPriceProducts);
    }

    public CategoryMinPriceResponse getCategoriesMinPrice() {
        List<CategoryMinPrice> categoryMinPrices = productRepository.findCategoryMinPrice().stream()
                .distinct()
                .sorted(Comparator.comparingInt(cp -> cp.getCategory().getOrder()))
                .toList();

        List<CategoryBrandPrice> categoryBrandPrices = categoryMinPrices.stream()
                .map(CategoryBrandPrice::new)
                .toList();

        Long totalPrice = categoryMinPrices.stream()
                .mapToLong(CategoryMinPrice::getPrice)
                .sum();

        return new CategoryMinPriceResponse(categoryBrandPrices, totalPrice);
    }
}
