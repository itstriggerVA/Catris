package main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class LevelSelectionDialog extends JDialog {
    private int selectedLevel;

    public LevelSelectionDialog(JFrame parentFrame, UserProfile userProfile) {
        super(parentFrame, "Select Level", true);
        this.setLayout(new GridLayout(4, 1));
        this.setSize(400, 300);
        this.setLocationRelativeTo(parentFrame);

        JLabel instructionLabel = new JLabel("Select a Level:", SwingConstants.CENTER);
        instructionLabel.setFont(new Font("Daydream", Font.BOLD, 24));
        this.add(instructionLabel);

        JButton level1Button = new JButton("Level 1");
        JButton level2Button = new JButton("Level 2");
        JButton level3Button = new JButton("Level 3 (Endless)");

        level1Button.setFont(new Font("Daydream", Font.PLAIN, 18));
        level2Button.setFont(new Font("Daydream", Font.PLAIN, 18));
        level3Button.setFont(new Font("Daydream", Font.PLAIN, 18));

        level1Button.setEnabled(userProfile.getMaxLevelUnlocked() >= 1);
        level2Button.setEnabled(userProfile.getMaxLevelUnlocked() >= 2);
        level3Button.setEnabled(userProfile.getMaxLevelUnlocked() >= 3);

        level1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedLevel = 1;
                dispose();
            }
        });

        level2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedLevel = 2;
                dispose();
            }
        });

        level3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedLevel = 3;
                dispose();
            }
        });

        this.add(level1Button);
        this.add(level2Button);
        this.add(level3Button);
    }

    public int getSelectedLevel() {
        return selectedLevel;
    }
}
