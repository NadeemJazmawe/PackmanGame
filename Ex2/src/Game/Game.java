package Game;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;

import Geom.Circle;
import Geom.Point3D;
import Robot.Box;
import Robot.Fruit;
import Robot.Packman;
import Robot.ghosts;
import Robot.player;
import sun.security.provider.JavaKeyStore.DualFormatJKS;
/**
 * This class represent the game class that read from csv and wrote to csv that every csv file have a fruit and packman
 * @author jbareen Mohamad nadeem jazmawe 
 * 207392283_314638867
 */
public class Game {
	private ArrayList<Packman> packman ;//the list of all the packmans on the game
	private ArrayList<Fruit> fruit;//the list of all the fruit from the game
	private player Player = null;
	private ArrayList<ghosts> ghost ;
	private ArrayList<Box> box ;

	public player getPlayer() {
		return Player;
	}
	public void setPlayer(player player) {
		Player = player;
	}
	public ArrayList<ghosts> getGhost() {
		return ghost;
	}
	public void setGhost(ArrayList<ghosts> ghost) {
		this.ghost = ghost;
	}
	public ArrayList<Box> getBox() {
		return box;
	}
	public void setBox(ArrayList<Box> box) {
		this.box = box;
	}
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
		box = new ArrayList<Box>();
		ghost = new ArrayList<ghosts>();
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

			if(Player != null) {// to print the player .
				fileWriter.append("M,"+1+","+Player.getCoords().get_cen().x()+","
						+Player.getCoords().get_cen().y()+","+Player.getCoords().get_cen().z()+","
						+Player.getSpeed()+","+Player.getCoords().get_radius()+"\n");
			}
			for (Packman packman2:packman) {//to move on all the list of the packman
				fileWriter.append("P,"+packman2.getId()+","+packman2.getCoords().get_cen().x()+","
						+packman2.getCoords().get_cen().y()+","+packman2.getCoords().get_cen().z()+","
						+packman2.getSpeed()+","+packman2.getCoords().get_radius()+"\n");
			}//and add the packman by the point and speed, id, radius
			for (Fruit fruit2:fruit) {//the same for the fruit list
				fileWriter.append("F,"+fruit2.getId()+","+fruit2.getFruit().x()+","
						+fruit2.getFruit().y()+","+fruit2.getFruit().z()+",1\n");
			}//and add the fruit by the point , id and radius
			for (ghosts ghost1:ghost) {//to move on all the list of the ghosts
				fileWriter.append("G,"+ghost1.getId()+","+ghost1.getCoords().get_cen().x()+","
						+ghost1.getCoords().get_cen().y()+","+ghost1.getCoords().get_cen().z()+","
						+ghost1.getSpeed()+","+ghost1.getCoords().get_radius()+"\n");
			}//and add the ghost by the point and speed, id, radius
			for (Box b :box) {//to move on all the list of the box
				fileWriter.append("B,"+b.getId()+","+b.getMin().x()+","
						+b.getMin().y()+","+b.getMin().z()+","
						+b.getMax().x()+","+b.getMax().y()+"," 
						+b.getMax().z()+","+ 1 +"\n");
			}//and add the box by the two point 
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
		Game game = new Game("C:\\Users\\Nadeem\\Desktop\\Ex4\\Ex4_OOP_example5.csv");
		//		game.fruit.add(new Fruit(new Point3D(32.10526801,	35.21085983,	0)));
		//		game.fruit.add(new Fruit(new Point3D(32.1001,35.2183,	0)));
		//		game.fruit.add(new Fruit(new Point3D(32.10512326801,	35.2123085983,	0)));
		//
		//		game.packman.add(new Packman(new Circle(new Point3D(32.10526801,35.21085983,0),10),10));
		//		game.packman.add(new Packman(new Circle(new Point3D(32.1026801,	35.2105983,	0),10),10));
		//		game.packman.add(new Packman(new Circle(new Point3D(32.106801,	35.25983,	0),10),10));
		//		game.saveGame("asd.csv");
		//game.print();
	}
	/**
	 * this constructor is reading from files by the string name 
	 * @param name name of the file
	 */
	public Game(String name) {//this constructor is reading from files by the string name 
		packman=new ArrayList<Packman>();
		fruit=new ArrayList<Fruit>();
		box = new ArrayList<Box>();
		ghost = new ArrayList<ghosts>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(name));//BufferedReader that is reading from files  
			br.readLine();//esc the first line
			String line=br.readLine();//save the second line to line
			while (line != null && !line.isEmpty()) {//move on all the lines
				if((line.replaceAll(",","")).replaceAll(" ","").isEmpty())
					break;
				String[] row = line.split(",");
				//[0] type [1] id [2] lat [3] lon [4] alt [5] speed [6] radius
				System.out.println(Arrays.toString(row));
				if(row[0].equals("F")) {
					fruit.add(new Fruit(new Point3D(Double.parseDouble(row[2]),Double.parseDouble(row[3]),Double.parseDouble(row[4]))));
				}
				else if(row[0].equals("P")) {
					System.out.println("packman added" );
					packman.add(new Packman(new Circle(new Point3D(Double.parseDouble(row[2]),Double.parseDouble(row[3]),Double.parseDouble(row[4])),Integer.parseInt(row[6])),Integer.parseInt(row[5])));
				}
				else if(row[0].equals("M")) {
					Player = new player(new Circle(new Point3D(Double.parseDouble(row[2]),Double.parseDouble(row[3]),Double.parseDouble(row[4])) , Integer.parseInt(row[6])) , Integer.parseInt(row[5]) ) ;
				}
				else if(row[0].equals("B")) {
					box.add( new Box(new Point3D(Double.parseDouble(row[2]),Double.parseDouble(row[3]),Double.parseDouble(row[4])) , new Point3D(Double.parseDouble(row[5]),Double.parseDouble(row[6]),Double.parseDouble(row[7]))));
				}
				else if(row[0].equals("G")) {
					ghost.add(new ghosts(new Circle(new Point3D(Double.parseDouble(row[2]),Double.parseDouble(row[3]),Double.parseDouble(row[4])) , Integer.parseInt(row[6])) , Integer.parseInt(row[5])) ) ;
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
