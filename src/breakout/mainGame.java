//Ken Lee
//ICS4U
//June 1st, 2016
//Description: This program is a remake of the classic atari breakout game.
//This class creates the frame for the game.
package breakout;

import java.awt.*;
import javax.swing.*;
//Swing is what we will use for the frame.

public class mainGame{
    public static void main(String[] args){ //The main method
    JFrame frame = new JFrame("Breakout"); //The frame is created
    frame.setSize(500,500); 
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Board breakoutBoard = new Board(500,500); //The board is created.
    Container pane = frame.getContentPane();
    pane.add(breakoutBoard); //The board is added to the frame
    frame.setVisible(true);

    }
    
}
