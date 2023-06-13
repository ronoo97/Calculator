package calculator.core.math;

import calculator.core.Core;
import calculator.core.errors.UndefinedResultException;
import calculator.core.errors.UndefinedSolveException;
import calculator.core.math.functions.*;
import calculator.core.math.operations.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

import java.util.ArrayList;
import java.util.Arrays;

public class Calculate {

    public static String Solve(String inputText) {

        inputText = RemoveFunctions(inputText);

        if (inputText.contains("(")) {
            inputText = RemoveBrackets(inputText);
        }
        inputText = RemoveOperations(inputText);

        String roundedResult = GetRoundedResult(inputText);

        if (roundedResult.contains("E")) {
            roundedResult = NormalForm(roundedResult);
        }

        return roundedResult;
    }

    //Brackets
    private static String RemoveBrackets(String str) {

        while (str.contains("(")) {
            int[] bracketIndex = GetDeepestBrackets(str);
            str = str.replace(str.substring(bracketIndex[0], bracketIndex[1]),
                    RemoveOperations(str.substring(bracketIndex[0] + 1, bracketIndex[1] - 1)));
        }

        return str;
    }

    private static int[] GetDeepestBrackets(String str) {

        int[] startEnd = new int[2];

        startEnd[0] = str.lastIndexOf("(");
        startEnd[1] = startEnd[0] + str.substring(startEnd[0]).indexOf(")") + 1;

        return startEnd;
    }

    //Functions
    private static String RemoveFunctions(String str) {

        ArrayList<String> ContainedFunctions = GetContainedFunctions(str);
        while (Contains(str, Function.FUNCTIONS)) {
            int[] funcIndex = GetFunc(str, ContainedFunctions);

            String func = str.substring(funcIndex[0], funcIndex[1]);
            str = str.replace(func, SolveFunc(func));

            if (!str.contains(ContainedFunctions.get(0))) {
                ContainedFunctions.remove(0);
            }
        }

        return str;
    }

    private static int[] GetFunc(String str, ArrayList<String> ContainedFunctions) {

        int[] startEnd = new int[2];

        startEnd[0] = str.indexOf(ContainedFunctions.get(0));
        startEnd[1] = str.substring(startEnd[0]).indexOf(')') + startEnd[0] + 1;

        return startEnd;

    }

    private static ArrayList<String> GetContainedFunctions(String str) {
        ArrayList<String> ContainedFunctions = new ArrayList<>();
        for (String function : Function.FUNCTIONS) {
            if (str.contains(function)) {
                ContainedFunctions.add(function);
            }
        }
        return ContainedFunctions;
    }

    private static String SolveFunc(String func) {

        String functype = func.substring(0, func.indexOf("("));

        String numString = func.substring(func.indexOf("(") + 1, func.indexOf(")"));

        switch (functype) {
            case "sin" -> {
                try {
                    Sin funcSolve = new Sin(Num.ParseNum(numString));
                    return funcSolve.Perform();
                } catch (UndefinedSolveException e) {
                    e.ClearAll();
                    e.PrintToGui();
                }
            }
            case "cos" -> {
                try {
                    Cos funcSolve = new Cos(Num.ParseNum(numString));
                    return funcSolve.Perform();
                } catch (UndefinedSolveException e) {
                    e.ClearAll();
                    e.PrintToGui();
                }
            }
            case "tan" -> {
                try {
                    Tan funcSolve = new Tan(Num.ParseNum(numString));
                    return funcSolve.Perform();
                } catch (UndefinedSolveException e) {
                    e.ClearAll();
                    e.PrintToGui();
                }
            }
            case "asin" -> {
                try {
                    Asin funcSolve = new Asin(Num.ParseNum(numString));
                    return funcSolve.Perform();
                } catch (UndefinedSolveException e) {
                    e.ClearAll();
                    e.PrintToGui();
                } catch (UndefinedResultException e) {
                    e.ClearAll();
                    e.PrintToGui();
                }
            }
            case "acos" -> {
                try {
                    Acos funcSolve = new Acos(Num.ParseNum(numString));
                    return funcSolve.Perform();
                } catch (UndefinedSolveException e) {
                    e.ClearAll();
                    e.PrintToGui();
                } catch (UndefinedResultException e) {
                    e.ClearAll();
                    e.PrintToGui();
                }
            }
            case "atan" -> {
                try {
                    Atan funcSolve = new Atan(Num.ParseNum(numString));
                    return funcSolve.Perform();
                } catch (UndefinedSolveException e) {
                    e.ClearAll();
                    e.PrintToGui();
                }
            }
            case "log" -> {
                try {
                    Log funcSolve = new Log(Num.ParseNum(numString));
                    return funcSolve.Perform();
                } catch (UndefinedSolveException e) {
                    e.ClearAll();
                    e.PrintToGui();
                } catch (UndefinedResultException e) {
                    e.ClearAll();
                    e.PrintToGui();
                }
            }
            case "fact" -> {
                try {
                    Fact funcSolve = new Fact(Num.ParseNum(numString));
                    return funcSolve.Perform();
                } catch (UndefinedResultException e) {
                    e.ClearAll();
                    e.PrintToGui();
                }
            }
            case "abs" -> {
                Abs funcSolve = new Abs(Num.ParseNum(numString));
                return funcSolve.Perform();
            }
            case "sqrt" -> {
                Sqrt funcSolve = new Sqrt(Num.ParseNum(numString));
                return funcSolve.Perform();
            }

        }

        return "";
    }

