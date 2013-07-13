package jgame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jgame.math.Vec2;
import jgame.math.Vec2Int;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 *
 * @author hector
 */
public class Debug {
    private static List<Vec2Int> points = new ArrayList();
    private static Map<Vec2, Image> images = new HashMap();
    private static boolean enabled = false;
    
    public static void enable()
    {
        enabled = true;
    }
    
    public static void addPoint(Vec2 point)
    {
        points.add(point.div(new Vec2Int(4, 4)));
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
            for(Vec2Int point : points)
                g.drawRect(point.x*4, point.y*4, 4, 4);
            
            for(Vec2 key : images.keySet())
                g.drawImage(images.get(key), (float) key.x, (float) key.y);
        }
        
        points.clear();
    }
    
}
