package Coords;

import Geom.Point3D;
/**
 * This class represent the Coordinators for the map of ariel on GUI, that changes the global coords to pixels and the sane for pixels to global coords
 * @author jbareen Mohamad nadeem jazmawe 
 * 207392283_314638867
 *
 */
public class Coord_Game {
	MyCoords coord=new MyCoords();

	double First_Lon= 35.202436;//the first global longitude from the (0,0) point on the frame 
	double First_Lat = 32.105712;//the first global latitude from the (0,0) point on the frame 
	double Second_Lon = 35.212441;//the last long on the frame
	double Second_Lat = 32.101856;//the last lat on the frame
	final Point3D Point = new Point3D(First_Lat, First_Lon , 0 );
	final double DistX = coord.distance3d(Point, new Point3D(First_Lat , Second_Lon ,0));//compiled the distance for x on the global by meters
	final double DistY = coord.distance3d(Point, new Point3D(Second_Lat , First_Lon , 0));//compiled the distance for x on the global by meters 
	final double DistLon = Second_Lon - First_Lon;//compiled the distance for x on the global
	final double DistLat = Second_Lat - First_Lat;//compiled the distance for x on the global
	/**
	 * 
	 * @param pixel took the point clicked from map class
	 * @param height took from the height of the GUI frame from map class
	 * @param width took from the width of the GUI frame from map class
	 * @return global point
	 */
	public Point3D PixelGps(Point3D pixel , int height, int  width){//compiled the pixel frame point to global point
		return new Point3D(First_Lat + (pixel.y()/height)*(DistLat),First_Lon+(pixel.x()/width)*(DistLon) , pixel.z());
	}
	/**
	 * 
	 * @param gps took the point clicked from map class
	 * @param height took from the height of map on global point and compiled it to frame point 
	 * @param width took from the width of map on global point and compiled it to frame point 
	 * @return frame point
	 */
	public Point3D GpsPixel(Point3D gps  , int height, int width ){//compiled the global point to pixel frame point
		return new Point3D(width*(gps.y() - First_Lon)/(DistLon)  ,height*(gps.x() - First_Lat)/(DistLat) , gps.z());
	}

	public Point3D MetersGps(Point3D meters){//compiled the meters distance to global
		return new Point3D(First_Lat + meters.y()/DistY*(DistLat) ,First_Lon+meters.x()/DistX*(DistLon) , meters.z());
	}
}
