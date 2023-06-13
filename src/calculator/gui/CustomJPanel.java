package calculator.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.LayoutManager;
import javax.swing.JPanel;

public final class CustomJPanel extends JPanel implements IFrequentColors {

    public CustomJPanel(Dimension size, Color color) {
        this(size, color, null);
    }

    public CustomJPanel(Color color, LayoutManager mgr) {
        setBackground(color);
        setLayout(mgr);
    }

    public CustomJPanel(Dimension size, Color color, LayoutManager mgr) {
        setBackground(color);
        setSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
        setLayout(mgr);
    }

}
