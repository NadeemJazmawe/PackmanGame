package File_format;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import Geom.Point3D;
/**
 * @author 207392283 and 314638867 
 * 
 * This class convert File from Csv to Kml .
 *
 */
public class Csv2kml {
	private static String CsvName;

	public static void main(String [] args) {
		Csv2kml x=new Csv2kml("/Users/jemejbareen/Desktop/test.csv");
		x.write();
		x.read();

	}
	/**
	 * @param name the Name of the file that we want to use.
	 * Constructor for our class.
	 */
	public Csv2kml(String name){
		CsvName = name; // in the format: "name.csv"
	}

	
	/**
	 * this function open the file that we want to convert and read it.
	 */
	public void read() {
		// The name of the file to open.
		String fileName = this.CsvName.substring(0,CsvName.length()-3)+"kml";

		try {
			// Use this for reading the data.
			byte[] buffer = new byte[1000];

			FileInputStream inputStream = 
					new FileInputStream(fileName);

			// read fills buffer with data and returns
			// the number of bytes read (which of course
			// may be less than the buffer size, but
			// it will never be more).
			int nRead = 0;
			while((nRead = inputStream.read(buffer)) != -1) {
				// Convert to String so we can display it.
				// Of course you wouldn't want to do this with
				// a 'real' binary file.
				System.out.println(new String(buffer));
			}   

			// Always close files.
			inputStream.close();        
		}
		catch(FileNotFoundException ex) {
			System.out.println(
					"Unable to open file '" + 
							fileName + "'");                
		}
		catch(IOException ex) {
			System.out.println(
					"Error reading file '" 
							+ fileName + "'");                  
			// Or we could just do this: 
			// ex.printStackTrace();
		}
	}
	
	
	/**
	 * @param SSID the App that the release use .
	 * @param MAC the WigleWifi of who release the point .
	 * @param AuthMode is the security options of the network that the release use .
	 * @param FirstSeen it tell us when was the last seen of this point .
	 * @param point this will be our point .
	 * @return the point that we want to place(in kml).
	 * 
	 * this method get information of the place that we want to mark and write it in kml.
	 */
	public String Placemark( String SSID, String MAC,String AuthMode,String FirstSeen, Point3D point)
	{
		return "\t<Placemark>\n" +
				"\t<name><![CDATA[" + SSID + "]]></name>\n" +
				"\t<description><![CDATA[BSSID: <b>"+MAC+"</b>" +
				"<br/>Capabilities: <b>"+AuthMode+"</b>"+ 
				"<br/>Date: <b>" + FirstSeen + "</b>]]></description><styleUrl>#red</styleUrl>\n"+
				"\t<Point>\n" +
				"\t\t<coordinates>"+point.y()+","+point.x()+ "</coordinates>\n" +
				"\t</Point>\n" +
				"\t</Placemark>\n";

	}

	/**
	 * this method convert the file from Csv to Kml .
	 */
	public void write() {
		// The name of the file to open.
		String fileName =CsvName.substring(0, CsvName.length()-4)+".kml";
		try {
			// Assume default encoding.
			FileWriter fileWriter =
					new FileWriter(fileName);

			// Always wrap FileWriter in BufferedWriter.
			BufferedWriter bufferedWriter =
					new BufferedWriter(fileWriter);
			BufferedReader br = new BufferedReader(new FileReader(CsvName));
			String line = ""; //one row from the csv file
			br.readLine();
			br.readLine();
			line = br.readLine();
			bufferedWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
					"<kml xmlns=\"http://www.opengis.net/kml/2.2\"><Document><Style id=\"red\"><IconStyle><Icon><href>http://maps.google.com/mapfiles/ms/icons/red-dot.png</href></Icon></IconStyle></Style><Style id=\"yellow\"><IconStyle><Icon><href>http://maps.google.com/mapfiles/ms/icons/yellow-dot.png</href></Icon></IconStyle></Style><Style id=\"green\"><IconStyle><Icon><href>http://maps.google.com/mapfiles/ms/icons/green-dot.png</href></Icon></IconStyle></Style><Folder><name>Wifi Networks</name>\n"); //add the open of the line
			//br.readLine(); //skip the first line on csv
			while (line != null && !line.isEmpty()) {
				if((line.replaceAll(",","")).replaceAll(" ","").isEmpty())
					break;
				String[] row = line.split(",");
				bufferedWriter.write(Placemark(row[1], row[0], row[2], row[3], new Point3D(Double.parseDouble(row[6]),Double.parseDouble(row[7]))));
				line = br.readLine();
			}
			// Note that write() does not automatically
			// append a newline character.
			bufferedWriter.write("</Folder>\n" + 
					"</Document></kml>");
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