package Game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import Geom.Point3D;
import Robot.Fruit;
import Robot.Packman;

public class Path2KML {
	private ArrayList<Point3D> path;
	private String PathName;
	private ArrayList<Packman> p;
	private ArrayList<Fruit> f;
	public Path2KML(String name) {
		PathName=name;
		path=new ArrayList<Point3D>();
		p=new ArrayList<Packman>();
		f=new ArrayList<Fruit>();
	}
	public static void main(String[] args) {
		Path2KML p=new Path2KML("wigleWifi_20171203085618.csv");
		p.write();
	}
	public String print(ArrayList<Point3D> p) {
		String s="";
		Iterator<Point3D> it=p.iterator();
		while(it.hasNext()) {
			Point3D n=new Point3D(it.next());
			s+=n.x()+","+n.y()+" ";
		}
		return s;
	}
	public String PlaceMark(String Date,Point3D p,String type) {
		path.add(p);
		String s;
		s=      "      <Placemark>\n" + 
				"        <TimeStamp>\n" ;
		if(type.equals("WIFI"))
			s+="          <when>"+Date.replace(" ", "T")+"Z</when>\n";
		else if(type.equals("GSM"))
			s+="          <when>"+Date+"</when>\n";
		s+="        </TimeStamp>\n" + 
				"        <styleUrl>";
		if(type.equals("GSM"))
			s+= "#paddle-a</styleUrl>\n";
		else if(type.equals("WIFI"))
			s+="#hiker-icon</styleUrl>\n";
		s+=	"        <Point>\n" + 
				"          <coordinates>"+p.x()+","+p.y()+"</coordinates>\n" + 
				"        </Point>\n" + 
				"      </Placemark>\n";

		return s;
	}
	public void write() {
		String fileName =PathName.substring(0, PathName.length()-4)+"-Path.kml";
		try {
			// Assume default encoding.
			FileWriter fileWriter =
					new FileWriter(fileName);

			// Always wrap FileWriter in BufferedWriter.
			BufferedWriter bufferedWriter =
					new BufferedWriter(fileWriter);
			BufferedReader br = new BufferedReader(new FileReader(PathName));
			String line = ""; //one row from the csv file
			br.readLine();
			br.readLine();
			line = br.readLine();
			bufferedWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
					"<kml xmlns=\"http://www.opengis.net/kml/2.2\">\n" + 
					"  <Document>\n" + 
					"    <name>Points with TimeStamps</name>\n" + 
					"    <Style id=\"paddle-a\">\n" + 
					"      <IconStyle>\n" + 
					"        <Icon>\n" + 
					"          <href>http://maps.google.com/mapfiles/kml/paddle/A.png</href>\n" + 
					"        </Icon>\n" + 
					"        <hotSpot x=\"32\" y=\"1\" xunits=\"pixels\" yunits=\"pixels\"/>\n" + 
					"      </IconStyle>\n" + 
					"    </Style>\n" + 
					"    <Style id=\"paddle-b\">\n" + 
					"      <IconStyle>\n" + 
					"        <Icon>\n" + 
					"          <href>http://maps.google.com/mapfiles/kml/paddle/B.png</href>\n" + 
					"        </Icon>\n" + 
					"        <hotSpot x=\"32\" y=\"1\" xunits=\"pixels\" yunits=\"pixels\"/>\n" + 
					"      </IconStyle>\n" + 
					"    </Style>\n" + 
					"    <Style id=\"hiker-icon\">\n" + 
					"      <IconStyle>\n" + 
					"        <Icon>\n" + 
					"          <href>http://maps.google.com/mapfiles/ms/icons/hiker.png</href>\n" + 
					"        </Icon>\n" + 
					"        <hotSpot x=\"0\" y=\".5\" xunits=\"fraction\" yunits=\"fraction\"/>\n" + 
					"      </IconStyle>\n" + 
					"    </Style>\n" + 
					"    <Style id=\"check-hide-children\">\n" + 
					"      <ListStyle>\n" + 
					"        <listItemType>checkHideChildren</listItemType>\n" + 
					"      </ListStyle>\n" + 
					"    </Style>\n" + 
					"    <styleUrl>#check-hide-children</styleUrl>\n<Folder>\n"); 
			while (line != null && !line.isEmpty()) {
				if((line.replaceAll(",","")).replaceAll(" ","").isEmpty())
					break;
				String[] row = line.split(",");
				bufferedWriter.write(PlaceMark(row[3], new Point3D(Double.parseDouble(row[7]),Double.parseDouble(row[6])),row[10]));
				//path.add(new Point3D(Double.parseDouble(row[7]),Double.parseDouble(row[6])));
				line = br.readLine();
			}
			bufferedWriter.write("   </Folder>\n   <Placemark>\n" + 
					"      <name>Migration path</name>\n" + 
					"      <Style>\n" + 
					"        <LineStyle>\n" + 
					"          <color>ff0000ff</color>\n" + 
					"          <width>2</width>\n" + 
					"        </LineStyle>\n" + 
					"      </Style>\n" + 
					"      <LineString>\n" + 
					"        <tessellate>1</tessellate>\n" + 
					"        <altitudeMode>absolute</altitudeMode>\n" + 
					"		 <coordinates>");
			bufferedWriter.write(print(path));
			bufferedWriter.write("</coordinates>\n      </LineString>\n" + 
					"    </Placemark>\n");
			// Note that write() does not automatically
			// append a newline character.
			bufferedWriter.write("\n" + 
					"  </Document>\n" + 
					"</kml>");
			// Always close files.
			bufferedWriter.close();
		}
		catch(IOException ex) {
			System.out.println(
					"Error writing to file '"
							+ fileName + "'");
			// Or we could just do this:
			// ex.printStackTrace();
		}
	}
}
