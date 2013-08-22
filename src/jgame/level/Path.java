package jgame.level;

import java.util.ArrayList;
import java.util.List;
import jgame.Debug;
import jgame.math.Vec2Int;
import jgame.utils.Dir;
import org.newdawn.slick.Color;

/**
 *
 * @author hector
 */
public class Path
{
    private Vec2Int origin;
    private List<Vec2Int> steps;
    
    public Path(Vec2Int origin)
    {
        this.origin = origin;
        this.steps = new ArrayList();
    }
    
    public boolean isEmpty()
    {
        return steps.isEmpty();
    }
    
    public Dir[] getFirstStepDirection()
    {
        Vec2Int dirVector = steps.get(0).sub(origin);
        
        if(dirVector.x == 0 || dirVector.y == 0)
            return new Dir[]{ Dir.fromVector(dirVector) };
        
        Dir[] directions = new Dir[2];
        int x = dirVector.x;
        
        dirVector.x = 0;
        directions[0] = Dir.fromVector(dirVector);
        
        dirVector.x = x;
        dirVector.y = 0;
        directions[1] = Dir.fromVector(dirVector);
        
        return directions;
    }
    
    public void addStep(Vec2Int step)
    {
        steps.add(step);
        Debug.highlight(Color.green, step);
    }
}
