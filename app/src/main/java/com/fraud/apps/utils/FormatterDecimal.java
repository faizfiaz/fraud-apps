package com.fraud.apps.utils;

import java.math.BigDecimal;
import java.text.DecimalFormatSymbols;

public class FormatterDecimal {
    private static java.text.DecimalFormat decimalFormat;

    public static String PERCENT = "percent";
    public static String RUPIAH = "rupiah";

    private static void makeFormatter(String format) {
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setGroupingSeparator('.');
        decimalFormat = new java.text.DecimalFormat(format, decimalFormatSymbols);
    }

    public static String decimalFormat(Number data) {
        makeFormatter("#,##0");
        return decimalFormat.format(data);
    }

    public static String decimalFormat(Number data, int digit) {
        String[] nums = String.valueOf(data).split("\\.");
        return Integer.parseInt(nums[1]) > 0 ? String.format("%." + digit + "f", data) : nums[0];
    }

    public static long split(String price) {
        price = price != null ? price : "0";
        String split = price;
        if (price.contains(".")) {
            split = price.split("\\.")[0];
        }
        return Long.parseLong(split);
    }

    public static String priceFormat(Number data) {
        makeFormatter("###,##0");
        return "Rp. " + decimalFormat.format(data);
    }

    public static String qtyFormat(Number data) {
        String check = String.valueOf(data);
        String[] split = check.split("\\.");
        if (split.length > 1 && Integer.parseInt(split[1]) * 1 > 0) {
            return check;
        } else {
            return split[0];
        }
    }

    public static BigDecimal convertStringValue(String value) {
        return new BigDecimal(value);
    }

    public static String convertStringDisplay(String value) {
        return decimalFormat(new BigDecimal(value));
    }

    public static String convertBigDecimalDisplay(BigDecimal value) {
        makeFormatter("###,##0");
        return "Rp" + decimalFormat(value);
    }

}
