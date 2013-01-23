package jgame.level;

import jgame.entity.Dir;
import jgame.entity.Player;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;


/**
 * Represents a level.
 * @author hector
 */
public class Level
{
    private String name;
    private TiledMap map;
    private Player player;
    
    public Level(String lname) throws SlickException
    {
        name = lname;
        map = new TiledMap("res/levels/" + name + ".tmx");
        player = null;
    }
    
    public void setPlayer(Player p, String location)
    {
        player = p;
        
        double playerX = Double.parseDouble(map.getMapProperty(location + "X", "0"));
        double playerY = Double.parseDouble(map.getMapProperty(location + "Y", "0"));
                
        player.setPos(playerX * map.getTileWidth(), (playerY-0.5) * map.getTileHeight());
        
        String playerFacing = map.getMapProperty(location + "F", "UP");
        Dir facing = Dir.valueOf(playerFacing);
        
        player.setFacing(facing);
    }
    
    public void update(Input input, int delta)
    {
        if(player != null)
        {
            player.update(this, input, delta);
        }
    }
    
    public void render(int x, int y)
    {
        map.render(x, y);
        
        if(player != null)
        {
            player.render();
        }
    }   
}
