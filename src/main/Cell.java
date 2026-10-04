package main;

import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Graphics2D;

public class Cell extends Rectangle {

        public int x, y;
        public static final int SIZE = 30;
        public Color color;

        public Cell(Color color) {
            this.color = color;
        }

        public void draw(Graphics2D g2) {
            int border = 2;
            g2.setColor(color);
            g2.fillRect(x+border, y+border, SIZE-border*2, SIZE-border*2);
        }
}
