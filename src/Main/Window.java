package Main;


import EventHandledClasses.WindowChange;
import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Chicken
 */
public class Window extends Canvas{
    
    private static JFrame frame;
    
    /**
     * Creates a window
     * @param title Window title
     * @param height Window height
     * @param width Window width
     * @param game Game, for adding to the window.
     */
    public Window(String title, int height, int width, Game game){
        frame = new JFrame();
        WindowChange changer = new WindowChange(frame);
        frame.setPreferredSize(new Dimension(height, width));
        frame.add(changer);
        frame.setMinimumSize(new Dimension(height, width));
        frame.setMaximumSize(new Dimension(height, width));
        frame.setLocationRelativeTo(null);
        frame.add(game);
        frame.setVisible(true);
        frame.setResizable(true);
        frame.setName(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        game.start();
    }
    
    public static JFrame getFrame(){
        return frame;
    }
    
}
