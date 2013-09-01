/*
 */
package jgame.entity;

import jgame.math.Vec2;
import org.newdawn.slick.Graphics;

/**
 *
 * @author hector
 */
public interface Health
{
    public boolean isEmpty();
    
    public void damage(int points);
    public void heal(int points);
    
    public void setRenderCentered(boolean renderCentered);
    public void setRenderOver(boolean renderOver);
    public void render(Graphics g, Vec2 pos);
}
