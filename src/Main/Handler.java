package Main;


import Rooms.Room;
import java.awt.Graphics;
import java.util.LinkedList;
import java.awt.image.BufferedImage;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Chicken
 */
public class Handler {
    
    public static List<GameObj> objects = new LinkedList<>();
    public static List<OverworldObj> items = new LinkedList<>();
    private GameCamera camera;
    private boolean initialized = false;
    private static int BACKGROUND_HEIGHT = 0;
    private static int BACKGROUND_WIDTH = 0;
    private boolean freeCam = true;
    private boolean done = true;
    private boolean largerX = false;
    private boolean largerY = false;
    private static boolean changedScale = false;
    private static boolean CHANGE = false;
    
    private static Handler HANDLER = new Handler();
    
    public static Handler instance(){
        return HANDLER;
    }
        
    public void init() {
        for (GameObj tempObj : objects) {
            if(tempObj == null){
                break;
            }
            
            tempObj.init();
            if(tempObj.getID() == ID.Background){
                //System.out.println(tempObj.getHeight());
                BACKGROUND_HEIGHT = (int)(tempObj.getHeight() * GameState.getScale());
                BACKGROUND_WIDTH = (int)(tempObj.getWidth() * GameState.getScale());
            }
            if(tempObj.getID() == ID.Obstacle){
                //System.out.println(tempObj.getWidth() + ", " + tempObj.getHeight());
                //System.out.println(GameState.getWidth() + ", " + GameState.getHeight());
                if(tempObj.getHor() == true){
                    tempObj.setY(GameState.getNoBorderHeight() - tempObj.getHeight());
                }
                else if(tempObj.getHor() == false){
                    tempObj.setX(GameState.getNoBorderWidth() - tempObj.getWidth());
                }
            }
        }
        if(BACKGROUND_WIDTH <= GameState.getWidth()){
            largerX = true;
        }
        else{
            largerX = false;
        }
        if(BACKGROUND_HEIGHT <= GameState.getHeight() - 18){
            largerY = true;
        }
        else{
            largerY = false;
        }
        //System.out.println("LargerX: " + largerX + "\nLargerY: " + largerY);
        
        initialized = true;
    }

    public void tick(){
        if (!initialized) {
            return;
        }

        for (GameObj tempObj : objects) {
            if(tempObj == null){
                break;
            }
            tempObj.tick();
        }
    }
    
