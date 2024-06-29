package viewmodel;

import java.awt.Graphics;
import java.util.ArrayList;

import model.GameInterface;
import model.GameObject;

public class Handler implements GameInterface {
    private ArrayList<GameObject> object; // List of objects
    private ObstacleManager obstacleManager; // ObstacleManager object
    private PlayerManager playerManager; // PlayerManager object

    // Flag to check if initial obstacles have been added
    private boolean hasInitialObstaclesBeenAdded = false;

    // constructor
    public Handler(Game game) {
        this.object = new ArrayList<>();
        this.obstacleManager = game.getObstacleManager();
        this.playerManager = game.getPlayerManager();
    }

    /**
     * Object access and manipulations.
     */

    // Add object to list.
    public void add(GameObject object) {
        this.object.add(object);
    }

    // Access object from list.
    public GameObject get(int i) {
        return this.object.get(i);
    }

    // Count total object on list.
    public int count() {
        return this.object.size();
    }

    // Remove object from list based on its index.
    public void remove(int i) {
        this.object.remove(i);
    }

    // Remove object from list.
    public void remove(GameObject object) {
        this.object.remove(object);
    }

    /**
     * 
     * Override interface.
     */

    @Override
    public void render(Graphics g) {
        for (int i = 0; i < object.size(); i++) {
            GameObject temp;
            temp = object.get(i);

            temp.render(g);
        }
    }

    @Override
    public void loop() {
        if (!hasInitialObstaclesBeenAdded) {
            // Add initial obstacles (land obstacle)
            obstacleManager.addInitialObstacles();
            hasInitialObstaclesBeenAdded = true; // Ensure this runs only once
        }

        obstacleManager.addObstacles();
        obstacleManager.removeObstacles();

        playerManager.movePlayer();
        playerManager.CheckGameState();

        for (int i = 0; i < object.size(); i++) {
            GameObject temp;
            temp = object.get(i);

            temp.loop();
        }
    }
}
