package jgame.level;

import jgame.entity.Player;
import jgame.math.Vec2;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;


/**
 * Represents a level.
 * @author hector
 */
public class Level
{
    private Map map;
    private Player player;
    
    
    public Level(String name) throws SlickException
    {
        map = new Map(name);
        player = null;
    }
    
    public void setPlayer(Player p, String locationName)
    {
        player = p;
        
        Location location = map.getLocation(locationName);
        
        player.setPos(location.getPos());
        player.setFacing(location.getFacing());
    }
    
    public boolean isAreaBlocked(Vec2 topLeft, Vec2 bottomRight)
    {
        return map.areTilesBlocked(topLeft, bottomRight);
    }
    
    public void update(int delta)
    {
        if(player != null)
        {
            player.update(this, delta);
        }
    }
    
    public void render(Graphics g)
    {
        if(player == null)
            map.render(0, 0);
        
        else
        {
            map.renderLayersBelow(0, 0);
            player.render();
            map.renderLayersAbove(0, 0);
            
            Vec2 pos = player.getPos();
            g.drawString("(" + (int)pos.x + ", " + (int)pos.y + ")", (int)pos.x + 21, (int)pos.y + 27);
        }
    }
}
