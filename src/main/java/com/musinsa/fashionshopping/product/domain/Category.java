package com.musinsa.fashionshopping.product.domain;

import com.musinsa.fashionshopping.product.exception.CategoryNotFoundException;

public enum Category {
    TOP("상의"),
    OUTER("아우터"),
    PANTS("바지"),
    SNEAKERS("스니커즈"),
    BAG("가방"),
    HAT("모자"),
    SOCKS("양말"),
    ACCESSORY("액세서리");

    private final String name;
    private final int order;

    Category(final String name) {
        this.name = name;
        this.order = ordinal();
    }

    public static Category from(String value) {
        for (Category type : Category.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new CategoryNotFoundException(value);
    }

    public String getName() {
        return name;
    }

    public int getOrder() {
        return order;
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
