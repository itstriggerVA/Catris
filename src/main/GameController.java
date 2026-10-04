package main;

import Pieces.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class GameController {

    //Grid
    final int WIDTH = 300;
    final int HEIGHT = 630;
    public static int left_x;
    public static int right_x;
    public static int top_y;
    public static int bottom_y;

    //Cat
    private Cat cat;
    private boolean catFalling = false; //Supposed to be used for changing cat's animations when falling

    // Piece
    Piece currentPiece;
    final int PIECE_START_X;
    final int PIECE_START_Y;
    Piece nextPiece;
    final int NEXTPIECE_X;
    final int NEXTPIECE_Y;
    // Piece bag is used for making nextPiece more random
    private LinkedList<Piece> pieceBag = new LinkedList<>();

    // Array for storing the nextPiece
    public static ArrayList<Cell> staticCells = new ArrayList<>();
    public static int fallSpeed = 60; //piece drops every 60 frames

    //Progression
    public int level = 1;
    public int levelMult = 2;
    public int linesCleared;
    public int lineGoal = levelMult*level;
    boolean gameOver;
    boolean levelComplete;

    //VisualEffects
    boolean effectCounterOn;
    int effectCounter;
    ArrayList<Integer> effectY = new ArrayList<>();

    //Game Images
    BufferedImage bgImage1, bgImage2, bgImage3, currentBgImage, pauseImage, levelCompleteImage;
    ImageIcon gameOverGif;

    //URLs for images
    URL[] imageURL = new URL[10];

    // UserProfile for saving progress
    private UserProfile userProfile;

    // Constructor that accepts UserProfile
    public GameController(UserProfile userProfile) {
        this.userProfile = userProfile; //sets the userProfile. Guest by default

        left_x = (TetrisPanel.WIDTH / 2) - (WIDTH / 2); // 1280/2 - 300/2 = 490
        right_x = left_x + WIDTH;
        top_y = 50;
        bottom_y = top_y + HEIGHT;

        PIECE_START_X = left_x + (WIDTH / 2) - Cell.SIZE;
        PIECE_START_Y = top_y;

        // This is where the next piece will be shown
        NEXTPIECE_X = right_x + 190;
        NEXTPIECE_Y = top_y + 505;

        // Shuffle pieces before selecting first piece
        refillBag();

        // Setting the first piece
        currentPiece = drawPiece();
        currentPiece.setXY(PIECE_START_X, PIECE_START_Y);

        // Setting the next piece
        nextPiece = drawPiece();
        nextPiece.setXY(NEXTPIECE_X, NEXTPIECE_Y);

        // Setting the cat's initial position
        int catStartY = bottom_y;
        int catSize = 100;
        String catClimbPath = "/img/cat_climb.gif";
        String catIdlePath = "/img/cat_fall.gif";
        String catFallingPath = "/img/cat_fall.gif";
        cat = new Cat(left_x - 110, catStartY, catSize, catClimbPath, catIdlePath, catFallingPath);

        // Load the background images
        try {
            imageURL[0] = getClass().getResource("/img/pausemenu.png");
            imageURL[1] = getClass().getResource("/img/lvl1.jpg");
            imageURL[2] = getClass().getResource("/img/lvl2.jpg");
            imageURL[3] = getClass().getResource("/img/lvl3.jpg");
            imageURL[4] = getClass().getResource("/img/gameover.gif");
            imageURL[5] = getClass().getResource("/img/levelComplete.png");
            imageURL[6] = getClass().getResource("/img/game_over.png");
            // setting the URLs to images
            bgImage1 = ImageIO.read(imageURL[1]);
            bgImage2 = ImageIO.read(imageURL[2]);
            bgImage3 = ImageIO.read(imageURL[3]);
            gameOverGif = new ImageIcon(imageURL[6]);
            pauseImage = ImageIO.read(imageURL[0]);
            levelCompleteImage = ImageIO.read(imageURL[5]);
        } catch (Exception e) {
            System.out.println("Images not loaded");
            e.printStackTrace();
        }
    }

    // Method to update the cat's position when pieces are placed
    private void updateCatPosition() {
        int highestY = bottom_y;
        for (Cell cell : staticCells) {
            if (cell.y < highestY) {
                highestY = cell.y;
            }
        }
        cat.updatePosition(Math.min(highestY, bottom_y)); // this is to ensure that the cat does not go below bottom_y
        if (cat.getY() == bottom_y - cat.getSize()) {
            cat.setState("idle");
        } else {
            cat.setState("climbing");
        }
    }

    // Puts all 7 pieces in a bag and shuffles them for less duplicate pieces
    private void refillBag() {
        List<Piece> pieces = new ArrayList<>();
        pieces.add(new Piece_I());
        pieces.add(new Piece_J());
        pieces.add(new Piece_L());
        pieces.add(new Piece_O());
        pieces.add(new Piece_S());
        pieces.add(new Piece_T());
        pieces.add(new Piece_Z());
        Collections.shuffle(pieces);
        pieceBag.addAll(pieces);
    } // Previously used a Switch statement which gave too many duplicates

    // Selects a new piece from the bag
    private Piece drawPiece() {
        if (pieceBag.isEmpty()) {
            refillBag();
        }
        return pieceBag.removeFirst();
    }

    public void update() {
        if (!currentPiece.inPlay) {
            // If this piece isn't active, put it into static array
            staticCells.add(currentPiece.c[0]);
            staticCells.add(currentPiece.c[1]);
            staticCells.add(currentPiece.c[2]);
            staticCells.add(currentPiece.c[3]);

            // check if the game is over
            if (currentPiece.c[0].x == PIECE_START_X && currentPiece.c[0].y == PIECE_START_Y) {
                // As soon as a piece is deactivated, we check whether we have space to move the piece
                gameOver = true;
                TetrisPanel.music.stop();
                TetrisPanel.se.play(9, false);
                // Logic for catItem falling
            }

            currentPiece.deactivating = false;

            // Replace the current piece with the next piece after
            currentPiece = nextPiece;
            currentPiece.setXY(PIECE_START_X, PIECE_START_Y);
            nextPiece = drawPiece();
            nextPiece.setXY(NEXTPIECE_X, NEXTPIECE_Y);

            // When a piece is out of play, check if lines can be cleared
            checkClear();

            // After the player meets the line goal, they level up
            if (linesCleared >= lineGoal && level < 3) {
                levelComplete = true; // ends the level upon reaching the line goal
            } else if (linesCleared >= lineGoal && level >= 3) {
                linesCleared = 0;
                level++;
                lineGoal = levelMult * level; // Updates the lineGoal
                speedUp();
            }

            updateCatPosition();
        } else {
            currentPiece.update();
        }
    }

    public void checkClear() {
        int x = left_x;
        int y = top_y;
        int cellCount = 0;

        while (x < right_x && y < bottom_y) {

            for (int i = 0; i < staticCells.size(); i++) {
                if (staticCells.get(i).x == x && staticCells.get(i).y == y) {
                    // We scan this array through and see if the x/y coords match to see if the cell exists on the board
                    // Increase the count for each static block
                    cellCount++;
                }
            }

            x += Cell.SIZE;

            if (x == right_x) {
                // row is 10-cells wide right now but we can use this for dynamic calc
                // if the row is full, delete line via for loop

                if (cellCount == WIDTH / 30) {
                    effectCounterOn = true;
                    effectY.add(y); // Adding this to array so it can make effect for multiple lines

                    for (int i = staticCells.size() - 1; i > -1; i--) {
                        // remove all cells in the current y-position
                        // must remove from max to min index since deleting from min
                        // will mess up the variable positions in the array
                        if (staticCells.get(i).y == y) {
                            staticCells.remove(i);
                        }
                    }
                    // Setting new temp line count for different sound effects per line clear
                    int tempLinesCleared = linesCleared;
                    linesCleared++; // update the line

                    switch (linesCleared - tempLinesCleared) {
                        case 1:
                            TetrisPanel.se.play(1, false);
                            break;
                        case 2:
                            TetrisPanel.se.play(2, false);
                            break;
                        case 3:
                            TetrisPanel.se.play(3, false);
                            break;
                        case 4:
                            TetrisPanel.se.play(4, false);
                            break;
                        default:
                            break;
                    }

                    // slide down cells above the cleared line
                    for (int i = 0; i < staticCells.size(); i++) {
                        if (staticCells.get(i).y < y) {
                            staticCells.get(i).y += Cell.SIZE;
                        }
                    }

                    // Cat falls after lines are moved down
                    cat.setState("falling");
                }

                // resetting the count and add line cleared after clearing
                cellCount = 0;
                x = left_x;
                y += Cell.SIZE;
            }
        }
        updateCatPosition();
    }

    public void speedUp() { // Previous lvl up method. Now increases the fall speed
        if (fallSpeed > 1) {
            if (fallSpeed > 10) {
                fallSpeed -= 10;
            } else {
                fallSpeed -= 1;
            }
        }
    }

    public void resetGame() { // Fully resets the game to start a new one
        linesCleared = 0; // linesCleared is set to 0 to restart
        lineGoal = levelMult * level; // Updates the lineGoal
        staticCells.clear();
        refillBag();
        currentPiece = drawPiece();
        currentPiece.setXY(PIECE_START_X, PIECE_START_Y);
        nextPiece = drawPiece();
        nextPiece.setXY(NEXTPIECE_X, NEXTPIECE_Y);
        updateCatPosition();
    }

    public void draw(Graphics2D g2) {

        // Drawing the background image
        currentBgImage = bgImage1;
        if (currentBgImage != null) {
            if (level == 1) {
                currentBgImage = bgImage1;
                g2.drawImage(currentBgImage, 0, 0, TetrisPanel.WIDTH, TetrisPanel.HEIGHT, null);
            } else if (level == 2) {
                currentBgImage = bgImage2;
                g2.drawImage(currentBgImage, 0, 0, TetrisPanel.WIDTH, TetrisPanel.HEIGHT, null);
            } else {
                currentBgImage = bgImage3;
                g2.drawImage(currentBgImage, 0, 0, TetrisPanel.WIDTH, TetrisPanel.HEIGHT, null);
            }
        }

        // Drawing the grid Frame
        g2.setColor(Color.black);
        g2.fillRect(left_x, top_y, WIDTH, HEIGHT); // Filling grid to make sure background image is visible
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(4f));
        g2.drawRect(left_x - 4, top_y - 4, WIDTH + 8, HEIGHT + 8); // (4,4) is where we want collision to happen cause the width of the grid border is 4

        // Drawing the gridwall for the cat
        g2.setColor(Color.black);
        g2.fillRect(left_x - 107, top_y, WIDTH / 3, HEIGHT);
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(4f));

        // Draw the cat
        cat.draw(g2);

        // Drawing next piece frame
        int x = right_x + 100;
        int y = bottom_y - 250;
        g2.setColor(Color.black); // Set background color to black
        g2.fillRect(x, y, 200, 200); // Fill the background
        g2.setColor(Color.white); // Set frame color to white
        g2.drawRect(x, y, 200, 200);
        g2.drawRect(x, y, 200, 200);
        g2.setFont(new Font("Daydream", Font.PLAIN, 20));
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        // Cosmetic rendering hint to remove jaggies from the text
        g2.drawString("NEXT", x + 60, y + 60);

        // Draw Progress Frame
        x = right_x + 100;
        y = top_y;
        g2.setColor(Color.black); // Set background color to black
        g2.fillRect(x, y, 350, 250); // Fill the background
        g2.setColor(Color.white); // Set frame color to white
        g2.drawRect(x, y, 350, 250);
        x += 40;
        y = top_y + 50;
        g2.drawString("LEVEL: " + level, x, y);
        y += 70;
        g2.drawString("LINES CLEARED: " + linesCleared, x, y);
        y += 70;
        g2.drawString("LINE GOAL: " + lineGoal, x, y);

        // Draw the current Piece
        if (currentPiece != null) {
            currentPiece.draw(g2);
        }

        // Draw next Piece
        nextPiece.draw(g2);

        // Draw static cells, one at a time
        for (int i = 0; i < staticCells.size(); i++) {
            staticCells.get(i).draw(g2);
        }

        // Draw Effect
        if (effectCounterOn) {
            effectCounter++;

            g2.setColor(Color.white);
            for (int i = 0; i < effectY.size(); i++) {
                g2.fillRect(left_x, effectY.get(i), WIDTH, Cell.SIZE);
            } // this draws a rectangle in the lines cleared

            if (effectCounter == 6) { // 6 frames is the duration of this effect
                effectCounterOn = false;
                effectCounter = 0;
                effectY.clear();
            }
        }

        // Draw the pause menu and Game Over.
        g2.setColor(Color.black);
        g2.setFont(g2.getFont().deriveFont(30f));

        if (gameOver) {
            x = (TetrisPanel.WIDTH / 2);
            y = (TetrisPanel.HEIGHT / 2);
            g2.setColor(Color.darkGray);
            g2.setStroke(new BasicStroke(10f));
            g2.drawRect(x - 250, y - 225, 500, 500);
            g2.drawImage(gameOverGif.getImage(), x - 250, y - 225, 500, 500, null);
            g2.setFont(new Font("Daydream", Font.PLAIN, 10));
            g2.setColor(Color.black);
            g2.drawString("Press Enter to Retry", x - 200, y += 200);
            g2.drawString("Press Escape for Main Menu", x - 200, y + 30);

        } else if (KeyHandler.pausePressed) {
            x = (TetrisPanel.WIDTH / 2);
            y = (TetrisPanel.HEIGHT / 2);
            g2.drawImage(pauseImage, x - 250, y - 200, 500, 500, null);
            g2.drawString("GAME PAUSED", x - 170, y -= 130);
            g2.setFont(new Font("Daydream", Font.PLAIN, 12));
            g2.drawString("Press P to Resume", x - 170, y += 350);
            g2.drawString("Press Escape for Main Menu", x - 170, y += 30);

        } else if (levelComplete) {
            x = (TetrisPanel.WIDTH / 2);
            y = (TetrisPanel.HEIGHT / 2);
            g2.drawImage(levelCompleteImage, x - 250, y - 225, 500, 500, null);
            g2.setColor(Color.darkGray);
            g2.setStroke(new BasicStroke(10f));
            g2.drawRect(x - 250, y - 225, 500, 500);
            g2.setColor(Color.white);
            g2.drawString("Level Complete", x - 200, y - 180);
            // Add text for Continue and Main Menu inputs
            g2.setFont(new Font("Daydream", Font.PLAIN, 10));
            g2.drawString("Press Enter to Continue", x - 200, y += 210);
            g2.drawString("Press Escape for Main Menu", x - 200, y + 30);
        }
    }
}