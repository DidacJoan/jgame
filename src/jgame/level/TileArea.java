package jgame.level;

import java.util.ArrayList;
import java.util.List;
import jgame.entity.Dir;
import jgame.entity.Entity;
import jgame.math.Vec2;
import jgame.math.Vec2Int;

/**
 *
 * @author hector
 */
public class TileArea
{
    Vec2 posCenter;
    Vec2Int areaCenter;
    char[][] area;
    
    Dir orientation;
    
    public TileArea(char[][] area)
    {
        this.area = area;
        areaCenter = findAreaCenter();
        
        posCenter = new Vec2(0, 0);
        orientation = Dir.DOWN;
    }
    
    private Vec2Int findAreaCenter()
    {
        for(int i = 0; i < area[0].length; ++i)
            if(area[0][i] == 'C')
                return new Vec2Int(i, 0);
        
        return new Vec2Int(0, 0);
    }
    
    public void setCenter(Entity e)
    {
        System.out.println("Entity is at " + e.getPos());
        posCenter = e.getPos().add(new Vec2(e.getWidth() / 2.0, e.getHeight() / 2.0));
        System.out.println("Entity center is at " + posCenter);
    }
    
    public void setOrientation(Dir d)
    {
        orientation = d;
    }
    
    public List<Vec2Int> getSubtiles(Vec2Int subtileDim, Vec2Int subtileCount)
    {
        List<Vec2Int> tiles = new ArrayList();
        
        if(orientation == Dir.DOWN)
            addSubtilesDown(tiles, subtileDim, subtileCount);
        
        else if(orientation == Dir.UP)
            addSubtilesUp(tiles, subtileDim, subtileCount);
        
        else if(orientation == Dir.LEFT)
            addSubtilesLeft(tiles, subtileDim, subtileCount);
        
        else if(orientation == Dir.RIGHT)
            addSubtilesRight(tiles, subtileDim, subtileCount);
        
        System.out.println(tiles.size() + " subtiles in the area.");
        
        return tiles;
    }
    
    private void addSubtilesDown(List<Vec2Int> tiles, Vec2Int subtileDim, Vec2Int subtileCount)
    {
        Vec2Int posCenterSubtile = posCenter.div(subtileDim);
        Vec2Int areaSubtile = posCenterSubtile.sub(areaCenter.mul(subtileCount));
        
        for(int i = 0; i < area.length; ++i)
            for(int j = 0; j < area[0].length; ++j)
                addSubtiles(tiles, subtileDim, subtileCount, areaSubtile, j, i);
    }
    
    private void addSubtilesUp(List<Vec2Int> tiles, Vec2Int subtileDim, Vec2Int subtileCount)
    {
        Vec2Int posCenterSubtile = posCenter.div(subtileDim);
        Vec2Int areaBottomRight = new Vec2Int(area[0].length, area.length);
        Vec2Int areaSubtile = posCenterSubtile.sub(areaBottomRight.sub(areaCenter).mul(subtileCount));
        
        for(int i = area.length; i > 0; --i)
            for(int j = area[0].length; j > 0; --j)
                addSubtiles(tiles, subtileDim, subtileCount, areaSubtile, area[0].length - j, area.length - i);
    }
    
    private void addSubtilesLeft(List<Vec2Int> tiles, Vec2Int subtileDim, Vec2Int subtileCount)
    {
        
    }
       
    private void addSubtilesRight(List<Vec2Int> tiles, Vec2Int subtileDim, Vec2Int subtileCount)
    {
        
    }
    
    private void addSubtiles(List<Vec2Int> tiles, Vec2Int sD, Vec2Int sC, Vec2Int aS, int x, int y)
    {
        if(area[y][x] != 'X')
            return;
        
        System.out.println("Area starts at " + aS);
        
        for(int i = 0; i < sC.x; ++i)
        {
            for(int j = 0; j < sC.y; ++j)
            {
                // subtile = areaSubtile + iterator*subtileDimension
                tiles.add(new Vec2Int(aS.x + x*sD.x + i, aS.y + y*sD.y + j));
            }
        }
    }
}
