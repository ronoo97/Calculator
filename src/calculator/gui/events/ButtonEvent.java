package calculator.gui.events;

import calculator.core.InputEvent;
import calculator.gui.CustomButton;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JFrame;
import calculator.gui.IFrequentColors;

public final class ButtonEvent extends MouseAdapter implements IFrequentColors {

    public static enum Tasks {
        EXIT, MINIMIZE, INPUT, ACTIVATE
    };

    private static ArrayList<CustomButton> activeButtonEvents = new ArrayList<>();

    private Tasks task;
    private Object caller;
    private String pair;

    public ButtonEvent(Tasks task) {
        this.task = task;
    }

    public ButtonEvent(Tasks task, Object caller) {
        this(task);
        this.caller = caller;

        if (task == Tasks.ACTIVATE) {
            if (caller instanceof CustomButton button) {
                activeButtonEvents.add(button);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (task) {
            case EXIT ->
                System.exit(0);
            case MINIMIZE -> {
                if (caller instanceof JFrame frame) {
                    frame.setState(Frame.ICONIFIED);
                }
            }
            case INPUT -> {
                if (caller instanceof CustomButton button) {
                    InputEvent.NewInput(button.GetButtonText());
                } else {
                    throw new IllegalArgumentException();
                }
            }
            case ACTIVATE -> {
                if (caller instanceof CustomButton button) {
                    if (button.IsActive()) {
                        button.SetState(false);
                        button.SetBackgroundColor(INPUT_BUTTON_COLOR);
                        button.SetBackgroundHoverColor(INPUT_BUTTON_HOVER_COLOR);
                        button.SetTextColor(SECONDARY_TEXT_COLOR);
                        button.SetTextHoverColor(MAIN_TEXT_COLOR);
                        InputEvent.NewInput(button.GetButtonText());
                    } else {
                        boolean IsActivable = true;
                        if (pair != null) {
                            for (int i = 0; i < activeButtonEvents.size(); i++) {
                                if (activeButtonEvents.get(i).GetButtonText() == pair) {
                                    if (activeButtonEvents.get(i).IsActive()) {
                                        IsActivable = false;
                                    }
                                }
                            }
                        }

                        if (IsActivable) {
                            button.SetState(true);
                            button.SetBackgroundColor(ACTIVATED_INPUT_BUTTON_COLOR);
                            button.SetBackgroundHoverColor(ACTIVATED_INPUT_BUTTON_HOVER_COLOR);
                            button.SetTextColor(MAIN_TEXT_COLOR);
                            button.SetTextHoverColor(SECONDARY_TEXT_COLOR);
                            InputEvent.NewInput(button.GetButtonText());
                        }

                    }
                } else {
                    throw new IllegalArgumentException();
                }
            }
            default ->
                throw new RuntimeException("Invalid task");
        }
    }

    public void SetPair(String str) {
        if (task == Tasks.ACTIVATE) {
            pair = str;
        }
    }
}
