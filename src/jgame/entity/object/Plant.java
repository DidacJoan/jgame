package jgame.entity.object;

import jgame.Entity;
import jgame.entity.MapObject;
import jgame.entity.MessageType;
import org.newdawn.slick.Image;

/**
 * A simple plant.
 * @author hector
 */
public class Plant extends MapObject
{
    public Plant(int width, int height, Image image)
    {
        super("plant", width, height, image);
    }
    
    @Override
    public void receive(MessageType msg, Entity from)
    {
        System.out.println(msg + " received");
        
        if(msg == MessageType.DAMAGE)
            kill();
    }
}
