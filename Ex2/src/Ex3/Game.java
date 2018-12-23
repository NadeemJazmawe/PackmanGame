package Ex3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import Geom.Circle;
import Geom.Point3D;
/**
 * This class represent the game class that read from csv and wrote to csv that every csv file have a fruit and packman
 * @author jbareen Mohamad nadeem jazmawe 
 * 207392283_314638867
 */
public class Game {
	private ArrayList<Packman> packman ;//the list of all the packmans on the game
	private ArrayList<Fruit> fruit;//the list of all the fruit from the game
	public ArrayList<Fruit> getFruit() {
		return fruit;
	}
	public void setFruit(ArrayList<Fruit> fruit) {
		this.fruit = fruit;
	}
	public ArrayList<Packman> getPackman() {
		return packman;
	}
	public void setPackman(ArrayList<Packman> packman) {
		this.packman = packman;
	}

	public Game() {
		packman=new ArrayList<Packman>();
		fruit=new ArrayList<Fruit>();
	}
	/**
	 * this function saves the game in local project by the packman list and the fruit list
	 * @param fileName name for the file saved
	 */
	public void saveGame(String fileName) {
		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter(fileName);//make a file with this name
			fileWriter.append("Type,id,Lat,Lon,Alt,Speed/Weight,Radius\n");//add to the first line this string
			for (Packman packman2:packman) {//to move on all the list of the packman
				fileWriter.append("P,"+packman2.getId()+","+packman2.getCoords().get_cen().x()+","
						+packman2.getCoords().get_cen().y()+","+packman2.getCoords().get_cen().z()+","
						+packman2.getSpeed()+","+packman2.getCoords().get_radius()+"\n");
			}//and add the packman by the point and speed, id, radius
			for (Fruit fruit2:fruit) {//the same for the fruit list
				fileWriter.append("F,"+fruit2.getId()+","+fruit2.getFruit().x()+","
						+fruit2.getFruit().y()+","+fruit2.getFruit().z()+",1\n");
			}
			fileWriter.flush();//make file
			fileWriter.close();//close the file after finishing
		}
		catch (Exception e) {
			e.getStackTrace();
		}
	}

	public void print() {//prints the fruit list
		for (Fruit fruit2:fruit) {
			System.out.println(fruit2.getFruit().x()+","+fruit2.getFruit().y());
		}
	}
	public static void main(String[] args) {
		Game game = new Game("game_1543684662657.csv");
		//		game.fruit.add(new Fruit(new Point3D(32.10526801,	35.21085983,	0)));
		//		game.fruit.add(new Fruit(new Point3D(32.1001,35.2183,	0)));
		//		game.fruit.add(new Fruit(new Point3D(32.10512326801,	35.2123085983,	0)));
		//
		//		game.packman.add(new Packman(new Circle(new Point3D(32.10526801,35.21085983,0),10),10));
		//		game.packman.add(new Packman(new Circle(new Point3D(32.1026801,	35.2105983,	0),10),10));
		//		game.packman.add(new Packman(new Circle(new Point3D(32.106801,	35.25983,	0),10),10));
		//		game.saveGame("asd.csv");
		game.print();
	}
	/**
	 * this constructor is reading from files by the string name 
	 * @param name name of the file
	 */
	public Game(String name) {//this constructor is reading from files by the string name 
		packman=new ArrayList<Packman>();
		fruit=new ArrayList<Fruit>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(name));//BufferedReader that is reading from files  
			br.readLine();//esc the first line
			String line=br.readLine();//save the second line to line
			while (line != null && !line.isEmpty()) {//move on all the lines
				if((line.replaceAll(",","")).replaceAll(" ","").isEmpty())
					break;
				String[] row = line.split(",");
				//[0] type [1] id [2] lat [3] lon [4] alt [5] speed [6] radius
				if(row[0].equals("F")) {
					fruit.add(new Fruit(new Point3D(Double.parseDouble(row[2]),Double.parseDouble(row[3]),Double.parseDouble(row[4]))));
				}
				else if(row[0].equals("P")) {
					packman.add(new Packman(new Circle(new Point3D(Double.parseDouble(row[2]),Double.parseDouble(row[3]),Double.parseDouble(row[4])),Integer.parseInt(row[6])),Integer.parseInt(row[5])));
				}
				line = br.readLine();
			}
		}
		catch(Exception ex) {
			System.out.println(
					"Error Converting csv file '" 
							+ name + "'");                  
		}
	}
}
