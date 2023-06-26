package org.example;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
public class CustomRadioButtonIcon implements Icon {
    private static final int SIZE = 16;
    private static final Color SELECTED_COLOR = Color.BLUE;
    private static final Color DESELECTED_COLOR = Color.GRAY;

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        AbstractButton button = (AbstractButton) c;
        ButtonModel model = button.getModel();

        g.setColor(button.getBackground());
        g.fillRect(x, y, getIconWidth(), getIconHeight());

        Color color = model.isSelected() ? SELECTED_COLOR : DESELECTED_COLOR;
        g.setColor(color);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.fill(new Ellipse2D.Double(x, y, SIZE, SIZE));

        if (model.isSelected()) {
            int dotSize = SIZE / 2;
            int dotX = x + (SIZE - dotSize) / 2;
            int dotY = y + (SIZE - dotSize) / 2;
            g.setColor(button.getForeground());
            g.fillOval(dotX, dotY, dotSize, dotSize);
        }
    }
    @Override
    public int getIconWidth() {
        return SIZE;
    }
    @Override
    public int getIconHeight() {
        return SIZE;
    }
}