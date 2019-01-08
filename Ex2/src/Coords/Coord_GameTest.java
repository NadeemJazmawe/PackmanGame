package Coords;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Geom.Point3D;

class Coord_GameTest {

	@Test
	void testPixelGps() {
		Coord_Game g=new Coord_Game();
		int height=1433,width=642;
		double x=Math.random()*height,y=Math.random()*width;
		Point3D b=g.PixelGps(new Point3D(x,y,0),height,width);
		if(b.x()>35.212441&&b.x()<35.202436||b.y()<32.105712&&b.y()>32.101856)
			fail("ERR! out of the map");
	}

	@Test
	void testGpsPixel() {
		Coord_Game g=new Coord_Game();
		int height=1433,width=642;
		double x=Math.random()*35.212441+35.202436,y=Math.random()*32.101856+32.105712;
		Point3D b=g.GpsPixel(new Point3D(x,y,0),height,width);
		if(b.x()>height&&b.x()<0||b.y()<0&&b.y()>width)
			fail("Err! out of the frame");
	}
}
