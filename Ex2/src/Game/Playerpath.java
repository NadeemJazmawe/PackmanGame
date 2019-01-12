package Game;

import java.util.ArrayList;
import java.util.Date;

import Coords.MyCoords;
import Geom.Circle;
import Geom.Point3D;
import Robot.Box;
import Robot.Fruit;
import Robot.Packman;
import Robot.ghosts;
import Robot.player;

public class Playerpath{
	final double First_Lon= 35.202436;//the first global longitude from the (0,0) point on the frame 
	final double First_Lat = 32.105712;//the first global latitude from the (0,0) point on the frame 
	final double Second_Lon = 35.212441;//the last long on the frame
	final double Second_Lat = 32.101856;//the last lat on the frame
	private ArrayList<Packman> pac_list;//packman list
	private ArrayList<Fruit> fruit_list;//fruit list
	private ArrayList<ghosts> ghost_list;//ghost list
	private ArrayList<Box> box_list ;//all the box list
	private player Player; //our player
	private Point3D vec ;
	private double time = 0;
	private Date gameDate ;
	public Playerpath(ArrayList<Packman> pac_list, ArrayList<Fruit> fruit_list, ArrayList<ghosts> ghost_list, player player,Point3D p,Date date , ArrayList<Box> Box_List) {
		super();
		this.pac_list = pac_list;
		this.fruit_list = fruit_list;
		this.ghost_list = ghost_list;
		Player = player;
		this.box_list = Box_List ;
		gameDate = new Date();
		playerVec(player, p);
		//workall(date);
	}
	public void playerVec(player Player, Point3D f) {
		MyCoords coord = new MyCoords();
		time=coord.distance3d(f,Player.getCoords().get_cen())/Player.getSpeed(); ;
		vec =new  Point3D(f.x() - Player.getCoords().get_cen().x() , f.y() - Player.getCoords().get_cen().y() , f.z() - Player.getCoords().get_cen().z());
		//return new Point3D(Player.getCoords().get_cen().x() + vec.x()/time , Player.getCoords().get_cen().y() + vec.y()/time, Player.getCoords().get_cen().z() + vec.z()/time);		
	}
	public void workall(Date date) {
		moveplayer();
		deletepac();
		deletefruit();
		moveghosts(date);
		movepac();
	}
	private void moveplayer() {
		Point3D p = Player.getCoords().get_cen() ;
		p = new Point3D(Player.getCoords().get_cen().x() + vec.x()/time , Player.getCoords().get_cen().y() + vec.y()/time, Player.getCoords().get_cen().z() + vec.z()/time);
		if(getOut(p)) {
			Player.setCoords(new Circle(p , Player.getCoords().get_radius())); ;
			Fruit f = closestfruit(Player.getCoords());
			MyCoords coords = new MyCoords() ;
			if(coords.distance3d(Player.getCoords().get_cen(),f.getFruit()) <= 1.5*Player.getCoords().get_radius()) {
				fruit_list.remove(f);
				Player.setPoint(1);
			}
		}
	}
	private void moveghosts(Date date) {
		Date dateNow = new Date();
		if(ghost_list != null & Player != null) {
			MyCoords coords=new MyCoords();
			for(ghosts g : ghost_list) {
				Point3D vec = new Point3D( Player.getCoords().get_cen().x() -g.getCoords().get_cen().x()  , Player.getCoords().get_cen().y() - g.getCoords().get_cen().y()  , Player.getCoords().get_cen().z() - g.getCoords().get_cen().z() );
				double time1=((coords.distance3d(Player.getCoords().get_cen() , g.getCoords().get_cen()))/g.getSpeed());
				if(time1 != 0) {
					Point3D goTo = new Point3D(g.getCoords().get_cen().x()+vec.x()/time1 ,g.getCoords().get_cen().y() + vec.y()/time1 , g.getCoords().get_cen().z()+ vec.z()/time1);
					g.setCoords(new Circle(goTo , g.getCoords().get_radius()));
					if(coords.distance3d(Player.getCoords().get_cen() , g.getCoords().get_cen())<= g.getCoords().get_radius()) 
						if(dateNow.getTime()- date.getTime() >= 3000) {
							Player.setPoint(-20);
							date.setTime(dateNow.getTime());
						}
				}
			}
		}
	}
	private void movepac() {
		if(pac_list != null && fruit_list != null) {
			MyCoords coords=new MyCoords();
			Fruit f = null ;
			for(Packman pac : pac_list) {
				if(fruit_list.isEmpty())
					break;
				f = closestfruit(pac.getCoords());
				Point3D vec = new Point3D(f.getFruit().x() - pac.getCoords().get_cen().x() , f.getFruit().y() - pac.getCoords().get_cen().y(), f.getFruit().z() -pac.getCoords().get_cen().z());
				double time1=((coords.distance3d(f.getFruit(),pac.getCoords().get_cen()))/pac.getSpeed());
				Point3D goTo = new Point3D(pac.getCoords().get_cen().x()+vec.x()/time1 ,pac.getCoords().get_cen().y()+vec.y()/time1 , pac.getCoords().get_cen().z()+vec.z()/time1);
				double max=Math.max(f.getFruit().x() , pac.getCoords().get_cen().x());
				double min=Math.min(f.getFruit().x() , pac.getCoords().get_cen().x());
				if(goTo.x() < max && goTo.x() > min) {
					pac.setCoords(new Circle(goTo , pac.getCoords().get_radius()));
				}
				else {
					pac.setCoords(new Circle(f.getFruit() , pac.getCoords().get_radius()));
					fruit_list.remove(f);
				}
			}
		}
	}
	private Fruit closestfruit(Circle c) {
		MyCoords Coords = new MyCoords();
		double Dist = 0.0 , min =Double.MAX_VALUE ;
		Fruit ans = null ;
		for(int i=0 ; i < fruit_list.size() ; i++) {
			Fruit f = fruit_list.get(i);
			Dist = Math.abs(Coords.distance3d(c.get_cen(), f.getFruit()));
			if(Dist <= min) {
				min = Dist ;
				ans = f ;
			}
		}
		return ans ;
	}
	private void deletepac() {
		MyCoords coords = new MyCoords();
		for(int i=0 ; i<pac_list.size() ; i++)
			if(coords.distance3d(Player.getCoords().get_cen(),pac_list.get(i).getCoords().get_cen()) <= 1.5*Player.getCoords().get_radius()) {
				pac_list.remove(pac_list.get(i));
				Player.setPoint(1);
			}
	}
	private void deletefruit() {
		MyCoords coords = new MyCoords();
		for(int i=0 ; i<fruit_list.size() ; i++) 
			if(coords.distance3d(Player.getCoords().get_cen(),fruit_list.get(i).getFruit()) <= 1.5*Player.getCoords().get_radius()) {
				fruit_list.remove(fruit_list.get(i));
				Player.setPoint(1);
				i--;
			}
	}
	private boolean getOut(Point3D p) {
		Date dateNow1 = new Date();
		if(!(p.x() < First_Lat && p.x() > Second_Lat && p.y() > First_Lon && p.y() < Second_Lon)) {
			if(dateNow1.getTime() - gameDate.getTime() >= 3000) {
				Player.setPoint(-1);
				gameDate = new Date() ;
			}
			return false;
		}
		for(int i=0 ; i < box_list.size() ; i++) {
			if( ((p.x() < Double.max(box_list.get(i).getMin().x() , box_list.get(i).getMax().x())) && (p.x() > Double.min(box_list.get(i).getMin().x() , box_list.get(i).getMax().x())))&&
					((p.y() < Double.max(box_list.get(i).getMin().y() , box_list.get(i).getMax().y())) && (p.y() > Double.min(box_list.get(i).getMin().y() , box_list.get(i).getMax().y())))) {
				if(dateNow1.getTime() - gameDate.getTime() >= 3000) {
					Player.setPoint(-1);
					gameDate = new Date() ;
				}
				return false;
			}
		}
		return true;
	}

	public ArrayList<Packman> getPac_list() {return pac_list;}
	public ArrayList<Fruit> getFruit_list() {return fruit_list;}
	public ArrayList<ghosts> getGhost_list() {return ghost_list;}
	public player getPlayer() {return Player;}





}
