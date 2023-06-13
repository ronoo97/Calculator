package calculator.core.math.functions;

import calculator.core.errors.UndefinedResultException;
import calculator.core.errors.UndefinedSolveException;
import java.math.BigDecimal;
import java.math.BigInteger;

public final class Log<T extends Number> extends Function {

    private final T num;

    public Log(T num) {
        this.num = num;
    }

    @Override
    public String Perform() throws UndefinedSolveException, UndefinedResultException {
        if (num instanceof BigDecimal || num instanceof BigInteger) {
            throw new UndefinedSolveException("Túl nagy bemeneti érték");
        }
        if (num.doubleValue() <= 0) {
            throw new UndefinedResultException("Nem definiált bemeneti érték");

        }
        return String.valueOf(Math.log10(num.doubleValue()));
    }

    @Override
    public String GetSymbol() {
        return "log";
    }

}
