package calculator.core.math;

import calculator.core.Core;
import java.math.BigDecimal;
import java.math.RoundingMode;

public final class Precision {

    public static <T extends Number> String RoundUp(T x) {

        Number num = x;

        if (num instanceof BigDecimal || num instanceof Double) {
            BigDecimal number = new BigDecimal(num.toString());
            number = number.setScale(((number.scale() - 1 != 0) ? number.scale() - 1 : 1), RoundingMode.UP);
            return String.valueOf(number);
        }
        return String.valueOf(num);
    }

    public static <T extends Number> String RoundDown(T x) {
        Number num = x;

        if (num instanceof BigDecimal || num instanceof Double) {
            BigDecimal number = new BigDecimal(num.toString());
            number = number.setScale(((number.scale() - 1 != 0) ? number.scale() - 1 : 1), RoundingMode.DOWN);
            return String.valueOf(number);
        }
        return String.valueOf(num);
    }

    public static String HigherPrecision(String number, String defNum) {

        return defNum.substring(0, number.length()+1);

    }

    public static String LowerPrecision(String number) {

        BigDecimal num = new BigDecimal(number);

        return String.valueOf(num.setScale(num.scale() - 1, RoundingMode.HALF_UP));
    }
}
