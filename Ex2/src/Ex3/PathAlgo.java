package Ex3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Semaphore;
import Coords.MyCoords;
import Geom.Circle;
import Geom.Point3D;
//we tried to use thread for every packmam to run on all the fruits and we have an issue that in some ways the same fruit eaten by two packmans
public class PathAlgo {	
	private ArrayList<Packman> pac;
	private ArrayList<Fruit> fruit;
	int[] removed;
	private Semaphore allDone;
	ArrayList<Path3> path=new ArrayList<Path3>();
	public PathAlgo(ArrayList<Packman> pac,ArrayList<Fruit> fruit) {
		this.pac=pac;
		this.fruit=fruit;
		removed=new int[fruit.size()];
	}
	public class Path3 extends Thread{
		Packman packman;
		int index=0;
		Point3D closest=null;
		double time = 0.0;
		ArrayList<Point3D> GPS = new ArrayList<Point3D>();
		Path3(Packman packman,int index) {
			super();
			this.packman = packman;
			GPS.add(packman.getCoords().get_cen());
			this.index=index;
		}
		@Override
		public String toString() {
			String s="Path (Packman=" + packman.getCoords().get_cen()+ ", Time=" + time + ", GPS=";
			for (Point3D point3d : GPS) {
				s+=" , "+point3d;
			}
			return s+=")";
		}

		public void sort(){
			for(int i=0 ; i<path.size()-1 ; i++) {
				for(int j=0 ; j<path.get(i).GPS.size() ; j++) {
					int[] check = search( i+1 , path.get(i).GPS.get(j)) ;
					if(check != null) {

					}
				}
			}
		}
		public int[] search(int start , Point3D fruit) {
			int pathnum = start , fruitnum = 0 ;
			for(; pathnum<path.size() ; pathnum++) {
				for( fruitnum=0 ; fruitnum<path.get(pathnum).GPS.size() ; fruitnum++) {
					if(path.get(pathnum).GPS.get(fruitnum) == fruit) {
						int [] ans = { pathnum , fruitnum} ;
						return ans ;
					}
				}
			}
			return null ;
		}



		public Point3D getClosestFruit(Point3D current_p) {
			MyCoords Coords = new MyCoords();
			Point3D p=null;
			int index=0;
			double Dist = 0.0,min=Integer.MAX_VALUE;
			for(int i=0;i<fruit.size();i++){
				Fruit current_Fruit = fruit.get(i);
				Dist = Coords.distance3d(current_p,current_Fruit.getFruit())/packman.getSpeed();
				if(removed[i]!=1&&Math.abs(Dist)<=min) {
					index=i;
					min=Math.abs(Dist);
					p=current_Fruit.getFruit();
				}
			}
			//if(closest==null&&p!=null) {
			closest=p;
			remove(p);
			//    System.out.println("This Dist from packman : "+current_p +" from this fruit :"+closest+" is ="+min);	

			//GPS.add(closest);
			return p;
			//}
			//			else if(p==null)
			//				return null;
			//			else if(closest!=null)
			//				return getClosestFruit();
			//			else
			//				return null;
		}
		public int remove(Point3D f) {
			if(f!=null&&!f.equals(new Point3D(-1,-1,-1))) {
				for(int i=0;i<fruit.size();i++) {
					if(fruit!=null&&fruit.get(i).getFruit().equals(f)) {
						//fruit.remove(i);
						removed[i]=1;
						fruit.get(i).setFruit(new Point3D(-1,-1,-1));
						return i;
					}
				}
			}
			return -1;
		}
		public Point3D Get(int i) {
			Iterator<Point3D> itr=GPS.iterator();
			Point3D p=null;
			int index=0,j=0;
			while(itr.hasNext()) {
				Point3D f=itr.next();
				j++;
				if(i==j) {
					p=f;
					break;
				}
			}
			return p;
		}
		@Override
		public void run() {
			int j2=0;
			for(int j=0;j<fruit.size();j++) {
//				if(GPS.size()-1>j2)
//					j2++;
				Point3D i= getClosestFruit(GPS.get(j2));
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(i!=null) {
					GPS.add(i);
					j2++;
				}
				//System.out.println("Fruit Added : "+i+" For Packman : "+this.packman.getCoords().get_cen());
			}
			int len=0;
			for (int i = 0; i < removed.length; i++) {
				if(removed[i]==1)
					len++;
			}
			if(len==removed.length)
				fruit.clear();

			allDone.release();
		}
	}
	public void runParallel() {
		allDone = new Semaphore(0);
		// Make and start all the workers, keeping them in a list.
		path = new ArrayList<Path3>();
		int j=0;
		for (Packman pack : pac) {
			Path3 worker=new Path3(pack,j);
			j++;
			// Special case: make the last worker take up all the excess.
			path.add(worker);
			worker.start();
			//			try {
			//				//Thread.sleep(1);
			//				//worker.join();
			//			}
			//			catch(Exception e) {
			//			}
		}
		int numWorkers = j;
		// Wait to finish (this strategy is an alternative to join())
		try {
			allDone.acquire(numWorkers);
			// Note: here use acquire(N) ..
			// could instead init semaphore with -9 and use
			// regular acquire() here
		} catch (InterruptedException ignored) {
		}
		// Gather sums from workers (yay foreach!)
		for (Path3 w: path)
			System.out.println(w.toString());
	}
	public static void main(String[] args) {
		// command line argument: array_length
		ArrayList<Packman> pac=new ArrayList<Packman>();
		ArrayList<Fruit> fruit=new ArrayList<Fruit>();
		pac.add(new Packman(new Circle(new Point3D(10,10,10),10),10));
		pac.add(new Packman(new Circle(new Point3D(20,20,20),10),10));
		pac.add(new Packman(new Circle(new Point3D(50,50,50),10),10));
		fruit.add(new Fruit(new Point3D(5,5,5)));
		fruit.add(new Fruit(new Point3D(6,6,6)));
		fruit.add(new Fruit(new Point3D(7,7,7)));
		fruit.add(new Fruit(new Point3D(8,8,8)));
		fruit.add(new Fruit(new Point3D(9,9,9)));
		fruit.add(new Fruit(new Point3D(52,52,52)));
		fruit.add(new Fruit(new Point3D(25,25,25)));
		fruit.add(new Fruit(new Point3D(15,15,15)));
		fruit.add(new Fruit(new Point3D(35,35,35)));
		PathAlgo at = new PathAlgo(pac,fruit);
		at.runParallel();
	}
}
