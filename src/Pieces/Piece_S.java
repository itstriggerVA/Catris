package Pieces;

import main.Cell;

import java.awt.*;

public class Piece_S extends Piece{

    public Piece_S() {
        create(Color.green);
    }

    //   c0 1
    // 1 1

    public void setXY(int x, int y){
        c[0].x = x;
        c[0].y = y;
        c[1].x = c[0].x + Cell.SIZE;
        c[1].y = c[0].y;
        c[2].x = c[0].x;
        c[2].y = c[0].y + Cell.SIZE;
        c[3].x = c[0].x - Cell.SIZE;
        c[3].y = c[0].y + Cell.SIZE;
    }

    public void getDirection1() {
        //   c0 1
        // 1 1
        tempC[0].x = c[0].x;
        tempC[0].y = c[0].y;
        tempC[1].x = c[0].x + Cell.SIZE;
        tempC[1].y = c[0].y;
        tempC[2].x = c[0].x;
        tempC[2].y = c[0].y + Cell.SIZE;
        tempC[3].x = c[0].x - Cell.SIZE;
        tempC[3].y = c[0].y + Cell.SIZE;

        updateXY(1);
    }

    public void getDirection2() {
        // 1
        // c0 1
        //    1
        tempC[0].x = c[0].x;
        tempC[0].y = c[0].y;
        tempC[1].x = c[0].x;
        tempC[1].y = c[0].y - Cell.SIZE;
        tempC[2].x = c[0].x + Cell.SIZE;
        tempC[2].y = c[0].y;
        tempC[3].x = c[0].x + Cell.SIZE;
        tempC[3].y = c[0].y + Cell.SIZE;

        updateXY(2);
    }
    public void getDirection3() {
        getDirection1();
    }
    public void getDirection4() {
        getDirection2();
    }
}