    public void render(Graphics g, int width, int height){
        int count = 1;
        for(OverworldObj tempObj : items){
            if(CHANGE == true){
                setChange(false);
                break;
            }
        }
        
        for (GameObj tempObj : objects) {
            //System.out.println(tempObj.getName());
            if(tempObj == null){
                break;
            }
            if(tempObj.getID() == ID.Player){
                if(freeCam = true){
                    this.camera.setFollowing(true, true);
                }
                //System.out.println(BACKGROUND_WIDTH);
                //System.out.println(tempObj.getPosX());



                if(largerX == false && largerY == false){

                    //System.out.println("Game width/height: " + GameState.getOriginWidth() + ", " + GameState.getOriginHeight());
                    //System.out.println("Background width/height: " + BACKGROUND_WIDTH + ", " + BACKGROUND_HEIGHT);
                    //System.out.println("Sprite width/height: " + camera.getTarget().getWidth() + ", " + camera.getTarget().getHeight());
                    //System.out.println("Player Location: " + camera.getTarget().getPosX() + ", " + camera.getTarget().getPosY());
                    //System.out.println("Player Unscaled Location: " + camera.getTarget().getUnscaledX() + ", " + camera.getTarget().getUnscaledY());
                    //System.out.println("Camera Location: " + camera.getPosX() + ", " + camera.getPosY());
                    //System.out.println("Scale: " + GameState.getScale());
                    //System.out.println("No Border Width/Height: " + GameState.getNoBorderWidth() + ", " + Game.getNoBorderHeight());
                    //System.out.println("tempObj.getPosX() + tempObj.getWidth()/2: " + tempObj.getName() + ": " + (tempObj.getPosX() + tempObj.getWidth()/2));
                    //System.out.println("Game.getTexture().getSprite().getWidth() - Game.getNoBorderHeight() + Game.BACKGROUND_OFFSET_X: " + (GameState.getTexture().getSprite().getWidth() - GameState.getFrameWidth()/2 + Game.BACKGROUND_OFFSET_X));
                    
                    if(tempObj.getUnscaledX()<= GameState.getOriginWidth()/2 - camera.getTarget().getOriginWidth()/2){
                        this.camera.setFollowX(false);
                        this.camera.setX(0);
                        done = false;
                        freeCam = false;
                    } if(tempObj.getUnscaledY() <= GameState.getOriginHeight()/2 - camera.getTarget().getOriginHeight()/2){
                        this.camera.setFollowY(false);
                        this.camera.setY(0);
                        done = false;
                        freeCam = false;
                    } if(tempObj.getPosX() + tempObj.getWidth()/2 - 9 >= BACKGROUND_WIDTH - GameState.getNoBorderWidth()/2 + GameState.getOffsetX()){
                        this.camera.setFollowX(false);
                        this.camera.setX(BACKGROUND_WIDTH - GameState.getNoBorderWidth());
                        done = false;
                        freeCam = false;

                    } if(tempObj.getPosY() + tempObj.getHeight()/2 - 18 >= BACKGROUND_HEIGHT - GameState.getNoBorderHeight()/2 + GameState.getOffsetY()){
                        this.camera.setFollowY(false);
                        this.camera.setY(BACKGROUND_HEIGHT - GameState.getNoBorderHeight());
                        done = false;
                        freeCam = false;
                    } 
                    if(done = true){
                        freeCam = true;
                    }
                    done = true;

                } else if(largerX == true && largerY == false){
                    this.camera.setX(BACKGROUND_WIDTH/2 - Game.WIDTH/2);
                    if(freeCam = true){
                        this.camera.setFollowing(false, true);
                    }
                    if(tempObj.getPosY() <= Game.HEIGHT/2 + Game.BACKGROUND_OFFSET_Y - camera.getTarget().getHeight()/2){
                        this.camera.setFollowY(false);
                        this.camera.setY(0);
                        done = false;
                        freeCam = false;
                    }
                    if(tempObj.getPosY() >= BACKGROUND_HEIGHT - Game.HEIGHT/2 + Game.BACKGROUND_OFFSET_Y){
                        this.camera.setFollowY(false);
                        this.camera.setY(BACKGROUND_HEIGHT - Game.HEIGHT + camera.getTarget().getHeight()/2);
                        done = false;
                        freeCam = false;
                    }
                    if(done = true){
                        freeCam = true;
                    }
                    done = true;
                } else if(largerY == true && largerX == false){
                    this.camera.setY(BACKGROUND_HEIGHT/2 - Game.HEIGHT/2);
                    if(freeCam = true){
                        this.camera.setFollowing(true, false);
                    }
                    if(tempObj.getPosX() <= Game.WIDTH/2 + Game.BACKGROUND_OFFSET_X - (camera.getTarget().getWidth()*GameState.getScale())/2){
                        this.camera.setFollowX(false);
                        this.camera.setX(0);
                        done = false;
                        freeCam = false;
                    }
                    if(done = true){
                        freeCam = true;
                    }
                    done = true;
                    if(tempObj.getPosX() >= BACKGROUND_WIDTH - Game.WIDTH/2 + Game.BACKGROUND_OFFSET_X - (camera.getTarget().getWidth()*GameState.getScale())/2){
                        this.camera.setFollowX(false);
                        this.camera.setX(BACKGROUND_WIDTH - Game.WIDTH);
                        done = false;
                        freeCam = false;

                    }
                } else if(largerX == true && largerY == true){
                    this.camera.setFollowY(false);
                    this.camera.setFollowX(false);
                    this.camera.setX(BACKGROUND_WIDTH/2 - Game.WIDTH/2);
                    this.camera.setY(BACKGROUND_HEIGHT/2 - Game.HEIGHT/2 + 18);
                }

            }
            BufferedImage tempSprite = tempObj.getSprite();
            if (tempSprite != null) {
                
                int xDelta = tempObj.getPosX();
                int yDelta = tempObj.getPosY();
                if(tempObj.getID() != ID.Obstacle){
                    xDelta = (int)((tempObj.getPosX() - this.camera.getPosX()));
                    yDelta = (int)((tempObj.getPosY() - this.camera.getPosY()));
                } else{
                    if(tempObj.getHor() == true){
                       
                        double dec = (double)(GameState.getTexture().getWidth() - camera.getTarget().getPosX())/GameState.getTexture().getWidth();
                        xDelta = (int)(dec * (double)GameState.getNoBorderWidth());
                    }
                    else if(tempObj.getHor() == false){
                        double dec = (double)(GameState.getTexture().getHeight() - camera.getTarget().getPosY())/GameState.getTexture().getHeight();
                        xDelta = (int)(dec * (double)GameState.getNoBorderHeight());
                    }
                    //System.out.println((double)(GameState.getTexture().getWidth() - camera.getTarget().getPosX())/GameState.getTexture().getWidth());
                }
                if(getChangeScaled() == true){
                    
                    if(tempObj.getID() == ID.Background){
                        
                        if(BACKGROUND_WIDTH <= Game.WIDTH){
                            largerX = true;
                        }
                        if(BACKGROUND_HEIGHT <= Game.HEIGHT - 18){
                            largerY = true;
                        }
                        if(BACKGROUND_WIDTH > Game.WIDTH){
                            largerX = false;
                        }
                        if(BACKGROUND_HEIGHT > Game.HEIGHT){
                            largerY = false;
                        }
                        //System.out.println(tempObj.getID());
                    }
                    /*
                    tempObj.setX((int)(tempObj.getUnscaledX() * Game.getScale()));
                    tempObj.setY((int)(tempObj.getUnscaledY() * Game.getScale()));
                    tempObj.setWidth((int)(tempObj.getOriginWidth() * Game.getScale()));
                    tempObj.setHeight((int)(tempObj.getOriginHeight() * Game.getScale()));
*/
                    //System.out.println(tempObj.getName() + ": " + tempObj.getHeight() + ", " + tempObj.getWidth());
                    //System.out.println(tempObj.getID() + ": " + tempObj.getPosX() + ", " + tempObj.getPosY());
                    count++;
                    if(objects.size() == count){
                        changeScaled(false);
                        count = 1;
                    }
                    //System.out.println(count);
                    //System.out.println(object.size());
                }
                
                tempObj.setActX(tempObj.getUnscaledX() + tempObj.getOriginWidth()/2);
                tempObj.setActY(tempObj.getUnscaledY() + tempObj.getOriginHeight()/2);
                //System.out.println(tempObj.getName() + ": (" + tempObj.getPosX() + ", " + tempObj.getPosY() + ")");
                g.drawImage(tempSprite, xDelta, yDelta, (int)(GameState.getScale() * tempObj.getOriginWidth()), (int)(GameState.getScale() * tempObj.getOriginHeight()), null);
            }
        }
    }
    
