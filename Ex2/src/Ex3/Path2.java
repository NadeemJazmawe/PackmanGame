package Ex3;

import java.util.ArrayList;
import java.util.Iterator;
import Coords.MyCoords;
import Geom.Circle;

public class Path2 {
	private ArrayList<Packman> pac;
	private ArrayList<Fruit> fruit;
	ArrayList<Path> path=new ArrayList<Path>();
	public ArrayList<Path> getPath() {
		return path;
	}
	public Path2(ArrayList<Packman> pac,ArrayList<Fruit> fruit) {
		this.pac=pac;
		this.fruit=fruit;
		for (int j = 0; j < pac.size(); j++) {
			path.add(new Path(pac.get(j),j));
		}
	}
	public double getTime(Packman packman){
		return getDist(packman)/packman.getSpeed();
	}
	public double getDist(Packman packman){
		MyCoords Coords = new MyCoords();
		double totalDist = 0.0;
		Iterator<Fruit> itr = fruit.iterator();
		Fruit current_Fruit = itr.next();
		while (itr.hasNext()){
			Fruit Next_Fruit = itr.next();
			totalDist += Coords.distance3d(current_Fruit.getFruit(),Next_Fruit.getFruit());
			current_Fruit = Next_Fruit;
		}
		return totalDist;
	}
	public int getClosestPac(Fruit fruit,ArrayList<Packman> pacman) {
		MyCoords Coords = new MyCoords();
		double Dist = 0.0,min=0;
		Iterator<Packman> itr = pacman.iterator();
		int i=-1,index=0;
		while (itr.hasNext()){
			Packman current_Pac = itr.next();
			i++;
			Dist = Coords.distance3d(current_Pac.getCoords().get_cen(),fruit.getFruit())/current_Pac.getSpeed();
			if(Math.abs(Dist)<min) {
				min=Math.abs(Dist);
				index=i;
			}
		}
		return index;
	}
	public ArrayList<Packman> getNewPac(){
		ArrayList<Packman> pacman=new ArrayList<Packman>();
		for (Packman packman2 : pac) {
			pacman.add(packman2);
		}
		return pacman;
	}
	public ArrayList<Fruit> getNewFruit(){
		ArrayList<Fruit> fruit=new ArrayList<Fruit>();
		for (Fruit fruit2 : fruit) {
			fruit.add(fruit2);
		}
		return fruit;
	}
	public void eaten(Packman packman,Fruit fruit) {
		packman.setCoords(new Circle(fruit.getFruit(),packman.getCoords().get_radius()));
	}
	public void getMinTime(){
		ArrayList<Packman> newPac=getNewPac();
		ArrayList<Fruit> newFruit=getNewFruit();
		for (int i=0;i<newFruit.size();) {
			Fruit f=newFruit.get(i);
			int closest=getClosestPac(f,newPac);
			path.get(closest).getGPS().get(closest).add(f.getFruit());
			System.out.println("the Packman is:"+ newPac.get(closest));
			//eaten(newPac.get(closest), f);
			newPac.get(closest).setCoords(new Circle(f.getFruit(),newPac.get(closest).getCoords().get_radius()));
			System.out.println("the Packman now is:"+ newPac.get(closest));
			newFruit.remove(f);
		}
	}
	@Override
	public String toString() {
		String s="";
		for (Path path2 : path) {
			s+=path2.toString()+"\n";
		}
		return s;
	}
}
