/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgame.entity.action.link;

import jgame.entity.Action;
import jgame.entity.Dir;
import jgame.entity.Player;
import jgame.level.Level;
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
    private static final Vec2       ENTITY_POS      =   new Vec2(0, 0);
    private static final Vec2Int    DIM             =   new Vec2Int(21, 27);
    private static final int[]      SPRITE_COUNT    =   {7, 7, 7, 7};
    private static final int[]      SPRITE_SPEED    =   {50, 50, 50, 50};
    private static final int[]      SPRITE_STAND    =   {3, 3, 3, 3};
    
    private static final int[]      KEYS            =   {Input.KEY_DOWN, Input.KEY_UP, Input.KEY_RIGHT,
                                                        Input.KEY_LEFT};
    private static final Dir[]      KEY_DIR         =   {Dir.DOWN, Dir.UP, Dir.RIGHT, Dir.LEFT};
    
    private boolean movingBefore;
    
    public Move() throws SlickException
    {
        super(NAME, ENTITY, ENTITY_POS, DIM, SPRITE_COUNT, SPRITE_SPEED, SPRITE_STAND);
        
        // UP and DOWN animations are ping pong animations!
        // This means that the animation goes back and forth.
        // If each number represents an sprite (3 standing sprite):
        // 3 4 5 6 5 4 3 2 1 0 1 2 3 ...
        anims[Dir.UP.getValue()].setPingPong(true);
        anims[Dir.DOWN.getValue()].setPingPong(true);
    }
    
    @Override
    public void transition(Player player)
    {
        // Transitions here!
    }
    
    @Override
    public void update(Player player, Level level, int delta)
    {
        Vec2 pos;
        boolean moving = false;
        boolean blocked = (player.hasKeyDown(KEYS[player.getFacing().getValue()]));
        int i = 0;
        
        for(int key : KEYS)
        {
            if(player.hasKeyDown(key))
            {
                pos = player.getPos();
                Vec2 vecDir = KEY_DIR[i].getVector().mul(new Vec2(0.1 * delta, 0.1 * delta));
                pos = pos.add(vecDir);
                
                tryToMove(player, level, pos);
                
                moving = true;
                
                if(! blocked)
                {
                    player.setFacing(KEY_DIR[i]);
                    blocked = true;
                }
            }
            
            i++;
        }
        
        movingBefore = moving;
        setAnim(player.getFacing().getValue());
        
        if(moving)
            updateAnim(delta);
        else
            standByAnim();
    }
    
    private void tryToMove(Player player, Level level, Vec2 pos)
    {
        Vec2 topLeft = pos.add(new Vec2(4, 13.5));
        Vec2 bottomRight = pos.add(new Vec2(17.0, 27.0));

        if (! level.areTilesBlocked(topLeft, bottomRight))
            player.setPos(pos.x, pos.y);
    }
}
