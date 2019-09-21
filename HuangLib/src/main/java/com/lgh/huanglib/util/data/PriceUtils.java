package com.lgh.huanglib.util.data;


import com.lgh.huanglib.R;

import java.text.DecimalFormat;

public class PriceUtils {

    public static DecimalFormat decimalFormat = new DecimalFormat("0.00");

    public static String formatPriceUnit(String calcTotalPrice) {
        return ResUtil.getFormatString(R.string.format_unit_price, calcTotalPrice);
    }

    public static String formatPriceAndUnit(String calcTotalPrice) {
        return ResUtil.getFormatString(R.string.format_unit_price, formatPriceText(calcTotalPrice));
    }

    public static String formatPrice(double calcTotalPrice) {
        return decimalFormat.format(calcTotalPrice);
    }

    public static String formatPriceText(String calcTotalPrice) {
        return decimalFormat.format(Double.parseDouble(calcTotalPrice));
    }

    public static String addComma(String str) {
        DecimalFormat decimalFormat = new DecimalFormat(",###");
        return decimalFormat.format(Double.parseDouble(str));
    }
}
