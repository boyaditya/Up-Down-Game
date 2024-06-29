package viewmodel;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.awt.Toolkit;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

import model.Player;
import view.MainMenu;
import view.Display;

public class Game extends Canvas implements Runnable {
    public enum GameState {
        RUNNING, // Game is running
        GAME_OVER // Game is over
    }

    // Game attributes.
    public static final int WIDTH = 1080;
    public static final int HEIGHT = 720;

    private Display display; // Display object
    private Handler handler; // Handler object
    private Player player; // Player object
    private ObstacleManager obstacleManager; // ObstacleManager object
    private PlayerManager playerManager; // PlayerManager object
    private Thread gameThread; // Game thread
    private Clip gameMusic; // Game music

    private boolean isRunning; // flag to check if the game is running
    private boolean isCounting = false;
    private int stateCounter = 0;
    private int direction = 0;
    private int counter = 0;

    private String username; // Username of the player
    private int score = 0; // Score of the player
    private int up = 0; // Up counter
    private int down = 0; // Down counter

    private GameState currentGameState = GameState.RUNNING; // Set the initial game state to RUNNING

    // constructor
    public Game() {
        initializeGame();
    }

    // Initialize game attributes.
    private void initializeGame() {
        try {
            player = new Player(20, 0, 64, 64);
            obstacleManager = new ObstacleManager(this);
            playerManager = new PlayerManager(this);
            handler = new Handler(this);
            display = new Display(WIDTH, HEIGHT, "Up Down");
            display.open(this);

            setFocusable(true);
            requestFocus();
            addKeyListener(new InputManager(this, handler));

            isRunning = true;
            handler.add(player);
            gameMusic = Sound.playSound("backsound.wav", true);
        } catch (Exception e) {
            System.err.println("Failed to instantiate data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Getters and Setters

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        this.isRunning = running;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getUp() {
        return up;
    }

    public void setUp(int up) {
        this.up = up;
    }

    public int getDown() {
        return down;
    }

    public void setDown(int down) {
        this.down = down;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Handler getHandler() {
        return handler;
    }

    public Player getPlayer() {
        return player;
    }

    public ObstacleManager getObstacleManager() {
        return obstacleManager;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    // Clamp player position, so it won't go out of the screen.
    public static double clamp(double var, double min, double max) {
        if (var >= max) {
            return var = max;
        } else if (var <= min) {
            return var = min;
        }

        return var;
    }

    // Close display.
    public void close() {
        display.close();
    }

    // Start threading.
    public synchronized void start() {
        gameThread = new Thread(this);
        gameThread.start();
        isRunning = true;
    }

    // Stop threading.
    public synchronized void stop() {
        try {
            gameThread.join();
            isRunning = false;
        } catch (InterruptedException e) {
            System.out.println("Thread error : " + e.getMessage());
        }
    }

    public void stopGame() {
        Sound.stopSound(gameMusic);
        Sound.playSound("splash.wav", false);
        this.currentGameState = GameState.GAME_OVER;
    }

    // Save score to database.
    public void saveScore() {
        try {
            TscoreProcess Tscore = new TscoreProcess();
            Tscore.saveData(this.username, this.score, this.up, this.down);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        JOptionPane.showMessageDialog(null,
                "Username : " + this.username + "\nScore : " + this.score + "\nUp : " + this.up + "\nDown: : "
                        + this.down,
                "GAME OVER",
                JOptionPane.INFORMATION_MESSAGE);
    }

    // Initialize game when it run for the first time.
    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(4);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        Image background = Toolkit.getDefaultToolkit()
                .getImage(getClass().getResource("/assets/images/background.jpg"));
        g.drawImage(background, 0, 0, null);

        if (isRunning) {
            handler.render(g);
            // Draw score.
            Font font = g.getFont().deriveFont(Font.BOLD, 24f);
            g.setFont(font);
            g.setColor(Color.WHITE);
            g.drawString("Score: " + score, 10, 20);
            g.drawString("Up: " + up, 10, 50);
            g.drawString("Down: " + down, 10, 80);
        }

        g.dispose();
        bs.show();
    }

    // Main loop proccess.
    public void loop() {
        this.handler.loop();
        if (this.isRunning) {
            counter++;
            if (isCounting) {
                stateCounter++;
            }

            if (stateCounter >= 40) {
                stateCounter = 0;
                isCounting = false;
            }

            if (counter >= 50) {
                direction = (direction == 0) ? 1 : 0;
                counter = 0;
            }
        }
        if (currentGameState == GameState.GAME_OVER) {
            saveScore();
            Sound.stopSound(gameMusic);
            close();
            new MainMenu().setVisible(true);
            stop();
        }
    }

    /**
     * Override interface.
     */

    @Override
    public void run() {
        double fps = 60.0;
        double ns = (1000000000 / fps);
        double delta = 0;

        // Timer attributes.
        long time = System.nanoTime();
        long now = 0;
        long timer = System.currentTimeMillis();

        while (isRunning) {
            now = System.nanoTime();
            delta += (now - time) / ns;
            time = now;

            while (delta > 1) {
                loop();
                delta--;
            }

            if (isRunning) {
                render();
            }

            if ((System.currentTimeMillis() - timer) > 1000) {
                timer += 1000;
            }
        }

        stop();
    }
}
