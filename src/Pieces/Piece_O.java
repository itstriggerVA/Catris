package Pieces;

import main.Cell;

import java.awt.Color;

public class Piece_O extends Piece {

    public Piece_O() {
        create(Color.yellow);
    }
    public void setXY(int x, int y) {
        // c0 1
        // 1  1
        c[0].x = x;
        c[0].y = y;
        c[1].x = c[0].x;
        c[1].y = c[0].y + Cell.SIZE;
        c[2].x = c[0].x + Cell.SIZE;
        c[2].y = c[0].y;
        c[3].x = c[0].x + Cell.SIZE;
        c[3].y = c[0].y + Cell.SIZE;
    }
    public void getDirection1() {}
    public void getDirection2() {}
    public void getDirection3() {}
    public void getDirection4() {}

}
