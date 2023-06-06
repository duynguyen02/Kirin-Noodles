package bomoncntt.svk62.mssv2051067158.utils;

import java.text.DecimalFormat;

public class CurrencyHelper {
    public static String currencyConverter(double value){
        return new DecimalFormat("#,###").format(value);
    }
}
