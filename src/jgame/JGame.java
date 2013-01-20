package jgame;

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

/**
 * JGame main class.
 * Based on Mojang's game: Catacomb Snatch. Thanks!
 * @author hector
 */
public class JGame extends Canvas implements Runnable
{
    
    public static final int GAME_WIDTH = 512;
    public static final int GAME_HEIGHT = GAME_WIDTH * 3 / 4;
    public static final int SCALE = 2;
    private boolean running = false;
    private int framerate = 60;
    private int fps;
    
    public JGame()
    {
        this.setPreferredSize(new Dimension(GAME_WIDTH * SCALE, GAME_HEIGHT * SCALE));
        this.setMinimumSize(new Dimension(GAME_WIDTH * SCALE, GAME_HEIGHT * SCALE));
        this.setMaximumSize(new Dimension(GAME_WIDTH * SCALE, GAME_HEIGHT * SCALE));
    }
    
    public void init()
    {
        
    }
    
    public void paint(Graphics g)
    {    }

    public void update(Graphics g)
    {    }
    
    public void start()
    {
        running = true;
        Thread thread = new Thread(this);
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();
    }
    
    public void stop()
    {
        running = false;
    }
    
    /**
     * Runs the game loop!
     * It ticks and renders the game properly depending on the framerate.
     */
    public void run()
    {
        long lastTime = System.nanoTime();
        double unprocessed = 0;
        int frames = 0;
        long lastTimer1 = System.currentTimeMillis();

        try
        {
            init();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return;
        }
        
        int toTick = 0;

        long lastRenderTime = System.nanoTime();
        int min = 999999999;
        int max = 0;
        
        // Game loop!
        while(running)
        {
            if(!this.hasFocus())
            {
                // keys.release();
            }

            double nsPerTick = 1000000000.0 / framerate;
            boolean shouldRender = false;
            while(unprocessed >= 1)
            {
                toTick++;
                unprocessed -= 1;
            }

            int tickCount = toTick;
            if(toTick > 0 && toTick < 3)
            {
                tickCount = 1;
            }
            
            if(toTick > 20)
            {
                toTick = 20;
            }

            for(int i = 0; i < tickCount; i++)
            {
                toTick--;
//                long before = System.nanoTime();
                tick();
//                long after = System.nanoTime();
//                System.out.println("Tick time took " + (after - before) * 100.0 / nsPerTick + "% of the max time");
                shouldRender = true;
            }

            BufferStrategy bs = getBufferStrategy();
            if(bs == null)
            {
                createBufferStrategy(3);
                continue;
            }
            
            if(shouldRender)
            {
                frames++;
                Graphics g = bs.getDrawGraphics();

                render(g);

                long renderTime = System.nanoTime();
                int timePassed = (int) (renderTime - lastRenderTime);
                
                if(timePassed < min)
                {
                    min = timePassed;
                }
                
                if(timePassed > max)
                {
                    max = timePassed;
                }
                
                lastRenderTime = renderTime;
            }

            long now = System.nanoTime();
            unprocessed += (now - lastTime) / nsPerTick;
            lastTime = now;

            try
            {
                Thread.sleep(1);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }

            if(shouldRender)
            {
                if(bs != null)
                {
                    bs.show();
                }
            }

            if(System.currentTimeMillis() - lastTimer1 > 1000)
            {
                lastTimer1 += 1000;
                fps = frames;
                frames = 0;
            }
        }
    }
    
    private synchronized void render(Graphics g)
    {
        // Rendering logic here!
    }
    
    private void tick()
    {
        // Game logic here!
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        JGame game = new JGame();
        JFrame frame = new JFrame();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(game);
        frame.setContentPane(panel);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        game.start();
    }
}
