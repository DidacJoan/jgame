package jgame.level.pathfinding;

import java.util.PriorityQueue;
import java.util.Set;
import jgame.Entity;
import jgame.Level;
import jgame.level.Path;
import jgame.math.Vec2Int;
import org.newdawn.slick.Color;

/**
 *
 * @author hector
 */
public class Pathfinder
{
    private Level level;
    private Node[][] nodes;
    
    public Pathfinder(Level level)
    {
        this.level = level;
    }
    
    public Path getPath(Entity from, Entity to)
    {
        nodes = new Node[level.getVerticalSubtiles()][level.getHorizontalSubtiles()];
        PriorityQueue<Node> pending = new PriorityQueue();
        Vec2Int origin = level.getSubtile(from.getPos());
        Vec2Int destination = level.getSubtile(to.getCenter());
        
        Node start = new Node(origin, 0, destination);
        
        nodes[origin.y][origin.x] = start;
        pending.add(start);
        
        while(!pending.isEmpty())
        {
            Node current = pending.poll();
            level.highlight(Color.darkGray, current.getSubtile());
            
            Set<Entity> currentEntities = level.getEntitiesCollidedBy(from, current.getSubtile());
            
            if(currentEntities.contains(to))
                return buildPath(start, current); // build and return found path
            
            for(Vec2Int neighbor : level.getNeighbors(current.getSubtile()))
            {
                if(level.areTilesBlocked(from, neighbor))
                    continue;
                
                currentEntities = level.getEntitiesCollidedBy(from, neighbor);
                
                if(!currentEntities.isEmpty() && !currentEntities.contains(to))
                    continue;
                
                int newCost = current.getCost() + 1;
                
                Node prevNode = nodes[neighbor.y][neighbor.x];
                
                if(prevNode == null || newCost < prevNode.getCost())
                {
                    if(prevNode != null)
                        pending.remove(prevNode);
                    
                    Node newNode = new Node(neighbor, newCost, destination, current);
                    
                    nodes[neighbor.y][neighbor.x] = newNode;
                    pending.add(newNode);
                }
            }
        }
        
        
        return new Path(origin); // return an empty path
    }
    
    private Path buildPath(Node start, Node last)
    {
        Path path = new Path(start.getSubtile());
        
        buildPath(path, last);
        
        return path;
    }
    
    private void buildPath(Path path, Node next)
    {
        Node parent = next.getParent();
        
        if(parent != null)
        {
            buildPath(path, parent);
            path.addStep(next.getSubtile());
        }
    }
}
