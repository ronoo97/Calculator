package calculator.core.math.functions;

import calculator.core.errors.UndefinedSolveException;
import java.math.BigDecimal;
import java.math.BigInteger;

public final class Atan<T extends Number> extends Function {

    private final T num;

    public Atan(T num) {
        this.num = num;
    }

    @Override
    public String Perform() throws UndefinedSolveException {
        if (num instanceof BigDecimal || num instanceof BigInteger) {
            throw new UndefinedSolveException("Túl nagy bemeneti érték");
        }

        return String.valueOf(Math.atan(num.doubleValue()));
        
    }

    @Override
    public String GetSymbol() {
        return "atan";
    }

}
