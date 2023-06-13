package calculator.gui.events;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public final class HoverEvent extends MouseAdapter {

    private Color baseColor;
    private Color hoverColor;
    private final Component c;

    public HoverEvent(Component c, Color baseColor, Color hoverColor) {
        this.baseColor = baseColor;
        this.hoverColor = hoverColor;
        this.c = c;

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (c instanceof JLabel) {
            c.setForeground(hoverColor);
        } else if (c instanceof JPanel) {
            c.setBackground(hoverColor);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (c instanceof JLabel) {
            c.setForeground(baseColor);
        } else if (c instanceof JPanel) {
            c.setBackground(baseColor);
        }
    }

    public void SetColor(Color c) {
        baseColor = c;
    }

    public void SetHoverColor(Color c) {
        hoverColor = c;
    }

}
