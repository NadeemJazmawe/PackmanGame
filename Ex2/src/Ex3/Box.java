package Ex3;

import Geom.Point3D;

public class Box {

	private Point3D min , max ;
	private static int id ;
	
	public Box(Point3D min, Point3D max) {
		this.min = min;
		this.max = max;
		this.id++;
	}
	public Point3D getMin() {
		return min;
	}
	public Point3D getMax() {
		return max;
	}
	public static int getId() {
		return id;
	}
	
}
