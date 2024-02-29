package com.musinsa.fashionshopping.brand.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musinsa.fashionshopping.brand.controller.dto.NewBrandRequest;
import com.musinsa.fashionshopping.brand.exception.InvalidBrandNameException;
import com.musinsa.fashionshopping.brand.service.BrandService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class BrandControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private BrandService brandService;

    @DisplayName("브랜드를 생성하면 201을 반환한다.")
    @Test
    void createBrand() throws Exception {
        //given
        NewBrandRequest newBrandRequest = new NewBrandRequest("nike");
        String brandRequestJson = objectMapper.writeValueAsString(newBrandRequest);

        //when
        doNothing()
                .when(brandService)
                .createBrand(newBrandRequest);

        //then
        mockMvc.perform(
                        post("/brands")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(brandRequestJson))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @DisplayName("브랜드 생성 요청에 빈 이름에 대해 400을 반환한다.")
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "  "})
    void createBrand_Exception_NoName(final String name) throws Exception {
        //given
        NewBrandRequest newBrandRequest = new NewBrandRequest(name);
        String brandRequestJson = objectMapper.writeValueAsString(newBrandRequest);

        //then
        mockMvc.perform(
                        post("/brands")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(brandRequestJson))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @DisplayName("브랜드 이름 등록 시 잘못된 형식이면 400 반환한다.")
    @ParameterizedTest
    @ValueSource(strings = {"ㄱㄴㄷ", "abdf123ㅏㅇ", "11112222333344445"})
    void createBrand_Exception_InvalidFormat(final String invalidBrandName) throws Exception {
        //given
        NewBrandRequest newBrandRequest = new NewBrandRequest(invalidBrandName);
        String brandRequestJson = objectMapper.writeValueAsString(newBrandRequest);

        //when
        doThrow(InvalidBrandNameException.class)
                .when(brandService)
                .createBrand(any());

        //when & then
        mockMvc.perform(
                        post("/brands")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(brandRequestJson))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}
