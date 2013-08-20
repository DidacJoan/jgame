package jgame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jgame.math.Vec2;
import jgame.math.Vec2Int;
import jgame.utils.Grid;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 *
 * @author hector
 */
public class Debug
{
    private static List<Vec2Int> points = new ArrayList();
    private static Map<Vec2, Image> images = new HashMap();
    private static boolean enabled = false;
    private static Grid grid;
    
    public static void enable()
    {
        enabled = true;
    }
    
    public static void toggle()
    {
        enabled = !enabled;
    }
    
    public static void addPoint(Vec2 point)
    {
        points.add(new Vec2Int((int)point.x, (int)point.y));
    }
    
    public static void addPoint(Vec2Int point)
    {
        points.add(point);
    }
    
    public static void addImage(Image img, Vec2 pos)
    {
        images.put(pos, img);
    }
    
    public static void render(Graphics g)
    {
        if(enabled)
        {
            drawGrid(g);
            drawPoints(g);
            drawImages(g);
        }
        
        points.clear();
    }
    
    public static void setGrid(Grid g)
    {
        grid = g;
    }
    
    public static void highlight(Color color, Vec2Int subtile)
    {
        if(grid == null)
            return;
        
        grid.highlight(color, subtile);
    }
    
    private static void drawGrid(Graphics g)
    {
        if(grid != null)
            grid.draw(g);
    }
    
    private static void drawPoints(Graphics g)
    {
        for(Vec2Int point : points)
            g.drawRect(point.x, point.y, 1, 1);
    }
    
    private static void drawImages(Graphics g)
    {
        for(Vec2 key : images.keySet())
            g.drawImage(images.get(key), (float) key.x, (float) key.y);
    }
}
