package calculator.core.math.functions;

import calculator.core.errors.UndefinedResultException;
import calculator.core.errors.UndefinedSolveException;

public final class Fact<T extends Number> extends Function {

    private final T num;

    public Fact(T num) {
        this.num = num;
    }

    @Override
    public String Perform() throws UndefinedResultException {

        if (num.doubleValue() < 0) {
            throw new UndefinedResultException("Nem definiált bemeneti érték");
        }

        return String.valueOf(factorial(num.intValue()));

    }

    public double factorial(int number) {
        if (number == 0) {
            return 1;
        } else {
            return number * factorial(number - 1);
        }
    }

    @Override
    public String GetSymbol() {
        return "fact";
    }

}
