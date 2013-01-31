package jgame.level;

import java.util.ArrayList;
import java.util.List;
import jgame.Debug;
import jgame.entity.Entity;
import jgame.entity.MessageType;
import jgame.math.Vec2;
import jgame.math.Vec2Int;

/**
 * An Entity manager.
 * Provides methods to manage entities in a tile based map.
 * @author hector
 */
public class EntityMap
{
    private static final Vec2Int SUBTILE_COUNT = new Vec2Int(4, 4);
    
    private List<Entity>[][] map;
    private List<Entity> entities;
    
    Vec2Int subtileDim;
    
    public EntityMap(int width, int height, int tileWidth, int tileHeight)
    {
        map = new List[height*SUBTILE_COUNT.y][width*SUBTILE_COUNT.x]; // One tile has 4 subtiles
        initMap();
        
        entities = new ArrayList();
        subtileDim = new Vec2Int(tileWidth, tileHeight).div(SUBTILE_COUNT);
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
            map[tile.y][tile.x].add(entity);
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
        List<Entity> new_entities = new ArrayList();
        
        for(Entity e : entities)
        {
            if(e.shouldDie())
                free(e);
            else
                new_entities.add(e);
        }
        
        entities = new_entities;
    }
    
    public List<Entity> getEntities()
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
        for(Vec2Int subtile : area.getSubtiles(subtileDim, SUBTILE_COUNT))
        {
            if(subtile.x < 0 || subtile.y < 0)
                continue;
            
            if(subtile.x >= map[0].length || subtile.y >= map.length)
                continue;
            
            Debug.addPoint(subtile);
            
            for(Entity e : map[subtile.y][subtile.x])
                e.receive(msg, from);
        }
    }
    
    private List<Vec2Int> getSubtiles(Entity entity)
    {
        return getSubtiles(entity, entity.getPos());
    }
    
    private List<Vec2Int> getSubtiles(Entity entity, Vec2 pos)
    {
        List<Vec2Int> subtiles = new ArrayList();
        
        Vec2Int subtilesTop = entity.getTopLeft(pos).div(subtileDim);
        Vec2Int subtilesBot = entity.getBottomRight(pos).div(subtileDim);
        
        for(int i = subtilesTop.y; i <= subtilesBot.y; ++i)
            for(int j = subtilesTop.x; j <= subtilesBot.x; ++j)
                subtiles.add(new Vec2Int(j, i));
        
        return subtiles;
    }
}
