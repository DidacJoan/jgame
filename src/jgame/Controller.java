package jgame;

import java.util.HashMap;
import java.util.Map;
import org.newdawn.slick.Input;

/**
 *
 * @author hector
 */
public class Controller
{
    private Input input;
    private Map<Integer, Boolean> toggledKeys;
    
    public Controller(Input input)
    {
        this.input = input;
        toggledKeys = new HashMap();
    }
    
    public boolean isKeyDown(int keyCode)
    {
        return input.isKeyDown(keyCode);
    }
    
    public boolean isKeyUp(int keyCode)
    {
        if(!toggledKeys.containsKey(keyCode) || !toggledKeys.get(keyCode))
        {
            toggledKeys.put(keyCode, isKeyDown(keyCode));
            return false;
        }
        
        if(isKeyDown(keyCode))
            return false;
        
        toggledKeys.put(keyCode, false);
        return true;
    }
}
