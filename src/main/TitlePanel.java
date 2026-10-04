package main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class TitlePanel extends JPanel {

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    private JButton startButton;
    private JButton profileButton;
    private Image backgroundImage;
    private UserProfile userProfile;

    public TitlePanel(JFrame gameWindow) {
        backgroundImage = new ImageIcon(getClass().getResource("/img/Title.jpg")).getImage();

        // Panel Settings
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.black);
        this.setLayout(null);

        // Start as a guest by default
        userProfile = new UserProfile("Guest");

        // Start Button
        startButton = new JButton("Start");
        startButton.setFont(new Font("Daydream", Font.BOLD, 36));
        startButton.setBounds(WIDTH / 2 - 150, HEIGHT - 150, 300, 100);
        this.add(startButton);

        // Profile Button
        profileButton = new JButton("Profile");
        profileButton.setFont(new Font("Daydream", Font.BOLD, 18));
        profileButton.setBounds(WIDTH / 4 - 200, HEIGHT - 140, 200, 40);
        this.add(profileButton);

        // Start Button Action
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show the level selection dialog
                LevelSelectionDialog levelDialog = new LevelSelectionDialog(gameWindow, userProfile);
                levelDialog.setVisible(true);

                int selectedLevel = levelDialog.getSelectedLevel();
                if (selectedLevel > 0) {
                    // Start the game with the selected level
                    gameWindow.remove(TitlePanel.this);
                    TetrisPanel tp = new TetrisPanel(gameWindow, userProfile); // Pass the user profile to TetrisPanel
                    tp.gc.level = selectedLevel; // Set the selected level
                    gameWindow.add(tp);
                    gameWindow.revalidate();
                    gameWindow.repaint();
                    tp.startGame();
                }
            }
        });

        // Profile Button Action
        profileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = JOptionPane.showInputDialog(TitlePanel.this, "Enter your username:");
                if (username != null && !username.trim().isEmpty()) {
                    userProfile = UserProfile.loadProfile(username);
                    JOptionPane.showMessageDialog(TitlePanel.this, "Profile loaded: " + userProfile.getUsername());
                }
            }
        });
    }

    // Background image
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the background image
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