    public void addObj(GameObj obj){
        objects.add(obj);
    }
    
    public void removeObj(GameObj obj){
        objects.remove(obj);
    }
    
    public void addOverObj(OverworldObj obj){
        items.add(obj);
    }
    
    public void removeOverObj(OverworldObj obj){
        items.remove(obj);
    }
    
    public void setCamera(GameCamera camera) {
        this.camera = camera;
    }
    public static void changeScaled(boolean change){
        changedScale = change;
    }
    public boolean getChangeScaled(){
        return changedScale;
    }
    public static void setScaledLocation(){
        for(GameObj tempObj : objects){
            GameState.calculateScale(GameState.getByHeight());
            tempObj.setX((int)(tempObj.getUnscaledX() * GameState.getScale()));
            tempObj.setY((int)(tempObj.getUnscaledY() * GameState.getScale()));
            //System.out.println(tempObj.getName() + ": " + tempObj.getPosX() + ", " + tempObj.getPosY() + "/" + tempObj.getUnscaledX() + ", " + tempObj.getUnscaledY());
            tempObj.setWidth((int)(tempObj.getOriginWidth() * GameState.getScale()));
            tempObj.setHeight((int)(tempObj.getOriginHeight() * GameState.getScale()));
        }
    }
    public static void setBackgroundWidth(){
        //System.out.println("ORIGIN: " + BACKGROUND_HEIGHT);
        double d = GameState.getTexture().getOriginWidth() * GameState.getScale();
        //System.out.println("Interim: " + d);
        BACKGROUND_WIDTH = (int)d;
        GameState.getTexture().setWidth(BACKGROUND_WIDTH);
        //System.out.println("CHANGE: " + BACKGROUND_HEIGHT);
    }
    public static void setBackgroundHeight(){
        //System.out.println("ORIGIN: " + BACKGROUND_HEIGHT);
        double d = GameState.getTexture().getOriginHeight() * GameState.getScale();
        //System.out.println("Interim: " + d);
        BACKGROUND_HEIGHT = (int)d;
        GameState.getTexture().setHeight(BACKGROUND_HEIGHT);
        //System.out.println("CHANGE: " + BACKGROUND_HEIGHT);
    }
    
    public static void setChange(boolean bool){
        CHANGE = bool;
    }
}
