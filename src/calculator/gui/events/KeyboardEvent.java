package calculator.gui.events;

import calculator.core.Core;
import calculator.core.errors.InvalidSyntaxException;
import calculator.core.errors.UndefinedResultException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class KeyboardEvent extends KeyAdapter {

    @Override
    public void keyTyped(KeyEvent event) {

        Character c = event.getKeyChar();
        String input = c.toString();

        switch (input) {
            case "=" -> {
                try {
                    Core.CalculateResult();
                } catch (UndefinedResultException ex) {
                    ex.PrintToGui();
                    ex.ClearNums();
                }
            }
            case "(" ->
                Core.NewBracket(input);
            case ")" -> {
                try {
                    if (Core.ValideSyntax(input)) {
                        Core.NewBracket(input);
                    }
                } catch (InvalidSyntaxException ex) {

                }
            }
            case "/", "+", "-", "*" -> {
                Core.NewOperation(input);
            }
            case "," -> {
                Core.NewComma();
            }
            default -> {
                try {
                    Integer.parseInt(input);
                    Core.AddResult(input);
                } catch (NumberFormatException e) {

                }
            }
        }
    }

}
