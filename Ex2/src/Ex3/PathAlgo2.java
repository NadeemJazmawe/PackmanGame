package Ex3;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;

import Coords.MyCoords;
import Geom.Circle;
import Geom.Point3D;
/**
 * this class represent the Path algorithm for the game by taking all the fruit and add it to packman that can eat it faster
 * @author jbareen Mohamad nadeem jazmawe 
 * 207392283_314638867
 */
public class PathAlgo2 {	
	private ArrayList<Packman> pac;//the packman list 
	private ArrayList<Fruit> fruit;//the fruit list
	ArrayList<Path4> path=new ArrayList<Path4>();//the path for every packman 
	public PathAlgo2(ArrayList<Packman> pac,ArrayList<Fruit> fruit) {//constructor that build a path for every packman and add the packman into it
		this.pac=pac;
		this.fruit=fruit;
		int i=0;
		for (Packman pack2 : pac) {
			path.add(new Path4());
			path.get(i).GPS.add(new Fruit(pack2.getCoords().get_cen()));
			path.get(i).speed=pack2.getSpeed();
			path.get(i).packman=new Point3D(pack2.getCoords().get_cen());
			i++;
		}
	}
	@Override
	public String toString() {//print all the paths
		String s="";
		for (Path4 path2 : path) {
			s+=path2.toString()+"\n";
		}
		return s;
	}
	/**
	 * this class represent a path for a single packman by the fruit he must to eat
 * @author jbareen Mohamad nadeem jazmawe 
 * 207392283_314638867
	 */
	public class Path4 extends Thread{
		double time = 0.0;
		ArrayList<Double> PathTime=new ArrayList<Double>();//the times he took to eat the next fruit
		ArrayList<Point3D> Slope=new ArrayList<Point3D>();//all the points he must step from fruit to fruit by the speed and time
		ArrayList<String> PacTimeList=new ArrayList<String>();//the list of time to show on the kml file
		ArrayList<String> FruitTimeList=new ArrayList<String>();//the list of time to show on the kml file
		int speed=0;
		Point3D packman;
		ArrayList<Fruit> GPS = new ArrayList<Fruit>();//the path that contain the packman and all the fruit to eat
		public ArrayList<Fruit> getGPS() {
			return GPS;
		}
		@Override
		public String toString() {
			String s="Path [ GPS:";
			for (Fruit point3d : GPS) {
				s+=" , "+point3d.getFruit();
			}
			return s+=" Time: "+time+" ]";
		}
		public void Locations_2_points() {//this function add the time for the packman to kml and added all the points he must to step
			Slope.add(GPS.get(0).getFruit());
			int LocalSec=1;
			int LocalMin=0;
			int LocalHour=0;
			String timeLocation="2018-12-16T00:00:00Z";//the time that showed on kml
			PacTimeList.add(timeLocation);
			for (int i = 0; i < GPS.size()-1; i++) {//move on all gps points and add the steps by compute the vector between two points
				Fruit f1=GPS.get(i);//the first point
				Fruit f2=GPS.get(i+1);//the second point
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
		public void swap(int i,int j) {//to swap between to points on the gps list to sort them
			Fruit f3=new Fruit(GPS.get(i).getFruit());
			Fruit f4=new Fruit(GPS.get(j).getFruit());
			GPS.add(i, f4);
			GPS.remove(i+1);
			GPS.add(j, f3);
			GPS.remove(j+1);
		}
		/**
		 * sort the gps list by order that who is closest to the packman
		 */
		public void sort() {//sort the gps list by order that who is closest to the packman
			MyCoords Coord=new MyCoords();
			int index=0;
			for (int i = 0; i < GPS.size()-1; i++) {
				double min=Double.MAX_VALUE;
				Fruit nextFruit=GPS.get(i);
				for (int j = i+1; j < GPS.size(); j++) {
					Fruit f2=GPS.get(j);
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
		public ArrayList<Path4> PathCopy(ArrayList<Path4> pa) {//copy the path of this packman
			ArrayList<Path4> newArray=new ArrayList<Path4>();
			for (int i = 0; i < pa.size(); i++) {
				Path4 newPath=new Path4();
				newPath.time=pa.get(i).time;
				newPath.GPS=pa.get(i).getGPS();
				newPath.speed=pa.get(i).speed;
				newArray.add(newPath);
			}
			return newArray;
		}
		public void MinTime(Path4 a,Path4 b) {//sort two paths to get min time
			double totalORIG=Math.max(a.time, b.time);
			Path4 maxPath=null;
			Path4 minPath=null;
			if(a.time>b.time) {
				maxPath=a;
				minPath=b;
			}
			else {
				maxPath=b;
				minPath=a;	
			}
			for(int i= maxPath.GPS.size()-1;i>0;i--) {
				Fruit swap=maxPath.GPS.get(i);
				minPath.GPS.add(swap);
				minPath.sort();
				minPath.getTime();
				for (int j = 0; j < maxPath.GPS.size(); j++) {
					if(maxPath.GPS.get(j).getFruit().equals(swap.getFruit())) {
						maxPath.GPS.remove(j);
						break;
					}
				}
				//maxPath.GPS.remove(swap);
				maxPath.sort();
				maxPath.getTime();
				if(totalORIG<Math.max(a.time, b.time)) {
					maxPath.GPS.add(swap);
					maxPath.sort();
					maxPath.getTime();
					for (int j = 0; j < minPath.GPS.size(); j++) {
						if(maxPath.GPS.get(j).getFruit().equals(swap.getFruit())) {
							minPath.GPS.remove(j);
							break;
						}
					}
					//minPath.GPS.remove(swap);
					minPath.sort();
					minPath.getTime();
				}
				else {
					i--;
				}
			}
		}
		public void sortByMinTime() {//uses the mintime function to sort all the paths
			ArrayList<Path4> pathCopy=PathCopy(path);
			if(pathCopy.size()>1)
				for (int i=0;i<pathCopy.size()-1;i++){
					for (int j = i+1; j < pathCopy.size(); j++) {
						MinTime(pathCopy.get(i),pathCopy.get(j));
					}
				}
			path=pathCopy;
		}
		public int MaxGPSTime(ArrayList<Path4> path) {//return the index of the max time path
			double max=0;
			int index=0;
			for (Path4 path2 : path) {
				if(path2.getTime()>max) {
					max=path2.getTime();
					index++;
				}
			}
			return index;
		}
		public int MinGPSTime(ArrayList<Path4> path) {//return the index of the min time path
			double min=Double.MAX_VALUE;
			int index=0;
			for (Path4 path2 : path) {
				if(path2.getTime()>min) {
					min=path2.getTime();
					index++;
				}
			}
			return index;
		}
		public double totalTime(ArrayList<Path4> path) {//return the total time of all the paths
			double max=0;
			for (Path4 path2 : path) {
				if(path2.getTime()>max)
					max=path2.getTime();
			}
			return max;
		}
		public void timeFromOneFruit() {//the time it took to eat a single fruit by speed and distance
			double time1=0;
			MyCoords coords=new MyCoords();
			PathTime.add((double) 0);
			for (int i = 0; i <GPS.size()-1; i++) {
				time1=coords.distance3d(GPS.get(i).getFruit(),GPS.get(i+1).getFruit());
				PathTime.add(time1/speed);
				//findPoints(GPS.get(i),GPS.get(i+1),time1/100);
			}
		}
		public double getTime(){//get the total time for this path by compute the distance between the packman and all the fruits
			time= getDist()/this.speed;
			return time;
		}
		public double getDist(){//compute the distance between the packman and all the fruits
			MyCoords Coords = new MyCoords();
			double totalDist = 0.0;
			Iterator<Fruit> itr = GPS.iterator();
			Fruit current_Fruit = itr.next();
			while (itr.hasNext()){
				Fruit Next_Fruit = itr.next();
				totalDist += Coords.distance3d(current_Fruit.getFruit(),Next_Fruit.getFruit());
				current_Fruit = Next_Fruit;
			}
			return totalDist;
		}

		public void getClosestPac(Fruit current_f) {//get the closest packman from the list for a single fruit
			MyCoords Coords = new MyCoords();
			double Dist = 0.0,min=Integer.MAX_VALUE;
			int index=0;
			Path4 newPath=null;
			for (int i = 0; i < path.size();i++) {
				Packman f=pac.get(i);
				Dist = Math.abs(Coords.distance3d(f.getCoords().get_cen(),current_f.getFruit())/f.getSpeed());
				if(Dist<min) {
					min=Dist;
					newPath=path.get(i);
					index=i;
				}
			}
			newPath.GPS.add(current_f);
			//fruit.remove(current_f);
		}
		public void runAll() {//this function run all the function from a single path and add it to all the paths
			for (int i = 0; i < fruit.size();i++) {
				getClosestPac(fruit.get(i));
			}
			for (int i = 0; i < path.size(); i++) {
				path.get(i).sort();
				path.get(i).getTime();
				path.get(i).timeFromOneFruit();
				path.get(i).Locations_2_points() ;
//				for (int k = 0; k < path.get(i).GPS.size(); k++) {
//					System.out.println("Path is : "+path.get(i).GPS.get(k).getFruit()+" ,");
//				}
				//System.out.println("Movement is : "+path.get(i).Slope);
				//packman=new Point3D(path.get(i).getGPS().get(path.get(i).GPS.size()-1).getFruit());//here
				//pac.get(i).setCoords(new Circle(packman,pac.get(i).getCoords().get_radius()));
			}
			//sortByMinTime();
		}

	}
	public void runParallel() {//make all the paths
		Path4 p=new Path4();
		p.runAll();
	}

	public static void main(String[] args) {
		// command line argument: array_length
		ArrayList<Packman> pac=new ArrayList<Packman>();
		ArrayList<Fruit> fruit=new ArrayList<Fruit>();
		pac.add(new Packman(new Circle(new Point3D(10,10,0),10),10000));
		//pac.add(new Packman(new Circle(new Point3D(20,20,0),10),1000));
		//pac.add(new Packman(new Circle(new Point3D(50,50,0),10),1000));
		fruit.add(new Fruit(new Point3D(5,5,0)));
		fruit.add(new Fruit(new Point3D(6,6,0)));
		fruit.add(new Fruit(new Point3D(9,9,0)));
		fruit.add(new Fruit(new Point3D(7,7,0)));
		fruit.add(new Fruit(new Point3D(8,8,0)));
		//		fruit.add(new Fruit(new Point3D(52,52,0)));
		//	fruit.add(new Fruit(new Point3D(25,25,0)));
		//fruit.add(new Fruit(new Point3D(15,15,0)));
		//		fruit.add(new Fruit(new Point3D(35,35,0)));
		PathAlgo2 at = new PathAlgo2(pac,fruit);
		at.runParallel();
		System.out.println(at.toString());
	}
}


