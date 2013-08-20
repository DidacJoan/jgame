package jgame.level;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import jgame.Debug;
import jgame.Entity;
import jgame.entity.MessageType;
import jgame.level.area.TileArea;
import jgame.math.Vec2;
import jgame.math.Vec2Int;
import jgame.utils.Grid;
import org.newdawn.slick.Color;

/**
 * An Entity manager.
 * Provides methods to manage entities in a tile based map.
 * @author hector
 */
public class EntityMap
{
    private static final int SUBTILE_DIM = 4;
    
    private List<Entity>[][] map;
    private Vec2Int subtileCount;
    private TreeSet<Entity> entities;
    
    public EntityMap(int width, int height, int tileWidth, int tileHeight)
    {
        subtileCount = new Vec2Int(tileWidth, tileHeight).div(SUBTILE_DIM);
        map = new List[height*subtileCount.y][width*subtileCount.x];
        entities = new TreeSet();
        
        // @TODO Debug factory?
        Grid grid = new Grid(width * tileWidth, height * tileHeight, SUBTILE_DIM);
        Debug.setGrid(grid);
        
        initMap();
    }
    
    private void initMap()
    {
        for(int i = 0; i < map.length; ++i)
            for(int j = 0; j < map[0].length; ++j)
                map[i][j] = new ArrayList();
    }
    
    public void lock(Entity entity)
    {
        for(Vec2Int tile : getSubtiles(entity))
        {
            map[tile.y][tile.x].add(entity);
            
            Debug.highlight(Color.red, tile);
        }
    }
    
    public void free(Entity entity)
    {
        for(Vec2Int tile : getSubtiles(entity))
            map[tile.y][tile.x].remove(entity);
    }
    
    public void add(Entity entity)
    {
        lock(entity);
        entities.add(entity);
    }
    
    public void remove(Entity entity)
    {
        free(entity);
        entities.remove(entity);
    }
    
    public void removeDead()
    {
        Iterator<Entity> it = entities.iterator();
        
        while(it.hasNext())
        {
            Entity e = it.next();
            
            if(e.shouldDie())
            {
                free(e);
                it.remove();
            }
        }
    }
    
    public Set<Entity> getEntities()
    {
        return entities;
    }
    
    public boolean handleCollisions(Entity e, Vec2 pos)
    {
        boolean collides = false;
        
        for(Vec2Int tile : getSubtiles(e, pos))
        {
            for(Entity e_collision : map[tile.y][tile.x])
            {
                if(e_collision.collidesWith(e))
                {
                    e_collision.handleCollisionWith(e);
                    collides = true;
                }
            }
        }
        
        return !collides;
    }
    
    public void send(MessageType msg, Entity from, TileArea area)
    {   
        for(Vec2Int subtile : area.getSubtiles(SUBTILE_DIM))
        {
            if(subtile.x < 0 || subtile.y < 0)
                continue;
            
            if(subtile.x >= map[0].length || subtile.y >= map.length)
                continue;
            
            Debug.highlight(Color.blue, subtile);
            
            for(Entity e : map[subtile.y][subtile.x])
                e.receive(msg, from);
        }
    }
    
    public void update(int delta)
    {
        TreeSet<Entity> entitiesUpdated = new TreeSet();
        
        for(Entity e : entities)
        {
            free(e);
            e.update(delta);
            lock(e);
            
            entitiesUpdated.add(e);
        }
        
        entities = entitiesUpdated;
    }
    
    private List<Vec2Int> getSubtiles(Entity entity)
    {
        return getSubtiles(entity, entity.getPos());
    }
    
    private List<Vec2Int> getSubtiles(Entity entity, Vec2 pos)
    {
        List<Vec2Int> subtiles = new ArrayList();
        
        Vec2Int subtilesTop = entity.getTopLeft(pos).div(SUBTILE_DIM);
        Vec2Int subtilesBot = entity.getBottomRight(pos).div(SUBTILE_DIM);
        
        for(int i = subtilesTop.y; i <= subtilesBot.y; ++i)
            for(int j = subtilesTop.x; j <= subtilesBot.x; ++j)
                subtiles.add(new Vec2Int(j, i));
        
        return subtiles;
    }
}
