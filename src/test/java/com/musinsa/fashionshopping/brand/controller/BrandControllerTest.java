package com.musinsa.fashionshopping.brand.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musinsa.fashionshopping.brand.service.BrandService;
import com.musinsa.fashionshopping.brand.controller.dto.NewBrandRequest;
import com.musinsa.fashionshopping.brand.controller.dto.NewBrandResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

    @Test
    @DisplayName("브랜드를 생성하면 201을 반환한다.")
    void createBrand() throws Exception {
        //given
        NewBrandRequest newBrandRequest = new NewBrandRequest("nike");
        String brandRequestJson = objectMapper.writeValueAsString(newBrandRequest);

        //when
        doReturn(new NewBrandResponse(1L))
                .when(brandService)
                .createBrand(any());


        //then
        mockMvc.perform(
                        post("/brands")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(brandRequestJson))
                .andExpect(status().isCreated())
                .andDo(print());
    }
}
