package jgame.level.pathfinding;

import jgame.math.Vec2Int;

/**
 *
 * @author hector
 */
public class Node implements Comparable<Node>
{
    private Vec2Int subtile;
    private int cost;
    private Node parent;
    private int heuristicCost;
    
    public Node(Vec2Int subtile, int cost, Vec2Int end, Node parent)
    {
        this.subtile = subtile;
        this.cost = cost;
        this.parent = parent;
        
        calculateHeuristicCost(end);
    }
    
    public Node(Vec2Int subtile, int cost, Vec2Int end)
    {
        this(subtile, cost, end, null);
    }
    
    public Vec2Int getSubtile()
    {
        return subtile;
    }
    
    public int getCost()
    {
        return cost;
    }
    
    public Node getParent()
    {
        return parent;
    }
    
    private void calculateHeuristicCost(Vec2Int end)
    {
        heuristicCost = Math.abs(end.x - subtile.x) + Math.abs(end.y - subtile.y);
    }

    @Override
    public int compareTo(Node o)
    {
        if(heuristicCost < o.heuristicCost)
            return -1;
        
        return 1;
    }
}
