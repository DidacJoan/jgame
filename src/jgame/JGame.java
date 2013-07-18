package jgame;

import jgame.entity.mob.Link;
import jgame.entity.mob.ia.Player;
import jgame.level.Level;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;


/**
 * JGame main class.
 * @author hector
 */
public class JGame extends BasicGame
{
    private static final int framerate = 60;
    
    private Level level;
    private boolean updated = false;
    
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
            //app.setVSync(true); // This seems to cause some render delays?
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
        Debug.enable();
        
        level = new Level("demo");
        
        Link link = new Link(level);
        Player player = new Player(link, container.getInput());
        link.setIA(player);
        
        level.setPlayer(link, "entrance");
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException
    {
        level.update(delta);
        
        updated = true;
    }
    
    @Override
    public void render(GameContainer container, Graphics g) throws SlickException
    {
        level.render(g);
        Debug.render(g);
        
        updated = false;
    }
}
