package calculator.core;

import calculator.core.errors.InvalidSyntaxException;
import calculator.core.errors.MemoryOverflowException;
import calculator.core.errors.UndefinedResultException;
import calculator.core.math.Calculate;
import calculator.core.math.Num;
import calculator.core.math.Precision;
import calculator.core.math.operations.Operation;
import calculator.gui.GUI;
import java.math.BigInteger;
import java.util.Arrays;

public final class Core {

    public static final String BUTTONS[][] = {
        {"1", "2", "3", "÷", "π", "e", "sin", "asin", "AC", "DEL"},
        {"4", "5", "6", "×", "|x|", "^2", "cos", "acos", ".,..", "RU"},
        {"7", "8", "9", "-", "n!", "^x", "tan", "atan", "..,.", "RD"},
        {"(", "0", ")", "+", ",", "sqrt", "log", "+/-", "MOD", "="}
    };

    private static String inputText = "";
    private static String resultNum = "";

    private static String QuadraticA = "";
    private static String QuadraticB = "";
    private static String QuadraticC = "";

    private static String lastInput = "";

    private static String lastCalc;
    private static boolean reCalc = false;
    private static String lastResult;
    private static String[] QuadraticResult;

    private static boolean MOD = false;

    //0 - RD, 1 - normál, 2 - RU
    private static int rounding = 1;

    private Core() {

    }

    public static void AddInput(String str) {

        solveClear();
        str = CommaCheck(str);

        inputText = GetInputNum() + str;
        UpdateGUIInput();
        lastInput = str;

    }

    public static void AddResult(String str) {

        resultNum = resultNum + str;
        if (resultNum.length() > 1 && resultNum.charAt(0) == 0 && resultNum.charAt(1) != ',') {
            resultNum.replace("0", "");
        }
        UpdateGUIResult();
    }

    public static void DeletLast() {

        if (resultNum.length() > 1) {
            resultNum = resultNum.substring(0, resultNum.length() - 1);
            if (GetMod()) {
                UpdateGUIResult();
            } else {
                UpdateGUI();
            }
        } else if (resultNum.length() == 1 || resultNum == "") {
            resultNum = "0";
            if (GetMod()) {
                UpdateGUIResult();
            } else {
                UpdateGUI();
            }
            resultNum = "";
        }

    }

    public static void AllClear() {

        if (GetMod()) {
            ClearResult(true);
            ClearQuadratic();
            return;
        }

        inputText = "";
        resultNum = "0";
        UpdateGUI();
        resultNum = "";
        lastInput = "";
    }

    public static void ClearResult(boolean updateGUI) {
        if (updateGUI) {
            resultNum = "0";
            UpdateGUIResult();
        }
        resultNum = "";
    }

    public static void ClearInput() {
        inputText = "";
        UpdateGUIInput();
        lastInput = "";
    }

    public static void ClearQuadratic() {
        QuadraticA = "█";
        QuadraticB = "";
        QuadraticC = "";
        UpdateGUIQuadraticInput();
    }

