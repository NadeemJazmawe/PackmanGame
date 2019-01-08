package Robot;

import Geom.Circle;

/**
 * this class represent the packman class that have a point on global and speed rate 
 * @author jbareen Mohamad nadeem jazmawe 
 * 207392283_314638867
 */
public class ghosts {
	private int speed;//the speed for the Ghosts
	private Circle Coords;//the point on the global by circle
	private static int id;
	public static int getId() {
		return id;
	}
	public ghosts(Circle c,int speed) {
		this.Coords=new Circle(c);
		this.speed=speed;
		this.id++;
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
