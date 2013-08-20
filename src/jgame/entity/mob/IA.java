package jgame.entity.mob;

import jgame.entity.Mob;

/**
 *
 * @author Hector
 */
abstract public class IA
{
    protected Mob mob;
    
    public IA(Mob mob)
    {
        this.mob = mob;
    }
    
    abstract public void update(int delta);
}
