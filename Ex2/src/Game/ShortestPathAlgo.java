package Game;

import java.util.ArrayList;
import java.util.Arrays;

import Coords.MyCoords;
import Geom.Circle;
import Geom.Point3D;
import Robot.Fruit;
import Robot.Packman;
/*
 * this function represent the path algorithm for the packmans to eat the fruit n the game 
 * coded by jbareen mohamad, nadeem jazmawe
 * 207392283_314638867
 */
public class ShortestPathAlgo {
	
	private ArrayList<Packman> pac;//the packman list 
	private ArrayList<Fruit> fruit;//the fruit list
	ArrayList<path> all_path=new ArrayList<path>();//the path for every packman 
	public ShortestPathAlgo(ArrayList<Packman> pac,ArrayList<Fruit> fruit) {//constructor that build a path for every packman and add the packman into it
		this.pac=pac;
		this.fruit=fruit;
		int i=0;
		for (Packman pack2 : pac) {
			all_path.add(new path());
			all_path.get(i).fruitToEat.add(new Fruit(pack2.getCoords().get_cen()));
			for(Fruit f : fruit)
				all_path.get(i).fruitToEat.add(f);
			all_path.get(i).pac = pack2 ;
			all_path.get(i).sort();
			i++;
		}
		sortByTime();//this function sort the fruit to get eaten with min time 
	}
	public String toString() {//print all the paths
		String s="";
		for (path path2 : all_path) {
			s+=path2.toString()+"\n";
		}
		return s;
	}
	
	public void sortByTime() {//this function sort the fruit to be eaten with min time by the packman speed and distance from the fruits
		
		for(Fruit f : fruit) {
			double minTime = Double.MAX_VALUE ;
			double index = 0 ;
			int i=0;
			for(path pp : all_path) {
				if(minTime>pp.timeToFruit(pp.findOfFruit(f))) {//the min time using the findoffruit function
					minTime=pp.timeToFruit(pp.findOfFruit(f));//get the time 
					index=i;
				}
				i++;
			}
			for (int j = 0; j < all_path.size(); j++) {
				if(j!=index) {
					all_path.get(j).fruitToEat.remove(all_path.get(j).findOfFruit(f));//remove the fruit from the packman list that will eat it with more time than others
				}
			}
		}
		for(path pp : all_path) {
			pp.timeFromOneFruit();//the time for one fruit
			pp.Locations_2_points();//the points for the packman 
		}
	}

	
	
