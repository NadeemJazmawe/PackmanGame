package Game;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import Geom.Circle;
import Geom.Point3D;
import Robot.Fruit;
import Robot.Packman;
/**
 * this class represent the kml file
 * @author jbareen Mohamad nadeem jazmawe 
 * 207392283_314638867
 */
public class Path2KML2 {
	private ShortestPathAlgo path;//calling the path Algorithm
	public Path2KML2(ArrayList<Packman> pac,ArrayList<Fruit> fruit) {//build the kml by the packman list and the fruit list that called from the map class
		path=new ShortestPathAlgo(pac,fruit);
	}
	public static void main(String[] args) {
		ArrayList<Packman> pac=new ArrayList<Packman>();
		ArrayList<Fruit> fruit=new ArrayList<Fruit>();
		pac.add(new Packman(new Circle(new Point3D(32.10463087850467,35.205452161898116,0.0),10),10));
		fruit.add(new Fruit(new Point3D(32.10406028660436,35.20613638381019,0.0)));
		fruit.add(new Fruit(new Point3D(32.10344765109034,35.208035448709,0.0)));
		fruit.add(new Fruit(new Point3D(32.10254071028037,35.207511809490576,0.0)));
		Path2KML2 p=new Path2KML2(pac,fruit);
		p.write("s");
	}
	public String PlaceMarkFruit(int j) {//build the place mark for the fruit by the coordinator
		String s="";
		String t="";
		for (int i = 1; i < path.all_path.get(j).fruitToEat.size(); i++) {
			s+=      "      <Placemark>\n" + 
					"        <TimeStamp>\n" ;
			t=path.all_path.get(j).FruitTimeList.get(i);
			t=t.replaceAll("T", " ");
			s+="          <when>"+t+"</when>\n";
			s+="        </TimeStamp>\n" + 
					"        <styleUrl>";
			s+="#paddle-a</styleUrl>\n";
			s+=	"        <Point>\n" + 
					"          <coordinates>"+path.all_path.get(j).fruitToEat.get(i).getFruit().y()+","+path.all_path.get(j).fruitToEat.get(i).getFruit().x()+"</coordinates>\n" + 
					"        </Point>\n" + 
					"      </Placemark>\n";
		}
		return s;
	}
	public String PlaceMark(int j) {//build the place mark for the packman by the coordinator
		String s="";
		for (int i = 0; i < path.all_path.get(j).Slope.size(); i++) {
				s+=      "      <Placemark>\n" + 
						"        <TimeStamp>\n" ;
				s+="          <when>"+path.all_path.get(j).PacTimeList.get(i)+"</when>\n";
				s+="        </TimeStamp>\n" + 
						"        <styleUrl>";
				s+="#hiker-icon</styleUrl>\n";
				s+=	"        <Point>\n" + 
						"          <coordinates>"+path.all_path.get(j).Slope.get(i).y()+","+path.all_path.get(j).Slope.get(i).x()+"</coordinates>\n" + 
						"        </Point>\n" + 
						"      </Placemark>\n";
			}
		return s;
	}
	public String Migration(int j) {
		String s="";
		s+= "<Placemark>\n" + 
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
				"		 <coordinates>";
		for (int i = 0; i < path.all_path.get(j).fruitToEat.size(); i++) {
			s+=path.all_path.get(j).fruitToEat.get(i).getFruit().y()+","+path.all_path.get(j).fruitToEat.get(i).getFruit().x()+" ";
		}
		s+="</coordinates>\n      </LineString>\n" + 
				"    </Placemark>\n";
		return s;
	}
	public void write(String Name) {//save the kml file by a name
		String fileName =Name+"-Path.kml";
		try {
			// Assume default encoding.
			FileWriter fileWriter =
					new FileWriter(fileName);//write the file 

			// Always wrap FileWriter in BufferedWriter.
			BufferedWriter bufferedWriter =
					new BufferedWriter(fileWriter);
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
			System.out.println(path.all_path.size());
			for (int i = 0; i <path.all_path.size(); i++) {
				bufferedWriter.write(PlaceMarkFruit(i));
				bufferedWriter.write(PlaceMark(i));
			}
			//path.add(new Point3D(Double.parseDouble(row[7]),Double.parseDouble(row[6])));
			bufferedWriter.write("   </Folder>\n");
			for (int i = 0; i <path.all_path.size(); i++) {
				bufferedWriter.write(Migration(i));
			}
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

