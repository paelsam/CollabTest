package Views;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import javax.swing.JLabel;

public class CircularLabel extends JLabel {
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(getBackground());
        g2.fill(new Ellipse2D.Double(0, 0, getWidth(), getHeight()));
        super.paintComponent(g);
        g2.dispose();
    }
}