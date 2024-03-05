package com.musinsa.fashionshopping.product.service;

import static com.musinsa.fashionshopping.fixture.BrandFixture.createBrandA;
import static com.musinsa.fashionshopping.fixture.BrandFixture.createBrandB;
import static com.musinsa.fashionshopping.fixture.ProductFixture.createProductAPants;
import static com.musinsa.fashionshopping.fixture.ProductFixture.createProductATop;
import static com.musinsa.fashionshopping.fixture.ProductFixture.createProductBTop;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

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
import com.musinsa.fashionshopping.product.exception.CategoryNotFoundException;
import com.musinsa.fashionshopping.product.exception.InvalidProductPriceException;
import com.musinsa.fashionshopping.product.exception.ProductNotFoundException;
import com.musinsa.fashionshopping.product.repository.ProductRepository;
import java.text.DecimalFormat;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    BrandRepository brandRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductService productService;

    private Brand brandA;
    private Brand brandB;

    private Product productATop;
    private Product productBTop;

    private Product productAPants;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
        brandRepository.deleteAll();

        brandA = createBrandA();
        brandB = createBrandB();
        brandRepository.saveAll(List.of(brandA, brandB));

        productATop = createProductATop();
        productBTop = createProductBTop();
        productAPants = createProductAPants();
        productATop.addBrand(brandA);
        productBTop.addBrand(brandB);
        productAPants.addBrand(brandA);
        productRepository.saveAll(List.of(productATop, productBTop, productAPants));

    }

    @DisplayName("모든 상품 조회에 성공한다.")
    @Test
    void findProducts() {
        //given
        DecimalFormat decimalFormat = new DecimalFormat("#,###");

        //when
        List<ProductResponse> products = productService.findProducts();

        //then
        assertThat(products.size()).isEqualTo(3);
        assertThat(products.get(0).getId()).isEqualTo(productATop.getId());
        assertThat(products.get(0).getCategory()).isEqualTo(productATop.getCategory().getName());
        assertThat(products.get(0).getPrice()).isEqualTo(
                decimalFormat.format(productATop.getProductPrice().getPrice()));
    }

    @DisplayName("상품을 생성할 수 있다.")
    @Test
    void createProduct() {
        //given
        int productSize = productRepository.findAll().size();
        Long price = 10_000L;
        Category category = Category.TOP;
        NewProductRequest newProductRequest = new NewProductRequest(price, "TOP");

        //when
        productService.createProduct(brandA.getId(), newProductRequest);
        List<Product> products = productRepository.findAll();

        //then
        assertThat(products).isNotNull();
        assertThat(products.size()).isEqualTo(productSize + 1);
        assertThat(products.get(productSize).getCategory()).isEqualTo(category);
        assertThat(products.get(productSize).getBrand().getId()).isEqualTo(brandA.getId());
    }

    @DisplayName("존재하지 않는 브랜드의 상품 생성 시 예외가 발생한다.")
    @Test
    void createProduct_Exception_InvalidBrand() {
        //given
        Long price = 10_000L;
        String category = "TOP";
        NewProductRequest newProductRequest = new NewProductRequest(price, category);

        //when & then
        assertThatThrownBy(() -> productService.createProduct(3L, newProductRequest))
                .isInstanceOf(BrandNotFoundException.class);
    }

    @DisplayName("존재하지 않는 카테고리로 생성 시 예외가 발생한다.")
    @Test
    void createProduct_Exception_InvalidCategory() {
        //given
        Long price = 10_000L;
        String invalidCategory = "MIDDLE";
        NewProductRequest newProductRequest = new NewProductRequest(price, invalidCategory);

        //when & then
        assertThatThrownBy(() -> productService.createProduct(brandA.getId(), newProductRequest))
                .isInstanceOf(CategoryNotFoundException.class);
    }

    @DisplayName("상품 금액 수정에 성공한다.")
    @Test
    void editProductPrice() {
        //given
        Category category = productATop.getCategory();
        Long toChangePrice = 100_000L;
        PriceUpdateRequest priceUpdateRequest = new PriceUpdateRequest(toChangePrice);

        //when
        productService.editPrice(productATop.getId(), priceUpdateRequest);

        //then
        Product foundProduct = productRepository.findById(productATop.getId()).get();

        assertThat(foundProduct.getProductPrice()).isEqualTo(new ProductPrice(toChangePrice));
        assertThat(foundProduct.getCategory()).isEqualTo(category);
    }

    @DisplayName("범위에 벗어난 가격으로 상품 가격 수정 시 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(longs = {-1, 100_000_000_000L, 0})
    void editProductPrice_Exception_InvalidPrice(final Long invalidPrice) {
        //given
        PriceUpdateRequest priceUpdateRequest = new PriceUpdateRequest(invalidPrice);

        //when & then
        assertThatThrownBy(() -> productService.editPrice(productATop.getId(), priceUpdateRequest))
                .isInstanceOf(InvalidProductPriceException.class);
    }

    @DisplayName("존재하지 않는 상품으로 가격 수정 시 예외가 발생한다.")
    @Test
    void editProductPrice_Exception_InvalidProduct() {
        //given
        Long price = 10_000L;
        Long invalidProductId = 4L;
        productRepository.deleteAll();
        PriceUpdateRequest priceUpdateRequest = new PriceUpdateRequest(price);

        //when & then
        assertThatThrownBy(() -> productService.editPrice(invalidProductId, priceUpdateRequest))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @DisplayName("상품 카테고리 수정에 성공한다.")
    @Test
    void editCategory() {
        //given
        Long price = productATop.getProductPrice().getPrice();
        String toChangeCategory = "BAG";
        CategoryUpdateRequest categoryUpdateRequest = new CategoryUpdateRequest(toChangeCategory);

        //when
        productService.editCategory(productATop.getId(), categoryUpdateRequest);

        //then
        Product foundProduct = productRepository.findById(productATop.getId()).get();

        assertThat(foundProduct.getProductPrice()).isEqualTo(new ProductPrice(price));
        assertThat(foundProduct.getCategory()).isEqualTo(Category.from(toChangeCategory));
    }

    @DisplayName("존재하지 않는 상품의 카테고리 수정 시 예외가 발생한다.")
    @Test
    void editCategory_Exception_InvalidProduct() {
        //given
        String category = "TOP";
        Long invalidProductId = 4L;
        CategoryUpdateRequest categoryUpdateRequest = new CategoryUpdateRequest(category);

        //when & then
        assertThatThrownBy(() -> productService.editCategory(invalidProductId, categoryUpdateRequest))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @DisplayName("존재하지 않는 카테고리로 수정 시 예외가 발생한다.")
    @Test
    void editCategory_Exception_InvalidCategory() {
        //given
        String invalidCategory = "TOPP";
        CategoryUpdateRequest categoryUpdateRequest = new CategoryUpdateRequest(invalidCategory);

        //when & then
        assertThatThrownBy(() -> productService.editCategory(productATop.getId(), categoryUpdateRequest))
                .isInstanceOf(CategoryNotFoundException.class);
    }

    @DisplayName("상품 삭제에 성공한다.")
    @Test
    void deleteProduct() {
        //given & when
        productService.deleteProduct(productATop.getId());

        //then
        assertThatThrownBy(() -> productRepository.findById(productATop.getId()).get())
                .isInstanceOf(NoSuchElementException.class);
    }

    @DisplayName("존재하지 않는 상품 삭제 시 예외가 발생한다.")
    @Test
    void deleteProduct_Exception_InvalidProduct() {
        //given
        Long invalidProductId = 1L;
        productRepository.deleteAll();

        //when & then
        assertThatThrownBy(() -> productService.deleteProduct(invalidProductId))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @DisplayName("특정 카테고리의 최저, 최고 상품의 브랜드와 가격을 반환한다.")
    @Test
    void findPriceBrandByCategory() {
        //given
        String category = "TOP";

        //when
        CategoryPriceResponse priceBrandByCategory = productService.getPriceBrandByCategory(category);

        //then
        assertThat(priceBrandByCategory.getCategory()).isEqualTo("상의");
        assertThat(priceBrandByCategory.getMinBrandPrice().size()).isEqualTo(1);
        assertThat(priceBrandByCategory.getMinBrandPrice().size()).isEqualTo(1);

        BrandPrice minBrandPrice = priceBrandByCategory.getMinBrandPrice().get(0);
        BrandPrice maxBrandPrice = priceBrandByCategory.getMaxBrandPrice().get(0);

        assertThat(minBrandPrice.getBrandName()).isEqualTo("B");
        assertThat(minBrandPrice.getPrice()).isEqualTo("10,500");
        assertThat(maxBrandPrice.getBrandName()).isEqualTo("A");
        assertThat(maxBrandPrice.getPrice()).isEqualTo("11,200");
    }

    @DisplayName("카테고리별 최저 가격의 브랜드와 가격을 반환한다.")
    @Test
    void findCategoryMinPrices() {
        //given & when
        CategoryMinPriceResponse categoriesMinPrice = productService.getCategoriesMinPrice();
        List<CategoryBrandPrice> categories = categoriesMinPrice.getCategories();

        //then
        assertThat(categoriesMinPrice.getTotalPrice()).isEqualTo("110,500");
        assertThat(categories).isNotNull();
        assertThat(categories.size()).isEqualTo(2);
        assertThat(categories.get(0).getCategory()).isEqualTo("상의");
        assertThat(categories.get(1).getCategory()).isEqualTo("바지");
    }
}
