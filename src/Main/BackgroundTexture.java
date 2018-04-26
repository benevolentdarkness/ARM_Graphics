package Main;


import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileReader;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Chicken
 */
public class BackgroundTexture extends GameObj{

    //Class to load image
    private ImageLoader load = new ImageLoader();
    private String file;
    
    private boolean atSide = false;
    
    //The buffered image
    private BufferedImage bi;
    
    public BackgroundTexture(int x, int y, String name, String file) {
        super(x, y, ID.Background, name);
        this.file = file;
    }
    
    public BackgroundTexture(Scanner scan){
        super(scan);
    }

    /**
     * initializes background
     */
    @Override
    public void init() {
        if(bi == null){
            bi = load.loadImage(file);
            this.setSprite(bi); //Temp, probably.
            this.width = bi.getWidth();
            this.height = bi.getHeight();
            this.origin_width = width;
            this.origin_height = height;
        }
    }

    /**
     * Should be where background movement would be, should the background need to be moved.
     */
    @Override
    public void tick() {
        
    }

    /**
     * Never worked cuz Java Swing is fucking weird. Probably more efficient if fixed.
     * @param g 
     */
    @Override
    public void render(Graphics g) {
        //g.drawImage(this.getSprite(), x, y, (int)(width * Game.getScale()), (int)(height * Game.getScale()), null);
    }
}

