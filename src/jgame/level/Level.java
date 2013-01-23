package jgame.level;

import jgame.entity.Dir;
import jgame.entity.Player;
import jgame.math.Vec2;
import org.newdawn.slick.Graphics;
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
    private int layerCount;
    private int aboveIndex;
    
    public Level(String name) throws SlickException
    {
        this.name = name;
        player = null;
        aboveIndex = 0;
        
        load();
    }
    
    private void load() throws SlickException
    {
        System.out.println("Loading level " + name + "...");
        
        map = new TiledMap("res/levels/" + name + ".tmx");
        
        loadTiles();
        
        System.out.println(name + " level loaded successfully!");
    }
    
    private void loadTiles()
    {
        blocked = new boolean[map.getHeight()][map.getWidth()];
        layerCount = aboveIndex = map.getLayerCount();
        
        for(int i = 0; i < layerCount; ++i)
        {
            System.out.println("Loading layer " + i + "...");
            
            if(map.getLayerProperty(i, "blocked", "false").equals("true"))
                loadBlockedTilesFromLayer(i);
            
            if(aboveIndex == layerCount && map.getLayerProperty(i, "above", "false").equals("true"))
            {
                aboveIndex = i;
                System.out.println("Layers " + i + "+ are above the player...");
            }
        }
    }
    
    private void loadBlockedTilesFromLayer(int layerIndex)
    {
        for (int j = 0; j < blocked.length; ++j)
        {
            for (int k = 0; k < blocked[0].length; ++k)
            {
                if (blocked[j][k])
                {
                    continue;
                }

                blocked[j][k] = (map.getTileId(k, j, layerIndex) > 0);
            }
        }
    }
    
    public void setPlayer(Player p, String location)
    {
        player = p;
        
        double playerX = Double.parseDouble(map.getMapProperty(location + "X", "0"));
        double playerY = Double.parseDouble(map.getMapProperty(location + "Y", "0"));
                
        player.setPos(playerX * map.getTileWidth(), (playerY-0.7) * map.getTileHeight());
        
        String playerFacing = map.getMapProperty(location + "F", "UP");
        Dir facing = Dir.valueOf(playerFacing);
        
        player.setFacing(facing);
    }
    
    public boolean areTilesBlocked(Vec2 topLeft, Vec2 bottomRight)
    {
        double topX = topLeft.x / map.getTileWidth();
        double topY = topLeft.y / map.getTileHeight();
        int botX = (int) bottomRight.x / map.getTileWidth();
        int botY = (int) bottomRight.y / map.getTileHeight();
        
        if(topX < 0 || botX >= blocked[0].length)
            return true;
        
        if(topY < 0 || botY >= blocked.length)
            return true;
        
        for(int i = (int)topX; i <= botX; ++i)
        {
            for(int j = (int)topY; j <= botY; ++j)
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
    
    public void render(Graphics g)
    {
        if(player == null)
            renderDirectly(g);
        
        else
            renderLayerByLayer(g);
    }
    
    private void renderDirectly(Graphics g)
    {
        map.render(0, 0);
    }
    
    private void renderLayerByLayer(Graphics g)
    {
        renderLayers(0, aboveIndex);
        player.render();
        renderLayers(aboveIndex, layerCount);
        
        Vec2 pos = player.getPos();
        g.drawString("(" + (int)pos.x + ", " + (int)pos.y + ")", (int)pos.x + 21, (int)pos.y + 27);
    }
    
    private void renderLayers(int from, int to)
    {
        while(from < to)
        {
            map.render(0, 0, from);
            from++;
        }
    }
}
