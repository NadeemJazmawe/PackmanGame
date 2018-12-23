package Geom;

public class Geom_element_class implements Geom_element{
	private final double EarthRad = 6371000;
	private Point3D Geom;
	
	public Geom_element_class(Point3D my_geom) {
		super();
		this.Geom = my_geom;
	}
	public Geom_element_class(double x,double y,double z) {
		this(new Point3D (x,y,z));
	}
	
	/* 
	 * this function calculate the distance between our point(in the class) and other point.
	 * 
	 */
	@Override
	public double distance3D(Point3D p) {
		double x0 = (EarthRad+p.z()) * Math.cos(p.x()/180*Math.PI) * Math.cos(p.y()/180*Math.PI);
		double y0 = (EarthRad+p.z()) * Math.cos(p.x()/180*Math.PI) * Math.sin(p.y()/180*Math.PI);
		double z0 = (EarthRad+p.z()) * Math.sin(p.x()/180*Math.PI);
		double x1 = (EarthRad+p.z()) * Math.cos(Geom.x()/180*Math.PI) * Math.cos(Geom.y()/180*Math.PI);
		double y1 = (EarthRad+p.z()) * Math.cos(Geom.x()/180*Math.PI) * Math.sin(Geom.y()/180*Math.PI);
		double z1 = (EarthRad+p.z()) * Math.sin(Geom.x()/180*Math.PI);
		Point3D gps0=new Point3D(x0,y0,z0);
		Point3D gps1=new Point3D(x1,y1,z1);
		return gps1.distance3D(gps0);
	}

	/* 	
	 * this function calculate the distance between our point(in the class) and other point.
	 */
	@Override
	public double distance2D(Point3D p) {
		return distance3D(new Point3D(p.x(),p.y(),Geom.z()));
	}

	
}