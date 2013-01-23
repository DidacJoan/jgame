/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgame.entity;

/**
 * Represents the directions available in the game.
 * @author hector
 */
public enum Dir
{
    DOWN(0), UP(1), RIGHT(2), LEFT(3);
    
    private final int value;
    
    private Dir(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
};
