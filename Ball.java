
import java.io.File;

import javax.sound.sampled.*;

/**
 * Models a simple solid sphere. 
 * This class represents a Ball object. When combined with the GameArena class,
 * instances of the Ball class can be displayed on the screen.
 */
public class Ball 
{


	// The following instance variables define the
	// information needed to represent a Ball
	// Feel free to more instance variables if you think it will 
	// support your work... 
	
	private double xPosition;			// The X coordinate of this Ball
	private double yPosition;			// The Y coordinate of this Ball
	private double xVelocity;			// The X velocity of this Ball
	private double yVelocity;			// The Y velocity of this Ball
	private double size;				// The diameter of this Ball
	private int layer;					// The layer of this ball is on.
	private String colour;				// The colour of this Ball
	private double dissipation = 30;	//number between 0 and 100 which is percentage of energy lost on a collision
	private double friction = 0;		//number between 0 and 100 which is the percentage of energy lost every tick


										// Permissable colours are:
										// BLACK, BLUE, CYAN, DARKGREY, GREY,
										// GREEN, LIGHTGREY, MAGENTA, ORANGE,
										// PINK, RED, WHITE, YELLOW or #RRGGBB 

	/**
	 * Constructor. Creates a Ball with the given parameters.
	 * @param x The x co-ordinate of centre of the Ball (in pixels)
	 * @param y The y co-ordinate of centre of the Ball (in pixels)
	 * @param diameter The diameter of the Ball (in pixels)
	 * @param col The colour of the Ball (Permissable colours are: BLACK, BLUE, CYAN, DARKGREY, GREY, GREEN, LIGHTGREY, MAGENTA, ORANGE, PINK, RED, WHITE, YELLOW or ##RRGGBB)
	 */
	public Ball(double x, double y, double diameter, String col)
	{
		this.xPosition = x;
		this.yPosition = y;
		this.size = diameter;
		this.colour = col;
		this.layer = 0;
	}	

	/**
	 * Constructor. Creates a Ball with the given parameters.
	 * @param x The x co-ordinate of centre of the Ball (in pixels)
	 * @param y The y co-ordinate of centre of the Ball (in pixels)
	 * @param diameter The diameter of the Ball (in pixels)
	 * @param col The colour of the Ball (Permissable colours are: BLACK, BLUE, CYAN, DARKGREY, GREY, GREEN, LIGHTGREY, MAGENTA, ORANGE, PINK, RED, WHITE, YELLOW or ##RRGGBB)
	 * @param layer The layer this ball is to be drawn on. Objects with a higher layer number are always drawn on top of those with lower layer numbers.
	 */
	public Ball(double x, double y, double diameter, String col, int layer)
	{
		this.xPosition = x;
		this.yPosition = y;
		this.size = diameter;
		this.colour = col;
		this.layer = layer;
	}	

	/*
	 * sets the friction
	 * 
	 */
	public void setFriction(double f){
		this.friction = f;
	}


	/**
	 * Obtains the current position of this Ball.
	 * @return the X coordinate of this Ball within the GameArena.
	 */
	public double getXPosition()
	{
		return xPosition;
	}

	/**
	 * Obtains the current position of this Ball.
	 * @return the Y coordinate of this Ball within the GameArena.
	 */
	public double getYPosition()
	{
		return yPosition;
	}

	/**
	 * Moves the current position of this Ball to the given co-ordinates
	 * @param x the new x co-ordinate of this Ball
	 */
	public void setXPosition(double x)
	{
		this.xPosition = x;
	}

	/**
	 * Moves the current position of this Ball to the given co-ordinates
	 * @param y the new y co-ordinate of this Ball
	 */
	public void setYPosition(double y)
	{
		this.yPosition = y;
	}
	/**
	 * Obtains the velocity of the ball
	 * @return the velocity along the x axis
	 */
	public double getXVelocity(){
		return xVelocity;
	}

	/**
	 * Obtains the velocity of the ball
	 * @return the velocity along the y axis
	 */
	public double getYVelocity(){
		return yVelocity;
	}
	/**
	 * sets the velocity of the ball
	 * @param x the new velocity along the x axis
	 */
	public void setXVelocity(double x){
		this.xVelocity = x;
	}
	/**
	 * sets the velocity of the ball
	 * @param y the new velocity along the y axis
	 */
	public void setYVelocity(double y){
		this.yVelocity = y;
	}
	/**
	 * accelerates the ball
	 * @param x the acceleration of the ball allong the x axis
	 */
	public void accelerateX(double x){
		this.xVelocity += x;
	}
	/**
	 * accelerates the ball	
	 * @param y the acceleration of the ball allong the y axis
	 */
	public void accelerateY(double y){
		this.yVelocity += y;
	}


	/**
	 * Obtains the size of this Ball.
	 * @return the diameter of this Ball,in pixels.
	 */
	public double getSize()
	{
		return size;
	}
	
	/**
	 * Sets the diameter of this Ball to the given size.
	 * @param s the new diameter of this Ball, in pixels.
	 */
	public void setSize(double s)
	{
		size = s;
	}

