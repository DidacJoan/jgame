package jgame.entity.mob.action;

import jgame.utils.IntegerEnum;

/**
 *
 * @author hector
 */
public enum LinkAction implements IntegerEnum
{
    MOVE, ATTACK_SWORD;
    public static final int size = LinkAction.values().length;
    
    private final int value;

    private LinkAction()
    {
        this.value = ordinal();
    }

    @Override
    public int getValue()
    {
        return value;
    }
}
