package Pieces;

import main.Cell;

import java.awt.Color;

public class Piece_T extends Piece{

    public Piece_T() {
        create(Color.magenta);
    }

    //   1
    // 1 c0 1

    public void setXY(int x, int y) {
        c[0].x = x;
        c[0].y = y;
        c[1].x = c[0].x;
        c[1].y = c[0].y - Cell.SIZE;
        c[2].x = c[0].x - Cell.SIZE;
        c[2].y = c[0].y;
        c[3].x = c[0].x + Cell.SIZE;
        c[3].y = c[0].y;
    }

    public void getDirection1() {
        //   1
        // 1 c0 1
        tempC[0].x = c[0].x;
        tempC[0].y = c[0].y;
        tempC[1].x = c[0].x;
        tempC[1].y = c[0].y - Cell.SIZE;
        tempC[2].x = c[0].x - Cell.SIZE;
        tempC[2].y = c[0].y;
        tempC[3].x = c[0].x + Cell.SIZE;
        tempC[3].y = c[0].y;

        updateXY(1);
    }

    public void getDirection2() {
        // 1
        // c0 1
        // 1
        tempC[0].x = c[0].x;
        tempC[0].y = c[0].y;
        tempC[1].x = c[0].x + Cell.SIZE;
        tempC[1].y = c[0].y;
        tempC[2].x = c[0].x;
        tempC[2].y = c[0].y - Cell.SIZE;
        tempC[3].x = c[0].x;
        tempC[3].y = c[0].y + Cell.SIZE;

        updateXY(2);

    }
    public void getDirection3() {
        // 1 c0 1
        //   1
        tempC[0].x = c[0].x;
        tempC[0].y = c[0].y;
        tempC[1].x = c[0].x;
        tempC[1].y = c[0].y + Cell.SIZE;
        tempC[2].x = c[0].x + Cell.SIZE;
        tempC[2].y = c[0].y;
        tempC[3].x = c[0].x - Cell.SIZE;
        tempC[3].y = c[0].y;

        updateXY(3);

    }
    public void getDirection4() {
        //  1
        //1 c0
        //  1
        tempC[0].x = c[0].x;
        tempC[0].y = c[0].y;
        tempC[1].x = c[0].x - Cell.SIZE;
        tempC[1].y = c[0].y;
        tempC[2].x = c[0].x;
        tempC[2].y = c[0].y + Cell.SIZE;
        tempC[3].x = c[0].x;
        tempC[3].y = c[0].y - Cell.SIZE;

        updateXY(4);
    }
}
