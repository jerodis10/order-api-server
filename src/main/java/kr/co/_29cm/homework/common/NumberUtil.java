package kr.co._29cm.homework.common;

import java.text.DecimalFormat;

public class NumberUtil {

    public static String numberFormatter(Long number) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        return decimalFormat.format(number);
    }

    public static boolean isNumeric(String s) {
        try {
            s = s.replace(",", "");
            Long.parseLong(s);
        } catch (NullPointerException | NumberFormatException e) {
            return false;
        }
        return true;
    }
}
