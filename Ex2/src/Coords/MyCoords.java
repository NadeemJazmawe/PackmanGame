package Coords;

import java.util.Arrays;

import Geom.Point3D;


/**
 * @author 207392283 and 314638867
 * This class calculate  a basic coordinate system converter, including:
 * 1. The 3D vector between two lat,lon, alt points 
 * 2. Adding a 3D vector in meters to a global point.
 * 3. convert a 3D vector from meters to polar coordinates
 *
 */
public class MyCoords implements coords_converter {

	/**
	 * Enter the EarthRadius that equals to 6371000 meters.
	 */
	private final double EarthRad = 6371000;
	
	public static void main(String[] args) {
		MyCoords m=new MyCoords();
		Point3D a=new Point3D(32.10332,35.20904,670);
		Point3D b=new Point3D(32.10635,35.20523,650);
		System.out.println(m.vector3D(a,b));
		System.out.println(m.distance3d(a,b));
		System.out.println(Arrays.toString(m.azimuth_elevation_dist(a, b)));
	}
	
	
	/* 
	 * this method take a point in the GPS and vector , calculate where the point well be after going from the point in the vector.
	 */
	@Override
	public Point3D add(Point3D gps, Point3D local_vector_in_meter) {
		double x = gps.x() + Math.asin(local_vector_in_meter.x()/EarthRad)*180 / Math.PI; //these variable calculate the final lot.
		double y = gps.y() + Math.asin(local_vector_in_meter.y()/(Math.cos(gps.x()*Math.PI/180))*EarthRad)*180 / Math.PI; // these variable calculate the final lat. 
		double z = gps.z() + local_vector_in_meter.z();//these variable calculate the final alt.

		return new Point3D(x, y, z); // we but the final variables in new 3d Point and return it.
	}
	/* 
	 * this method calculate the distance between two point in the GPS ,
	 * we can say that this distance will be vector between the points.
	 */
	@Override
	public double distance3d(Point3D gps0, Point3D gps1) {
		Point3D vector = vector3D(gps0, gps1); // we use the Vector function to find the vector between the two Points.
		return vector.distance3D(0,0,0);	// calculate the distance of the vector by using dictance3D function and return it.
	}

	/*
	 * this method find the victor between two point in the GPS.
	 */
	@Override
	public Point3D vector3D(Point3D gps0, Point3D gps1) {
		double x = Math.sin((gps0.x()-gps1.x())*Math.PI/180)*EarthRad; // calculate the length of the variable x.
		double y = Math.sin((gps0.y()-gps1.y())*Math.PI/180)*EarthRad*Math.cos(gps0.x()*Math.PI/180); // calculate the length of the variable y.
		return new Point3D(x, y, gps0.z()-gps1.z()); // calculate the length of the variable z , and build new 3d point and return it .
	}

	/*
	 * this method calculate the Azimuth , elevation and distance between two points.
	 */
	@Override
	public double[] azimuth_elevation_dist(Point3D gps0, Point3D gps1) {	
		double azimuth = gps1.north_angle(gps0); // calculating the azimuth of the two point's by using the "north_angle" function .
		double dist = distance3d(gps1,gps0); // calculating the distance between the two point's by using "distance3D" function .
		double elevation = Math.toDegrees(Math.asin((gps1.z()-gps0.z())/dist)); // calculating the elevation between the two point's.
		return new double[] {azimuth,elevation,dist};
	}
	/*
	 * this function checking if some Point can find in the GPS.
	 */
	@Override
	public boolean isValid_GPS_Point(Point3D p) {
		return (p.x()<=180 && p.x()>=-180 &&p.y()<=90 && p.y()>=-90 &&p.z()>=-450); //checking if the lon , lat and alt is in the field of the GPS.
	}
}
