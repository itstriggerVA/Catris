package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public static boolean upPressed, downPressed, leftPressed, rightPressed,
            spacePressed, holdPressed, pausePressed, enterPressed, escPressed;

    @Override
    public void keyTyped(KeyEvent e) {
        //Don't need
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //Don't need
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_UP) {
            upPressed = true;
        }
        if(code == KeyEvent.VK_ESCAPE){
            escPressed = true;
        }
        if(code == KeyEvent.VK_ENTER){
            enterPressed = true;
        }
        if(code == KeyEvent.VK_LEFT){
            leftPressed = true;
        }
        if(code == KeyEvent.VK_DOWN){
            downPressed = true;
        }
        if(code == KeyEvent.VK_RIGHT){
            rightPressed = true;
        }
        if(code == KeyEvent.VK_SPACE){
            spacePressed = true;
        }
        if(code == KeyEvent.VK_C){
            holdPressed = true;
        }
        if(code == KeyEvent.VK_P){
            if(pausePressed) {
                pausePressed = false;
                TetrisPanel.music.play(0, true);
                TetrisPanel.music.loop(); //I do not know how to make it resume
            } else {
                pausePressed = true;
                TetrisPanel.music.stop();
                //Logic for displaying pause screen
            }
        }
    }
}
