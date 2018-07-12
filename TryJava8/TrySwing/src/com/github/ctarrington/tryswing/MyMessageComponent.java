package com.github.ctarrington.tryswing;

import javax.swing.*;
import java.awt.*;


public class MyMessageComponent extends JComponent {
  private final String message;

  public MyMessageComponent(String message) {
    this.message = message;
  }

  @Override
  public void paintComponent(Graphics g) {
    g.drawString(this.message, 50, 50);
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(500, 250);
  }
}
