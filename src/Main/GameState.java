/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import static Main.Game.WIDTH;
import Rooms.Room;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author User
 */
public class GameState {
    
    private static BackgroundTexture TEXTURE;
    private static List<Room> ROOMS = new ArrayList<>();
    private static List<OverworldObj> objects = new ArrayList<>();
    
    private static GameState GAME_STATE = new GameState();
    private static Room CURRENT_ROOM;
    private static GameCamera CAMERA = new GameCamera(0, 0);
    private static Player player;
    private static Window WINDOW;
    private static int ORIGIN_WIDTH;
    private static int ORIGIN_HEIGHT;
    private static int WIDTH;
    private static int HEIGHT;
    private static double SCALE = 1.0000000000000000;
    private static double TEMP = HEIGHT;
    private static boolean BY_HEIGHT = true;
    private static int BACKGROUND_OFFSET_X;
    private static int BACKGROUND_OFFSET_Y;
    private static int noBorderWidth = 0;
    private static int noBorderHeight = 0;
    
    public static Window getWindow(){
        return WINDOW;
    }
    
    public static void initWindow(String name, int width, int height, Game game){
        WINDOW = new Window(name, width, height, game);
    }
    
    public static Room getCurrentRoom(){
        return GameState.CURRENT_ROOM;
    }
    
    public static void loadGame(Scanner scan, Game game){
        
        String line = scan.nextLine();
        String s = line.substring(line.indexOf(":") + 2, line.length());
        line = scan.nextLine();
        String w = line.substring(line.indexOf(":") + 2, line.indexOf("x") - 1);
        String h = line.substring(line.indexOf("x") + 2, line.length());
        setOriginWidth((int) Integer.valueOf(w));
        setOriginHeight((int) Integer.valueOf(h));
        setWidth(getOriginWidth());
        setHeight(getOriginHeight());
        initWindow(s, WIDTH, HEIGHT, game);
        line = scan.nextLine();
        if("Player:".equals(scan.nextLine())){
            String name = scan.nextLine();
            line = scan.nextLine();
            String s1 = line.substring(line.indexOf("(") + 1, line.indexOf(","));
            String s2 = line.substring(line.indexOf(",") + 2, line.length() - 1);
            line = scan.nextLine();
            s = line.substring(line.indexOf(":") + 2, line.length());
            player = new Player(Integer.valueOf(s1), Integer.valueOf(s2), s);
            //spawnPlayer(400,400);
            setCameraTarget(player);
            Handler.instance().setCamera(CAMERA);
        }
        line = scan.nextLine();
        if("Rooms:".equals(scan.nextLine())){
            line = scan.nextLine();
            while(!scan.hasNext("===")){
                String name = scan.nextLine();
                line = scan.nextLine();
                String s1 = line.substring(line.indexOf("(") + 1, line.indexOf(","));
                String s2 = line.substring(line.indexOf(",") + 2, line.length() - 1);
                
                s = scan.nextLine();
                String link = scan.nextLine();
                link = link.substring(link.indexOf(":") + 2, link.length());
                Room room = new Room(name, player, new BackgroundTexture(Integer.valueOf(s1), Integer.valueOf(s2), s, link));
                addRoom(room);
                line = scan.nextLine();
                if(scan.nextLine().equals("&&&")){
                    while(!scan.hasNext("&&&")){
                        name = scan.nextLine();
                        line = scan.nextLine();
                        s1 = line.substring(line.indexOf("(") + 1, line.indexOf(","));
                        s2 = line.substring(line.indexOf(",") + 2, line.length() - 1);
                        s = scan.nextLine();
                        s = s.substring(s.indexOf(":") + 2, s.length());
                        OverworldObj obj = new Obstacle(Integer.valueOf(s1), Integer.valueOf(s2), name, s);
                        room.addObject(obj);
                        line = scan.nextLine();
                    }
                }
                //room.init();
                line = scan.nextLine();
                //line = scan.nextLine();
            }
        }
    }
    
    public static void setWidth(int width){
        WIDTH = width;
    }
    
    public static void setHeight(int height){
        HEIGHT = height;
    }
    
    public static void addRoom(Room room){
        ROOMS.add(room);
    }
    
    public static List getROOMS(){
        return ROOMS;
    }
    
    public static void setCurrentRoom(Room room){
        GameState.CURRENT_ROOM = room;
    }
    
    public static void setTexture(BackgroundTexture texture){
        GameState.TEXTURE = texture;
    }
    
    public static BackgroundTexture getTexture(){
        return TEXTURE;
    }
    
    public static GameState instance(){
        return GAME_STATE;
    }
    
    public static GameCamera getCamera(){
        return CAMERA;
    }
    
    public static void setCameraTarget(GameObj obj){
        CAMERA.setTarget(obj);
    }
    
    public static void spawnPlayer(int x, int y){
        player.setX((int)(x * getScale()));
        player.setY((int)(y * getScale()));
        player.setUnscaledX(x);
        player.setUnscaledY(y);
    } 
    
