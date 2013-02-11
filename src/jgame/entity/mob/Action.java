package jgame.entity.mob;

import jgame.entity.Dir;
import jgame.entity.Mob;
import jgame.level.EntityMap;
import jgame.level.TileMap;
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
    
    protected boolean updateAnim(int delta)
    {
        int frame = anims[animCurrent].getFrame();
        
        anims[animCurrent].update(delta);
        
        return frame != anims[animCurrent].getFrame();
    }
    
    public void render(Vec2 pos)
    {
        anims[animCurrent].draw(
                (float)(pos.x - entityPos[animCurrent].x),
                (float)(pos.y - entityPos[animCurrent].y)
        );
    }
    
    abstract public void transition(Mob mob);
    abstract public void update(Mob mob, TileMap map, EntityMap entities, int delta);
    
    public void enter(Mob mob)
    {   }
    
    public void leave(Mob mob)
    {   }
}
