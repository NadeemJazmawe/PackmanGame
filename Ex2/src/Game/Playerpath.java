package Game;

import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;

import Coords.MyCoords;
import Geom.Circle;
import Robot.Fruit;
import Robot.Packman;
import Robot.ghosts;
import Robot.player;

public class Playerpath{
	private ArrayList<Packman> pac_list;//packman list
	private ArrayList<Fruit> fruit_list;//fruit list
	private ArrayList<ghosts> ghost_list;//ghost list
	private player Player; //our player
	public Playerpath(ArrayList<Packman> pac_list, ArrayList<Fruit> fruit_list, ArrayList<ghosts> ghost_list, player player) {
		super();
		this.pac_list = pac_list;
		this.fruit_list = fruit_list;
		this.ghost_list = ghost_list;
		Player = player;
		workall();
	}

	private void workall() {
		deletepac();
		deletefruit();
		moveghosts();
		movepac();
	}
	private void moveghosts() {

	}
	private void movepac() {
		//pac_list.get(i).setCoords(new Circle(newPath.path.get(i).Slope.get(j),pac_list.get(i).getCoords().get_radius()));

		for(Packman pac : pac_list) {
			Fruit f = closestfruit(pac);
			pac.setCoords(new Circle(f.getFruit() , pac.getCoords().get_radius()));
		}
	}
	private Fruit closestfruit(Packman pac) {
		MyCoords Coords = new MyCoords();
		double Dist = 0.0 , min =Integer.MAX_VALUE ;
		Fruit ans = null ;
		for(int i=0 ; i < fruit_list.size() ; i++) {
			Fruit f = fruit_list.get(i);
			Dist = Math.abs(Coords.distance3d(pac.getCoords().get_cen(), f.getFruit()));
			if(Dist < min) {
				min = Dist ;
				ans = f ;
			}
		}
		return ans ;
	}

	private void deletepac() {
		for(Packman pac : pac_list) 
			if(pac.getCoords() == Player.getCoords())
				pac_list.remove(pac);
	}
	private void deletefruit() {
		for(Fruit fruit : fruit_list)
			if(Player.getCoords().get_cen() == fruit.getFruit())
				fruit_list.remove(fruit);
	}

	public ArrayList<Packman> getPac_list() {return pac_list;}
	public ArrayList<Fruit> getFruit_list() {return fruit_list;}
	public ArrayList<ghosts> getGhost_list() {return ghost_list;}
	public player getPlayer() {return Player;}





}
