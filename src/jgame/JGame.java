package jgame;

import jgame.entity.mob.Link;
import jgame.entity.mob.ai.Player;
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
    private static final int TARGET_FRAMERATE = 60;
    
    private Level level;
    
    public JGame() throws SlickException
    {
        super("JGame");
    }
    
    public static void main(String[] arguments)
    {
        try
        {
            JGame game = new JGame();
            AppGameContainer app = new AppGameContainer(game);
            
            app.setDisplayMode(800, 600, false);
            app.setTargetFrameRate(TARGET_FRAMERATE);
            app.setMaximumLogicUpdateInterval(1000 / TARGET_FRAMERATE);
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
        level = new DebugLevel("demo");
        level.load();
        
        Link link = new Link(level);
        Player player = new Player(link, container.getInput());
        link.setAI(player);
        
        level.setPlayer(player);
        level.setAtLocation(link, "entrance");
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException
    {
        level.update(delta);
    }
    
    @Override
    public void render(GameContainer container, Graphics g) throws SlickException
    {
        level.render(g);
    }
}
