package com.github.ctarrington.tryswing;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("yo");

      EventQueue.invokeLater(()-> {
        JFrame frame = new MyFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
      });
    }
}
