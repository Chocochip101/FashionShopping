package com.musinsa.fashionshopping.brand.controller.dto;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NewBrandRequest {
    @NotBlank
    private String name;
}
