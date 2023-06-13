package calculator.core.math.functions;

import calculator.core.errors.UndefinedResultException;
import calculator.core.errors.UndefinedSolveException;

public abstract class Function {

    public static final String[] FUNCTIONS = {"asin", "acos", "atan", "abs", "fact", "sqrt", "sin", "cos", "tan", "log"};

    protected abstract String Perform() throws UndefinedSolveException, UndefinedResultException;

    protected abstract String GetSymbol();

}
