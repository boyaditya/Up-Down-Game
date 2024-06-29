package model;

public class Tscore {
    // Attribute
    private String username;
    private int score;
    private int up;
    private int down;

    // Constructor
    public Tscore() {
        this.username = "";
        this.score = 0;
        this.up = 0;
        this.down = 0;
    }

    // Setter and Getter
    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return this.score;
    }

    public void setUp(int up) {
        this.up = up;
    }

    public int getUp() {
        return this.up;
    }

    public void setDown(int down) {
        this.down = down;
    }

    public int getDown() {
        return this.down;
    }
}