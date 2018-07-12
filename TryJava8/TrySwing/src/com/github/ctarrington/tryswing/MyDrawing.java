package com.github.ctarrington.tryswing;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class MyDrawing extends JComponent {
  @Override
  public void paintComponent(Graphics g) {
    Shape ellipse1 = new Ellipse2D.Double(11,11,49,49);
    Shape ellipse2 = new Ellipse2D.Double(10,10,50,50);


    Graphics2D g2 = (Graphics2D) g;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);

    g2.setPaint(Color.BLUE);
    g2.setStroke(new BasicStroke(3.0f));
    g2.draw(ellipse2);
    g2.setPaint(Color.RED);
    g2.fill(ellipse1);
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(500, 250);
  }
}
