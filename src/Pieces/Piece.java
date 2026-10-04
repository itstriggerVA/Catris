package Pieces;

import main.Cell;
import main.GameController;
import main.KeyHandler;
import main.TetrisPanel;

import java.awt.Color;
import java.awt.Graphics2D;

public class Piece {

    public Cell c[] = new Cell[4];
    public Cell tempC[] = new Cell[4];
    public int direction = 1; // 1-4: These reflect 0/90/180/270-degree rotation
    int autoFallCounter = 0;
    boolean leftCollision, rightCollision, bottomCollision;
    public boolean inPlay = true;
    public boolean deactivating;
    public int deactivateCounter = 0;

    public void create(Color color) {
        c[0] = new Cell(color);
        c[1] = new Cell(color);
        c[2] = new Cell(color);
        c[3] = new Cell(color);
        tempC[0] = new Cell(color);
        tempC[1] = new Cell(color);
        tempC[2] = new Cell(color);
        tempC[3] = new Cell(color);
    }

    public void setXY(int x, int y) {
    }

    public void updateXY(int direction) {
        //We're using the TempC array as a way to save the Piece's previous location
        //So in case of collision, we can bump it back to its old position
        checkRotationCollision();


        if(!leftCollision && !rightCollision && !bottomCollision) {
            this.direction = direction;
            c[0].x = tempC[0].x;
            c[0].y = tempC[0].y;
            c[1].x = tempC[1].x;
            c[1].y = tempC[1].y;
            c[2].x = tempC[2].x;
            c[2].y = tempC[2].y;
            c[3].x = tempC[3].x;
            c[3].y = tempC[3].y;
        }
    }

    public void getDirection1() {}
    public void getDirection2() {}
    public void getDirection3() {}
    public void getDirection4() {}

    public void checkMovementCollision() {
        leftCollision = false;
        rightCollision = false;
        bottomCollision = false;

        //check if current piece is on top of other pieces before moving
        checkStaticCellCollision();

        //Check collision
        //Left wall
        for(int i = 0; i < c.length; i++) {
            if(c[i].x  == GameController.left_x) {
                leftCollision = true;
            }
        }
        //Implementing this without + cell size lets it pass through the wall
        //Right wall
        for(int i = 0; i < c.length; i++) {
            if(c[i].x + Cell.SIZE == GameController.right_x){
                rightCollision = true;
            }
        }
        //Bottom floor
        for(int i = 0; i < c.length; i++) {
            if(c[i].y + Cell.SIZE == GameController.bottom_y){
                bottomCollision = true;
            }
        }
    }

    public void checkRotationCollision() {

        leftCollision = false;
        rightCollision = false;
        bottomCollision = false;

        //check if current piece is on top of other pieces before rotating
        checkStaticCellCollision();

        //Check collision
        //Left wall
        for(int i = 0; i < c.length; i++) {
            if(tempC[i].x  < GameController.left_x) {
                leftCollision = true;
            }
        }
        //Implementing this without + cell size lets it pass through the wall
        //Right wall
        for(int i = 0; i < c.length; i++) {
            if(tempC[i].x + Cell.SIZE > GameController.right_x){
                rightCollision = true;
            }
        }
        //Bottom floor
        for(int i = 0; i < c.length; i++) {
            if(tempC[i].y + Cell.SIZE > GameController.bottom_y){
                bottomCollision = true;
            }
        }
    }

    private void checkStaticCellCollision(){
        //for loop to go through all the static cells
        for(int i = 0; i < GameController.staticCells.size(); i++) {
            int targetX = GameController.staticCells.get(i).x;
            int targetY = GameController.staticCells.get(i).y;

            //nested for-loops for checking all sides of each cell on current Piece
            for (int ii = 0; ii < c.length; ii++) {
                if (c[ii].y + Cell.SIZE == targetY && c[ii].x == targetX) {
                    bottomCollision = true;
                }
            }
            for(int ii = 0; ii < c.length; ii++) {
                if(c[ii].x - Cell.SIZE == targetX && c[ii].y == targetY) {
                    leftCollision = true;
                }
            }
            for (int ii = 0; ii < c.length; ii++) {
                if(c[ii].x + Cell.SIZE == targetX && c[ii].y == targetY) {
                    rightCollision = true;
                }
            }
        }
    }

    public void update() {
        if(deactivating) {
            deactivating();
        }
        //Move the piece
        if(KeyHandler.upPressed) {
            switch(direction){
                case 1: getDirection2();
                break;
                case 2: getDirection3();
                break;
                case 3: getDirection4();
                break;
                case 4: getDirection1();
                break;
            }
            KeyHandler.upPressed = false;
            TetrisPanel.se.play(6,false);
        }

        checkMovementCollision();

        if(KeyHandler.downPressed) {
            //pieces can only move down when its bottom is not hitting something
            if(!bottomCollision) {
                c[0].y += Cell.SIZE;
                c[1].y += Cell.SIZE;
                c[2].y += Cell.SIZE;
                c[3].y += Cell.SIZE;

                //Reset fall counter so it doesn't instant-drop
                autoFallCounter = 0;
            }
//            else if(bottomCollision == true){
//                System.out.println("Uh oh");
//            }
            KeyHandler.downPressed = false;
        }

        if(KeyHandler.leftPressed) {
            if(!leftCollision){
                c[0].x -= Cell.SIZE;
                c[1].x -= Cell.SIZE;
                c[2].x -= Cell.SIZE;
                c[3].x -= Cell.SIZE;
                TetrisPanel.se.play(5, false);
            }
            KeyHandler.leftPressed = false;
        }

        if(KeyHandler.rightPressed) {
            if(!rightCollision) {
                c[0].x += Cell.SIZE;
                c[1].x += Cell.SIZE;
                c[2].x += Cell.SIZE;
                c[3].x += Cell.SIZE;
                TetrisPanel.se.play(5, false);
        }
            KeyHandler.rightPressed = false;
    }

        if(KeyHandler.spacePressed) {
            while (!bottomCollision) {
                c[0].y += 10;
                c[1].y += 10;
                c[2].y += 10;
                c[3].y += 10;
                checkMovementCollision();
            }
            KeyHandler.spacePressed = false;
            inPlay = false;
        }

        //We stop the piece from moving after it hits the ground
        if(bottomCollision) {
            if(!deactivating) { //plays sound once before deactivating or else it plays over and over ._.
                TetrisPanel.se.play(8, false);
            }
            deactivating = true;
        } else {
            //Make the piece slowly fall
            autoFallCounter++;
            if(autoFallCounter == GameController.fallSpeed) {
                c[0].y += Cell.SIZE;
                c[1].y += Cell.SIZE;
                c[2].y += Cell.SIZE;
                c[3].y += Cell.SIZE;
                autoFallCounter = 0;
            }
        }
    }

    //This method is used to stall a bit before the piece lands completely
    private void deactivating() {
        deactivateCounter++;

        //Wait 45 frames until deactivate
        if(deactivateCounter == 30) {
            deactivateCounter = 0;
            checkMovementCollision(); //check if the bottom of the block is still colliding

            //if the collision is still happening
            if(bottomCollision) {
                inPlay = false;
            }
        }
    }

    public void draw(Graphics2D g2) {
        int border = 2;
        g2.setColor(c[0].color);
        for (Cell cell : c) {
            if (cell.y >= GameController.top_y) { // Only draw cells within the visible grid area
                g2.fillRect(cell.x + border, cell.y + border, Cell.SIZE - (border * 2), Cell.SIZE - (border * 2));
            }
        }
    }
}