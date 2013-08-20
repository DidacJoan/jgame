package jgame.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jgame.math.Vec2Int;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 *
 * @author hector
 */
public class Grid
{
    private static final Color COLOR = new Color(255f, 255f, 255f, 0.3f);
    
    private int width;
    private int height;
    private int subtileDim;
    private Map<Color, List<Vec2Int>> highlights;
    
    public Grid(int width, int height, int subtileDim)
    {
        this.width = width;
        this.height = height;
        this.subtileDim = subtileDim;
        this.highlights = new HashMap();
    }
    
    public void draw(Graphics g)
    {
        g.setColor(COLOR);
        
        for(int i = 0; i < width; i += subtileDim)
            g.drawLine(i, 0, i, height);
        
        for(int j = 0; j < height; j += subtileDim)
            g.drawLine(0, j, width, j);
        
        for(Color color : highlights.keySet())
        {
            g.setColor(color);
            
            for(Vec2Int subtile : highlights.get(color))
                g.drawRect(subtile.x * subtileDim, subtile.y * subtileDim, subtileDim, subtileDim);
        }
        
        highlights.clear();
    }
    
    public void highlight(Color color, Vec2Int subtile)
    {
        if(! highlights.containsKey(color))
            highlights.put(color, new ArrayList());
        
        highlights.get(color).add(subtile);
    }
}
