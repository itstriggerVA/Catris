package Pieces;

import main.Cell;

import java.awt.*;

public class Piece_L extends Piece{

    public Piece_L() {
        create(Color.orange);
    }

    //Default shape for reference
    // 1
    // 1
    // 1 1

    public void setXY(int x, int y) {
        c[0].x = x; //this c[0] is the 2nd block cause all pieces rotate from 2nd block
        c[0].y = y;
        c[1].x = c[0].x; //Since these are in the same row
        c[1].y = c[0].y - Cell.SIZE; //Since this is on top of c[0]
        c[2].x = c[0].x;
        c[2].y = c[0].y + Cell.SIZE;
        c[3].x = c[0].x + Cell.SIZE;
        c[3].y = c[0].y + Cell.SIZE;
    }

    public void getDirection1() {
        // 1
        // c0
        // 1 1
        tempC[0].x = c[0].x;
        tempC[0].y = c[0].y;
        tempC[1].x = c[0].x;
        tempC[1].y = c[0].y - Cell.SIZE;
        tempC[2].x = c[0].x;
        tempC[2].y = c[0].y + Cell.SIZE;
        tempC[3].x = c[0].x + Cell.SIZE;
        tempC[3].y = c[0].y + Cell.SIZE;

        updateXY(1); //calling updateXY from Piece
    }

    public void getDirection2() {
        // c2 c0 c1
        // c3
        tempC[0].x = c[0].x;
        tempC[0].y = c[0].y;
        tempC[1].x = c[0].x + Cell.SIZE;
        tempC[1].y = c[0].y;
        tempC[2].x = c[0].x - Cell.SIZE;
        tempC[2].y = c[0].y;
        tempC[3].x = c[0].x - Cell.SIZE;
        tempC[3].y = c[0].y + Cell.SIZE;

        updateXY(2);
    }
    public void getDirection3() {
        // 1 1
        //   c0
        //   1
        tempC[0].x = c[0].x;
        tempC[0].y = c[0].y;
        tempC[1].x = c[0].x;
        tempC[1].y = c[0].y + Cell.SIZE;
        tempC[2].x = c[0].x;
        tempC[2].y = c[0].y - Cell.SIZE;
        tempC[3].x = c[0].x - Cell.SIZE;
        tempC[3].y = c[0].y - Cell.SIZE;

        updateXY(3);
    }
    public void getDirection4() {
        //       c3
        // c1 c0 c2
        tempC[0].x = c[0].x;
        tempC[0].y = c[0].y;
        tempC[1].x = c[0].x - Cell.SIZE;
        tempC[1].y = c[0].y;
        tempC[2].x = c[0].x + Cell.SIZE;
        tempC[2].y = c[0].y;
        tempC[3].x = c[0].x + Cell.SIZE;
        tempC[3].y = c[0].y - Cell.SIZE;

        updateXY(4);

    }
}
