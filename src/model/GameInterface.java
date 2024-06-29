package model;

import java.awt.Graphics;


// Interface for game objects
public interface GameInterface
{   
    public void render(Graphics object); // Render object
    public void loop(); // Update object
}
