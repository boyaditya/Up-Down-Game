package model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

public class UpperObstacle extends Obstacle {
    private static final String IMAGE_PATH = "/assets/images/rope.png"; // Image path
    private Image image; // Image object
    private static final int SCREEN_HEIGHT = 720; // Screen height game
    private static final int MAX_Y = SCREEN_HEIGHT / 2; // Maximum Y value, center of the screen

    // Constructor
    public UpperObstacle(double x, double y) {
        super(x, y, 64, 512, "UpperObstacle");
        loadImage();
    }

    // Load image
    private void loadImage() {
        this.image = new ImageIcon(getClass().getResource(IMAGE_PATH)).getImage();
    }

    // Render the obstacle
    @Override
    public void render(Graphics g) {
        drawObstacle(g);
        drawScore(g);
    }

    // Draw the obstacle
    private void drawObstacle(Graphics g) {
        g.drawImage(image, (int) x, (int) y, (int) width, (int) height, null);
    }

    // Draw the score
    private void drawScore(Graphics g) {
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.setColor(Color.WHITE);

        String scoreText = String.valueOf(getScore());
        java.awt.FontMetrics metrics = g.getFontMetrics();
        int textWidth = metrics.stringWidth(scoreText);
        int textHeight = metrics.getHeight();
        int textYPosition = (int) y + (int) height + textHeight;

        g.drawString(scoreText, (int) x + (int) width / 2 - textWidth / 2, textYPosition);
    }

    // Get the score    
    @Override
    public int getScore() {
        double normalizedY = (double) (MAX_Y - y) / MAX_Y; // Normalize the Y value
        int score = 60 - (int) (normalizedY * 50); // Scale and adjust the score within the range of 10 to 60
        return Math.abs(score); // Return the absolute value of the score
    }
}