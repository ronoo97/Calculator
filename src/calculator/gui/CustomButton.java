package calculator.gui;

import calculator.gui.events.HoverEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public final class CustomButton extends JPanel {

    private Icon labelIcon;
    private JLabel buttonChild;
    private HoverEvent BackgroundEvent;
    private HoverEvent TextEvent;

    private boolean active = false;

    private CustomButton(Dimension boxSize, Color textColor, Color backgroundColor, Color hoverTextColor, Color hoverBackgroundColor) {

        setBackground(backgroundColor);
        setSize(boxSize);
        setPreferredSize(boxSize);
        setLayout(new BorderLayout());
        setVisible(true);

        BackgroundEvent = new HoverEvent(this, backgroundColor, hoverBackgroundColor);
        this.addMouseListener(BackgroundEvent);

    }

    public CustomButton(ImageIcon icon, Dimension boxSize, Color textColor, Color backgroundColor, Color hoverTextColor, Color hoverBackgroundColor) {
        this(boxSize, textColor, backgroundColor, hoverTextColor, hoverBackgroundColor);

        buttonChild = new JLabel(icon);

        add(buttonChild);
    }

    public CustomButton(String text, int textSize, Dimension boxSize, Color textColor, Color backgroundColor, Color hoverTextColor, Color hoverBackgroundColor) {
        this(boxSize, textColor, backgroundColor, hoverTextColor, hoverBackgroundColor);

        buttonChild = new JLabel(text);
        buttonChild.setFont(new Font("Arial", Font.BOLD, textSize));
        buttonChild.setHorizontalAlignment(SwingConstants.CENTER);
        buttonChild.setVerticalAlignment(SwingConstants.CENTER);
        buttonChild.setForeground(textColor);

        TextEvent = new HoverEvent(buttonChild, textColor, hoverTextColor);
        this.addMouseListener(TextEvent);

        add(buttonChild);
    }

    public String GetButtonText() {
        return buttonChild.getText();
    }

    public void SetTextBorder(Border b) {
        buttonChild.setBorder(b);
    }

    public void SetBackgroundColor(Color c) {
        setBackground(c);
        BackgroundEvent.SetColor(c);
    }

    public void SetTextColor(Color c) {
        setForeground(c);
        TextEvent.SetColor(c);
    }

    public void SetBackgroundHoverColor(Color c) {
        BackgroundEvent.SetHoverColor(c);
    }
    
    public void SetTextHoverColor(Color c){
        TextEvent.SetHoverColor(c);
    }

    public boolean IsActive() {
        return active;
    }

    public void SetState(boolean s) {
        active = s;
    }

}
