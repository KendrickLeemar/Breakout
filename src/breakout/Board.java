//The board is a panel where the game runs.
//This is where most of the code is located.
package breakout;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.File;
import java.io.*;
import java.io.IOException;

public class Board extends JPanel implements Runnable{
	Paddle paddle;
	Ball ball;
	Brick[][] brick = new Brick[10][5];
	//All the object classes are called. 
	int lives = 3, bricksLeft = 50, bricksBroken = 0;
	//The player will start with 3 lives, and every level will have 50 bricks
	//bricksBroken is the score. 
	Thread thread;
	int xSpeed; //Ball speed.
	int delay; //The slowness of the game.
	int rank; //Rank of the player
	long startTime, endTime;
	double timeTaken;
	//time will be taken in the beginning and the end for time taken.
        String name; //Name will be taken in the beginning of the game.
        ArrayList<Integer> highScore = new ArrayList<Integer>();
        //ArrayList to sort/rank scores.
        File file = new File("scores.txt");
        //However, to store scores from previous runs, a text file will store the scores.
        {
        	try{
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextInt()){
                        highScore.add(scanner.nextInt()); //Add the next integer to the arraylist.
                    }
        	} catch (IOException e){
        		System.out.println("File not found.");
        	}
        }
       
	public Board(int width, int height){ //Method to create the board.
		
		super.setSize(width, height);

		int y2 = 0;

		createBricks();
                name = JOptionPane.showInputDialog(null,"Please enter your name.","Name",JOptionPane.QUESTION_MESSAGE);
		paddle = new Paddle(width/2,height-(height/10),width/7,height/50,Color.BLACK);
		addMouseMotionListener(new PanelMotionListener());
		ball = new Ball(getWidth()/2,getHeight()/2,getWidth()/100,1,1,Color.BLUE);
		//Bricks, paddle, ball, and mouse motion tracker are added.

		delay = 7; //Game speed. It can be edited. 
		thread = new Thread(this); //Thread for execution path.
		thread.start();
		thread.suspend();
		addMouseListener(new PanelListener()); //Mouse click listener is added.
		
		startTime = System.currentTimeMillis();
		//Time is taken.
		

	}
	public void sort(){ //Selection sort to sort high score. 
		int temp;
		for (int i = 0; i<highScore.size()-1;i++){
			for (int j=i+1;j<highScore.size();j++){
				if (highScore.get(i)<highScore.get(j)){
				temp = highScore.get(i);
				highScore.set(i, highScore.get(j));
				highScore.set(j, temp);
			}
			}
		}
	}
	public int search(){ //Linear sequential search to find the rank of the score.
		for (int index = 0; index<highScore.size();index++){
			if (highScore.get(index)==bricksBroken){
				return (index+1); //When the score is equal to an element, the index + 1 is the rank.
			}
		}
		return 0;
	}

	public void start(){
		thread.resume();
	}

  	public void stop(){
		thread.suspend();
	}

  	public void destroy(){
		thread.resume();
		thread.stop();
	}

  	public void run(){
  		xSpeed = 1; //Ball speed
		while(true){
			int x1 = ball.getX(); //Get the position of the ball
			int y1 = ball.getY();

			//Moderates the speed, so it wont go too fast
			if (Math.abs(xSpeed) > 2){
				if(xSpeed > 2)
					xSpeed--;
				if(xSpeed < 2)
					xSpeed++;
			}

			checkPaddle(x1,y1);

			checkWall(x1,y1);

			checkBricks(x1,y1);
			//Check if it collided with paddle, wall, bricks.
			checkLives();
			//Check if user has no lives.
			checkIfOut(y1);
			//Check if the ball is out of bounds.
			ball.move();
			//Move the ball.
			repaint();
			try {thread.sleep(delay);}
			catch (InterruptedException e ) {}
		}
	}

	public void checkLives(){
		if(lives == 0){
			repaint(); 
			stop(); //Stop running.
		}
	}


	public void checkPaddle(int x1, int y1){ //Check if the ball hit the paddle
			if(paddle.hitLeft(x1,y1)){ //Use methods in the paddle class to check if it hit left or right.
				ball.setYDir(-2); //Adjust velocity accordingly.
				xSpeed += -1;
				ball.setXDir(xSpeed);
			}else if(paddle.hitRight(x1,y1)){
				ball.setYDir(-2);
				xSpeed += 1;
				ball.setXDir(xSpeed);
			}
	}

	public void checkWall(int x1, int y1){//Check if it hit the wall.
			if(x1 >= getWidth())
				xSpeed = -Math.abs(xSpeed); //The wall wouldn't change the speed. It would change the direction.
				ball.setXDir(xSpeed);
			if(x1 <= 0)
				xSpeed = Math.abs(xSpeed);
				ball.setXDir(xSpeed);
			if(y1 <= 0)
				ball.setYDir(2); //The wall facing the paddle however, sets the speed at 2.
	}

    public void checkBricks(int x1, int y1){ //Check if the ball hit the bricks.
    		for(int X = 0; X < 10; X++)
				for(int Y = 0; Y < 5; Y++){
					if(brick[X][Y].hitBottom(x1,y1)){ //Check where the ball hit the brick.
						ball.setYDir(2); //Set the velocity of the ball accordingly.
						if(brick[X][Y].destroyed()){
							bricksLeft--; //We know there is one less brick remaining now.
                            bricksBroken++; //Add one point to the score.
						}
					}
					if(brick[X][Y].hitLeft(x1,y1)){
						xSpeed = -xSpeed;
						ball.setXDir(xSpeed);
						if(brick[X][Y].destroyed()){
							bricksLeft--;
                            bricksBroken++;
						}
					}
					if(brick[X][Y].hitRight(x1,y1)){
						xSpeed = -xSpeed;
						ball.setXDir(xSpeed);
						if(brick[X][Y].destroyed()){
							bricksLeft--;
                            bricksBroken++;
						}
					}
					if(brick[X][Y].hitTop(x1,y1)){
						ball.setYDir(-2);
						if(brick[X][Y].destroyed()){
							bricksLeft--;
                            bricksBroken++;
						}
					}
				}
    }

    public void checkIfOut(int y1){ //Check if the ball is out of bounds.
    	if(y1 > paddle.getY() + 10){
			lives --; //if so, take one life.
			if (lives==0){
				//If there are no lives left, take the time.
				endTime = System.currentTimeMillis();
				timeTaken = (endTime - startTime)/1000; //Calculate the duration of the game.
				highScore.add(bricksBroken); //Add the score to the highScore arraylist.
				try(FileWriter fw = new FileWriter(file,true);
	                BufferedWriter bw = new BufferedWriter(fw);
					PrintWriter out = new PrintWriter (bw)){
	                out.println(bricksBroken);
	                sort(); //Sort the array list
	                rank = search(); //use the search to retrieve the rank of the player. 
	                
	        	} catch (IOException e){
	        		System.out.println("File not found.");
	        	}
			}
			ball.setY(getHeight()/2); //Reset the position of the ball.
			repaint();
			stop();
		}
    }

	public void createBricks(){ //Creating bricks.
		int widthDistance = getWidth()/10;
		int heightDistance = getHeight()/20;
		for(int X = 0; X < 10; X++){ //Use a for loop to create all the bricks.
			for(int Y = 0; Y < 5; Y++){
				brick[X][Y] = new Brick(X*widthDistance,(heightDistance/2)+(Y*heightDistance),
					getWidth()/11,getHeight()/23);
			}
		}
	}

	public void paintComponent(Graphics g){ //Painting all the objects
		super.paintComponent(g);
		paddle.draw(g);
		ball.draw(g);
		for(int X = 0; X < 10; X++)
			for(int Y = 0; Y < 5; Y++)
				brick[X][Y].draw(g);
		g.setColor(Color.BLACK);
		g.drawString("Lives left: " + Integer.toString(lives), getWidth() - (getWidth()/7), getHeight() - (getHeight()/10));
                g.drawString("Bricks broken: " + Integer.toString(bricksBroken), getWidth() - (getWidth()/5), getHeight() - (getHeight()/7));
		//Paint information: lives, score. These information are constantly updated.
        if(lives == 0){//IF the user runs out of lives,
			int heightBorder = getHeight()/10;
			int widthBorder = getWidth()/10;
                     
			g.setColor(Color.BLACK);
			g.fillRect(widthBorder, heightBorder, getWidth() - (2 * widthBorder), getHeight() - (2 * heightBorder ));
			g.setColor(Color.WHITE);
			g.drawString("You took "+timeTaken+" seconds.", getWidth()/5, 250);
			g.drawString("Click to start again.", getWidth()/5, 275);
			g.drawString(name+", your score is: "+bricksBroken+", at rank "+rank+".", getWidth()/5,getHeight()/2);
			//A game over screen is painted. It gives the duration of the game, the score, and your rank.
		}

		if(bricksLeft == 0){ //If all the bricks are destroyed, create a new level.
				createBricks();
				for(int X = 0; X < 10; X++){
					for(int Y = 0; Y < 5; Y++){
						brick[X][Y].setVisible(true);
					}
				}
				ball.setY(getHeight()/2);
				bricksLeft = 50;
				repaint();
				start();
		}
                

               
	}

	private class PanelMotionListener extends MouseMotionAdapter{
		public void mouseMoved(MouseEvent e){ //Mouse motion is used to control the paddle.
			int newX = e.getX()-getWidth()/17;
			paddle.setX(newX); //The paddle is set to the x position of the cursor.
			repaint();
		}
	}
	private class PanelListener extends MouseAdapter{
		public void mousePressed(MouseEvent e){ //Pressing the mouse would start the game.
			if (lives>0){
				start();
			}else{ //If the game is over(player died), clicking would restart the game.
				//Reset all the variables and create the bricks.
				startTime = System.currentTimeMillis();
				lives = 3;
				createBricks();
				for(int X = 0; X < 10; X++){
					for(int Y = 0; Y < 5; Y++){
						brick[X][Y].setVisible(true);
					}
				}
				ball.setY(getHeight()/2);
				bricksLeft = 50;
				bricksBroken = 0;
				repaint();
				start();
			}
		}
}

}
