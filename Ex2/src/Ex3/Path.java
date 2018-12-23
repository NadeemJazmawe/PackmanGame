package Ex3;

import java.util.ArrayList;
import java.util.Iterator;
import Coords.MyCoords;
import Geom.Point3D;

public class Path {


	private Coord_Game Points = new Coord_Game();
	private Packman packman;
	private int index=0;
	private double time = 0.0;
	private ArrayList<Point3D> GPS = new ArrayList<Point3D>();
	public Coord_Game getPoints() {
		return Points;
	}
	public Packman getPackman() {
		return packman;
	}
	public int getIndex() {
		return index;
	}
	public Path(Packman packman,int index) {
		super();
		this.packman = packman;
		GPS.add(packman.getCoords().get_cen());
		this.index=index;
	}
	public ArrayList<Point3D> getGPS() {
		return GPS;
	}
	public void setGPS(ArrayList<Point3D> GPS) {
		this.GPS = GPS;
	}
	public double getTime(){
		return getDist()/packman.getSpeed();
	}
	public double getDist(){
		MyCoords Coords = new MyCoords();
		double totalDist = 0.0;
		Iterator<Point3D> itr = GPS.iterator();
		Point3D current_GPS = itr.next();
		while (itr.hasNext()){
			Point3D Next_GPS = itr.next();
			totalDist += Coords.distance3d(current_GPS,Next_GPS);
			current_GPS = Next_GPS;
		}
		return totalDist;
	}

	@Override
	public String toString() {
		String s="Path (Packman=" + packman.getCoords().get_cen()+ ", Time=" + time + ", GPS=";
		for (Point3D point3d : GPS) {
			s+=" , "+point3d;
		}
		return s+=")";
	}
	

}
