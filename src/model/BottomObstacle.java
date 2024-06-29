package model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

public class BottomObstacle extends Obstacle {
    private static final String IMAGE_PATH = "/assets/images/rock.png"; // Image path
    private static final int SCREEN_HEIGHT = 720; // Screen height game
    private static final int MIN_Y = SCREEN_HEIGHT / 2; // Minimum Y value, center of the screen
    private Image obstacleImage; // Image object

    // Constructor
    public BottomObstacle(double x, double y) {
        super(x, y, 128, 512, "BottomObstacle");
        loadImage();
    }

    private void loadImage() {
        this.obstacleImage = new ImageIcon(getClass().getResource(IMAGE_PATH)).getImage();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(obstacleImage, (int) x, (int) y, (int) width, (int) height, null);
        drawScore(g);
    }

    // Draw the score
    private void drawScore(Graphics g) {
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.setColor(Color.WHITE);

        String scoreText = String.valueOf(getScore());
        java.awt.FontMetrics metrics = g.getFontMetrics();
        int textWidth = metrics.stringWidth(scoreText);
        int textHeight = metrics.getHeight();
        int textYPosition = (int) y + textHeight - 5;

        g.drawString(scoreText, (int) x + (int) width / 2 - textWidth / 2, textYPosition);
    }

    @Override
    public int getScore() {
        double normalizedY = (y - MIN_Y) / (double) (SCREEN_HEIGHT - MIN_Y); // Normalize the Y value
        int score = 10 + (int) (normalizedY * 50); // Scale and adjust the score within the range of 10 to 60
        return score;
    }
}