    //Operation
    private static String RemoveOperations(String str) {

        ArrayList<String> ContainedOperations = GetContainedOperations(str);
        while (!ContainedOperations.isEmpty()) {
            int[] opIndex = GetOperation(str, ContainedOperations);

            String operation = str.substring(opIndex[0], opIndex[1]);

            str = str.replace(operation, SolveOperation(operation));

            if (!str.contains(ContainedOperations.get(0))
                    || (ContainedOperations.get(0) == "-" && Core.CountCharDB('-', str) == 1 && IsSign(str, str.indexOf("-")))) {
                ContainedOperations.remove(0);
            }
        }
        return str;
    }

    public static boolean IsSign(String str, int index) {

        try {
            char prevChar = str.charAt(index - 1);
            if (Arrays.asList(Operation.OPERATIONS).contains(String.valueOf(prevChar))) {
                return true;
            }
        } catch (IndexOutOfBoundsException e) {
            return true;
        }

        return false;
    }

    private static ArrayList<String> GetContainedOperations(String str) {

        ArrayList<String> ContainedOperations = new ArrayList<>();
        for (String operation : Operation.OPERATIONS) {
            if (str.contains(operation)) {
                if (operation == "-") {
                    int i = 0;
                    for (char c : str.toCharArray()) {
                        if (c == '-' && !IsSign(str, i)) {
                            ContainedOperations.add(operation);
                            break;
                        }
                        i++;
                    }
                } else {
                    ContainedOperations.add(operation);
                }

            }
        }
        return ContainedOperations;
    }

    private static int[] GetOperation(String str, ArrayList<String> ContainedOperations) {

        int[] startEnd = new int[2];

        if (ContainedOperations.get(0) == "-") {
            int opIndex = 0;
            int i = 0;
            for (char c : str.toCharArray()) {
                if (c == '-' && !IsSign(str, i)) {
                    opIndex = i;
                    break;
                }
                i++;
            }

            int startIndex = opIndex;
            do {
                startIndex -= 1;
            } while (startIndex > 0 && (Character.isDigit(str.charAt(startIndex)) || str.charAt(startIndex) == '.'));
            char firstChar = str.charAt(startIndex);
            if ((firstChar == '-' && IsSign(str, startIndex)) || Character.isDigit(firstChar)) {
                startEnd[0] = startIndex;
            } else {
                startEnd[0] = startIndex + 1;
            }

            int endIndex = opIndex;

            do {
                endIndex += 1;
            } while (str.length() > endIndex
                    && (Character.isDigit(str.charAt(endIndex)) || str.charAt(endIndex) == '.' || IsSign(str, endIndex)));
            startEnd[1] = endIndex;

        } else {

            int opIndex = str.indexOf(ContainedOperations.get(0));

            int startIndex = opIndex;
            do {
                startIndex -= 1;
            } while (startIndex > 0 && (Character.isDigit(str.charAt(startIndex)) || str.charAt(startIndex) == '.'));
            char firstChar = str.charAt(startIndex);
            if ((firstChar == '-' && IsSign(str, startIndex)) || Character.isDigit(firstChar)) {
                startEnd[0] = startIndex;
            } else {
                startEnd[0] = startIndex + 1;
            }

            int endIndex = opIndex;

            do {
                endIndex += 1;
            } while (str.length() > endIndex
                    && (Character.isDigit(str.charAt(endIndex)) || str.charAt(endIndex) == '.' || IsSign(str, endIndex)));
            startEnd[1] = endIndex;

        }

        return startEnd;
    }

