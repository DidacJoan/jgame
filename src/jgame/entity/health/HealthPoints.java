package jgame.entity.health;

import jgame.entity.Health;
import jgame.math.Vec2;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 *
 * @author hector
 */
public class HealthPoints implements Health
{
    private static final int BAR_HEIGHT = 5;
    
    private int total;
    private int current;
    private Vec2 offset;
    
    public HealthPoints(int total)
    {
        this(total, total);
    }
    
    public HealthPoints(int total, int current)
    {
        this.total = total;
        this.current = current;
        this.offset = new Vec2(0, 0);
    }
    
    @Override
    public boolean isEmpty()
    {
        return current <= 0;
    }
    
    @Override
    public void damage(int points)
    {
        current -= points;
        
        if(current < 0)
            current = 0;
    }
    
    @Override
    public void heal(int points)
    {
        current += points;
        
        if(current > total)
            current = total;
    }
    
    @Override
    public void setRenderCentered(boolean renderCentered)
    {
        if(renderCentered)
            offset.x = (total / 2) - 1;
        else
            offset.x = 0;
    }
    
    @Override
    public void setRenderOver(boolean renderOver)
    {
        if(renderOver)
            offset.y = BAR_HEIGHT;
        else
            offset.y = 0;
    }
    
    @Override
    public void render(Graphics g, Vec2 pos)
    {
        Vec2 renderPos = pos.sub(offset);
        
        g.setColor(Color.black);
        g.fillRect((float)renderPos.x, (float)renderPos.y, total + 2, BAR_HEIGHT);
        
        g.setColor(getCurrentColor());
        g.fillRect((float)renderPos.x + 1, (float)renderPos.y + 1, current, BAR_HEIGHT - 2);
    }
    
    private Color getCurrentColor()
    {
        if(current <= total / 4)
            return Color.red;
        
        if(current <= total / 2)
            return Color.yellow;
        
        return Color.green;
    }
}