    public static void CalculateResult() throws UndefinedResultException {
        if (GetMod()) {

            if (QuadraticA == "█") {

                try {
                    Double.parseDouble(GetResultNum());
                } catch (NumberFormatException e) {
                    ClearQuadratic();
                }
                QuadraticA = GetResultNum();
                QuadraticB = "█";
                resultNum = "";

            } else if (QuadraticB == "█") {

                try {
                    Double.parseDouble(GetResultNum());
                } catch (NumberFormatException e) {
                    ClearQuadratic();
                }
                QuadraticB = GetResultNum();
                QuadraticC = "█";
                resultNum = "";

            } else if (QuadraticC == "█") {

                try {
                    Double.parseDouble(GetResultNum());
                } catch (NumberFormatException e) {
                    ClearQuadratic();
                }
                QuadraticC = GetResultNum();
                resultNum = "";

            } else {

                String[] calcResult;

                try {
                    calcResult = Calculate.SolveQuadraticEquation(Double.parseDouble(QuadraticA.replace(",", ".")),
                            Double.parseDouble(QuadraticB.replace(",", ".")),
                            Double.parseDouble(QuadraticC.replace(",", "."))
                    );
                } catch (UndefinedResultException e) {
                    e.ClearAll();
                    e.PrintToGui();
                    return;
                }

                if (QuadraticResult != null) {
                    if (Arrays.equals(QuadraticResult, calcResult)) {
                        return;
                    }
                }

                if (calcResult.length == 1) {
                    AddResult("X: " + calcResult[0].replace(".", ","));
                } else {
                    AddResult("X1: " + calcResult[0].replace(".", ",") + "  X2: " + calcResult[1].replace(".", ","));
                }

                QuadraticResult = calcResult;
            }
            UpdateGUIQuadraticInput();

        } else {

            if (GetInputNum().isEmpty()) {
                return;
            }

            if (IsOperation(inputText.charAt(inputText.length() - 1))) {
                AddInput(CheckSign(GetResultNum()));
            }

            int openB = CountCharDB('(', inputText);
            int closeB = CountCharDB(')', inputText);
            if (openB != closeB) {
                while (openB != closeB) {
                    AddInput(")");
                    closeB++;
                }
            }

            if ((lastCalc != null && lastCalc == inputText)) {
                if (!reCalc) {
                    return;
                }
            }

            reCalc = false;
            lastCalc = inputText;
            lastInput = "";
            ClearResult(true);

            String calcResult = Calculate.Solve(inputText.replace(",", "."));

            if (calcResult.substring(calcResult.length() - 2).equals(".0")) {
                calcResult = calcResult.replace(".0", "");
            }

            calcResult = calcResult.replace(".", ",");

            lastResult = calcResult;

            AddResult(calcResult);

            GUI.GetInstance().UpdateInputField(inputText + "=");
        }
    }

    public static void UpdateGUI() {
        GUI.GetInstance().UpdateTextFields(GetResultNum(), inputText);
    }

    public static void UpdateGUIInput() {
        GUI.GetInstance().UpdateInputField(inputText);
    }

    public static void UpdateGUIResult() {
        GUI.GetInstance().UpdateResultField(GetResultNum());
    }

    public static void UpdateGUIQuadraticInput() {
        GUI.GetInstance().UpdateQuadraticInputField(QuadraticA, QuadraticB, QuadraticC);
    }

    public static boolean ValideSyntax(String input) throws InvalidSyntaxException {
        switch (input) {
            case ")" -> {
                int openerDB = CountCharDB('(', inputText);
                int closerDB = CountCharDB(')', inputText);
                if (openerDB > closerDB) {
                    return true;
                }
            }
            default ->
                throw new InvalidSyntaxException();
        }
        return false;
    }

