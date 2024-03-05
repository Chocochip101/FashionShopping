package com.musinsa.fashionshopping.global;

import java.text.DecimalFormat;

public class PriceFormatter {

    private static final DecimalFormat decimalFormat = new DecimalFormat("#,###");

    public static String convert(final Long price) {
        return decimalFormat.format(price);
    }
}
