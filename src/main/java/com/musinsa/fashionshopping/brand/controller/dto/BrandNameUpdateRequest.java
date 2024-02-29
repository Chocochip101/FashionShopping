package com.musinsa.fashionshopping.brand.controller.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BrandNameUpdateRequest {
    @NotNull
    private Long id;
    @NotBlank
    private String name;
}
