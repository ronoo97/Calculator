package calculator.core;

import calculator.core.errors.InvalidSyntaxException;
import calculator.core.errors.MemoryOverflowException;
import calculator.core.errors.UndefinedResultException;
import calculator.core.math.operations.Operation;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class InputEvent {

    public static void NewInput(String input) {
        switch (input) {
            case "=" -> {
                try {
                    Core.CalculateResult();
                } catch (UndefinedResultException e) {
                    e.PrintToGui();
                    e.ClearNums();
                }
            }
            case "DEL" ->
                Core.DeletLast();
            case "AC" ->
                Core.AllClear();
            case "(" ->
                Core.NewBracket(input);
            case ")" -> {
                try {
                    if (Core.ValideSyntax(input)) {
                        Core.NewBracket(input);
                    }
                } catch (InvalidSyntaxException e) {

                }
            }
            case "RD", "RU" -> {
                Core.SetRoundMode(input);
            }
            case "π" -> {
                Core.AddPi();
            }
            case "e" -> {
                Core.AddEuler();
            }
            case "+", "×", "-", "÷" -> {
                Core.NewOperation(input);
            }
            case "^x" -> {
                Core.NewOperation("^");
            }
            case "," -> {
                Core.NewComma();
            }
            case "n!" -> {
                try {
                    Core.NewFactorial();
                } catch (MemoryOverflowException e) {
                    e.PrintToGui();
                    e.ClearNums();
                } catch (UndefinedResultException e) {
                    e.PrintToGui();
                    e.ClearNums();
                }
            }
            case "|x|" -> {
                Core.NewFunc("abs");
            }
            case "sin", "cos", "tan", "log", "asin", "acos", "atan", "sqrt" -> {
                Core.NewFunc(input);
            }
            case "^2" -> {
                Core.NewOperation("^2");
            }
            case "+/-" -> {
                Core.ReversSign();
            }
            case ".,..", "..,." -> {
                Core.SetPrecison(input);
            }
            case "MOD" ->{
                Core.SetMod(!Core.GetMod());
            }
            default -> {
                try {
                    Integer.parseInt(input);
                    Core.NewNum(input);
                } catch (NumberFormatException e) {

                }
            }
        }
    }

}
