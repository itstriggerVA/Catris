package Pieces;

import main.Cell;

import java.awt.Color;

public class Piece_I extends Piece{

    public Piece_I () {
        create(Color.cyan);
    }

    public void setXY(int x, int y){
        //
        // 1 c0 1 1
        c[0].x = x;
        c[0].y = y;
        c[1].x = c[0].x - Cell.SIZE;
        c[1].y = c[0].y;
        c[2].x = c[0].x + Cell.SIZE;
        c[2].y = c[0].y;
        c[3].x = c[0].x + Cell.SIZE*2;
        c[3].y = c[0].y;

    }

    public void getDirection1() {
        //
        // 1 c0 1 1
        tempC[0].x = c[0].x;
        tempC[0].y = c[0].y;
        tempC[1].x = c[0].x - Cell.SIZE;
        tempC[1].y = c[0].y;
        tempC[2].x = c[0].x + Cell.SIZE;
        tempC[2].y = c[0].y;
        tempC[3].x = c[0].x + Cell.SIZE*2;
        tempC[3].y = c[0].y;

        updateXY(1);
    }
    public void getDirection2() {
        // 1
        // c0
        // 1
        // 1
        tempC[0].x = c[0].x;
        tempC[0].y = c[0].y;
        tempC[1].x = c[0].x;
        tempC[1].y = c[0].y - Cell.SIZE;
        tempC[2].x = c[0].x ;
        tempC[2].y = c[0].y + Cell.SIZE;
        tempC[3].x = c[0].x ;
        tempC[3].y = c[0].y + Cell.SIZE*2;

        updateXY(2);
    }
    //This piece only has 2 rotations 0 and 90
    //Since we need 4 methods we will call the above 2 methods in the other directions too

    public void getDirection3() {
        getDirection1();
    }
    public void getDirection4() {
        getDirection2();
    }
}