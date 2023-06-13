package calculator.core.errors;

import calculator.core.Core;
import calculator.gui.GUI;

public class CustomException extends Exception {

    private final GUI gui;

    public CustomException() {
        super();
        gui = GUI.GetInstance();
    }
    public CustomException(String message) {
        super(message);
        gui = GUI.GetInstance();
    }

    public void PrintToGui() {
        if (getMessage() != null) {
            gui.UpdateTextFields(getMessage(), "");
        } else {
            gui.UpdateTextFields("Hiba!", "");
        }
    }
    public void ClearNums(){
        Core.ClearResult(false);
    }
    public void ClearAll(){
        Core.AllClear();
    }
    
}
