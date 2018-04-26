package Main;

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
public abstract class OverworldObj extends GameObj{
    
    public OverworldObj(int x, int y, ID id, String name) {
        super(x, y, id, name);
        overworld = true;
    }
    
    public OverworldObj(Scanner scan){
        super(scan);
    }
    
}
