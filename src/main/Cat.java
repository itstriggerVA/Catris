package main;

import javax.swing.*;
import java.awt.*;

public class Cat {
    private int x;
    private int y;
    private final int size;
    private ImageIcon climbingImage;
    private ImageIcon idleImage;
    private ImageIcon fallingImage;
    private ImageIcon currentImage;

    public Cat(int x, int y, int size, String climbingImagePath, String idleImagePath, String fallingImagePath) {
        this.x = x;
        this.size = size;
        this.climbingImage = new ImageIcon(getClass().getResource(climbingImagePath));
        this.idleImage = new ImageIcon(getClass().getResource(idleImagePath));
        this.fallingImage = new ImageIcon(getClass().getResource(fallingImagePath));
        this.currentImage = idleImage;
        // Ensure the initial y position is set correctly
        this.y = y - size;
    }

    public int getY() {
        return y;
    }

    public int getSize() {
        return size;
    }

    public void updatePosition(int newY) {
            this.y = newY - size;
        }


    public void setState(String state) {
        switch (state) {
            case "climbing":
                currentImage = climbingImage;
                break;
            case "idle":
                currentImage = idleImage;
                break;
            case "falling":
                currentImage = fallingImage;
                break;
        }
    }

    public void draw(Graphics2D g) {
        if (currentImage != null) {
            Image image = currentImage.getImage();
            g.drawImage(image, x, y, size, size, null);
        }
    }
}
