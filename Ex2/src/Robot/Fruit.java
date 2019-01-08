package Robot;

import Geom.Point3D;
/**
 * This class represent the fruit Point
 * @author jbareen Mohamad nadeem jazmawe 
 * 207392283_314638867
 */
public class Fruit {
	private Point3D fruit;//the point of the fruit on global
	private static int id;//the id of this fruit
	public static int getId() {//return the id of this fruit
		return id;
	}
	public Fruit(Point3D p) {//constructor for fruit by Point3D
		fruit =new Point3D(p);
		this.id++;
	}
	public void setFruit(Point3D fruit) {
		this.fruit = fruit;
	}
	public Point3D getFruit() {
		return fruit;
	}
}
