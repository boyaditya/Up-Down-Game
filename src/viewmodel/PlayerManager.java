package viewmodel;

import model.Obstacle;
import model.Player;

public class PlayerManager {
    private Game game; // Game object
    private Player player; // Player object
    private ObstacleManager obstacleManager; // ObstacleManager object

    // Movement flags
    private boolean pressedUp = false, pressedLeft = false, pressedRight = false, pressedDown = false;
    // Jump flags
    private boolean onGround = false, isHanging = false, jumpReleased = false;
    // Gravity and jump force
    private final double gravity = 0.5;
    private final double jumpForce = -10;

    // Constructor
    public PlayerManager(Game game) {
        this.game = game;
        this.player = game.getPlayer();
        this.obstacleManager = game.getObstacleManager();
    }

    // Setters
    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setPressedUp(Boolean pressedUp) {
        this.pressedUp = pressedUp;
    }

    public void setPressedLeft(Boolean pressedLeft) {
        this.pressedLeft = pressedLeft;
    }

    public void setPressedRight(Boolean pressedRight) {
        this.pressedRight = pressedRight;
    }

    public void setPressedDown(Boolean pressedDown) {
        this.pressedDown = pressedDown;
    }

    // Smooth movement
    public double interpolate(double start, double finish, double fraction) {
        return start + fraction * (finish - start);
    }

    // Apply gravity to the player
    private void applyGravity() {
        player.setVelY(player.getVelY() + gravity);
    }

    // Calculate horizontal speed based on user input
    private double calculateHorizontalSpeed() {
        if ((pressedLeft && pressedRight) || (!pressedLeft && !pressedRight)) {
            return interpolate(player.getVelX(), 0, 0.1);
        } else if (pressedLeft) {
            player.setDirectionRight(false);
            return interpolate(player.getVelX(), -player.getHorizontalSpeed(), 0.1);
        } else {
            player.setDirectionRight(true);
            return interpolate(player.getVelX(), player.getHorizontalSpeed(), 0.1);
        }
    }

    // Handle jump logic
    private void handleJumpLogic() {
        if (pressedUp && !jumpReleased && canJump()) {
            performJump();
        }
        if (!pressedUp) {
            jumpReleased = false;
        }
    }

    // Check if the player can jump
    private boolean canJump() {
        return player.isOnGround() || player.getJumpCount() < 2;
    }

    // Perform jump
    private void performJump() {
        if (!isHanging) {
            Sound.playSound("jump.wav", false);
        }
        player.setVelY(jumpForce);
        player.incrementJumpCount();
        jumpReleased = true;
        player.setOnGround(player.getJumpCount() == 1 && !player.isOnGround() ? false : player.isOnGround());
    }

