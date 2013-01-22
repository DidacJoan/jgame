package jgame;

import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.tiled.TiledMap;


/**
 * JGame main class.
 * @author hector
 */
public class JGame extends BasicGame
{
    private static final int DOWN = 0;
    private static final int RIGHT = 1;
    private static final int UP = 2;
    private static final int LEFT = 3;
    private static final int framerate = 60;
    
    private TiledMap map;
    private Animation sprite, stand, up, down, left, right;
    private float x = 16f*14f, y = 16f*18.5f;
    private int dir = UP;
    
    public JGame()
    {
        super("JGame");
    }
    
    public static void main(String[] arguments)
    {
        try
        {
            AppGameContainer app = new AppGameContainer(new JGame());
            app.setDisplayMode(800, 600, false);
            app.setTargetFrameRate(framerate);
            app.setVSync(true);
            app.setMinimumLogicUpdateInterval(1000 / framerate);
            app.start();
        }
        catch (SlickException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void init(GameContainer container) throws SlickException
    {
        map = new TiledMap("res/levels/demo.tmx");
        
        SpriteSheet s = new SpriteSheet("res/charsets/link_move.png", 18, 26);
        
        Image[][] dirs = new Image[4][];
        
        for(int i = 0; i < 4; ++i)
        {
            dirs[i] = loadDir(s, i);
        }
        
        down = new Animation(dirs[0], 100, false);
        right = new Animation(dirs[1], 100, false);
        up = new Animation(dirs[2], 100, false);
        left = new Animation(dirs[3], 100, false);
        stand = new Animation(loadStand(s), 100, false);
        
        dir = UP;
        sprite = stand;
    }
    
    private Image[] loadDir(SpriteSheet s, int dir)
    {
        Image[] imgs = new Image[8];
        
        for(int i = 0; i < 8; ++i)
        {
            imgs[i] = s.getSubImage(i+2, dir);
        }
        
        return imgs;
    }
    
    private Image[] loadStand(SpriteSheet s)
    {
        Image[] imgs = new Image[4];
        
        for(int i = 0; i < 4; ++i)
        {
            imgs[i] = s.getSubImage(0, i);
        }
        
        return imgs;
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException
    {
        Input input = container.getInput();
        if (input.isKeyDown(Input.KEY_UP))
        {
            sprite = up;
            sprite.update(delta);
            // The lower the delta the slowest the sprite will animate.
            y -= delta * 0.1f;
            dir = UP;
        }
        else if (input.isKeyDown(Input.KEY_DOWN))
        {
            sprite = down;
            sprite.update(delta);
            y += delta * 0.1f;
            dir = DOWN;
        }
        
        else if (input.isKeyDown(Input.KEY_LEFT))
        {
            sprite = left;
            sprite.update(delta);
            x -= delta * 0.1f;
            dir = LEFT;
        }
        else if (input.isKeyDown(Input.KEY_RIGHT))
        {
            sprite = right;
            sprite.update(delta);
            x += delta * 0.1f;
            dir = RIGHT;
        }
        else
        {
            sprite = stand;
            sprite.setCurrentFrame(dir);
        }
    }
    
    @Override
    public void render(GameContainer container, Graphics g) throws SlickException
    {
        map.render(0, 0);
        sprite.draw(x, y);
    }
}
