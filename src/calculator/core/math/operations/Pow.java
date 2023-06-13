package calculator.core.math.operations;

import calculator.core.errors.UndefinedSolveException;
import java.math.BigDecimal;
import java.math.BigInteger;

public final class Pow extends Operation {

    @Override
    public <T1 extends Number, T2 extends Number> String Perform(T1 x, T2 y) throws UndefinedSolveException{

        Number num1 = x;
        Number num2 = y;

        if (num1 instanceof BigDecimal || num2 instanceof BigDecimal || num1 instanceof BigInteger || num2 instanceof BigInteger) {
            throw new UndefinedSolveException("Túl nagy bemeneti érték");
        } else {
            double number1 = num1.doubleValue();
            double number2 = num2.doubleValue();
            return String.valueOf(Math.pow(number1, number2));
        }
    }

    @Override
    public String GetSymbol() {
        return "^";
    }

}
