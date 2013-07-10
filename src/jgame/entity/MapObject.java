package jgame.entity;

import org.newdawn.slick.Image;

/**
 *
 * @author hector
 */
public class MapObject extends Entity {
    private int width;
    private int height;
    private Image image;
    
    public MapObject(String name, int width, int height, Image image)
    {
        super(name, width, height);
        this.image = image;
    }
    
    @Override
    public void render()
    {
        image.draw((float)pos.x, (float)pos.y);
    }
}
