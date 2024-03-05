package com.musinsa.fashionshopping.product.controller;

import com.musinsa.fashionshopping.product.controller.dto.CategoryMinPriceResponse;
import com.musinsa.fashionshopping.product.controller.dto.CategoryPriceResponse;
import com.musinsa.fashionshopping.product.controller.dto.CategoryUpdateRequest;
import com.musinsa.fashionshopping.product.controller.dto.NewProductRequest;
import com.musinsa.fashionshopping.product.controller.dto.PriceUpdateRequest;
import com.musinsa.fashionshopping.product.controller.dto.ProductResponse;
import com.musinsa.fashionshopping.product.service.ProductService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponse>> findProducts() {
        List<ProductResponse> products = productService.findProducts();
        return ResponseEntity.ok(products);
    }

    @PostMapping("/brands/{id}/products")
    public ResponseEntity<Void> addProduct(@PathVariable(name = "id") Long brandId,
                                           @Valid @RequestBody NewProductRequest newProductRequest) {
        productService.createProduct(brandId, newProductRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/products/{id}/price")
    public ResponseEntity<Void> editPrice(@PathVariable(name = "id") Long productId,
                                          @Valid @RequestBody PriceUpdateRequest priceUpdateRequest) {
        productService.editPrice(productId, priceUpdateRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/products/{id}/category")
    public ResponseEntity<Void> editCategory(@PathVariable(name = "id") Long productId,
                                             @Valid @RequestBody CategoryUpdateRequest categoryUpdateRequest) {
        productService.editCategory(productId, categoryUpdateRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable(name = "id") Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/categories/price-brand")
    public ResponseEntity<CategoryPriceResponse> findPriceBrandByCategory(@RequestParam String category) {
        CategoryPriceResponse priceBrandByCategory = productService.getPriceBrandByCategory(category);
        return ResponseEntity.ok(priceBrandByCategory);
    }

    @GetMapping("/categories/min-prices")
    public ResponseEntity<CategoryMinPriceResponse> findCategoryMinPrices() {
        final CategoryMinPriceResponse minPrice = productService.getCategoriesMinPrice();
        return ResponseEntity.ok(minPrice);
    }
}
