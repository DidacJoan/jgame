package jgame.level;

import jgame.entity.Entity;
import jgame.entity.Player;
import jgame.entity.object.Plant;
import jgame.math.Vec2;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.GroupObject;
import org.newdawn.slick.tiled.ObjectGroup;


/**
 * Represents a level.
 * @author hector
 */
public class Level
{
    private TileMap map;
    private EntityMap entities;
    
    
    public Level(String name) throws SlickException
    {
        map = new TileMap(name);
        entities = new EntityMap(
                map.getWidthInTiles(),
                map.getHeightInTiles(),
                map.getTileWidth(),
                map.getTileHeight()
                );
        
        loadEntities();
    }
    
    private void loadEntities() throws SlickException
    {
        for(ObjectGroup group : map.getObjectGroups())
            for(GroupObject object : group.getObjects())
                addEntity(object);
    }
    
    private void addEntity(GroupObject object) throws SlickException
    {
        Entity e;
        
        switch(object.type)
        {
            case "plant":
            default:
                e = new Plant(object.width, object.height, map.getImage(object));
                break;
        }
        
        e.setPos(object.x, object.y - map.getTileHeight());
        entities.add(e);
    }
    
    public void setPlayer(Player p, String locationName)
    {
        Location location = map.getLocation(locationName);
        
        p.setPos(location.getPos());
        p.setFacing(location.getFacing());
        
        entities.add(p);
    }
    
    public void update(int delta)
    {
        entities.removeDead();
        
        for(Entity e : entities.getEntities())
        {
            entities.free(e);
            e.update(map, entities, delta);
            entities.lock(e);
        }
    }
    
    public void render(Graphics g)
    {
        map.renderLayersBelow(0, 0);
        
        for(Entity e : entities.getEntities())
            e.render();
        
        map.renderLayersAbove(0, 0);
    }
}