	 class path{
		Packman pac ;
		ArrayList<Fruit> fruitToEat = new ArrayList<Fruit>() ;//the gps points to the packman 
		ArrayList<Double> PathTime=new ArrayList<Double>();//the times he took to eat the next fruit
		ArrayList<Point3D> Slope=new ArrayList<Point3D>();//all the points he must step from fruit to fruit by the speed and time
		ArrayList<String> PacTimeList=new ArrayList<String>();//the list of time to show on the kml file
		ArrayList<String> FruitTimeList=new ArrayList<String>();//the list of time to show on the kml file
		public String toString() {
			String s="Path [ GPS:";
			for (Fruit point3d : fruitToEat) {
				s+=" , "+point3d.getFruit();
			}
			System.out.println(Slope.toString());
			return s+=" Time: "+timeToFruit(fruitToEat.size()-1)+" ]";
		}
		public void swap(int i,int j) {//to swap between to points on the gps list to sort them
			Fruit f3=new Fruit(fruitToEat.get(i).getFruit());
			Fruit f4=new Fruit(fruitToEat.get(j).getFruit());
			fruitToEat.add(i, f4);
			fruitToEat.remove(i+1);
			fruitToEat.add(j, f3);
			fruitToEat.remove(j+1);
		}
		/**
		 * sort the gps list by order that who is closest to the packman
		 */
		public void sort() {//sort the gps list by order that who is closest to the packman
			MyCoords Coord=new MyCoords();
			int index=0;
			for (int i = 0; i < fruitToEat.size()-1; i++) {
				double min=Double.MAX_VALUE;
				Fruit nextFruit=fruitToEat.get(i);
				for (int j = i+1; j < fruitToEat.size(); j++) {
					Fruit f2=fruitToEat.get(j);
					double Dist=Math.abs(Coord.distance3d(nextFruit.getFruit(), f2.getFruit()));
					if(Dist<min) {
						min=Dist;
						index=j;
					}
				}
				if(index!=0) {
					swap(i+1,index);
				}
			}
		}
		public double timeToFruit(int index) {//get the time from the packman to selected fruit
			double time=0;
			MyCoords coords = new MyCoords();
			
			for(int i =0 ; i<index ; i++) {
				time += coords.distance3d(fruitToEat.get(i).getFruit(), fruitToEat.get(i+1).getFruit()) ;
			}
			return time/pac.getSpeed();
		}
		public int findOfFruit(Fruit f) {
			for(int i =0 ; i <fruitToEat.size() ; i++) 
				if(f.getFruit().equals(fruitToEat.get(i).getFruit()))
					return i ;
			return (-1);
		}
		public void timeFromOneFruit() {//the time it took to eat a single fruit by speed and distance
			double time1=0;
			MyCoords coords=new MyCoords();
			PathTime.add((double) 0);
			for (int i = 0; i <fruitToEat.size()-1; i++) {
				time1=coords.distance3d(fruitToEat.get(i).getFruit(),fruitToEat.get(i+1).getFruit());
				PathTime.add(time1/pac.getSpeed());
				//findPoints(GPS.get(i),GPS.get(i+1),time1/100);
			}
		}
		public void Locations_2_points() {//this function add the time for the packman to kml and added all the points he must to step
			Slope.add(fruitToEat.get(0).getFruit());
			int LocalSec=1;
			int LocalMin=0;
			int LocalHour=0;
			String timeLocation="2018-12-16T00:00:00Z";//the time that showed on kml
			PacTimeList.add(timeLocation);
			for (int i = 0; i < fruitToEat.size()-1; i++) {//move on all gps points and add the steps by compute the vector between two points
				Fruit f1=fruitToEat.get(i);//the first point
				Fruit f2=fruitToEat.get(i+1);//the second point
				double max=Math.max(f1.getFruit().x(), f2.getFruit().x());
				double min=Math.min(f1.getFruit().x(), f2.getFruit().x());
				Point3D vec=new Point3D(f2.getFruit().x()-f1.getFruit().x(),f2.getFruit().y()-f1.getFruit().y(),f2.getFruit().z()-f1.getFruit().z());
				double Time=PathTime.get(i+1);
				Point3D currentF=new Point3D(f1.getFruit().x(),f1.getFruit().y(),f1.getFruit().z());
				for (int j = 0; j < Time; j++) {//move on all the steps by time
					Point3D vecF=new Point3D(currentF.x()+vec.x()/Time,currentF.y()+vec.y()/Time,currentF.y()+vec.z()/Time);
					currentF=new Point3D(vecF.x(),vecF.y(),vecF.z());
					if(currentF.x()<=max&&currentF.x()>=min) {//if the point to add is between the two points
						Slope.add(vecF);
						LocalSec++;
						if(LocalSec<10) {
							timeLocation=timeLocation.substring(0, 17)+"0"+LocalSec+"Z";
						}
						else if(LocalSec<60)
							timeLocation=timeLocation.substring(0, 17)+""+LocalSec+"Z";
						else {
							LocalSec=0;
							LocalMin++;
							if(LocalMin<10)
								timeLocation=timeLocation.substring(0, 14)+"0"+LocalMin+":00Z";
							else if(LocalMin<60)
								timeLocation=timeLocation.substring(0, 14)+""+LocalMin+":00Z";
							else {
								LocalMin=0;
								LocalHour++;
								if(LocalHour<10)
									timeLocation=timeLocation.substring(0,11) +"0"+LocalHour+":00:00Z";
								else if(LocalMin<60)
									timeLocation=timeLocation.substring(0,11) +""+LocalHour+":00:00Z";
							}
						}
						PacTimeList.add(timeLocation);
					}
				}
				Slope.add(f2.getFruit());
				PacTimeList.add(timeLocation);
				FruitTimeList.add(timeLocation);
			}
			FruitTimeList.add(timeLocation);
		}
	}
}
