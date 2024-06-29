package model;

import java.awt.Graphics;
import java.awt.Color;

public class Obstacle extends GameObject {
    private int score = 0; // Score value for obstacle
    private boolean scored = false; // Flag to check if obstacle has been scored

    public Obstacle(double x, double y, double width, double height, String type) {
        super(x, y, width, height, type);
        this.velX = -2; // Set horizontal speed for leftward movement
        this.velY = 0; // Obstacles don't move vertically
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect((int) this.x, (int) this.y, (int) this.width, (int) this.height);
    }

    @Override
    public void loop() {
        this.x += this.velX; // Move obstacle left
        updateBoxCollider(); // Update collision box position
    }

    public boolean isScored() {
        return this.scored;
    }

    public void setScored(boolean scored) {
        this.scored = scored;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    // Smooth transition between two points
    public static double interpolate(double start, double finish, double fraction) {
        return start + fraction * (finish - start);
    }
}