	/**
	 * Obtains the layer of this Ball.
	 * @return the layer of this Ball.
	 */
	public int getLayer()
	{
		return layer;
	}
	/**
	 * Sets the layer of this Ball.
	 * @param l the new layer of this Ball. Higher layer numbers are drawn on top of low layer numbers.
	 */
	public void setLayer(int l)
	{
		layer = l;
	}

	/**
	 * Obtains the colour of this Ball.
	 * @return a textual description of the colour of this Ball.
	 */
	public String getColour()
	{
		return colour;
	}

	/**
	 * Sets the colour of this 800Ball.
	 * @param c the new colour of this Ball, as a String value. Permissable colours are: BLACK, BLUE, CYAN, DARKGREY, GREY, GREEN, LIGHTGREY, MAGENTA, ORANGE, PINK, RED, WHITE, YELLOW or #RRGGBB.
	 */
	public void setColour(String c)
	{
		colour = c;
	}

	/**
	 * sets the disapation(the energy lost on collision)
	 * @param d the new disapation
	 */
	public void setDissapation(int d)
	{
		dissipation = d;
	}


	/**
	 * Moves this Ball by the given amount.
	 * 
	 * @param dx the distance to move on the x axis (in pixels)
	 * @param dy the distance to move on the y axis (in pixels)
	 */
	public void move(double dx, double dy)
	{
		xPosition += dx;
		yPosition += dy;
	}

	/**
	 * Determines if this Ball is overlapping a given ball.
	 * If the two balls overlap, they have collided.
	 * 
	 * @param b the ball to test for collision
	 * @return true of this ball is overlapping the ball b, false otherwise.
	 */
	public boolean collides(Ball b)
	{
		double dx = b.xPosition - xPosition;
		double dy = b.yPosition - yPosition;
		double distance = Math.sqrt(dx*dx+dy*dy);

		return distance < size/2 + b.size/2;


	}

	/**
	 * Calculates and changes the balls tragectory from coliding with a mallet
	 * @param mallet the mallet that the puck is deflected off
	 */
	public void deflectOff(Ball mallet){

		//gets relative velocity to the mallet
		double xV = xVelocity - mallet.getXVelocity();
		double yV = yVelocity - mallet.getYVelocity();
		//gets relative position to the mallet
		double x = xPosition - mallet.getXPosition();
		double y = yPosition - mallet.getYPosition();
		
		//checks if the puck is heading towards the mallet
		if((x * xV + y * yV)/ (x*x + y*y) <= 0){

			//c just makes the equations simpler
			double c = 2 * (x * yV - y * xV) / (x*x + y*y);
			xVelocity = mallet.getXVelocity() -xV - y * c;
			yVelocity = mallet.getYVelocity() -yV + x * c;
			
			hitNoise((float)(((x * xV + y * yV)/ (x*x + y*y)) * -400));


		}
		


	}



	/**
	 * accelerates and moves the ball according Velocity
	 */
	public void tick(){
		//acceleratesa
		xPosition += xVelocity;
		yPosition += yVelocity;
		
		
		//air resistance
		xVelocity = xVelocity * (100 - friction)/100;
		yVelocity = yVelocity * (100 - friction)/100;



		//bounce
		boolean notGoal = (yPosition > 354 || yPosition < 146) && xPosition > 10 && xPosition < 990;

		if(xPosition > 950 - size/2 && xVelocity >= 0 && notGoal){
			xVelocity = -xVelocity * (100 - dissipation)/100;
			xPosition = 950 - size/2 ;
			hitNoise((float)(xVelocity * -8));

		}
		
		if(xPosition < 50 + size/2 && xVelocity <= 0  && notGoal){
			xVelocity = -xVelocity * (100 - dissipation)/100;
			xPosition = 50 + size/2;			
			
			hitNoise((float)(xVelocity * 8));


		}

		if(yPosition > 450 - size/2 && yVelocity >= 0){
			yVelocity = -yVelocity * (100 - dissipation)/100;
			yPosition = 450 - size/2;
			hitNoise((float)(yVelocity * -8));

		}
		if(yPosition < 50 + size/2 && yVelocity <= 0 ){	
			yVelocity = -yVelocity * (100 - dissipation)/100;
			yPosition = 50 + size/2;
			hitNoise((float)(yVelocity * 8));

		}

	}

	/**
	 * playes the hit noise
	 * @param volume loudnes of the sound 0 being silent 80 being max
	 */
	public void hitNoise(float volume){

			


			if(volume > 80) volume = 80;
			volume = volume/2 + 40;
			
			try {
				File soundFile = new File("bounce.wav");
				if (!soundFile.exists()) {
					System.err.println("Error: File not found: " + soundFile.getAbsolutePath());
					return;
				}
				AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
				Clip clip = AudioSystem.getClip();
				clip.open(audioInputStream);

				FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            	gainControl.setValue(volume-80f);
				clip.start();
			} catch (Exception e) {
				e.printStackTrace();
			}

	}
}