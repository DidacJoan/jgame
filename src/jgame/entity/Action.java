/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgame.entity;

import jgame.level.Level;
import jgame.math.Vec2;
import jgame.math.Vec2Int;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Represents an action that an entity can perform.
 * @author hector
 */
abstract public class Action
{
    private Vec2 entityPos;
    private int[] spriteStand;

    protected Animation anims[];
    protected int animCurrent;
    
    public Action(String name, String entity, Vec2 entityPos, Vec2Int dim, int[] spriteCount,
            int[] spriteSpeed, int[] spriteStand) throws SlickException
    {
        SpriteSheet ss = new SpriteSheet("res/charsets/" + entity + "/" + name + ".png", dim.x, dim.y);
        
        anims = new Animation[spriteCount.length];
        
        for(Dir dir : Dir.values())
        {
            int dirValue = dir.getValue();
            anims[dirValue] = new Animation(ss, 0, dirValue, spriteCount[dirValue]-1, dirValue, true,
                    spriteSpeed[dirValue], false);
        }
        
        this.spriteStand = spriteStand;
        this.entityPos = entityPos;
        standByAnim();
    }
    
    protected void setAnim(int index)
    {
        animCurrent = index;
    }
    
    protected final void standByAnim()
    {
        anims[animCurrent].setCurrentFrame(spriteStand[animCurrent]);
    }
    
    protected void updateAnim(int delta)
    {
        anims[animCurrent].update(delta);
    }
    
    public void render(Vec2 pos)
    {
        anims[animCurrent].draw((float)(pos.x + entityPos.x), (float)(pos.y + entityPos.y));
    }
    
    abstract public void transition(Player player);
    abstract public void update(Player player, Level level, int delta);
}