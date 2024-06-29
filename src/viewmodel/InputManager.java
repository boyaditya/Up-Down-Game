package viewmodel;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InputManager extends KeyAdapter {
    private Game game; // Game object
    private Handler handler; // Handler object
    private PlayerManager playerManager; // PlayerManager object

    // Constructor
    public InputManager(Game game, Handler handler) {
        this.game = game;
        this.handler = handler;
        this.playerManager = game.getPlayerManager();
    }

    // Getters and Setters
    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    // Key pressed event
    @Override
    public synchronized void keyPressed(KeyEvent e) {
        if (game.isRunning()) {
            updateMovementKeys(e.getKeyCode(), true);
        }
    }

    // Key released event
    @Override
    public synchronized void keyReleased(KeyEvent e) {
        if (game.isRunning()) {
            // If the game is running and the space key is pressed, stop the game
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                game.stopGame();
                return;
            }
            updateMovementKeys(e.getKeyCode(), false);
        }
    }

    // Update movement keys
    private void updateMovementKeys(int keyCode, boolean isPressed) {
        switch (keyCode) {
            // Move player up
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                playerManager.setPressedUp(isPressed);
                break;
            // Move player left
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                playerManager.setPressedLeft(isPressed);
                break;
            // Move player right
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                playerManager.setPressedRight(isPressed);
                break;
            // Move player down
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                playerManager.setPressedDown(isPressed);
                break;
        }
    }
}