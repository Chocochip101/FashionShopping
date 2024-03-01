package com.musinsa.fashionshopping;

import com.musinsa.fashionshopping.brand.domain.Brand;
import com.musinsa.fashionshopping.brand.domain.BrandName;
import com.musinsa.fashionshopping.brand.repository.BrandRepository;
import com.musinsa.fashionshopping.product.domain.Category;
import com.musinsa.fashionshopping.product.domain.Product;
import com.musinsa.fashionshopping.product.domain.ProductPrice;
import com.musinsa.fashionshopping.product.repository.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {
    @Value("${dataloader.enable}")
    private boolean enableDataloader;

    private final BrandRepository brandRepository;
    private final ProductRepository productRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (enableDataloader) {
            runDataLoader();
        }
    }

    private void runDataLoader() {
        Brand A = Brand.builder()
                .brandName(new BrandName("A"))
                .build();
        Brand B = Brand.builder()
                .brandName(new BrandName("B"))
                .build();
        Brand C = Brand.builder()
                .brandName(new BrandName("C"))
                .build();
        Brand D = Brand.builder()
                .brandName(new BrandName("D"))
                .build();
        Brand E = Brand.builder()
                .brandName(new BrandName("E"))
                .build();
        Brand F = Brand.builder()
                .brandName(new BrandName("F"))
                .build();
        Brand G = Brand.builder()
                .brandName(new BrandName("G"))
                .build();
        Brand H = Brand.builder()
                .brandName(new BrandName("H"))
                .build();
        Brand I = Brand.builder()
                .brandName(new BrandName("I"))
                .build();

        List<Brand> brands = List.of(A, B, C, D, E, F, G, H, I);

        brandRepository.saveAll(brands);

        List<Product> products = new ArrayList<>();
        products.add(Product.builder().brand(A).productPrice(new ProductPrice(1120L)).category(Category.TOP).build());
        products.add(Product.builder().brand(A).productPrice(new ProductPrice(5500L)).category(Category.OUTER).build());
        products.add(Product.builder().brand(A).productPrice(new ProductPrice(4200L)).category(Category.PANTS).build());
        products.add(
                Product.builder().brand(A).productPrice(new ProductPrice(9000L)).category(Category.SNEAKERS).build());
        products.add(Product.builder().brand(A).productPrice(new ProductPrice(2000L)).category(Category.BAG).build());
        products.add(Product.builder().brand(A).productPrice(new ProductPrice(1700L)).category(Category.HAT).build());
        products.add(Product.builder().brand(A).productPrice(new ProductPrice(1800L)).category(Category.SOCKS).build());
        products.add(
                Product.builder().brand(A).productPrice(new ProductPrice(2300L)).category(Category.ACCESSORY).build());

        products.add(Product.builder().brand(B).productPrice(new ProductPrice(10500L)).category(Category.TOP).build());
        products.add(Product.builder().brand(B).productPrice(new ProductPrice(5900L)).category(Category.OUTER).build());
        products.add(Product.builder().brand(B).productPrice(new ProductPrice(3800L)).category(Category.PANTS).build());
        products.add(
                Product.builder().brand(B).productPrice(new ProductPrice(9100L)).category(Category.SNEAKERS).build());
        products.add(Product.builder().brand(B).productPrice(new ProductPrice(2100L)).category(Category.BAG).build());
        products.add(Product.builder().brand(B).productPrice(new ProductPrice(2000L)).category(Category.HAT).build());
        products.add(Product.builder().brand(B).productPrice(new ProductPrice(2000L)).category(Category.SOCKS).build());
        products.add(
                Product.builder().brand(B).productPrice(new ProductPrice(2200L)).category(Category.ACCESSORY).build());

        products.add(Product.builder().brand(C).productPrice(new ProductPrice(10000L)).category(Category.TOP).build());
        products.add(Product.builder().brand(C).productPrice(new ProductPrice(6200L)).category(Category.OUTER).build());
        products.add(Product.builder().brand(C).productPrice(new ProductPrice(3300L)).category(Category.PANTS).build());
        products.add(
                Product.builder().brand(C).productPrice(new ProductPrice(9200L)).category(Category.SNEAKERS).build());
        products.add(Product.builder().brand(C).productPrice(new ProductPrice(2200L)).category(Category.BAG).build());
        products.add(Product.builder().brand(C).productPrice(new ProductPrice(1900L)).category(Category.HAT).build());
        products.add(Product.builder().brand(C).productPrice(new ProductPrice(2200L)).category(Category.SOCKS).build());
        products.add(
                Product.builder().brand(C).productPrice(new ProductPrice(2100L)).category(Category.ACCESSORY).build());

        products.add(Product.builder().brand(D).productPrice(new ProductPrice(10100L)).category(Category.TOP).build());
        products.add(Product.builder().brand(D).productPrice(new ProductPrice(5100L)).category(Category.OUTER).build());
        products.add(Product.builder().brand(D).productPrice(new ProductPrice(3000L)).category(Category.PANTS).build());
        products.add(
                Product.builder().brand(D).productPrice(new ProductPrice(9500L)).category(Category.SNEAKERS).build());
        products.add(Product.builder().brand(D).productPrice(new ProductPrice(2500L)).category(Category.BAG).build());
        products.add(Product.builder().brand(D).productPrice(new ProductPrice(1500L)).category(Category.HAT).build());
        products.add(Product.builder().brand(D).productPrice(new ProductPrice(2400L)).category(Category.SOCKS).build());
        products.add(
                Product.builder().brand(D).productPrice(new ProductPrice(2000L)).category(Category.ACCESSORY).build());

        products.add(Product.builder().brand(E).productPrice(new ProductPrice(10700L)).category(Category.TOP).build());
        products.add(Product.builder().brand(E).productPrice(new ProductPrice(5000L)).category(Category.OUTER).build());
        products.add(Product.builder().brand(E).productPrice(new ProductPrice(3800L)).category(Category.PANTS).build());
        products.add(
                Product.builder().brand(E).productPrice(new ProductPrice(9900L)).category(Category.SNEAKERS).build());
        products.add(Product.builder().brand(E).productPrice(new ProductPrice(2300L)).category(Category.BAG).build());
        products.add(Product.builder().brand(E).productPrice(new ProductPrice(1800L)).category(Category.HAT).build());
        products.add(Product.builder().brand(E).productPrice(new ProductPrice(2100L)).category(Category.SOCKS).build());
        products.add(
                Product.builder().brand(E).productPrice(new ProductPrice(2100L)).category(Category.ACCESSORY).build());

        products.add(Product.builder().brand(F).productPrice(new ProductPrice(11200L)).category(Category.TOP).build());
        products.add(Product.builder().brand(F).productPrice(new ProductPrice(7200L)).category(Category.OUTER).build());
        products.add(Product.builder().brand(F).productPrice(new ProductPrice(4000L)).category(Category.PANTS).build());
        products.add(
                Product.builder().brand(F).productPrice(new ProductPrice(9300L)).category(Category.SNEAKERS).build());
        products.add(Product.builder().brand(F).productPrice(new ProductPrice(2100L)).category(Category.BAG).build());
        products.add(Product.builder().brand(F).productPrice(new ProductPrice(1600L)).category(Category.HAT).build());
        products.add(Product.builder().brand(F).productPrice(new ProductPrice(2300L)).category(Category.SOCKS).build());
        products.add(
                Product.builder().brand(F).productPrice(new ProductPrice(1900L)).category(Category.ACCESSORY).build());

        products.add(Product.builder().brand(G).productPrice(new ProductPrice(10500L)).category(Category.TOP).build());
        products.add(Product.builder().brand(G).productPrice(new ProductPrice(5800L)).category(Category.OUTER).build());
        products.add(Product.builder().brand(G).productPrice(new ProductPrice(3900L)).category(Category.PANTS).build());
        products.add(
                Product.builder().brand(G).productPrice(new ProductPrice(9000L)).category(Category.SNEAKERS).build());
        products.add(Product.builder().brand(G).productPrice(new ProductPrice(2200L)).category(Category.BAG).build());
        products.add(Product.builder().brand(G).productPrice(new ProductPrice(1700L)).category(Category.HAT).build());
        products.add(Product.builder().brand(G).productPrice(new ProductPrice(2100L)).category(Category.SOCKS).build());
        products.add(
                Product.builder().brand(G).productPrice(new ProductPrice(2000L)).category(Category.ACCESSORY).build());

        products.add(Product.builder().brand(H).productPrice(new ProductPrice(10800L)).category(Category.TOP).build());
        products.add(Product.builder().brand(H).productPrice(new ProductPrice(6300L)).category(Category.OUTER).build());
        products.add(Product.builder().brand(H).productPrice(new ProductPrice(3100L)).category(Category.PANTS).build());
        products.add(
                Product.builder().brand(H).productPrice(new ProductPrice(9700L)).category(Category.SNEAKERS).build());
        products.add(Product.builder().brand(H).productPrice(new ProductPrice(2100L)).category(Category.BAG).build());
        products.add(Product.builder().brand(H).productPrice(new ProductPrice(1600L)).category(Category.HAT).build());
        products.add(Product.builder().brand(H).productPrice(new ProductPrice(2000L)).category(Category.SOCKS).build());
        products.add(
                Product.builder().brand(H).productPrice(new ProductPrice(2000L)).category(Category.ACCESSORY).build());

        products.add(Product.builder().brand(I).productPrice(new ProductPrice(11400L)).category(Category.TOP).build());
        products.add(Product.builder().brand(I).productPrice(new ProductPrice(6700L)).category(Category.OUTER).build());
        products.add(Product.builder().brand(I).productPrice(new ProductPrice(3200L)).category(Category.PANTS).build());
        products.add(
                Product.builder().brand(I).productPrice(new ProductPrice(9500L)).category(Category.SNEAKERS).build());
        products.add(Product.builder().brand(I).productPrice(new ProductPrice(2400L)).category(Category.BAG).build());
        products.add(Product.builder().brand(I).productPrice(new ProductPrice(1700L)).category(Category.HAT).build());
        products.add(Product.builder().brand(I).productPrice(new ProductPrice(1700L)).category(Category.SOCKS).build());
        products.add(
                Product.builder().brand(I).productPrice(new ProductPrice(2400L)).category(Category.ACCESSORY).build());
        productRepository.saveAll(products);
    }
}