    // Move the player and handle collisions
    public void movePlayer() {
        // Apply gravity to the player
        applyGravity();

        // Calculate horizontal speed based on user input
        double xSpeed = calculateHorizontalSpeed();
        player.setVelX(xSpeed); // Set the horizontal speed

        // Apply additional velocity to the player
        player.setAdditionalVelocity(interpolate(player.getAdditionalVelocity(), 0, 0.1));

        // handle double jump mechanism
        handleJumpLogic();

        boolean insideUpperObstacle = false; // Flag to check if the player is inside an upperObstacle
        for (Obstacle obstacle : obstacleManager.getObstacles()) {
            // condition for upperObstacle (hanging in the rope)
            if (obstacle.getType().equals("UpperObstacle")) {
                // Check if the player is entering the upperObstacle
                if (player.getBoundLeft().intersects(obstacle.getBoxCollider())
                        || player.getBoundRight().intersects(obstacle.getBoxCollider())
                        || player.getBoundTop().intersects(obstacle.getBoxCollider())) {
                    insideUpperObstacle = true; // Player is inside the upperObstacle
                    onGround = true; // Player is on the ground
                    isHanging = true; // Player is hanging on the upperObstacle

                    if (!obstacle.isScored()) { // Update score only once per collision
                        game.setScore(game.getScore() + obstacle.getScore());
                        game.setUp(game.getUp() + 1);
                        obstacle.setScored(true); // Mark the obstacle as scored
                    }
                }

                // reset jump count if player land on obstacle
                if (!insideUpperObstacle && player.getBoundBottom().intersects(obstacle.getBoxCollider())) {
                    player.resetJumpCount();
                }

                // Handle collisions when player is inside an upperObstacle
                if (insideUpperObstacle && player.getBoundBottom().intersects(obstacle.getBoxCollider())
                        && player.getY() < obstacle.getBoxCollider().y + obstacle.getBoxCollider().height - 1) {

                    if (player.getVelY() > 0) {
                        player.setVelY(0); // Stop vertical movement immediately
                    }

                    // climb up the upperObstacle
                    if (pressedUp) {
                        player.setVelY(0);
                        player.setY(player.getY() - 2);
                    } else

                    // climb down the upperObstacle
                    if (pressedDown) {
                        player.setVelY(0);
                        player.setY(player.getY() + 2);
                    }

                    // Smoothly adjust horizontal position to center the player inside the
                    // upperObstacle
                    player.setX(player.getX() + (obstacle.getX() - player.getX()) * 0.1);
                }
            }

            // reset jump count if player land on obstacle
            if (player.getBoundBottom().intersects(obstacle.getBoxCollider())) {
                onGround = true;
                jumpReleased = false;
                player.resetJumpCount();
            } else {
                onGround = false;
            }

            // Handle collisions when player is not inside an upperObstacle
            if (!insideUpperObstacle) {

                if (player.getBoundBottom().intersects(obstacle.getBoxCollider())) {
                    // the bottom of the player collides with the obstacle
                    player.setVelY(0); // Stop vertical movement immediately
                    // Place the player on top of the obstacle
                    player.setY(obstacle.getBoxCollider().y - player.getHeight());

                    // player will get additional velocity to left from the obstacle, except for
                    // land obstacle
                    if (!obstacle.getType().equals("LandObstacle")) {
                        player.setAdditionalVelocity(obstacle.getVelX() - 0.4);
                    }

                    // Update score only once per collision
                    if (!obstacle.isScored() && !obstacle.getType().equals("LandObstacle")) {
                        game.setScore(game.getScore() + obstacle.getScore());
                        game.setDown(game.getDown() + 1);
                        obstacle.setScored(true);
                    }

                } else if (player.getBoundTop().intersects(obstacle.getBoxCollider())) {
                    // the top of the player collides with the obstacle
                    player.setVelY(0);
                    player.setX(player.getX() - 4); // Move the player to the left
                } else if (player.getBoundLeft().intersects(obstacle.getBoxCollider())) {
                    // the left side of the player collides with the obstacle
                    player.setVelX(0);
                    player.setX(obstacle.getBoxCollider().x + obstacle.getBoxCollider().width + 1);
                } else if (player.getBoundRight().intersects(obstacle.getBoxCollider())) {
                    // the right side of the player collides with the obstacle
                    player.setVelX(0);
                    player.setX(obstacle.getBoxCollider().x - player.getWidth() - 1);
                } else if (player.getBoundBottom().intersects(obstacle.getBoxCollider())
                        && obstacle.getType().equals("LandObstacle")) {
                    // the bottom of the player collides with the land obstacle
                    player.setVelX(0);
                    // no additional velocity for land obstacle. player will move easily
                }

                // player is not on inside the upperObstacle
                isHanging = false;
            }

        }

        // Update player position
        player.setOnGround(onGround);

        if (!player.isOnGround() && player.getVelY() > 0) {
            // This condition ensures that if the player is falling (velY > 0) and not on
            // the ground, they are in the air
            player.setOnGround(false);
        }

    }

    public void CheckGameState() {
        // Stop the game if the player goes out of the screen
        double offset = player.getHeight();
        if (player.getBoundBottom().y > Game.HEIGHT - offset) {
            game.stopGame();
        }
    }

}
