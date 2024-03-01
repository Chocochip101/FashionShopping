package com.musinsa.fashionshopping.product.controller;

import com.musinsa.fashionshopping.product.controller.dto.NewProductRequest;
import com.musinsa.fashionshopping.product.service.ProductService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/brands/{id}/products")
    public ResponseEntity<Void> addProduct(@PathVariable(name = "id") Long brandId,
                                           @Valid @RequestBody NewProductRequest newProductRequest) {
        productService.createProduct(brandId, newProductRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
