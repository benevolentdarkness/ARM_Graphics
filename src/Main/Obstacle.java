/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Scanner;

/**
 *
 * @author User
 */
public class Obstacle extends OverworldObj{

    ImageLoader load = new ImageLoader();
    private String file;
    BufferedImage louder = null;
    
    
    public Obstacle(int x, int y, String name, String file) {
        super(x, y, ID.Obstacle, name);
        this.file = file;
        this.name = name;
    }
    
    public Obstacle(Scanner scan){
        super(scan);
    }

    @Override
    public void init() {
        if(this.louder == null){
            if(this.name.equals("sliderVer")){
                this.hor = false;
            } else{
                this.hor = true;
            }
            louder = load.loadImage(file);
            width = louder.getWidth();
            height = louder.getHeight();
            origin_width = width;
            origin_height = height;
            this.setSprite(louder);
            if(unscaledX == -1 || unscaledY == -1){
                unscaledX = x;
                unscaledY = y;
            }
        }
    }
    
    
    @Override
    public void tick() {
        
    }

    @Override
    public void render(Graphics g) {
        
    }
    
}
