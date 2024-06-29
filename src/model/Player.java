package model;

import viewmodel.Game;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class Player extends GameObject {
    private static final int BOUND_OFFSET = 5;
    private static final int BOUND_THICKNESS = 5;

    // Image assets facing left and right
    private static final Image PLAYER_IMAGE = Toolkit.getDefaultToolkit()
            .getImage(Player.class.getResource("/assets/images/squirrel.png"));
    private static final Image PLAYER_IMAGE_RIGHT = Toolkit.getDefaultToolkit()
            .getImage(Player.class.getResource("/assets/images/squirrel_right.png"));

    private double additionalVelocity = 0; // Additional velocity of the player
    private double horizontalSpeed = 5; // Horizontal speed of the player
    private double jumpForce = -1; // Negative for upward movement
    private boolean directionRight = true; // Direction right by default
    private int jumpCount = 0; // Number of jumps made by the player in one event
    private boolean isOnGround = false; // Flag to check if player is on the ground

    // Constructor
    public Player(double x, double y, double width, double height) {
        super(x, y, width, height, "Player");
    }

    // Getters
    public double getHorizontalSpeed() {
        return horizontalSpeed;
    }

    public double getJumpForce() {
        return jumpForce;
    }

    public double getAdditionalVelocity() {
        return additionalVelocity;
    }

    public boolean isDirectionRight() {
        return directionRight;
    }

    public int getJumpCount() {
        return jumpCount;
    }

    public boolean isOnGround() {
        return isOnGround;
    }

    // Setters
    public void setAdditionalVelocity(double additionalVelocity) {
        this.additionalVelocity = additionalVelocity;
    }

    public void setDirectionRight(boolean directionRight) {
        this.directionRight = directionRight;
    }

    public void setOnGround(boolean onGround) {
        isOnGround = onGround;
    }

    // Jump logic
    public void jump() {
        this.velY = jumpForce;
    }

    // Increment jump count for double jump logic
    public void incrementJumpCount() {
        jumpCount++;
    }

    // Reset jump count for double jump logic
    public void resetJumpCount() {
        jumpCount = 0;
    }

    // Bounds
    private Rectangle createBound(int xOffset, int yOffset, int width, int height) {
        return new Rectangle((int) x + xOffset, (int) y + yOffset, width, height);
    }

    public Rectangle getBoundBottom() {
        return createBound((int) width / 4, (int) height / 2, (int) width / 2, (int) height / 2);
    }

    public Rectangle getBoundTop() {
        return createBound((int) width / 4, 0, (int) width / 2, (int) height / 2);
    }

    public Rectangle getBoundRight() {
        return createBound((int) (width - BOUND_THICKNESS), BOUND_OFFSET, BOUND_THICKNESS,
                (int) height - 2 * BOUND_OFFSET);
    }

    public Rectangle getBoundLeft() {
        return createBound(0, BOUND_OFFSET, BOUND_THICKNESS, (int) (height - 2 * BOUND_OFFSET));
    }

    @Override
    public void render(Graphics object) {
        Image toDraw = directionRight ? PLAYER_IMAGE_RIGHT : PLAYER_IMAGE;
        object.drawImage(toDraw, (int) x, (int) y, (int) width, (int) height, null);
    }

    // Game loop logic
    @Override
    public void loop() {
        this.x += (this.velX + this.additionalVelocity);
        this.y += this.velY;

        x = Game.clamp(x, 0, Game.WIDTH - width);
        y = Game.clamp(y, 0, Game.HEIGHT - height);

        updateBoxCollider();
    }
}