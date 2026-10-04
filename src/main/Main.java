package main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame gameWindow = new JFrame("Catris");
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setResizable(false);

        // Adding TitlePanel to the window
        TitlePanel titlePanel = new TitlePanel(gameWindow);
        gameWindow.add(titlePanel);
        gameWindow.pack();

        // Make window show up in center
        gameWindow.setLocationRelativeTo(null);
        gameWindow.setVisible(true);
    }
}