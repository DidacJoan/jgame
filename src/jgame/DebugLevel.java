package jgame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import jgame.entity.MessageType;
import jgame.level.area.TileArea;
import jgame.math.Vec2;
import jgame.math.Vec2Int;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 *
 * @author hector
 */
public class DebugLevel extends Level
{
    private static final Color COLOR = new Color(255f, 255f, 255f, 0.3f);
    private static final Color[] ORDER = new Color[]{ Color.cyan, Color.blue, Color.green, Color.orange,
        Color.red, Color.darkGray };
    
    private boolean enabled;
    private Map<Color, List<Vec2Int>> highlights;
    
    public DebugLevel(String name) throws SlickException
    {
        super(name);
        
        enabled = true;
        highlights = new HashMap();
    }
    
    public void toggle()
    {
        enabled = !enabled;
    }
    
    @Override
    public void load() throws SlickException
    {
        System.out.println("Loading map: " + getName());
        super.load();
        
        System.out.println("Layers " + getAboveLayerIndex() + "+ are above the player.");
    }
    
    @Override
    protected void loadLayer(int layerNum)
    {
        System.out.println("Loading layer " + layerNum + "...");
 
        super.loadLayer(layerNum);
    }
    
    @Override
    public void update(int delta)
    {
        if(player != null)
        {
            if(player.hasKeyUp(Input.KEY_ESCAPE))
                System.exit(0);
            
            if(player.hasKeyUp(Input.KEY_D))
                toggle();
        }
        
        if(enabled)
        {
            highlightEntities();
            //...
        }
        
        super.update(delta);
    }
    
    private void highlightEntities()
    {
        for(Entity entity : getEntities())
        {
            highlight(Color.cyan, getSubtile(entity.getPos()));
            highlight(Color.cyan, getSubtile(entity.getCenter()));
            
            for(Vec2Int tile : getSubtiles(entity))
                highlight(Color.red, tile);
        }
    }
    
    @Override
    public void send(MessageType msg, Entity from, TileArea area)
    {
        // May be slow?
        for(Vec2Int subtile : area.getSubtiles(getSubtileDimension()))
            highlight(Color.blue, subtile);
        
        super.send(msg, from, area);
    }
    
    @Override
    public Set<Entity> seek(Vec2 pos, int radius)
    {
        // @TODO Refactoring?
        Vec2Int centerSubtile = getSubtile(pos);
        Vec2Int topLeft = centerSubtile.sub(radius);
        Vec2Int bottomRight = centerSubtile.add(radius);
        
        for(int x = topLeft.x; x < bottomRight.x; ++x)
            for(int y = topLeft.y; y <= bottomRight.y; ++y)
                if(isSubtileValid(x, y))
                    highlight(Color.orange, new Vec2Int(x, y));
        
        return super.seek(pos, radius);
    }
    
    @Override
    public void render(Graphics g)
    {
        super.render(g);
        
        if(enabled)
            drawGrid(g);
        
        highlights.clear();
    }
    
    private void drawGrid(Graphics g)
    {
        int totalWidth = getWidthInPixels();
        int totalHeight = getHeightInPixels();
        int subtileDim = getSubtileDimension();
        
        g.setColor(COLOR);
        
        for(int i = 0; i <= totalWidth; i += subtileDim)
            g.drawLine(i, 0, i, totalHeight);
        
        for(int j = 0; j <= totalHeight; j += subtileDim)
            g.drawLine(0, j, totalWidth, j);
        
        for(int i = ORDER.length - 1; i >= 0; --i)
        {
            Color color = ORDER[i];
            
            if(! highlights.containsKey(color))
                continue;
            
            g.setColor(color);
            
            for(Vec2Int subtile : highlights.get(color))
                g.drawRect(subtile.x * subtileDim, subtile.y * subtileDim, subtileDim, subtileDim);
        }
    }
    
    @Override
    public void highlight(Color color, Vec2Int subtile)
    {
        if(! highlights.containsKey(color))
            highlights.put(color, new ArrayList());
        
        highlights.get(color).add(subtile);
    }
}