    public static int CountCharDB(char c, String s) {
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == c) {
                count++;
            }
        }
        return count;
    }

    public static void NewBracket(String input) {

        if (GetMod()) {
            return;
        }

        if (input == ")") {
            char lastChar = inputText.charAt(inputText.length() - 1);
            if (lastChar == '(') {
                if (resultNum.isEmpty()) {
                    AddInput(0 + input);
                } else {
                    AddInput(GetResultNum());
                    AddInput(input);
                    ClearResult(false);
                }
            } else if (!resultNum.isEmpty()) {
                AddInput(GetResultNum());
                AddInput(input);
                resultNum = "";
            } else {
                AddInput(input);
            }

        } else {
            char lastChar;
            try {
                lastChar = inputText.charAt(inputText.length() - 1);
                if (IsOperation(lastChar)) {
                    if (!resultNum.isEmpty()) {
                        AddInput(CheckSign(GetResultNum()) + "×" + input);
                        ClearResult(false);
                    } else {
                        AddInput(input);
                    }
                } else if (IsNumber(lastChar)) {
                    AddInput("×" + input);
                } else if (lastChar == ')') {
                    if (!resultNum.isEmpty()) {
                        ClearInput();
                        AddInput(CheckSign(GetResultNum()) + "×" + input);
                        ClearResult(false);
                    } else {
                        AddInput("×" + input);
                    }
                } else {
                    if (!resultNum.isEmpty()) {
                        AddInput(CheckSign(GetResultNum()) + "×" + input);
                        ClearResult(false);
                    } else {
                        AddInput(input);
                    }
                }
            } catch (IndexOutOfBoundsException e) {
                if (GetResultNum() != "0") {
                    AddInput(CheckSign(GetResultNum()) + "×" + input);
                    ClearResult(false);
                } else {
                    AddInput(input);
                }

            }

        }
    }

    private static boolean IsOperation(char c) {
        if (Arrays.asList(Operation.OPERATIONS).contains(String.valueOf(c))) {
            return true;
        }
        return false;
    }

    private static boolean IsNumber(char c) {
        if (Character.isDigit(c)) {
            return true;
        }
        return false;
    }

    public static void SetRoundMode(String input) {

        int type = 1;
        if (input.equals("RD")) {
            type = 0;
        } else if (input.equals("RU")) {
            type = 2;
        }

        if (rounding == 1) {
            rounding = type;
        } else if (rounding == type) {
            rounding = 1;
        }

        reCalc = true;
        try {
            CalculateResult();
        } catch (UndefinedResultException e) {
            e.PrintToGui();
            e.ClearNums();
            reCalc = false;
        }

    }

    public static int GetRoundMode() {
        return rounding;
    }

    public static void AddPi() {

        if (GetMod()) {
            return;
        }

        solveClear();
        resultNum = String.valueOf(Math.PI).replace('.', ',');
        UpdateGUI();
    }

    public static void AddEuler() {

        if (GetMod()) {
            return;
        }

        solveClear();
        resultNum = String.valueOf(Math.E).replace('.', ',');
        UpdateGUI();
    }

    public static void NewOperation(String input) {

        if (GetMod()) {
            return;
        }

        char lastChar;
        try {
            lastChar = inputText.charAt(inputText.length() - 1);
            if (IsOperation(lastChar) && resultNum.isEmpty()) {
                inputText = inputText.substring(0, inputText.length() - 1) + input;
                UpdateGUIInput();
            } else if (IsOperation(lastChar)) {
                AddInput(CheckSign(GetResultNum()) + input);
                ClearResult(false);
            } else if (lastChar != ')') {
                if (resultNum.isEmpty()) {
                    AddInput("0" + input);
                } else {
                    AddInput(GetResultNum() + input);
                    ClearResult(false);
                }
            } else if (GUI.GetInstance().GetInputText().contains("=")) {
                AddInput(GetResultNum() + input);
            } else {
                AddInput(input);
            }
        } catch (IndexOutOfBoundsException e) {
            if (resultNum.isEmpty()) {
                AddInput("0" + input);
            } else {
                AddInput(CheckSign(GetResultNum()) + input);
                ClearResult(false);
            }

        }

    }

    public static void NewComma() {

        if (GetMod()) {
            return;
        }

        solveClear();
        if (CountCharDB(',', resultNum) < 1) {
            if (resultNum.isEmpty()) {
                AddResult("0,");
            } else {
                AddResult(",");
            }
        }
    }

    public static void NewFactorial() throws MemoryOverflowException, UndefinedResultException {

        if (GetMod()) {
            return;
        }

        if (resultNum.contains(",")) {
            throw new UndefinedResultException("Nem definiált bemeneti érték");
        }
        if (!resultNum.isEmpty()) {
            Number num = Num.ParseNum(GetResultNum());

            if (num instanceof BigInteger) {
                throw new MemoryOverflowException("Túl nagy érték");
            }
            if (num.doubleValue() > 170) {
                throw new MemoryOverflowException("Túl nagy érték.");
            }
        }

        String lastInput;

        lastInput = LastInput();
        if (lastInput.contains("fact(")) {
            inputText = inputText.substring(0, inputText.lastIndexOf("fact("));

        } else if (LastInputChar() == ')') {
            AddInput("×");
        }

        AddInput("fact(" + GetResultNum() + ")");
        ClearResult(false);

    }

    public static void NewFunc(String type) {

        if (GetMod()) {
            return;
        }

        String lastInput;

        lastInput = LastInput();
        if (lastInput.contains(type + "(")) {
            if (lastInput.contains(type + "(" + GetResultNum() + ")")) {
                inputText = inputText.substring(0, inputText.lastIndexOf(lastInput));

                String newInput = lastInput.replace(GetResultNum(), type + "(" + GetResultNum() + ")");
                AddInput(newInput);
                return;
            } else {
                inputText = inputText.substring(0, inputText.lastIndexOf(lastInput));
            }
        } else if (LastInputChar() == ')') {
            AddInput("×");
        }

        AddInput(type + "(" + GetResultNum() + ")");
        ClearResult(false);
    }

    private static String LastInput() {
        return lastInput;
    }

    private static char LastInputChar() {
        try {
            return LastInput().charAt(inputText.length() - 1);
        } catch (IndexOutOfBoundsException e) {
            return '\0';
        }
    }

    public static String GetResultNum() {
        if (resultNum.isEmpty()) {
            return GUI.GetInstance().GetResultText();
        } else {
            return resultNum;
        }
    }

    public static String GetInputNum() {
        if (inputText.isEmpty()) {
            return inputText;
        } else {
            if (inputText.charAt(inputText.length() - 1) == '=') {
                return "";
            } else {
                return inputText;
            }
        }
    }

    private static char LastResultChar() {
        String ResultNum = GetResultNum();
        try {
            return ResultNum.charAt(ResultNum.length() - 1);
        } catch (IndexOutOfBoundsException e) {
            return '\0';
        }
    }

    private static String CommaCheck(String str) {
        if (str.contains(",")) {
            int commaIndex = str.indexOf(",");
            char afterComma = str.charAt(commaIndex + 1);
            if (!Character.isDigit(afterComma)) {
                return str.replace(",", "");
            }
        }
        return str;
    }

    public static void ReversSign() {
        if (GetResultNum() != "0") {
            char firstChar = GetResultNum().charAt(0);
            if (firstChar != '-') {
                resultNum = '-' + resultNum;
            } else {
                resultNum = resultNum.substring(1);
            }
            UpdateGUIResult();
        }
    }

    private static String CheckSign(String str) {
        if (str.contains("-")) {
            return "(" + str + ")";
        } else {
            return str;
        }
    }

    public static void NewNum(String str) {

        solveClear();
        if (GetResultNum() == "0") {
            resultNum = str;
            UpdateGUIResult();
        } else {
            AddResult(str);
        }
    }

    private static void solveClear() {
        String actInput = GUI.GetInstance().GetInputText();
        if (!actInput.isEmpty() && actInput.charAt(actInput.length() - 1) == '=') {
            AllClear();
        }
    }

    public static void SetPrecison(String str) {

        String GUIInp = GUI.GetInstance().GetInputText();

        if (GUIInp != null && !GUIInp.isEmpty()) {

            if (GUIInp.charAt(GUIInp.length() - 1) != '=') {
                return;
            }

            if (!GetResultNum().contains(",")) {
                return;
            }

            String GUIText = GUI.GetInstance().GetResultText();
            if (GUIText == null) {
                return;
            }
            switch (str) {
                case ".,.." -> {
                    if (GUIText.charAt(GUIText.length() - 2) == ',') {
                        return;
                    }
                    GUI.GetInstance().UpdateResultField(Precision.LowerPrecision(GUIText.replace(",", ".")).replace(".", ","));
                }
                case "..,." -> {
                    if (lastResult.length() == GUIText.length()) {
                        return;
                    }
                    GUI.GetInstance().UpdateResultField(Precision.HigherPrecision(GUIText.replace(",", "."), lastResult).replace(".", ","));
                }
            }
        }
    }

    public static void SetMod(boolean mod) {
        MOD = mod;
        if (mod) {
            ClearQuadratic();
            QuadraticA = "█";
            UpdateGUIQuadraticInput();
        } else {
            AllClear();
        }
    }

    public static boolean GetMod() {
        return MOD;
    }
}
