package Main;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.util.ArrayList;
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
public class Player extends OverworldObj{
    
    ImageLoader load = new ImageLoader();
    ArrayList<BufferedImage> sprites = new ArrayList<BufferedImage>();
    private String link;

    public Player(int x, int y, String link) {
        super(x, y, ID.Player, "Player");
        this.link = link;
    }

    @Override
    public void init() {
        this.width = 1;
        this.height = 1;
        this.unscaledX = x;
        this.unscaledY = y;
        this.setSprite(null);
        if(unscaledX == -1 || unscaledY == -1){
            unscaledX = x;
            unscaledY = y;
        }
    }

    @Override
    public void tick() {
        x += (velX * GameState.getScale());
        y += (velY * GameState.getScale());
        unscaledX += velX;
        unscaledY += velY;
        if(x < 0){
            x = 0;
        }
        if(y < 0){
            y = 0;
        }
        if(x > GameState.getTexture().getWidth() - this.getWidth()){
            x = GameState.getTexture().getWidth() - this.getWidth();
        }
        if(y > GameState.getTexture().getHeight() - this.getHeight()){
            y = GameState.getTexture().getHeight() - this.getHeight();
        }
        /*
        if(this.getTriangle() == 1 && this.getCollided() == true){
            y -= (velY * Game.getScale());
            unscaledY -= velY;
            //System.out.println("1");
        }
        if(this.getTriangle() == 2 && this.getCollided() == true){
            x -= (velX * Game.getScale());
            unscaledX -= velX;
            //System.out.println("2");
        }
        if(this.getTriangle() == 3 && this.getCollided() == true){
            y -= (velY * Game.getScale());
            unscaledY -= velY;
            //System.out.println("3");
        }
        if(this.getTriangle() == 4 && this.getCollided() == true){
            x -= velX * Game.getScale();
            unscaledX -= velX;
            //System.out.println("4");
        }
*/
        //System.out.println(this.getHeight());
        //System.out.println(this.getWidth());
        //System.out.println(this.getPosX());
        //System.out.println(this.getPosY());
        //System.out.println(Game.getTexture().getHeight());
        //System.out.println(Game.getTexture().getWidth());
        if(unscaledX > GameState.getTexture().getOriginWidth() - this.getOriginWidth()){
            unscaledX = GameState.getTexture().getOriginWidth() - this.getOriginWidth();
        }
        if(unscaledY > GameState.getTexture().getOriginHeight() - this.getOriginHeight()){
            unscaledY = GameState.getTexture().getOriginHeight() - this.getOriginHeight();
        }
        if(unscaledX < 0){
            unscaledX = 0;
        }
        if(unscaledY < 0){
            unscaledY = 0;
        }
        //x = Game.clamp(x, Game.BACKGROUND_OFFSET_X, (int)((Game.CURRENTTEXTURE.sprite.getWidth() + Game.BACKGROUND_OFFSET_X - width)*Game.getScale()));
        //DSDdy = Game.clamp(y, Game.BACKGROUND_OFFSET_Y, (int)((Game.CURRENTTEXTURE.sprite.getHeight() + Game.BACKGROUND_OFFSET_Y - height)*Game.getScale()));
    }

    @Override
    public void render(Graphics g) {
    //    g.setColor(Color.red);
    //    g.drawRect(x, y, 32, 32);
    }
    
}
