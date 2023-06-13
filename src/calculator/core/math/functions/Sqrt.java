package calculator.core.math.functions;

import calculator.core.errors.UndefinedSolveException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

public final class Sqrt<T extends Number> extends Function {

    private final T num;

    public Sqrt(T num) {
        this.num = num;
    }

    @Override
    public String Perform() {

        if (num instanceof BigDecimal number) {
            return String.valueOf(number.sqrt(new MathContext(10, RoundingMode.HALF_UP)));
        } else if (num instanceof BigInteger number) {
            return String.valueOf(number.sqrt());
        }

        return String.valueOf(Math.sqrt(num.doubleValue()));

    }

    @Override
    public String GetSymbol() {
        return "sqrt";
    }

}
