package main;

import java.awt.*;
import javax.swing.JPanel;
import javax.swing.JFrame;

public class TetrisPanel extends JPanel implements Runnable {

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    final int FPS = 60;
    Thread gameThread; // Using a thread to run the game loop
    GameController gc;
    public static Sound music = new Sound(); // bgm
    public static Sound se = new Sound(); // sound effects
    public boolean levelCompleteSoundPlayed;

    private JFrame gameWindow;
    private UserProfile userProfile;

    public TetrisPanel(JFrame gameWindow, UserProfile userProfile) {
        this.gameWindow = gameWindow;
        this.userProfile = userProfile;

        // Panel Settings
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.black);
        this.setLayout(null);

        // Adding KeyListener
        KeyHandler keyHandler = new KeyHandler();
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        this.requestFocusInWindow();

        gc = new GameController(userProfile);
    }

    @Override
    public void addNotify() {
        super.addNotify();
        this.requestFocusInWindow();
    }

    public void startGame() {
        gameThread = new Thread(this);
        gameThread.start();

        music.play(0, true);
        music.loop();
    }

    // Thread starts and auto-launches run method
    @Override
    public void run() {
        // Game loop
        double drawInterval = 1000000000 / FPS; // Cause time is in nanosec 10^9/FPS
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) { // while game is still running

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval; // Delta will quickly increment to 1
            lastTime = currentTime;

            if (delta >= 1) { // So the game updates 60 times per sec
                update();
                repaint();
                delta--;
            }
        }
    }

    private void update() {
        if (!KeyHandler.pausePressed && !gc.gameOver && !gc.levelComplete) {
            gc.update();
        }
        if (gc.levelComplete) {
            if (!levelCompleteSoundPlayed) {
                music.stop();
                music.play(10, false);
                levelCompleteSoundPlayed = true;
            }
            if (KeyHandler.enterPressed) {
                nextLevel();
                KeyHandler.enterPressed = false;
                levelCompleteSoundPlayed = false;
                music.play(0, true);
                music.loop();
            } else if (KeyHandler.escPressed) {
                goToMainMenu();
                KeyHandler.escPressed = false;
            }
        }
        if (gc.gameOver) { // There was a bug where if you had held space during game over, it would cause the 1st piece to insta-drop
            if (KeyHandler.enterPressed) {
                KeyHandler.enterPressed = false; // Reset the flag
                KeyHandler.spacePressed = false;
                gc.gameOver = false;
                retryGame();
                music.play(0, true);
                music.loop();
            } else if (KeyHandler.escPressed) {
                goToMainMenu();
                KeyHandler.escPressed = false; // Reset the flag
                KeyHandler.spacePressed = false;
            }
        }
        if (KeyHandler.pausePressed) {
            if (KeyHandler.escPressed) {
                goToMainMenu();
                KeyHandler.escPressed = false;
                KeyHandler.pausePressed = false;
            }
        }
    }

    private void nextLevel() {
        gc.level++;
        gc.levelComplete = false;
        userProfile.unlockNextLevel();
        userProfile.saveProfile();
        gc.resetGame();
        gc.speedUp();
        repaint();
    }

    private void retryGame() { // Retry method for game over
        if (gc.level >= 3) { // Set to manage the endless level
            gc.level = 3;
        }
        gc.levelComplete = false;
        gc.resetGame();
        repaint();
    }

    private void goToMainMenu() {
        // Stop the game and navigate to the main menu
        gc.resetGame();
        music.stop();
        gameThread = null;
        gameWindow.remove(this);
        TitlePanel titlePanel = new TitlePanel(gameWindow);
        gameWindow.add(titlePanel);
        gameWindow.revalidate();
        gameWindow.repaint();
    }

    // For drawing UI text and graphics
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g; // Calling Graphics2D since gridController uses it
        gc.draw(g2);
    }
}
