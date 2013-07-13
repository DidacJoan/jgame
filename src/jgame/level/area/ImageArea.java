package jgame.level.area;

import java.util.ArrayList;
import java.util.Collection;
import jgame.math.Vec2;
import jgame.math.Vec2Int;
import org.newdawn.slick.Image;

/**
 *
 * @author hector
 */
public class ImageArea implements TileArea
{
    private Image areaInfo;
    private Vec2 position;
    
    public ImageArea(Image areaInfo, Vec2 position)
    {
        this.areaInfo = areaInfo;
        this.position = position;        
    }
    
    @Override
    public Collection<Vec2Int> getSubtiles(Vec2Int subtileDimension)
    {
        Collection<Vec2Int> subtiles = new ArrayList();
        
        int dx = (int) position.x / subtileDimension.x;
        int dy = (int) position.y / subtileDimension.y;
        
        int rightLimit = areaInfo.getWidth() / subtileDimension.x;
        int bottomLimit = areaInfo.getHeight() / subtileDimension.y;
        
        for(int x = 0; x < rightLimit; x++)
            for(int y = 0; y < bottomLimit; y++)
                if(contains(x, y, subtileDimension))
                    subtiles.add(new Vec2Int(dx + x, dy + y));
        
        return subtiles;
    }
    
    public boolean contains(int subtileX, int subtileY, Vec2Int subtileDimension)
    {
        int rightLimit = (subtileX + 1) * subtileDimension.x;
        int bottomLimit = (subtileY + 1) * subtileDimension.y;
        
        for(int x = subtileX * subtileDimension.x; x < rightLimit; ++x)
            for(int y = subtileY * subtileDimension.y; y < bottomLimit; ++y)
                if(areaInfo.getColor(x, y).getAlpha() != 0)
                    return true;
        
        return false;
    }
}
