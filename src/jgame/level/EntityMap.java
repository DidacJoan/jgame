package jgame.level;

import java.util.ArrayList;
import java.util.List;
import jgame.entity.Dir;
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
    private static final int SUBTILES_X = 4;
    private static final int SUBITLES_Y = 4;
    
    private List<Entity>[][] map;
    private List<Entity> entities;
    
    int subtileWidth;
    int subtileHeight;
    
    public EntityMap(int width, int height, int tileWidth, int tileHeight)
    {
        map = new List[height*SUBITLES_Y][width*SUBTILES_X]; // One tile has 4 subtiles
        initMap(width*SUBTILES_X, height*SUBITLES_Y);
        
        entities = new ArrayList();
        this.subtileWidth = tileWidth / SUBTILES_X;
        this.subtileHeight = tileHeight / SUBITLES_Y;
    }
    
    private void initMap(int w, int h)
    {
        for(int i = 0; i < h; ++i)
            for(int j = 0; j < w; ++j)
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
    
    public void send(MessageType msg, Entity from, Dir to)
    {
        /*Vec2Int tileFrom = getTile(from);
        Vec2Int tileTo = tileFrom.add(to.getVector());
        
        if(tileTo.x >= map[0].length)
            return;
        
        if(tileTo.y >= map.length)
            return;
        
        System.out.println("Sending " + msg + " from " + tileFrom + " to " + tileTo);
        
        for(Entity e : map[tileTo.y][tileTo.x])
            e.receive(msg, from);*/
    }
    
    private List<Vec2Int> getSubtiles(Entity entity)
    {
        return getSubtiles(entity, entity.getPos());
    }
    
    private List<Vec2Int> getSubtiles(Entity entity, Vec2 pos)
    {
        List<Vec2Int> subtiles = new ArrayList();
        
        Vec2Int subtilesTop = entity.getTopLeft(pos).div(new Vec2Int(subtileWidth, subtileHeight));
        Vec2Int subtilesBot = entity.getBottomRight(pos).div(new Vec2Int(subtileWidth, subtileHeight));
        
        for(int i = subtilesTop.y; i <= subtilesBot.y; ++i)
            for(int j = subtilesTop.x; j <= subtilesBot.x; ++j)
                subtiles.add(new Vec2Int(j, i));
        
        return subtiles;
    }
}
