package jgame.entity.mob.action.link;

import jgame.entity.mob.Action;
import jgame.entity.Dir;
import jgame.entity.Mob;
import jgame.entity.mob.action.LinkAction;
import jgame.level.EntityMap;
import jgame.level.TileMap;
import jgame.math.Vec2;
import jgame.math.Vec2Int;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * Action that Link performs when he moves.
 * @author hector
 */
public class Move extends Action
{
    private static final String     NAME            =   "move";
    private static final String     ENTITY          =   "link";
    private static final Vec2[]     ENTITY_POS      =   { new Vec2(), new Vec2(), new Vec2(), new Vec2() };
    private static final Vec2Int    DIM             =   new Vec2Int(21, 27);
    private static final int[]      SPRITE_COUNT    =   {7, 7, 7, 7};
    private static final int[]      SPRITE_SPEED    =   {50, 50, 50, 50};
    
    private static final int        SPRITE_STAND    =   3;
    private static final int[]      KEYS            =   {
                                                            Input.KEY_DOWN,
                                                            Input.KEY_UP,
                                                            Input.KEY_RIGHT,
                                                            Input.KEY_LEFT
                                                        };
    
    private static final Dir[]      KEY_DIR         =   {
                                                            Dir.DOWN,
                                                            Dir.UP,
                                                            Dir.RIGHT,
                                                            Dir.LEFT
                                                        };
    
    private boolean movingBefore;
    
    public Move(String name) throws SlickException
    {
        super(NAME + "_" + name, ENTITY, ENTITY_POS, DIM, SPRITE_COUNT, SPRITE_SPEED);
        
        // UP and DOWN animations are ping pong animations!
        // This means that the animation goes back and forth.
        // If each number represents an sprite (3 standing sprite):
        // 3 4 5 6 5 4 3 2 1 0 1 2 3 ...
        getAnim(Dir.UP.getValue()).setPingPong(true);
        getAnim(Dir.DOWN.getValue()).setPingPong(true);
    }
    
    @Override
    public void enter(Mob player)
    {
        int index = player.getFacing().getValue();
        
        setAnim(index);
        standByAnim();
    }
    
    @Override
    public void transition(Mob player)
    {
        if(player.hasKeyDown(Input.KEY_Z))
            player.changeAction(LinkAction.ATTACK_SWORD);
    }
    
    @Override
    public void update(Mob player, TileMap map, EntityMap entities, int delta)
    {
        Dir facing = player.getFacing();
        
        if(player.hasKeyDown(KEYS[facing.getValue()]))
            player.move(facing, map, entities, delta);
        
        int i = 0;
        
        for(int key : KEYS)
        {
            if(KEY_DIR[i] != facing && player.hasKeyDown(key))
                player.move(KEY_DIR[i], map, entities, delta);
            
            i++;
        }
        
        setAnim(player.getFacing().getValue());
        
        if(player.isMoving())
            updateAnim(delta);
        else
            standByAnim();
    }
    
    private void standByAnim()
    {
        getCurrentAnim().setCurrentFrame(SPRITE_STAND);
    }
}
