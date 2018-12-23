package GIS;

import Geom.Geom_element;
import Geom.Geom_element_class;
import Geom.Point3D;

public class GIS_element_class implements GIS_element{

	private String MAC , SSID , AuthMode , FirstSeen , Channel , RSSI , AccuracyMeters , Type;
	private Geom_element_class Geom;
	private Meta_data_class data;
	

	public GIS_element_class(Geom_element_class geom, Meta_data_class data, String MAC, String SSID, String AuthMode, String FirstSeen, String Channel, String RSSI,
			String AccuracyMeters, String Type) {
		super();
		this.MAC = MAC;
		this.SSID = SSID;
		this.AuthMode = AuthMode;
		this.FirstSeen = FirstSeen;
		this.Channel = Channel;
		this.RSSI = RSSI;
		this.AccuracyMeters = AccuracyMeters;
		this.Type = Type;
		this.Geom = geom;
		this.data = data;
	}

	@Override
	public Geom_element getGeom() {
		return Geom;
	}

	@Override
	public Meta_data getData() {
		return data;
	}

	@Override
	public void translate(Point3D vec) {
		// TODO Auto-generated method stub
		
	}
}