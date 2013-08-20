package jgame.entity.mob;

import jgame.entity.Mob;

/**
 *
 * @author Hector
 */
abstract public class AI
{
    protected Mob mob;
    
    public AI(Mob mob)
    {
        this.mob = mob;
    }
    
    abstract public void update(int delta);
}
