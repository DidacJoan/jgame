package jgame.entity;

import jgame.level.Level;
import jgame.math.Vec2;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Represents the player in the game.
 * @author hector
 */
public class Player
{
    private String name;
    private Vec2 pos;
    private Animation sprites[];
    private Dir facing;
    private PlayerStatus status;
    
    public Player(String pname) throws SlickException
    {
        name = pname;
        pos = new Vec2(0, 0);
        
        loadSprites();
    }
    
    private void loadSprites() throws SlickException
    {
        SpriteSheet ss = new SpriteSheet("res/charsets/" + name + ".png", 21, 27);
        
        int h = ss.getHorizontalCount();
        int v = ss.getVerticalCount();
        
        sprites = new Animation[v];
        
        for(Dir dir : Dir.values())
        {
            int dirValue = dir.getValue();
            sprites[dirValue] = new Animation(ss, 0, dirValue, h-1, dirValue, true, 50, false);
        }
        
        // UP and DOWN animations are ping pong animations!
        // This means that the animation goes back and forth.
        // If each number represents an sprite (3 standing sprite):
        // 3 4 5 6 5 4 3 2 1 0 1 2 3 ...
        sprites[Dir.UP.getValue()].setPingPong(true);
        sprites[Dir.DOWN.getValue()].setPingPong(true);
    }
    
    public void setPos(double x, double y)
    {
        pos.x = x;
        pos.y = y;
    }
    
    public void setFacing(Dir facing)
    {
        this.facing = facing;
    }
    
    public void update(Level level, Input input, int delta)
    {
        boolean updateSprite = true;
        
        if (input.isKeyDown(Input.KEY_UP))
        {
            facing = Dir.UP;
            pos.y -= delta * 0.1f;
        }
        else if (input.isKeyDown(Input.KEY_DOWN))
        {
            facing = Dir.DOWN;
            pos.y += delta * 0.1f;
        }
        
        else if (input.isKeyDown(Input.KEY_LEFT))
        {
            facing = Dir.LEFT;
            pos.x -= delta * 0.1f;
        }
        else if (input.isKeyDown(Input.KEY_RIGHT))
        {
            facing = Dir.RIGHT;
            pos.x += delta * 0.1f;
        }
        else
        {
            updateSprite = false;
        }
        
        if(updateSprite)
        {
            sprites[facing.getValue()].update(delta);
        }
        else
        {
            int frameNum = sprites[facing.getValue()].getFrameCount() - 1;
            int standFrame =  frameNum / 2;
            
            if(frameNum % 2 != 0)
            {
                standFrame += 1;
            }
            
            sprites[facing.getValue()].setCurrentFrame(standFrame);
        }
    }
    
    public void render()
    {
        sprites[facing.getValue()].draw((float)pos.x, (float)pos.y);
    }
}
