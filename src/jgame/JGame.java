package jgame;

import jgame.entity.Player;
import jgame.level.Level;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;


/**
 * JGame main class.
 * @author hector
 */
public class JGame extends BasicGame
{
    private static final int framerate = 60;
    
    private Level level;
    
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
        level = new Level("demo");
        Player player = new Player("link");
        
        level.setPlayer(player, "entrance");
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException
    {
        Input input = container.getInput();
        
        level.update(input, delta);
    }
    
    @Override
    public void render(GameContainer container, Graphics g) throws SlickException
    {
        level.render(g);
    }
}
