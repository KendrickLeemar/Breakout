
package breakout;

import java.awt.*;
/**
 *
 * @author 1leeken
 */
public class Ball {
        private int xPosition, yPosition, radius;
	private Color color;
	private int xDir = 1, yDir = 1 ;
//Variables for size, position, velocity and color.
	public Ball(int x, int y , int r, int xD, int yD, Color c){
		xPosition = x;
		yPosition = y;
		setRadius(r);
		setColor(c);
		//Set variable values.
	}
        public void setRadius(int r){
		radius = r;
		//set "size" of the ball
	}
	public void setColor(Color c){
		color = c;
		//Assign a color to the ball.
	}
        public void setXDir(int x){
		xDir = x;
		//Set x component of the velocity.
	}
	public void setYDir(int y){
		//Set y component of the velocity.
		yDir = y;
	}
	public void setY(int y){
		//Set y position of the ball.
		yPosition = y;
	}
	public int getRadius(){
		return radius;
	}
	public Color getColor(){
		return color;
	}
        public int getX(){
		return xPosition;
	}
	public int getY(){
		return yPosition;
	}
	
	public int getXDir(){
		return xDir;
	}
	public int getYDir(){
		return yDir;
	}
	//The above methods are to retrieve many variable values.
	
	public void move(){ //Move method sets the position by adding velocity to the current position.
		xPosition = xPosition + xDir;
		yPosition  = yPosition + yDir;
	}
	public void draw(Graphics g){ 
		Color oldColor = g.getColor();
		g.setColor(color);
		g.fillOval(xPosition - radius, yPosition - radius, radius * 2, radius * 2 );
		g.setColor(oldColor);
		//Draw the oval that represents the ball.
	}
}
