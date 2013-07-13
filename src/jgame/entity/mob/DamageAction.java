package jgame.entity.mob;

import jgame.entity.Mob;
import jgame.level.area.ImageArea;
import jgame.level.area.TileArea;
import jgame.math.Vec2;
import jgame.math.Vec2Int;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author Hector
 */
abstract public class DamageAction extends Action
{
    private SpriteSheet damageArea;
    
    public DamageAction(String name, Mob entity, Vec2[] entityPos, Vec2Int dim, int[] spriteCount, int[] spriteSpeed)
            throws SlickException
    {
        super(name, entity, entityPos, dim, spriteCount, spriteSpeed);
        
        damageArea = new SpriteSheet(Action.PATH_CHARSETS + mob.getName() + "/" + name + "_hit.gif", dim.x, dim.y);
    }
    
    public TileArea getCurrentDamageArea()
    {
        Image sprite = damageArea.getSubImage(getCurrentAnim().getFrame(), getCurrentIndex());
        TileArea area = new ImageArea(sprite, getPos());
        
        return area;
    }
}
