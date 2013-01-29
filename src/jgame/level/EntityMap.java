package jgame.level;

import java.util.ArrayList;
import java.util.List;
import jgame.entity.Dir;
import jgame.entity.Entity;
import jgame.entity.MessageType;
import jgame.math.Vec2;
import jgame.math.Vec2Int;

/**
 *
 * @author hector
 */
public class EntityMap
{
    private List<Entity>[][] map;
    private List<Entity> entities;
    
    int tileWidth;
    int tileHeight;
    
    public EntityMap(int width, int height, int tileWidth, int tileHeight)
    {
        map = new List[height][width];
        initMap(width, height);
        
        entities = new ArrayList();
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
    }
    
    private void initMap(int w, int h)
    {
        for(int i = 0; i < h; ++i)
            for(int j = 0; j < w; ++j)
                map[i][j] = new ArrayList();
    }
    
    public void lock(Entity entity)
    {
        Vec2Int tile = getTile(entity);
        map[tile.y][tile.x].add(entity);
    }
    
    public void free(Entity entity)
    {
        Vec2Int tile = getTile(entity);
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
    
    public boolean hasEntity(int x, int y)
    {
        return (! map[y][x].isEmpty());
    }
    
    public void send(MessageType msg, Entity from, Dir to)
    {
        Vec2Int tileFrom = getTile(from);
        Vec2Int tileTo = tileFrom.add(to.getVector());
        
        if(tileTo.x >= map[0].length)
            return;
        
        if(tileTo.y >= map.length)
            return;
        
        System.out.println("Sending " + msg + " from " + tileFrom + " to " + tileTo);
        
        for(Entity e : map[tileTo.y][tileTo.x])
            e.receive(msg, from);
    }
    
    private Vec2Int getTile(Entity entity)
    {
        Vec2 pos = entity.getPos();
        
        // Center the position, small hack!
        // @todo REFACTORING NEEDED!
        int x = (int) pos.x + 12;
        int y = (int) pos.y + 14;
        
        return new Vec2Int(x / tileWidth, y / tileHeight);
    }
}
