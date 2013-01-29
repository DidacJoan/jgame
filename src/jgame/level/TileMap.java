package jgame.level;

import java.util.List;
import jgame.entity.Dir;
import jgame.math.Vec2;
import jgame.math.Vec2Int;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.GroupObject;
import org.newdawn.slick.tiled.ObjectGroup;
import org.newdawn.slick.tiled.TileSet;
import org.newdawn.slick.tiled.TiledMapPlus;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

/**
 * Represents a map in the game.
 * @author hector
 */
public class TileMap implements TileBasedMap {
    
    private String name;
    private TiledMapPlus map;
    private boolean[][] blocked;
    private int layerCount;
    private int aboveIndex;
    
    public TileMap(String name) throws SlickException
    {
        this.name = name;
        
        load();
    }
    
    private void load() throws SlickException
    {
        System.out.println("Loading map " + name + "...");
        
        map = new TiledMapPlus("res/levels/" + name + ".tmx");
        
        loadTiles();
        
        System.out.println(name + " map loaded successfully!");
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
    
    public Location getLocation(String location)
    {
        int x = Integer.parseInt(map.getMapProperty(location + "X", "0"));
        int y = Integer.parseInt(map.getMapProperty(location + "Y", "0"));
        
        Vec2Int tile = new Vec2Int(x, y-1);
        Vec2 pos = new Vec2(tile.x * map.getTileWidth(), tile.y * map.getTileHeight());
        Dir facing = Dir.valueOf(map.getMapProperty(location + "F", "UP"));
        
        return new Location(tile, pos, facing);
    }
    
    public int getTileWidth()
    {
        return map.getTileWidth();
    }
    
    public int getTileHeight()
    {
        return map.getTileHeight();
    }
    
    public List<ObjectGroup> getObjectGroups()
    {
        return map.getObjectGroups();
    }
    
    public Image getImage(GroupObject object)
    {
        TileSet tileset = map.getTileSetByGID(object.gid);
        int tilesetTileID = (object.gid - tileset.firstGID);
        
        return tileset.tiles.getSubImage(
                tileset.getTileX(tilesetTileID) * tileset.tileWidth,
                tileset.getTileY(tilesetTileID) * tileset.tileHeight,
                tileset.tileWidth,
                tileset.tileHeight
       );
    }
    
    public void renderLayersBelow(int x, int y)
    {
        renderLayers(0, aboveIndex, x, y);
    }
    
    public void renderLayersAbove(int x, int y)
    {
        renderLayers(aboveIndex, layerCount, x, y);
    }
    
    private void renderLayers(int from, int to, int x, int y)
    {
        while(from < to)
        {
            map.render(x, y, from);
            from++;
        }
    }
    
    @Override
    public boolean blocked(PathFindingContext ctx, int x, int y) {
        return blocked[y][x];
    }

    @Override
    public float getCost(PathFindingContext ctx, int x, int y) {
        return 1.0f;
    }

    @Override
    public int getHeightInTiles() {
        return map.getHeight();
    }

    @Override
    public int getWidthInTiles() {
        return map.getWidth();
    }

    @Override
    public void pathFinderVisited(int arg0, int arg1) {}
}
