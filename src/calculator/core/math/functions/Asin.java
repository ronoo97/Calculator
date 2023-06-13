package calculator.core.math.functions;

import calculator.core.errors.UndefinedResultException;
import calculator.core.errors.UndefinedSolveException;
import java.math.BigDecimal;
import java.math.BigInteger;

public final class Asin<T extends Number> extends Function {

    private final T num;

    public Asin(T num) {
        this.num = num;
    }

    @Override
    public String Perform() throws UndefinedSolveException, UndefinedResultException {
        if (num instanceof BigDecimal || num instanceof BigInteger) {
            throw new UndefinedSolveException("Túl nagy bemeneti érték");
        }
        double number = num.doubleValue();
        if (!(number <= 1 && number >= -1)) {
            throw new UndefinedResultException("Nem definiált bemeneti érték");
        }

        return String.valueOf(Math.asin(number));

    }

    @Override
    public String GetSymbol() {
        return "asin";
    }

}
