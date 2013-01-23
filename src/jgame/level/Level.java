package jgame.level;

import jgame.entity.Dir;
import jgame.entity.Player;
import jgame.math.Vec2;
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
    private boolean[][] blocked;
    
    public Level(String name) throws SlickException
    {
        this.name = name;
        player = null;
        
        load();
    }
    
    private void load() throws SlickException
    {
        System.out.println("Loading level " + name + "...");
        
        map = new TiledMap("res/levels/" + name + ".tmx");
        
        loadBlockedTiles();
        
        System.out.println(name + " level loaded successfully!");
    }
    
    private void loadBlockedTiles()
    {
        blocked = new boolean[map.getHeight()][map.getWidth()];
        
        int layerCount = map.getLayerCount();
        
        for(int i = 0; i < layerCount; ++i)
        {
            System.out.println("Loading layer " + i + "...");
            
            if(map.getLayerProperty(i, "blocked", "false").equals("false"))
            {
                continue;
            }
            
            for(int j = 0; j < blocked.length; ++j)
            {
                for(int k = 0; k < blocked[0].length; ++k)
                {
                    if(blocked[j][k])
                    {
                        continue;
                    }
                    
                    blocked[j][k] = (map.getTileId(k, j, i) > 0);
                }
            }
        }
    }
    
    public void setPlayer(Player p, String location)
    {
        player = p;
        
        double playerX = Double.parseDouble(map.getMapProperty(location + "X", "0"));
        double playerY = Double.parseDouble(map.getMapProperty(location + "Y", "0"));
                
        player.setPos(playerX * map.getTileWidth(), (playerY-1) * map.getTileHeight());
        
        String playerFacing = map.getMapProperty(location + "F", "UP");
        Dir facing = Dir.valueOf(playerFacing);
        
        player.setFacing(facing);
    }
    
    public boolean areTilesBlocked(Vec2 topLeft, Vec2 bottomRight)
    {
        int topX = (int) topLeft.x / map.getTileWidth();
        int topY = (int) topLeft.y / map.getTileHeight();
        int botX = (int) bottomRight.x / map.getTileWidth();
        int botY = (int) bottomRight.y / map.getTileHeight();
        
        if(topX < 0 || botX >= blocked[0].length)
            return true;
        
        if(topY < 0 || botY >= blocked.length)
            return true;
        
        for(int i = topX; i <= botX; ++i)
        {
            for(int j = topY; j <= botY; ++j)
            {
                if(blocked[j][i])
                    return true;
            }
        }
        
        return false;
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
