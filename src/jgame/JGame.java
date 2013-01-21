package jgame;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;


/**
 * JGame main class.
 * @author hector
 */
public class JGame extends BasicGame
{
    private TiledMap map;
    
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
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException
    {
        
    }
    
    @Override
    public void render(GameContainer container, Graphics g) throws SlickException
    {
        map.render(0, 0);
    }
}
