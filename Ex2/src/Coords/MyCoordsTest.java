package Coords;
import Coords.MyCoords;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Geom.Point3D;

class MyCoordsTest {

	private final double EarthRad = 6371000;



	@Test
	void testAdd() {

		MyCoords s = new MyCoords();
		double x1 = Math.random() * 360 - 180;
		double y1 = Math.random() * 180 - 90 ;
		double z1 = Math.random() * 1000- 450;
		Point3D p1 = new Point3D(x1 , y1 ,z1);
		double x2 = Math.random() * 100000 - 50000;
		double y2 = Math.random() * 100000 - 50000;
		double z2 = Math.random() * 100000 - 50000;
		Point3D p2 = new Point3D(x2 , y2 ,z2);

		Point3D p = s.add(p1, p2);

		double x = x1 + Math.asin(x2/EarthRad)*180 / Math.PI;
		double y = y1 + Math.asin(y2/(Math.cos(x*Math.PI/180))*EarthRad)*180 / Math.PI;
		double z = z1 + z2;

		if(p.x() != x && p.y() != y && p.z() != z)
			fail("Not yet implemented");
	}

	@Test
	void testDistance3d() {
		MyCoords s = new MyCoords();
		double x1 = Math.random() * 360 - 180;
		double y1 = Math.random() * 180 - 90 ;
		double z1 = Math.random() * 1000- 450;
		Point3D p1 = new Point3D(x1 , y1 ,z1);
		double x2 = Math.random() * 360 - 180;
		double y2 = Math.random() * 180 - 90 ;
		double z2 = Math.random() * 1000 -450;
		Point3D p2 = new Point3D(x2 , y2 ,z2);

		double ans1 = s.distance3d(p1 ,p2);

		double x = Math.pow((x1 - x2), (x1 - x2));
		double y = Math.pow((y1 - y2), (y1 - y2));
		double z = Math.pow((z1 - z2), (z1 - z2));
		double ans2 = Math.sqrt(x + y +z);

		if(ans1 == ans2)
			fail("Eror , uncorrect Distance");
	}

	@Test
	void testVector3D() {
		MyCoords s = new MyCoords();
		double x1 = Math.random() * 360 - 180;
		double y1 = Math.random() * 180 - 90 ;
		double z1 = Math.random() * 1000- 450;
		Point3D p1 = new Point3D(x1 , y1 ,z1);
		double x2 = Math.random() * 360 - 180;
		double y2 = Math.random() * 180 - 90 ;
		double z2 = Math.random() * 1000 -450;
		Point3D p2 = new Point3D(x2 , y2 ,z2);

		Point3D p3 = s.vector3D(p1 , p2);

		double x = Math.sin((x1-x2)*Math.PI/180)*EarthRad;
		double y = Math.sin((y1-y2)*Math.PI/180)*EarthRad*Math.cos(x1*Math.PI/180);
		double z = z1 - z2 ;

		if(p3.x() != x || p3.y() != y || p3.z() != z)
			fail("Eror , uncorrect Vector");
	}


	@Test
	void testAzimuth_elevation_dist() {
		MyCoords s = new MyCoords();
		double x1 = Math.random() * 360 - 180;
		double y1 = Math.random() * 180 - 90 ;
		double z1 = Math.random() * 1000- 450;
		Point3D p1 = new Point3D(x1 , y1 ,z1);
		double x2 = Math.random() * 360 - 180;
		double y2 = Math.random() * 180 - 90 ;
		double z2 = Math.random() * 1000 -450;
		Point3D p2 = new Point3D(x2 , y2 ,z2);

		double [] ans = s.azimuth_elevation_dist(p1, p2);

		double azimuth = p2.north_angle(p1);
		double dist = s.distance3d(p2,p1); 
		double elevation = Math.toDegrees(Math.asin((p2.z()-p1.z())/dist));

		if( ans[0] != azimuth && ans[1] != elevation && ans[2] != dist )
			fail("Not yet implemented");
	}

	@Test
	void testIsValid_GPS_Point() {
		MyCoords s = new MyCoords();
		double x1 = Math.random() * 1000 - 500;
		double y1 = Math.random() * 1000 - 500;
		double z1 = Math.random() * 1000 - 500;
		Point3D p1 = new Point3D(x1 , y1 , z1);

		boolean ans = s.isValid_GPS_Point(p1);

		if( (x1 <= 180 && x1 >= -180 && y1 <= 90 && y1 >= -90 && z1 >= -450) != ans)
			fail("Eror , uncorrect Value");
	}

}
