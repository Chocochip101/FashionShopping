package com.musinsa.fashionshopping.product.domain;

import com.musinsa.fashionshopping.product.exception.CategoryNotFoundException;

public enum Category {
    TOP, OUTER, PANTS, SNEAKERS, BAG, HAT, SOCKS, ACCESSORY;

    public static Category from(String value) {
        for (Category type : Category.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new CategoryNotFoundException();
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
