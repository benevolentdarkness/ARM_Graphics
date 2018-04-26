package Main;

import java.awt.Graphics;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *I am really fucking proud of this class. I feel very accomplished for having made this.
 * @author Chicken
 */
public class GameCamera extends GameObj {
    
    //THe target which the camera will follow, basically what is viewed.
    private GameObj target;
    
    //Determines if the camera is following on the X or Y coordinates.
    private boolean followX;
    private boolean followY;
    
    
    /**
     * Creates the camera at a specific location
     * @param x
     * @param y 
     */
    public GameCamera(int x, int y){
        super(x, y, ID.Camera, "Camera");
    }

    /**
     * Creates the camera following a specific target, and whether it's following the target when constructed.
     * @param target
     * @param following 
     */
    public GameCamera(GameObj target, boolean following) {
        super(0, 0, ID.Camera, "Camera");
        this.target = target;
        this.followX = following;
        this.followY = following;
        height = Game.HEIGHT;
        width = Game.WIDTH;
        origin_width = width;
        origin_height = height;
    }

    /**
     * Creates the camera following a specific target.
     * @param target 
     */
    public GameCamera(GameObj target) {
        this(target, true);
    }

    /**
     * Sets the location of the camera based on whether it's following the target.
     */
    @Override
    public void tick() {
        
        if (this.target != null && this.followX && this.followY) {
            this.setX(this.target.getPosX() - GameState.getWidth()/2 + this.target.getWidth()/2);
            this.setY(this.target.getPosY() - GameState.getHeight()/2 + this.target.getHeight()/2);
        }
        else if(this.target != null && this.followX && !this.followY){
            this.setY(y);
            this.setX(this.target.getPosX() - GameState.getWidth()/2 + this.target.getWidth()/2);
        }
        else if(this.target != null && !this.followX && this.followY){
            this.setX(x);
            this.setY(this.target.getPosY() - GameState.getHeight()/2 + this.target.getHeight()/2);
        }
        else{
            this.setX(x);
            this.setY(y);
        }
    }
    
    /**
     * 
     * @return target
     */
    public GameObj getTarget() {
        return this.target;
    }
    
    /**
     * Sets the following variables.
     * @param followX
     * @param followY 
     */
    public void setFollowing(boolean followX, boolean followY){
        this.followX = followX;
        this.followY = followY;
    }
    
    public void setFollowX(boolean x){
        this.followX = x;
    }
    
    public void setFollowY(boolean y){
        this.followY = y;
    }

    public void setTarget(GameObj target) {
        this.target = target;
    }

    @Override
    public void render(Graphics g){};
    
    @Override
    /**
     * Initializes the camera with no sprite
     */
    public void init(){ 
        this.setSprite(null);
    };
}
