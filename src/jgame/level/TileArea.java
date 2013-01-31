package jgame.level;

import java.util.ArrayList;
import java.util.List;
import jgame.entity.Dir;
import jgame.entity.Entity;
import jgame.math.Vec2;
import jgame.math.Vec2Int;

/**
 * Represents an area of tiles.
 * CAUTION:
 * Magic code, reading it can cause headaches.
 * @author hector
 */
public class TileArea
{
    private String[][] area;
    private int resolution;
    private Vec2 posCenter;
    private Vec2Int areaCenter;
    
    Dir orientation;
    
    public TileArea(String[][] area)
    {
        this.area = area;
        resolution = area[0][0].length();
        areaCenter = findAreaCenter();
        
        posCenter = new Vec2(0, 0);
        orientation = Dir.DOWN;
    }
    
    private Vec2Int findAreaCenter()
    {
        for(int i = 0; i < area.length; ++i)
            for(int j = 0; j < area[0].length; ++j)
                for(int k = 0; k < resolution; ++k)
                    if(area[i][j].charAt(k) == 'C')
                        return new Vec2Int(j*resolution + k, i);
        
        return new Vec2Int(area[0].length / 2, area.length / 2);
    }
    
    public void setCenter(Entity e)
    {
        posCenter = e.getCenter();
    }
    
    public void setOrientation(Dir d)
    {
        orientation = d;
    }
    
    public List<Vec2Int> getSubtiles(Vec2Int subtileDim, Vec2Int subtileCount)
    {
        List<Vec2Int> tiles = new ArrayList();
        Vec2Int posCenterSubtile = posCenter.div(subtileDim);
        
        if(orientation == Dir.DOWN)
            addSubtilesDown(tiles, posCenterSubtile);
        
        else if(orientation == Dir.UP)
            addSubtilesUp(tiles, posCenterSubtile);
        
        else if(orientation == Dir.LEFT)
            addSubtilesLeft(tiles, posCenterSubtile);
        
        else if(orientation == Dir.RIGHT)
            addSubtilesRight(tiles, posCenterSubtile);
        
        return tiles;
    }
    
    private void addSubtilesDown(List<Vec2Int> tiles, Vec2Int posCenterSubtile)
    {
        Vec2Int areaSubtile = posCenterSubtile.sub(areaCenter);
        
        for(int i = 0; i < area.length; ++i)
            for(int j = 0; j < area[0].length; ++j)
                for(int k = 0; k < resolution; ++k)
                    addSubtiles(tiles, areaSubtile, j*resolution+k, i, area[i][j].charAt(k));
    }
    
    private void addSubtilesUp(List<Vec2Int> tiles, Vec2Int posCenterSubtile)
    {
        Vec2Int areaBRight = new Vec2Int(area[0].length * resolution - 1, area.length);
        Vec2Int areaSubtile = posCenterSubtile.sub(areaBRight.sub(areaCenter));
        
        for(int i = area.length; i > 0; --i)
            for(int j = area[0].length; j > 0; --j)
                for(int k = resolution; k > 0; --k)
                    addSubtiles(tiles, areaSubtile, (area[0].length - j + 1) * resolution - k,
                        area.length - i, area[i-1][j-1].charAt(k-1));
    }
    
    private void addSubtilesLeft(List<Vec2Int> tiles, Vec2Int posCenterSubtile)
    {
        Vec2Int areaBLeft = new Vec2Int(area.length - 1 - areaCenter.y, areaCenter.x);
        Vec2Int areaSubtile = posCenterSubtile.sub(areaBLeft);
        
        for(int j = 0; j < area[0].length; ++j)
            for(int k = 0; k < resolution; ++k)
                for(int i = area.length; i > 0; --i)
                    addSubtiles(tiles, areaSubtile, area.length - i, j*resolution + k, area[i-1][j].charAt(k));
    }
       
    private void addSubtilesRight(List<Vec2Int> tiles, Vec2Int posCenterSubtile)
    {
        Vec2Int areaTRight = new Vec2Int(areaCenter.y, area[0].length*resolution - areaCenter.x - 2);
        Vec2Int areaSubtile = posCenterSubtile.sub(areaTRight);
        
        for(int j = area[0].length; j > 0; --j)
            for(int k = resolution; k > 0; --k)
                for(int i = 0; i < area.length; ++i)
                    addSubtiles(tiles, areaSubtile, i, (area[0].length - j + 1)*resolution - k, area[i][j-1].charAt(k-1));
    }
    
    private void addSubtiles(List<Vec2Int> tiles, Vec2Int aS, int x, int y, char c)
    {
        if(c != 'X')
            return;
        
        tiles.add(new Vec2Int(aS.x + x, aS.y + y));
    }
}
