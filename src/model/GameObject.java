package model;

import java.awt.Graphics;
import java.awt.Rectangle;

public class GameObject implements GameInterface {
    protected double x, y; // Position
    protected double width, height; // Size
    protected double velX, velY; // Velocity
    protected String type; // Object type
    protected Rectangle boxCollider; // Collision box

    // Constructor
    public GameObject(double x, double y, double width, double height, String type) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.type = type;
        this.boxCollider = new Rectangle((int) x, (int) y, (int) width, (int) height);
    }

    // Getters
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getVelX() {
        return velX;
    }

    public double getVelY() {
        return velY;
    }

    public String getType() {
        return type;
    }

    public Rectangle getBoxCollider() {
        return boxCollider;
    }

    // Setters
    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setVelX(double velX) {
        this.velX = velX;
    }

    public void setVelY(double velY) {
        this.velY = velY;
    }

    public void setType(String type) {
        this.type = type;
    }

    // Update collision box position
    protected void updateBoxCollider() {
        boxCollider.setBounds((int) x, (int) y, (int) width, (int) height);
    }

    @Override
    public void render(Graphics g) {
    }

    @Override
    public void loop() {
    }
}