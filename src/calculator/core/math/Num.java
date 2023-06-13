package calculator.core.math;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.lang.Float;

public class Num {

    public static Number ParseNum(String str) {

        if (str.contains(",") || str.contains(".")) {
            try {
                double num = Double.parseDouble(str);
                return num;
            } catch (NumberFormatException e2) {
                BigDecimal num = new BigDecimal(str);
                return num;
            }

        } else {
            try {
                short num = Short.parseShort(str);
                return num;
            } catch (NumberFormatException e1) {
                try {
                    int num = Integer.parseInt(str);
                    return num;
                } catch (NumberFormatException e2) {
                    try {
                        long num = Long.parseLong(str);
                        return num;
                    } catch (NumberFormatException e3) {
                        BigInteger num = new BigInteger(str);
                        return num;
                    }
                }
            }
        }
    }

}
