//The player controlled paddle, which bounces the ball back.
package breakout;
import java.awt.*;

public class Paddle{
	public Color color;
	private int x, y, width, height;
	private int maxWidth;
	private int minWidth;	
	//Variables for position, size, and color.
	//There is a maxWidth and minWidth, in case extra features may be added.
	public Paddle(int X, int Y, int Width, int Height, Color c){
		x = X;
		y = Y;
		width = Width;
		height = Height;
		color = c;
		maxWidth = width*2;
		minWidth = width/2;
		//Assign the values to the paddle.
	}
	public void setX(int X){
		x = X; //set the position of the paddle.
	}

	public int getY(){
		return y; //This is just to provide a frame of reference.
		//A lot of objects will be created in reference to the position of other objects.
	}

	public int getWidth(){
		//Also to provide a frame of reference, but for size instead of position.
		return width;
	}

	public void setWidth(int Width){
		//set the width of the paddle so that it's not higher than the max or lower than the min.
		if(Width > maxWidth || Width < minWidth){
			return;
		}else{
			width = Width;
		}
	}

	public boolean hitLeft(int X, int Y){
		//Check if the ball hit the paddle on the left by looking at their positions.
		if((X >= x) && (X <= x+(width/2)) && ((y == Y)||(y == Y - 1))){
			return true; //This will be used later to determine the direction of the ball.
		}
		return false;
	}
	public boolean hitRight(int X, int Y){
		//Check if the ball hit the paddle on the right by looking at their positions.
		if(((X >= x+(width/2)) && (X <= x+width)) && ((y == Y)||(y == Y - 1))){
			return true; //If so,return true.
		}
		return false; //If not, return false.
	}

	public void draw(Graphics g){
		Color oldColor = g.getColor();
		g.setColor(color);
		g.fillRect(x,y,width,height);
		g.setColor(oldColor);
		//Method to draw the paddle. //It is drawn as a rectangle.
	}
}