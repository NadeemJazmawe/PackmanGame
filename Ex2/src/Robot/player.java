package Robot;

import Geom.Circle;

/**
 * this class represent the packman class that have a point on global and speed rate 
 * @author jbareen Mohamad nadeem jazmawe 
 * 207392283_314638867
 */
public class player {

	private int speed;//the speed for the Player
	private Circle Coords;//the point on the global by circle
	
	public player(Circle c,int speed) {
		this.Coords=new Circle(c);
		this.speed=speed;

	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public Circle getCoords() {
		return Coords;
	}
	public void setCoords(Circle coords) {
		Coords = coords;
	}

}
