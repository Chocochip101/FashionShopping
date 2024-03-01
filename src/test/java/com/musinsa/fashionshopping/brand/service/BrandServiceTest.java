package com.musinsa.fashionshopping.brand.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.musinsa.fashionshopping.brand.controller.dto.BrandNameUpdateRequest;
import com.musinsa.fashionshopping.brand.controller.dto.LowestPriceInfo;
import com.musinsa.fashionshopping.brand.controller.dto.NewBrandRequest;
import com.musinsa.fashionshopping.brand.domain.Brand;
import com.musinsa.fashionshopping.brand.domain.BrandName;
import com.musinsa.fashionshopping.brand.exception.BrandNotFoundException;
import com.musinsa.fashionshopping.brand.exception.DuplicateBrandNameException;
import com.musinsa.fashionshopping.brand.exception.InvalidBrandNameException;
import com.musinsa.fashionshopping.brand.exception.ProductInsufficientException;
import com.musinsa.fashionshopping.brand.repository.BrandRepository;
import com.musinsa.fashionshopping.product.domain.Category;
import com.musinsa.fashionshopping.product.domain.Product;
import com.musinsa.fashionshopping.product.domain.ProductPrice;
import com.musinsa.fashionshopping.product.repository.ProductRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BrandServiceTest {
    @Autowired
    BrandRepository brandRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    BrandService brandService;

    @BeforeEach
    void setUp() {
        brandRepository.deleteAll();
        productRepository.deleteAll();
    }

    @DisplayName("브랜드를 생성할 수 있다.")
    @Test
    void createBrand() {
        //given
        String brandName = "nike";
        NewBrandRequest newBrandRequest = new NewBrandRequest(brandName);

        //when
        brandService.createBrand(newBrandRequest);
        final List<Brand> brands = brandRepository.findAll();

        //then
        assertThat(brands).isNotNull();
        assertThat(brands.get(0).getBrandName().getValue()).isEqualTo(brandName);
    }

    @DisplayName("숫자와 영문, 한글음절을 포함한 1자 이상 16자이하가 아닌 잘못된 형식의 브랜드 이름으로 등록할 시 예외 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "ㄱㄴㄷ", "abdf123ㅏㅇ", "11112222333344445"})
    void createBrandName_Exception_InvalidFormat(String invalidBrandName) {
        //given
        NewBrandRequest newBrandRequest = new NewBrandRequest(invalidBrandName);

        //when & then
        assertThatThrownBy(() -> brandService.createBrand(newBrandRequest))
                .isInstanceOf(InvalidBrandNameException.class);
    }

    @DisplayName("중복된 브랜드 이름 등록 시 예외가 발생한다.")
    @Test
    void createBrandName_Exception_Duplicated() {
        //given
        String brandName = "nike";
        Brand brand = Brand.builder()
                .brandName(new BrandName(brandName))
                .build();
        brandRepository.save(brand);

        //when
        NewBrandRequest newBrandRequest = new NewBrandRequest(brandName);

        //then
        assertThatThrownBy(() -> brandService.createBrand(newBrandRequest))
                .isInstanceOf(DuplicateBrandNameException.class);
    }

    @DisplayName("닉네임 수정에 성공한다.")
    @Test
    void editBrandName() {
        //given
        String brandName = "nike";
        Brand brand = Brand.builder()
                .brandName(new BrandName(brandName))
                .build();
        brandRepository.save(brand);

        String toChangeName = "나이키";
        BrandNameUpdateRequest brandNameUpdateRequest = new BrandNameUpdateRequest(brand.getId(), toChangeName);

        //when
        brandService.editBrandName(brandNameUpdateRequest);
        Brand foundBrand = brandRepository.findById(brand.getId()).get();

        //then
        assertThat(foundBrand.getBrandName().getValue()).isEqualTo(toChangeName);
    }

    @DisplayName("브랜드 삭제에 성공한다.")
    @Test
    void deleteBrand() {
        //given
        String brandName = "nike";
        Brand brand = Brand.builder()
                .brandName(new BrandName(brandName))
                .build();
        brandRepository.save(brand);

        Product product = Product.builder()
                .productPrice(new ProductPrice(10_00L))
                .category(Category.ACCESSORY)
                .brand(brand)
                .build();
        product.addBrand(brand);
        productRepository.save(product);

        //when
        brandService.deleteBrand(brand.getId());

        //then
        assertThat(brandRepository.findAll().size()).isEqualTo(0);
        assertThat(productRepository.findAll().size()).isEqualTo(0);
    }

    @DisplayName("존재하지 않는 브랜드 삭제 시 예외가 발생한다.")
    @Test
    void deleteBrand_Exception_InvalidBrand() {
        //given
        Long invalidBrandId = 4L;

        //when & then
        assertThatThrownBy(() -> brandService.deleteBrand(invalidBrandId))
                .isInstanceOf(BrandNotFoundException.class);
    }

    @DisplayName("최저 가격인 단일 브랜드의 카테고리 상품 조회에 성공한다.")
    @Test
    void findMinPriceBrandCategory() {
        //given
        Brand brandA = Brand.builder()
                .brandName(new BrandName("A"))
                .build();
        Brand brandB = Brand.builder()
                .brandName(new BrandName("B"))
                .build();
        brandRepository.saveAll(List.of(brandA, brandB));

        long priceA = 1000L;
        Product productA = Product.builder()
                .productPrice(new ProductPrice(priceA))
                .category(Category.ACCESSORY)
                .brand(brandA)
                .build();
        long priceB = 100000L;
        Product productB = Product.builder()
                .productPrice(new ProductPrice(priceB))
                .category(Category.TOP)
                .brand(brandB)
                .build();

        productRepository.saveAll(List.of(productA, productB));

        //when
        final LowestPriceInfo response = brandService.getMinPriceCategoryAndTotal().getLowestPrice();

        //then
        assertThat(response.getBrand()).isEqualTo("A");
        assertThat(response.getTotalPrice()).isEqualTo("1,000");
    }

    @DisplayName("상품 부족으로 최저 가격의 브랜드를 계산 불가 시 예외가 발생한다.")
    @Test
    void findMinPriceBrandCategory_Exception_InsufficientProduct() {
        //given &  when & then
        assertThatThrownBy(() -> brandService.getMinPriceCategoryAndTotal())
                .isInstanceOf(ProductInsufficientException.class);
    }
}
