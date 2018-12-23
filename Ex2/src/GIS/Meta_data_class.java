package GIS;

import Geom.Point3D;

public class Meta_data_class implements Meta_data{

	long UTC;
	Point3D Orientation;
	public Meta_data_class(long UTC, Point3D Orientation) {
		super();
		this.UTC = UTC;
		this.Orientation = Orientation;
	}
	@Override
	public long getUTC() {
		return UTC;
	}

	@Override
	public String toString() {
		return "Meta_data: [UTC=" + UTC + ", Orientation=(yaw:"+ Orientation.x() + ",Pitch:"+Orientation.y()+",roll:"+Orientation.z()+")]";
	}
	@Override
	public Point3D get_Orientation() {
		return Orientation;
	}
	
	
}