package Ex3;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.color.ColorSpace;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import Ex3.PathAlgo.Path3;
import Ex3.PathAlgo2.Path4;
import Geom.Circle;
import Geom.Point3D;
/**
 * this class represent the mapp class of GUI frame that showed the map image of ariel and play the game on it 
 * that every packman run to eat all the fruit on his path by his speed
 * @author jbareen Mohamad nadeem jazmawe 
 * 207392283_314638867
 */
public class Map extends JFrame {
	private boolean FClicked=false,PClicked=false,CLEARC=false;//the packman button is clicked ,fruit and clear
	private MenuBar MB;//make the menu bar 
	private static int index=0;//index to run the threads
	private PathAlgo2 newPath;//the pathAlgorthm to all the packmans
	private PathAlgo2 copy2;//a copy of the pathAlgorthm
	private ArrayList<Packman> pac_list;//all the packman list
	private boolean DrawIsClicked=false;//buttun that if the path draw clicked or not
	private ArrayList<Fruit> fruit_list;//all the fruit list
	private ArrayList<Packman> pac_Copy;//a copy of the pac list
	private ArrayList<Fruit> fruit_Copy;//a copy of the fruit list
	public BufferedImage myImage;//the image of ariel 
	public void paint(Graphics g){//the paint function that draw all the packmans from the list and all the fruit too and draw the path for every packman
		g.drawImage(myImage, 0, 0,getWidth(), getHeight(),null);
		DrawFruit();
		DrawPackman();
		if(DrawIsClicked)
			DrawLines();
	}
	public void init_to_gui(String fileName) throws IOException {//the build of the frame GUI
		try {
			myImage = ImageIO.read(new File("Ariel1.png"));//set the ariel image to be the background
		} catch (IOException e) {
			e.printStackTrace();
		}	
		pac_list=new ArrayList<Packman>();
		fruit_list=new ArrayList<Fruit>();
		Clicks handler = new Clicks();//call the mouse adapter from the class clicks
		getContentPane().addMouseListener(handler);
		MB = new MenuBar();
		Menu File = new Menu("File");//add the file list to the menu bar
		MB.add(File);
		Menu Game = new Menu("Game");//add the game list for the menu  bar
		MB.add(Game);
		Menu PathD =new Menu("Path");//add the path list to the menu bar
		MB.add(PathD);
		Menu imp =new Menu("Import");//add the import list for the menu bar
		MB.add(imp);
		MenuItem PathDR = new MenuItem("Draw Path");
		MenuItem exit = new MenuItem("Exit");
		MenuItem clear = new MenuItem("clear");
		MenuItem run = new MenuItem("run");
		MenuItem save = new MenuItem("save");
		MenuItem import1 = new MenuItem("import");
		MenuItem fruit = new MenuItem("fruit");
		MenuItem packman = new MenuItem("packman");
		MenuItem pathKML = new MenuItem("Path to KML");
		imp.add(import1);//add the import item to the import list
		File.add(save);//add the save item to the file list.....
		File.add(clear);
		File.add(exit);
		PathD.add(PathDR);
		PathD.add(run);
		PathD.add(pathKML);
		Game.add(fruit);
		Game.add(packman);
		setMenuBar(MB);//set the menu bar
		import1.addActionListener(new java.awt.event.ActionListener() {//add action by clicking on the import item
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				selectListener(evt);//cal the function selectListener that can read from all the pc
			}
		});
		exit.addActionListener(new java.awt.event.ActionListener() {//add action to exit item that close the frame
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				System.exit(JFrame.EXIT_ON_CLOSE);
			}
		});
		save.addActionListener(new java.awt.event.ActionListener() {//add action to save item that save the packman and fruit location by calling the game class
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				saveListener(evt);
			}
		});
		packman.addActionListener(new java.awt.event.ActionListener() {//add action to packman item that packman is clicked and now you can draw packmans on the frame
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				PClicked=true;
				FClicked=false;
			}
		});
		fruit.addActionListener(new java.awt.event.ActionListener() {//add action to fruit item that fruit is clicked and now you can draw fruits on the frame
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				FClicked=true;
				PClicked=false;
			}
		});
		clear.addActionListener(new java.awt.event.ActionListener() {//add action to clear item and now the frame is cleared by clearing the lists of the packmans and the fruits
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				PClicked=false;
				FClicked=false;
				CLEARC=true;
				pac_list.clear();
				fruit_list.clear();
				DrawIsClicked=false;
				repaint();
			}
		});
		run.addActionListener(new java.awt.event.ActionListener() {//add action to run item that starts the game and shows the paths for every packman and start moving the packmans
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				CLEARC=false;
				if(pac_list.isEmpty()&&fruit_list.isEmpty()) 
					JOptionPane.showMessageDialog(null, "Error! Can't run without any packmans and fruits!");//show message if the fruit list is empty and the packman
				else if(!pac_list.isEmpty()&&fruit_list.isEmpty()) 
					JOptionPane.showMessageDialog(null, "Error! Can't run without any fruits!");
				else if(!fruit_list.isEmpty()&&pac_list.isEmpty())
					JOptionPane.showMessageDialog(null, "Error! Can't run without any packmans!");
				else {//start the game
					Coord_Game cg=new Coord_Game();
					fruit_Copy=copyFruit(fruit_list);
					newPath=new PathAlgo2(pac_list,fruit_list);
					newPath.runParallel();
					index=0;
					PathDraw();
				}
			}
		});
		PathDR.addActionListener(new java.awt.event.ActionListener() {//add action to path draw item that only shows the path without running it
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				DrawIsClicked=true;
				fruit_Copy=copyFruit(fruit_list);
				pac_Copy=copyPac(pac_list);
				copy2=new PathAlgo2(pac_Copy,fruit_Copy);
				copy2.runParallel();
				DrawLines();
			}
		});
		pathKML.addActionListener(new java.awt.event.ActionListener() {//add action to path to kml that is saves the game to kml file and run it there
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				String s=JOptionPane.showInputDialog(null, "Enter a Name for the file");
				fruit_Copy=copyFruit(fruit_list);
				pac_Copy=copyPac(pac_list);
				Path2KML2 p=new Path2KML2(pac_Copy,fruit_Copy);
				p.write(s);
			}
		});
	}
	public void DrawLines() {//this function is draw the lines for every packman path
		Graphics g=getGraphics();
		Coord_Game cg=new Coord_Game();
		for (int i=0;i<copy2.path.size();i++) {
			g.setColor(Color.RED);
			for(int j =0 ; j< copy2.path.get(i).GPS.size()-1 ;j++ ) {
				Fruit newPoint = copy2.path.get(i).GPS.get(j);
				Fruit nextPoint = copy2.path.get(i).GPS.get(j+1);
				Point3D local=cg.GpsPixel(newPoint.getFruit(), getHeight(), getWidth());
				Point3D localp=cg.GpsPixel(nextPoint.getFruit(), getHeight(), getWidth());
				//getting the width and the height from the frame size
				g.drawLine((int)(local.x()-0.003*getWidth()),(int)(local.iy()+0.025*getHeight()), (int)(localp.ix()-0.003*getWidth()), (int)(localp.iy()+0.025*getHeight()));
				g.setColor(Color.CYAN);
			}
		}
	}
	public ArrayList<Fruit> copyFruit(ArrayList<Fruit> copy1){//copying the fruit list
		ArrayList<Fruit> newCopy=new ArrayList<Fruit>();
		Iterator<Fruit> itr = fruit_list.iterator();
		while(itr.hasNext()) {
			Fruit p=itr.next();
			newCopy.add(p);
		}
		return newCopy;
	}
	public ArrayList<Packman> copyPac(ArrayList<Packman> copy1){//copying the packman list
		ArrayList<Packman> newCopy=new ArrayList<Packman>();
		Iterator<Packman> itr = pac_list.iterator();
		while(itr.hasNext()) {
			Packman p=itr.next();
			newCopy.add(p);
		}
		return newCopy;
	}
	private void PathDraw() {//this function is runs the packman by the location he must to step
		fruit_Copy=copyFruit(fruit_list);
		pac_Copy=copyPac(pac_list);
		copy2=new PathAlgo2(pac_Copy,fruit_Copy);
		copy2.runParallel();
		DrawLines();
		DrawIsClicked=true;
		for (int i=0;i<newPath.path.size();i++) {
			double time1=newPath.path.get(i).totalTime(newPath.path);
			new Thread(new Runnable() {//making a thread to run all the packmans in the same time and every packman runs by his speed
				int i=index++;
				//				@Override
				//				public void run() {
				//					Graphics g=getGraphics();
				//					Coord_Game cg=new Coord_Game();
				//					for(int j =0 ; j< newPath.path.get(i).GPS.size()-1 ;j++ ) {
				//						g.setColor(Color.RED);
				//						try {
				//							Thread.sleep((int) (10*(newPath.path.get(i).PathTime.get(j+1))));
				//						} catch (InterruptedException e) {
				//							e.printStackTrace();
				//						}
				//						Fruit newPoint = newPath.path.get(i).GPS.get(j);
				//						Fruit nextPoint = newPath.path.get(i).GPS.get(j+1);
				//						Point3D local=cg.GpsPixel(newPoint.getFruit(), getHeight(), getWidth());
				//						Point3D localp=cg.GpsPixel(nextPoint.getFruit(), getHeight(), getWidth());
				//						g.drawLine((int)(local.x()-0.003*getWidth()),(int)(local.iy()+0.025*getHeight()), (int)(localp.ix()-0.003*getWidth()), (int)(localp.iy()+0.025*getHeight()));
				//						try {
				//							Thread.sleep((int) (10*(newPath.path.get(i).PathTime.get(j+1))));
				//						} catch (InterruptedException e) {
				//							e.printStackTrace();
				//						}
				//						pac_list.get(i).setCoords(new Circle(nextPoint.getFruit(),pac_list.get(i).getCoords().get_radius()));
				//						isEqualsVal(pac_list.get(i),nextPoint);
				//						repaint();
				//					}
				//				}
				@Override
				public void run() {//the run function of the thread that set the location of the packman by the list from the PathAlgorthm
					for (int j = 0; j < newPath.path.get(i).Slope.size(); j++) {
						repaint();
						try {//make the packman sleeps for 100 ms and then start working
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						if(CLEARC)//if the clear item is clicked stop the threads
							break;
						pac_list.get(i).setCoords(new Circle(newPath.path.get(i).Slope.get(j),pac_list.get(i).getCoords().get_radius()));
						isEqualsVal(pac_list.get(i));//set the packman location and if he is equal to fruit remove it
					}
					if(!CLEARC) 
						isEqualsVal(pac_list.get(i));
					repaint();
				}
			}).start();//start the thread run function
		}
	}
	private void selectListener(java.awt.event.ActionEvent evt) {//function the can select from your pc
		//https://docs.oracle.com/javase/7/docs/api/javax/swing/JFileChooser.html
		JFileChooser fC=new JFileChooser();
		int resault=fC.showOpenDialog(null);
		if(resault==JFileChooser.APPROVE_OPTION) {
			String filename=fC.getSelectedFile().getAbsolutePath();
			//JOptionPane.showMessageDialog(null, fileName);
			System.out.println(filename);
			Game g=new Game(filename);
			pac_list=g.getPackman();
			fruit_list=g.getFruit();
			DrawFruit();
			DrawPackman();
		}
	}
	private void saveListener(java.awt.event.ActionEvent evt) {//function that can save the packman list and the fruit list to the project
		Game g=new Game();//calling the game class and get the fruit list of the frame and set it on the game and save it by a random name
		g.setFruit(fruit_list);
		g.setPackman(pac_list);
		g.saveGame(""+(int)(Math.random()*100+1)+".csv");
	}
	public Map(String fileName) throws IOException  {
		super("Ariel1.png");
		init_to_gui(fileName);
	}
	public static void main(String[] args){
		Map map=null;
		try {
			map = new Map("Ariel1.png");
		} catch (IOException e) {
			e.printStackTrace();
		}	
		map.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		map.setSize(1433,642);
		map.setVisible(true);
		map.setLocationRelativeTo(null);
	}
	public boolean isEqualsVal(Packman p) {//check if the packman is near the fruit and eat it by removing from the list
		Iterator<Fruit> itr=fruit_list.iterator();
		boolean b=false;
		for (int i=0;i<fruit_list.size();i++) {
			Fruit f=itr.next();
			if(f!=null&f.getFruit().equals(p.getCoords().get_cen())){
				//if(f!=null&p.getCoords().get_cen().equals(f.getFruit())) {
				System.out.println(f.getFruit()+" has been eaten");
				fruit_list.get(i).setFruit(new Point3D(-1,-1,-1));
				b=true;
				break;
			}
		}
		return b;
	}
	public void DrawFruit() {//function that uses the graphics iterface that draw the fruits from the fruit list
		Graphics g=getGraphics();
		Coord_Game cg=new Coord_Game();
		for (Fruit fruit : fruit_list) {
			g.setColor(Color.GREEN);
			Point3D f=cg.GpsPixel(fruit.getFruit(),this.getHeight(), this.getWidth());//changing the global coords tpo pixels by the Coords_Game class
			//System.out.println("Fruit click : "+f);
			g.fillOval((int)(f.x()-0.003*getWidth()), (int)(f.y()+0.025*getHeight()), 10, 10);//draw the fruit by adding circle in the pixels
		}
	}
	public void DrawPackman() {//the same but to packmans
		Graphics g=getGraphics();
		Coord_Game cg=new Coord_Game();
		for (Packman packman :pac_list) {
			g.setColor(Color.YELLOW);
			Point3D p=cg.GpsPixel(packman.getCoords().get_cen(),this.getHeight(), this.getWidth());
			//System.out.println("Packman Click: "+p);
			g.fillOval((int)(p.x()-0.003*getWidth()), (int)(p.y()+0.025*getHeight()), 10*(int)packman.getCoords().get_radius(), 10*(int)packman.getCoords().get_radius());
		}
	}

	public class Clicks implements MouseListener{//clicks class that implements from the mouse listener interface that allow us to click on the frame

		@Override
		public void mouseClicked(MouseEvent e) {
			Coord_Game Coords = new Coord_Game();
			Point3D end = Coords.PixelGps(new Point3D(e.getX(),e.getY(),0), getHeight(), getWidth());//chnage the pixels to global point
			//System.out.println("Mouse Click : "+e.getX()+","+e.getY());
			if(PClicked) {//if the packman item is clicked then add a packman to the packman list and draw it
				FClicked=false;
				pac_list.add(new Packman(new Circle(end,1),1));
				//System.out.println("     Packman Added with this Coords: "+end);
				repaint();
			}
			else if(FClicked) {//if the fruit item is clicked then add a fruit to the fruit list and draw it
				PClicked=false;
				fruit_list.add(new Fruit(end));
				//System.out.println("     Fruit Added with this Coords: "+end);
				repaint();
			}
			else {//just print the clicked coords
				FClicked=false;
				PClicked=false;
				System.out.println(end.x()+","+end.y());
				repaint();
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}
	}
}

