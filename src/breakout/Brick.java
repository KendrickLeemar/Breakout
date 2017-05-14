
//This is the code for the bricks, which the player attempts to destroy.
package breakout;
import java.awt.*;

public class Brick{
	public Color color;	
	private int x, y, width, height, numHits, maxHits; //Position, size, and hits they took/would take.
	//Hits can be edited to make the game more difficult.
	private boolean visible;

	public Brick(int X, int Y, int Width, int Height){ //this method will be called in the board class.
		x = X; 
		y = Y;
		width = Width;
		height = Height;
                maxHits = 1;
                color = Color.RED;
		numHits = 0;
		visible = true; 
		//Assign values: position, size, durability, color, and whether it is destroyed or not.
	}
	public void setX(int X){
		x = X;
	}

	public void addHits(){ 
		numHits++;  
		color = Color.RED;
		if(numHits == maxHits){ //Increment numHits until it equals maxHits. Then destroy the block.
			visible = false;
		}
		//The code was created to be editable to make it more challenging.
		//In a challenging game, maxHits would be much higher, and the bricks would have to be hit more.
	}
	public boolean hitBottom(int X, int Y){
		//The code that check where the ball hit the brick are for the direction the ball will go.
		//Check the position of the ball and the brick to see if the ball hit the bottom of the brick.
		if((X>=x) && (X <= x+width) && ((y+height == Y)||(y+height == Y + 1)) && visible == true){
			addHits();
			return true; //if so, return true.
		}
		return false;//if not, return false.
	}

	public void setVisible(boolean b){
		visible = b; //Set a brick visible/invisible.
	}

	public boolean hitTop(int X, int Y){ 
		//Check the position of the ball and the brick to see if the ball hit the top of the brick.
		if((X>=x) && (X <= x+width) && ((y == Y)||(y == Y - 1)) && visible == true){
			addHits();
			return true; //if so, addHits(thus destroying the brick) and return true.
		}
		return false; //if not, return false.
	}

	public boolean hitLeft(int X, int Y){
		//Check the position of the ball and the brick to see if the ball hit left of the brick.
		if((Y>=y) && (Y <= y+height) && ((x == X)||(x == X - 1)) && visible == true){
			addHits(); 
			return true; //if so, add hits and return true.
		}
		return false;//if not, return false.
	}

	public boolean hitRight(int X, int Y){
		//Check the position of the ball and the brick to see if the ball hit right of the brick.
		if((Y>=y) && (Y <= y+height) && ((x+width == X)||(x+width == X - 1)) && visible == true){
			addHits();
			return true; //if so, return true.
		}
		return false; //if not, return false.
	}

	public boolean destroyed(){
		return !visible; //To check if destroyed, check if it is invisible. If it is visible, return false.
	}

	public void draw(Graphics g){ //Method to draw the bricks.(These are just visual representations)
		if(visible){ 
			Color oldColor = g.getColor();
			g.setColor(color);
			g.fillRect(x,y,width,height); //Create rectangles in the same position/size as the bricks.
			g.setColor(oldColor);
		}
	}

}
