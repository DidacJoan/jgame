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
    private Vec2[] entityPos;

    private Animation anims[];
    private int animCurrent;
    
    public Action(String name, String entity, Vec2[] entityPos, Vec2Int dim, int[] spriteCount,
            int[] spriteSpeed) throws SlickException
    {
        SpriteSheet ss = new SpriteSheet("res/charsets/" + entity + "/" + name + ".png", dim.x, dim.y);
        
        anims = new Animation[spriteCount.length];
        
        for(Dir dir : Dir.values())
        {
            int dirValue = dir.getValue();
            anims[dirValue] = new Animation(ss, 0, dirValue, spriteCount[dirValue]-1, dirValue, true,
                    spriteSpeed[dirValue], false);
        }
        
        this.entityPos = entityPos;
    }
    
    protected void setAnim(int index)
    {
        animCurrent = index;
    }
    
    protected Animation getAnim(int index)
    {
        return anims[index];
    }
    
    protected Animation getCurrentAnim()
    {
        return anims[animCurrent];
    }
    
    protected void updateAnim(int delta)
    {
        anims[animCurrent].update(delta);
    }
    
    public void render(Vec2 pos)
    {
        anims[animCurrent].draw(
                (float)(pos.x - entityPos[animCurrent].x),
                (float)(pos.y - entityPos[animCurrent].y)
        );
    }
    
    abstract public void transition(Mob mob);
    abstract public void update(Mob mob, Level level, int delta);
    
    public void enter(Mob mob)
    {   }
    
    public void leave(Mob mob)
    {   }
}
