package calculator.core.math.operations;

import calculator.core.errors.UndefinedResultException;
import calculator.core.errors.UndefinedSolveException;

public abstract class Operation {

    public static final String[] OPERATIONS = {"^", "÷", "×", "-", "+" };

    protected abstract <T1 extends Number, T2 extends Number> String Perform(T1 x, T2 y) throws UndefinedSolveException, UndefinedResultException;
    protected abstract String GetSymbol();
    
}
