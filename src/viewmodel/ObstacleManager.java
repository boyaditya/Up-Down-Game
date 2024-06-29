package viewmodel;

import java.util.ArrayList;
import java.util.Random;
import model.BottomObstacle;
import model.LandObstacle;
import model.Obstacle;
import model.UpperObstacle;

public class ObstacleManager {
    private Game game; // Game object
    private Random rand = new Random(); // Random object
    private ArrayList<Obstacle> obstacles = new ArrayList<>(); // List of obstacles
    private static final int MAX_OBSTACLES = 4; // Maximum number of obstacles
    private int currentObstacleCount = 0; // Current number of obstacles
    private static final int START_POS_X = Game.WIDTH; // Starting position X
    private static final int START_POS_Y = 0; // Starting position Y
    private long lastObstacleAdditionTime = 0; // Last obstacle addition time
    private static final long ADDITION_DELAY = 3000; // 3 seconds

    // Constructor
    public ObstacleManager(Game game) {
        this.game = game;
    }

    // Getter for obstacles
    public ArrayList<Obstacle> getObstacles() {
        return obstacles;
    }

    // Method to add initial obstacles (land obstacle)
    public void addInitialObstacles() {
        Obstacle initialObstacle = new LandObstacle(0, game.getHeight() / 2);
        addObstacle(initialObstacle);
        currentObstacleCount++;
    }

    // Method to add obstacles (upper and bottom obstacles)
    public void addObstacles() {
        long currentTime = System.currentTimeMillis(); // Get current time

        // Add obstacles if the current obstacle count is less than the maximum number
        // of obstacles
        if (currentObstacleCount < MAX_OBSTACLES && (currentTime - lastObstacleAdditionTime) > ADDITION_DELAY) {
            int gapSizeRope = getRandomGapSize(); // Get random gap size for rope
            int gapSizeRock = getRandomGapSize(); // Get random gap size for rock
            int posY = getRandomObstaclePositionY(); // Get random obstacle position Y

            // Create upper and bottom obstacles
            Obstacle topObstacle = new UpperObstacle(START_POS_X + calculateRandomOffset(gapSizeRope, 4, 128), posY);
            Obstacle bottomObstacle = new BottomObstacle(START_POS_X + calculateRandomOffset(gapSizeRock, 1, 1),
                    posY + Game.HEIGHT);

            // Set the obstacles as not scored
            topObstacle.setScored(false);
            bottomObstacle.setScored(false);

            // Add the obstacles to the list
            addObstacle(topObstacle);
            addObstacle(bottomObstacle);

            // Increment the current obstacle count and update the last obstacle addition
            // time
            currentObstacleCount += 2;
            lastObstacleAdditionTime = currentTime;
        }
    }

    // Method to remove obstacles that are out of the screen
    public void removeObstacles() {
        obstacles.removeIf(obstacle -> obstacle.getX() + obstacle.getWidth() < 0);
        currentObstacleCount = obstacles.size();
    }

    // Method to get random gap size
    private int getRandomGapSize() {
        int minGap = Game.HEIGHT / 6;
        int maxGap = Game.HEIGHT / 3;
        return minGap + rand.nextInt(maxGap - minGap + 1);
    }

    // Method to get random obstacle position Y
    private int getRandomObstaclePositionY() {
        return (int) (START_POS_Y - 512 / 4 - Math.random() * (512 / 2));
    }

    // Method to calculate random offset for obstacles
    private int calculateRandomOffset(int base, int multiplier, int addition) {
        return base * rand.nextInt(multiplier) + addition;
    }

    // Method to add obstacle to the list
    private void addObstacle(Obstacle obstacle) {
        obstacles.add(obstacle);
        game.getHandler().add(obstacle);
    }
}
