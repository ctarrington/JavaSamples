package com.github.ctarrington.tryswing;

import javax.swing.*;
import java.awt.*;


public class MyMessageComponent extends JComponent {
  private final String message;

  public MyMessageComponent(String message) {
    this.message = message;
  }

  public void paintComponent(Graphics g) {
    g.drawString(message, 50, 50);
  }
}
