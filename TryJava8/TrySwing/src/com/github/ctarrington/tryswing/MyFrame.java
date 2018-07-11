package com.github.ctarrington.tryswing;

import javax.swing.*;


public class MyFrame extends JFrame {
  public MyFrame() {
    setSize(500, 250);
    setTitle("My Frame");
    add(new MyMessageComponent("Hello cruel world!"));
    pack();
  }
}
