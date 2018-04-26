package Main;


import EventHandledClasses.WindowChange;
import Rooms.Room;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jon Erickson, Andrew Thompson
 */
public class Game extends Canvas implements Runnable{
        
    
    private boolean running = false;
    private Thread thread;
    static boolean clampedSide = false;
    static boolean clampedUpdown = false;
    static int BACKGROUND_OFFSET_X;
    static int BACKGROUND_OFFSET_Y;
    private static BackgroundTexture texture;
    KeyInput input;    
    private static volatile int initiated = 0;
    private static String file = "src/res/test.txt";
    private static volatile int sleeper = 0;
    
    public Game() throws FileNotFoundException{

        //Create reader
        Scanner scan = new Scanner(new BufferedReader(new FileReader(file)));
        
        //Load game data from file
        GameState.loadGame(scan, this);
        
        //Initialize game data from file
        GameState.init("Room1");
        
        //Create key input and add to listener
        input = new KeyInput(Handler.instance());
        this.addKeyListener(input);

        //Add objects to handler for graphics
        GameState.populateRoom();
        
        //Set to initialized so the game can begin
        initiated();
    }
    
    /**
     * Begins thread
     */
    public synchronized void start(){
        running = true;
        thread = new Thread(this);
        thread.start();
    }
    
    /**
     * Ends thread
     */
    public synchronized void stop(){
        try{
            thread.join();
            running = false;
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    /**
     * Main method
     * @param args Command line argument, unnecessary
     */
    public static void main(String[] args){
        try{
            Game game = new Game();
        }
        catch(FileNotFoundException e){
            System.out.println("Game instantiation didn't work");
        }
        
    }
    
    /**
     * Sets initiated to 1 to break from while loop
     */
    public static void initiated(){
        initiated = 1;
    }
    
    /**
     * Runs the program, does not exit this while loop until program is finished
     */
    @Override
    public void run() {
        while(initiated == 0){
            //do nothing until assets are loaded
        }
        double lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        double timer = System.currentTimeMillis();
        int frames = 0;
        
        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            while(delta >= 1){
                tick();
                delta--;
            }
            lastTime = System.nanoTime();
            if(running){
                render();
                
            }
            frames++;
            if(System.currentTimeMillis() - timer > 100){
                if(GameState.getTexture() != (null)){
                    GameState.getCurrentRoom().getTexture().setWidth((int)(GameState.getTexture().getOriginWidth() * GameState.getScale()));
                    GameState.getCurrentRoom().getTexture().setHeight((int)(GameState.getTexture().getOriginHeight() * GameState.getScale()));
                    GameState.instance().getPlayer().setWidth((int)(GameState.instance().getPlayer().getOriginWidth() * GameState.getScale()));
                    GameState.instance().getPlayer().setHeight((int)(GameState.instance().getPlayer().getOriginHeight() * GameState.getScale()));
                }
            }
            input.scaleWindow();
            input.moveCharacter();
            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                /*
                System.out.println("FPS: " + frames);
                System.out.println("SCALE: " + SCALE);
                System.out.println("PLAYER POSITION: " + player.getUnscaledX() + ", " + player.getUnscaledY());
                System.out.println("PLAYER ACTUAL POSITION: " + player.getPosX() + ", " + player.getPosY());
                System.out.println("CAMERA POSITION: " + camera.getUnscaledX() + ", " + camera.getUnscaledY());
                System.out.println("TEXTURE POSITION: " + texture.getUnscaledX() + ", " + texture.getUnscaledY());
                System.out.println("PLAYER WIDTH AND HEIGHT: " + player.getWidth() + ", " + player.getHeight());
                System.out.println("TEXTURE WIDTH AND HEIGHT: " + texture.getWidth() + ", " + texture.getHeight());
                */
                frames = 0;
            }
            
            //Limit frame rate
            try {
                Thread.sleep(16); //Pause thread for x milliseconds. May need to adjust this value later.
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            //Does something, can't remember lol
            if(initiated == 0){
                try{
                    Thread.sleep(sleeper);
                    initiated = 1;
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
        stop();
    }

    /**
     * Calls the tick command for updating objects
     */
    public void tick(){
        Handler.instance().tick();
    }
    
    /**
     * Renders the graphics associated with the objects
     */
    public void render(){
        BufferStrategy bs = getBufferStrategy();
        if(bs == null){
            createBufferStrategy(3);
            requestFocus();
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        //g.drawImage(bi, 0, 0, null);
        Handler.instance().render(g, WIDTH, HEIGHT);
        g.dispose();
        bs.show();
    }  
    
    /**
     * To be moved to GameState class
     * @param var
     * @param min
     * @param max
     * @return 
     */
    public static int clamp(int var, int min, int max){
        
        if(var >= max){
            return var = max;
        }
        else if(var <= min){
            return var = min;
        }
        else{
            return var;
        }
        
    }
    
    /**
     * To be moved to GameState class.
     * @param var
     * @param min
     * @param max
     * @return 
     */
    public static int reverseBackgroundClamp(int var, int min, int max){
        
        if(var >= min){
            return var = min;
        }
        else if(var <= -max){
            return var = -max;
        }
        else{
            return var;
        }        
    }
    
    /**
     * To be moved to GameState class.
     * @return 
     */
    public static boolean isClampedSide(){
        return clampedSide;
    }
    
    /**
     * To be moved to GameState class.
     * @return whether clamp is in effect
     */
    public static boolean isClampedUpdown(){
        return clampedUpdown;
    }
}