    public static void init(String roomName){
        if(player == null){
            player = new Player(GameState.getWidth(), GameState.getHeight(), "../res/louder.png");
            setCameraTarget(player);
            Handler.instance().setCamera(CAMERA);
        }
        
        setCurrentRoom(getRoom(roomName));
    }
    
    public static void init(){
        if(player == null){
            player = new Player(GameState.getWidth(), GameState.getHeight(), "../res/louder.png");
            setCameraTarget(player);
            Handler.instance().setCamera(CAMERA);
        }
        
        setCurrentRoom(getRoom("Room1"));
    }
    
    public static Room getRoom(String name){
        for(Room room : ROOMS){
            if(room.getName().equals(name)){
                return room;
            }
        }
        return null;
    }
    
    public static void populateRoom(){
        
        setTexture(getCurrentRoom().getTexture());
        Handler.instance().addObj(TEXTURE);
        Handler.instance().addObj(player);
        Handler.instance().addOverObj(player);
        Handler.instance().addObj(CAMERA);
        for(int i = 0; i < getCurrentRoom().getObjSize(); i++){
            Handler.instance().addObj(getCurrentRoom().getObject(i));
            
            //Handler.instance().addOverObj(obj);
        }
        
        getCurrentRoom().init();
        Handler.setScaledLocation();
        Handler.instance().setBackgroundWidth();
        Handler.instance().setBackgroundHeight();
        //Handler.changeScaled(true);
        //Handler.setScaledLocation();
        Handler.instance().init();
    }
    
    public static void depopulateRoom(){
        for(int i = 0; i < getCurrentRoom().getObjSize(); i++){
            Handler.instance().removeObj(getCurrentRoom().getObject(i));
            
            //Handler.instance().addOverObj(obj);
        }
        Handler.instance().removeObj(TEXTURE);
        Handler.instance().removeObj(player);
        Handler.instance().removeObj(CAMERA);
        Handler.instance().removeOverObj(player);
        getCurrentRoom().depopulate();
    }
    
    public static void addObject(OverworldObj obj){
        objects.add(obj);
    }
    
    public static Player getPlayer(){
        return player;
    }
    
    public static void setPlayer(Player player){
        GameState.player = player;
    }

    /**
     * @return the ORIGIN_WIDTH
     */
    public static int getOriginWidth() {
        return ORIGIN_WIDTH;
    }

    /**
     * @param aORIGIN_WIDTH the ORIGIN_WIDTH to set
     */
    public static void setOriginWidth(int aORIGIN_WIDTH) {
        ORIGIN_WIDTH = aORIGIN_WIDTH;
    }

    /**
     * @return the ORIGIN_HEIGHT
     */
    public static int getOriginHeight() {
        return ORIGIN_HEIGHT;
    }

    /**
     * @param aORIGIN_HEIGHT the ORIGIN_HEIGHT to set
     */
    public static void setOriginHeight(int aORIGIN_HEIGHT) {
        ORIGIN_HEIGHT = aORIGIN_HEIGHT;
    }

    /**
     * @return the WIDTH
     */
    public static int getWidth() {
        return WIDTH;
    }

    /**
     * @return the HEIGHT
     */
    public static int getHeight() {
        return HEIGHT;
    }

    public static void calculateScale(boolean byHeight){
        //SCALE = SCALE + ((TEMP - PREV_TEMP)/PREV_TEMP);
        //PREV_TEMP = TEMP;
        if(byHeight){
            SCALE = 1 + (TEMP - ORIGIN_HEIGHT)/ORIGIN_HEIGHT;
        } else{
            SCALE = 1 + (TEMP - ORIGIN_WIDTH)/ORIGIN_WIDTH;
        }
        
        
        
    }
    
    public static void setScale(double scale){
        SCALE = scale;
    }
    public static void setTemp(double temp){
        TEMP = temp;
    }
    
    public static double getScale(){
        return SCALE;
    }
    
    public static void setByHeight(boolean byHeight){
        BY_HEIGHT = byHeight;
    }
    public static boolean getByHeight(){
        return BY_HEIGHT;
    }
    
    static int getOffsetX() {
        return BACKGROUND_OFFSET_X;
    }
    
    static int getOffsetY(){
        return BACKGROUND_OFFSET_Y;
    }
    
    static void setOffset(int x, int y){
        GameState.BACKGROUND_OFFSET_X = x;
        GameState.BACKGROUND_OFFSET_Y = y;
    }
    public static void setNoBorderWidth(){
        noBorderWidth = Window.getFrame().getContentPane().getWidth();
    }
    public static void setNoBorderHeight(){
        noBorderHeight = Window.getFrame().getContentPane().getHeight();
    }
    public static int getNoBorderWidth(){
        return noBorderWidth;
    }
    public static int getNoBorderHeight(){
        return noBorderHeight;
    }
    
}
