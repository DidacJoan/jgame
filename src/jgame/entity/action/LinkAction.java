/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgame.entity.action;

import jgame.utils.IntegerEnum;

/**
 *
 * @author hector
 */
public enum LinkAction implements IntegerEnum
{
    MOVE, ATTACK_SWORD;
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
