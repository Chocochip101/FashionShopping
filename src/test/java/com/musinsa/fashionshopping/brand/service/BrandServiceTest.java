package com.musinsa.fashionshopping.brand.service;

import static com.musinsa.fashionshopping.fixture.BrandFixture.createBrandA;
import static com.musinsa.fashionshopping.fixture.BrandFixture.createBrandB;
import static com.musinsa.fashionshopping.fixture.ProductFixture.createProductATop;
import static com.musinsa.fashionshopping.fixture.ProductFixture.createProductBTop;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.musinsa.fashionshopping.brand.controller.dto.BrandNameUpdateRequest;
import com.musinsa.fashionshopping.brand.controller.dto.BrandResponse;
import com.musinsa.fashionshopping.brand.controller.dto.LowestPriceInfo;
import com.musinsa.fashionshopping.brand.controller.dto.NewBrandRequest;
import com.musinsa.fashionshopping.brand.domain.Brand;
import com.musinsa.fashionshopping.brand.exception.BrandNotFoundException;
import com.musinsa.fashionshopping.brand.exception.DuplicateBrandNameException;
import com.musinsa.fashionshopping.brand.exception.InvalidBrandNameException;
import com.musinsa.fashionshopping.brand.exception.ProductInsufficientException;
import com.musinsa.fashionshopping.brand.repository.BrandRepository;
import com.musinsa.fashionshopping.product.domain.Product;
import com.musinsa.fashionshopping.product.repository.ProductRepository;
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
class BrandServiceTest {
    @Autowired
    BrandRepository brandRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    BrandService brandService;

    private Brand brandA;
    private Brand brandB;

    private Product productA;
    private Product productB;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
        brandRepository.deleteAll();

        brandA = createBrandA();
        brandB = createBrandB();
        brandRepository.saveAll(List.of(brandA, brandB));

        productA = createProductATop();
        productB = createProductBTop();
        productA.addBrand(brandA);
        productB.addBrand(brandB);
        productRepository.saveAll(List.of(productA, productB));
    }

    @DisplayName("브랜드 전체 조회에 성공한다.")
    @Test
    void findBrands() {
        //given & when
        List<BrandResponse> brands = brandService.findBrands();

        //then
        assertThat(brands.size()).isEqualTo(2);
        assertThat(brands.get(0).getName()).isEqualTo("A");
        assertThat(brands.get(1).getName()).isEqualTo("B");
    }

    @DisplayName("브랜드를 생성할 수 있다.")
    @Test
    void createBrand() {
        //given
        String brandName = "nike";
        int brandSize = brandRepository.findAll().size();
        NewBrandRequest newBrandRequest = new NewBrandRequest(brandName);

        //when
        brandService.createBrand(newBrandRequest);
        List<Brand> brands = brandRepository.findAll();

        //then
        assertThat(brands).isNotNull();
        assertThat(brands.size()).isEqualTo(brandSize + 1);
        assertThat(brands.get(brandSize).getBrandName().getValue()).isEqualTo(brandName);
    }

    @DisplayName("숫자와 영문, 한글음절을 포함한 1자 이상 16자이하가 아닌 잘못된 형식의 브랜드 이름으로 등록할 시 예외 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "ㄱㄴㄷ", "abdf123ㅏㅇ", "11112222333344445"})
    void createBrandName_Exception_InvalidFormat(final String invalidBrandName) {
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
        String duplicatedBrandName = "A";

        //when
        NewBrandRequest newBrandRequest = new NewBrandRequest(duplicatedBrandName);

        //then
        assertThatThrownBy(() -> brandService.createBrand(newBrandRequest))
                .isInstanceOf(DuplicateBrandNameException.class);
    }

    @DisplayName("브랜드명 수정에 성공한다.")
    @Test
    void editBrandName() {
        //given
        String toChangeName = "나이키";
        BrandNameUpdateRequest brandNameUpdateRequest = new BrandNameUpdateRequest(toChangeName);

        //when
        brandService.editBrandName(brandA.getId(), brandNameUpdateRequest);
        Brand foundBrand = brandRepository.findById(brandA.getId()).get();

        //then
        assertThat(foundBrand.getBrandName().getValue()).isEqualTo(toChangeName);
    }

    @DisplayName("브랜드 삭제에 성공한다.")
    @Test
    void deleteBrand() {
        //given & when
        brandService.deleteBrand(brandA.getId());

        //then
        assertThatThrownBy(() -> brandRepository.findById(brandA.getId()).get())
                .isInstanceOf(NoSuchElementException.class);
        assertThatThrownBy(() -> productRepository.findById(productA.getId()).get())
                .isInstanceOf(NoSuchElementException.class);
    }

    @DisplayName("존재하지 않는 브랜드 삭제 시 예외가 발생한다.")
    @Test
    void deleteBrand_Exception_InvalidBrand() {
        //given
        Long invalidBrandId = 258L;

        //when & then
        assertThatThrownBy(() -> brandService.deleteBrand(invalidBrandId))
                .isInstanceOf(BrandNotFoundException.class);
    }

    @DisplayName("최저 가격인 단일 브랜드의 카테고리 상품 조회에 성공한다.")
    @Test
    void findMinPriceBrandCategory() {
        //given & when
        LowestPriceInfo response = brandService.getMinPriceCategoryAndTotal().getLowestPrice();

        //then
        assertThat(response.getBrand()).isEqualTo("B");
        assertThat(response.getTotalPrice()).isEqualTo("10,500");
    }

    @DisplayName("상품 부족으로 최저 가격의 브랜드를 계산 불가 시 예외가 발생한다.")
    @Test
    void findMinPriceBrandCategory_Exception_InsufficientProduct() {
        //given
        productRepository.deleteAll();

        //when & then
        assertThatThrownBy(() -> brandService.getMinPriceCategoryAndTotal())
                .isInstanceOf(ProductInsufficientException.class);
    }
}
