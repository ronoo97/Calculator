package calculator.core.math.operations;

import java.math.BigDecimal;
import java.math.BigInteger;

public final class Multiply extends Operation {

    @Override
    public <T1 extends Number, T2 extends Number> String Perform(T1 x, T2 y) {

        Number num1 = x;
        Number num2 = y;

        if (num1 instanceof BigDecimal || num2 instanceof BigDecimal) {
            BigDecimal number1 = new BigDecimal(num1.toString());
            BigDecimal number2 = new BigDecimal(num2.toString());
            return String.valueOf(number1.multiply(number2));
        } else if (num1 instanceof BigInteger || num2 instanceof BigInteger) {
            BigInteger number1 = new BigInteger(num1.toString());
            BigInteger number2 = new BigInteger(num2.toString());
            return String.valueOf(number1.multiply(number2));
        } else {
            double number1 = num1.doubleValue();
            double number2 = num2.doubleValue();
            return String.valueOf(number1 * number2);
        }
    }

    @Override
    public String GetSymbol() {
        return "×";
    }

}