    private static String SolveOperation(String operation) {

        char operationType = 0;

        int index = 0;
        for (char c : operation.toCharArray()) {
            if (Arrays.asList(Operation.OPERATIONS).contains(String.valueOf(c))) {
                if (c == '-') {
                    if (!IsSign(operation, index)) {
                        operationType = c;
                        break;
                    }
                } else {
                    operationType = c;
                    break;
                }
            }
            index++;
        }

        String firstNum = operation.substring(0, index);
        String secondNum = operation.substring(index + 1, operation.length());

        switch (operationType) {
            case '+' -> {
                Add operationSolve = new Add();
                return operationSolve.Perform(Num.ParseNum(firstNum), Num.ParseNum(secondNum));
            }
            case '×' -> {
                Multiply operationSolve = new Multiply();
                return operationSolve.Perform(Num.ParseNum(firstNum), Num.ParseNum(secondNum));
            }
            case '-' -> {
                Subtract operationSolve = new Subtract();
                return operationSolve.Perform(Num.ParseNum(firstNum), Num.ParseNum(secondNum));
            }
            case '^' -> {
                try {
                    Pow operationSolve = new Pow();
                    return operationSolve.Perform(Num.ParseNum(firstNum), Num.ParseNum(secondNum));
                } catch (UndefinedSolveException e) {
                    e.ClearAll();
                    e.PrintToGui();
                }

            }
            case '÷' -> {
                try {
                    Divide operationSolve = new Divide();
                    return operationSolve.Perform(Num.ParseNum(firstNum), Num.ParseNum(secondNum));
                } catch (UndefinedResultException e) {
                    e.ClearAll();
                    e.PrintToGui();
                }

            }

        }

        return "";
    }

    //other
    private static boolean Contains(String text, String[] words) {
        for (String word : words) {
            if (text.contains(word)) {
                return true;
            }
        }
        return false;
    }

    private static String GetRoundedResult(String str) {

        if (Core.GetRoundMode() == 2) {
            return Precision.RoundUp(Num.ParseNum(str));
        } else if (Core.GetRoundMode() == 0) {

            return Precision.RoundDown(Num.ParseNum(str));
        }
        return str;
    }

    private static String NormalForm(String str) {
        return String.valueOf(new BigDecimal(str));
    }

    public static String[] SolveQuadraticEquation(double a, double b, double c) throws UndefinedResultException{

        if (a == 0) {
            throw new UndefinedResultException("Nullávával való osztást nem értelmezzük!");
        }

        double discriminant = b * b - 4 * a * c;

        if (discriminant < 0) {

            double realPart = -b / (2 * a);
            String realPartFormat = FormatNumber(String.valueOf(realPart));

            double imaginaryPart = Math.sqrt(-discriminant) / (2 * a);
            String imaginaryPartFormat = FormatNumber(String.valueOf(imaginaryPart));

            String x1 = realPartFormat + " + " + imaginaryPartFormat + "i";
            String x2 = realPartFormat + " - " + imaginaryPartFormat + "i";
            return new String[]{x1, x2};

        } else if (discriminant == 0) {

            double x = -b / (2 * a);
            return new String[]{FormatNumber(Double.toString(x))};

        } else {

            double x1 = (-b + Math.sqrt(discriminant)) / (2 * a);
            double x2 = (-b - Math.sqrt(discriminant)) / (2 * a);
            return new String[]{FormatNumber(Double.toString(x1)), FormatNumber(Double.toString(x2))};

        }
    }

    public static String FormatNumber(String str) {
        if (str.substring(str.length() - 2).equals(".0")) {
            str = str.replace(".0", "");
        }

        BigDecimal num = new BigDecimal(str);
        if (num.scale() > 5) {
            num = num.setScale(5, RoundingMode.HALF_UP);
        }

        return String.valueOf(num);
    }

}
