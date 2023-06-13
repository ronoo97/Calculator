package calculator.gui.events;

import java.awt.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public final class DragDropEvent extends MouseAdapter {

    private Container c;
    private int titleOffsetX, titleOffsetY;

    public DragDropEvent(Container c) {
        this.c = c;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        titleOffsetX = e.getX();
        titleOffsetY = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        titleOffsetX = 0;
        titleOffsetY = 0;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        c.setLocation(
                e.getXOnScreen() - titleOffsetX,
                e.getYOnScreen() - titleOffsetY);
    }
}
