package view;

import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;

import viewmodel.Game;

public class Display extends Canvas
{
    private JFrame frame; // JFrame object

    // Constructor
    public Display(int width, int height, String title)
    {
        this.frame = new JFrame(title);
        this.frame.setPreferredSize(new Dimension(width, height));
        this.frame.setMinimumSize(new Dimension(width, height));
        this.frame.setMaximumSize(new Dimension(width, height));

        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLocationRelativeTo(null);

        this.frame.setResizable(false);
    }

    // Add the game to the frame and make it visible, then start the game
    public void open(Game game)
    {
        this.frame.add(game);
        this.frame.setVisible(true);

        game.start();
    }

    // Close the frame
    public void close()
    {
        this.frame.setVisible(false);
        this.frame.dispose();
    }
}
