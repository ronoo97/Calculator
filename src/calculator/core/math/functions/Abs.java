package calculator.core.math.functions;

import calculator.core.errors.UndefinedSolveException;
import java.math.BigDecimal;
import java.math.BigInteger;

public final class Abs<T extends Number> extends Function {

    private final T num;

    public Abs(T num) {
        this.num = num;
    }

    @Override
    public String Perform() {
        
        if (num instanceof BigDecimal number) {
            return String.valueOf(number.abs());
        } else if (num instanceof BigInteger number) {
            return String.valueOf(number.abs());
        }

        return String.valueOf(Math.abs(num.doubleValue()));

    }

    @Override
    public String GetSymbol() {
        return "abs";
    }

}
