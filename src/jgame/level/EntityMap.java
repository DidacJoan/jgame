package jgame.level;

import java.util.ArrayList;
import java.util.HashSet;
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
 *
 * @author hector
 */
public class EntityMap
{
    private static final int SUBTILE_DIM = 4;
    
    private List<Entity>[][] map;
    private TreeSet<Entity> entities;
    private Vec2Int subtileCount;
    
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
    
    public int getHorizontalSubtiles()
    {
        return map.length;
    }
    
    public int getVerticalSubtiles()
    {
        return map[0].length;
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
    
    public void lock(Entity entity)
    {
        for(Vec2Int tile : getSubtiles(entity))
        {
            map[tile.y][tile.x].add(entity);
            
            Debug.highlight(Color.cyan, getSubtile(entity.getPos()));
            Debug.highlight(Color.cyan, getSubtile(entity.getCenter()));
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
    
    public Set<Entity> getEntitiesCollidedBy(Entity entity, Vec2Int atSubtile)
    {
        Set<Entity> enties = new HashSet();
        
        for(Vec2Int subtile : getSubtiles(entity, getPosition(atSubtile)))
            for(Entity e_collision : map[subtile.y][subtile.x])
                if(e_collision.collidesWith(entity))
                    enties.add(e_collision);
        
        return enties;
    }
    
    public void send(MessageType msg, Entity from, TileArea area)
    {
        for(Vec2Int subtile : area.getSubtiles(SUBTILE_DIM))
        {
            if(!isSubtileValid(subtile))
                continue;
            
            Debug.highlight(Color.blue, subtile);
            
            for(Entity e : map[subtile.y][subtile.x])
                e.receive(msg, from);
        }
    }
    
    public Set<Entity> seek(Vec2 pos, int radius)
    {
        Set<Entity> foundEntities = new HashSet();
        
        // First approach. Rectangle area
        // @TODO Round area? Spiral?
        
        Vec2Int centerSubtile = getSubtile(pos);
        Vec2Int topLeft = centerSubtile.sub(radius);
        Vec2Int bottomRight = centerSubtile.add(radius);
        
        for(int x = topLeft.x; x < bottomRight.x; ++x)
        {
            for(int y = topLeft.y; y <= bottomRight.y; ++y)
            {
                if(isSubtileValid(x, y))
                {
                    foundEntities.addAll(map[y][x]);
                    Debug.highlight(Color.orange, new Vec2Int(x, y));
                }
            }
        }
        
        return foundEntities;
    }
    
    public List<Vec2Int> getNeighbors(Vec2Int subtile)
    {
        List<Vec2Int> neighbors = new ArrayList();
        
        Vec2Int topLeft = subtile.sub(1);
        Vec2Int bottomRight = subtile.add(1);
        
        for(int x = topLeft.x; x <= bottomRight.x; ++x)
        {
            for(int y = topLeft.y; y <= bottomRight.y; ++y)
            {
                if(isSubtileValid(x, y))
                    neighbors.add(new Vec2Int(x, y));
            }
        }
        
        return neighbors;
    }
    
    public Vec2 getPosition(Vec2Int subtile)
    {
        return new Vec2(subtile.x * SUBTILE_DIM, subtile.y * SUBTILE_DIM);
    }
    
    private List<Vec2Int> getSubtiles(Entity entity)
    {
        return getSubtiles(entity, entity.getPos());
    }
    
    private List<Vec2Int> getSubtiles(Entity entity, Vec2 pos)
    {
        List<Vec2Int> subtiles = new ArrayList();
        
        Vec2Int subtilesTop = getSubtile(entity.getTopLeft(pos));
        Vec2Int subtilesBot = getSubtile(entity.getBottomRight(pos));
        
        for(int i = subtilesTop.y; i <= subtilesBot.y; ++i)
            for(int j = subtilesTop.x; j <= subtilesBot.x; ++j)
                subtiles.add(new Vec2Int(j, i));
        
        return subtiles;
    }
    
    public Vec2Int getSubtile(Vec2 pos)
    {
        return pos.div(SUBTILE_DIM);
    }
    
    private boolean isSubtileValid(Vec2Int subtile)
    {
        return isSubtileValid(subtile.x, subtile.y);
    }
        
    private boolean isSubtileValid(int x, int y)
    {
        if(x < 0 || y < 0)
            return false;
            
        if(x >= map[0].length || y >= map.length)
            return false;
        
        return true;
    }
}
