package model;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

public class LandObstacle extends Obstacle {
    private static final String IMAGE_PATH = "/assets/images/land2.png";
    private Image obstacleImage;

    // Constructor
    public LandObstacle(double x, double y) {
        super(x, y, 600, 400, "LandObstacle");
        loadImage();
    }

    private void loadImage() {
        this.obstacleImage = new ImageIcon(getClass().getResource(IMAGE_PATH)).getImage();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(obstacleImage, (int) x, (int) y, (int) width, (int) height, null);
    }

    @Override
    public int getScore() {
        return 0;
    }
}