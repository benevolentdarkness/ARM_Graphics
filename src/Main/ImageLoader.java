package Main;


import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Chicken
 */
public class ImageLoader {
    
    private BufferedImage img;
    
    public BufferedImage loadImage(String filePath){
        try {
            img = ImageIO.read(this.getClass().getResourceAsStream(filePath));
            return img;
        } 
        catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
            return null;
        }
    }
    
    public int getHeight(BufferedImage bi){
        return bi.getHeight();
    }
    
    public int getWidth(BufferedImage bi){
        return bi.getWidth();
    }
    
}
