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

import Coords.Coord_Game;
import Geom.Circle;
import Geom.Point3D;
import Robot.Fruit;
import Robot.Packman;
import Robot.ghosts;
import Robot.player;
/**
 * this class represent the map class of GUI frame that showed the map image of ariel and play the game on it 
 * that every packman run to eat all the fruit on his path by his speed
 * @author jbareen Mohamad nadeem jazmawe 
 * 207392283_314638867
 */
public class Map extends JFrame {
	private Point3D min = null ;
	private boolean FClicked=false,PClicked=false,CLEARC=false,playerClicked=false,ghostsClicked=false,boxClicked=false;//the packman button is clicked ,fruit ,clear ,player ,ghost and box.
	private MenuBar MB;//make the menu bar 
	private static int index=0;//index to run the threads
	private ShortestPathAlgo newPath;//the pathAlgorthm to all the packmans
	private ShortestPathAlgo copy2;//a copy of the pathAlgorthm
	private boolean DrawIsClicked=false;//buttun that if the path draw clicked or not
	private ArrayList<Packman> pac_list;//all the packman list
	private ArrayList<Fruit> fruit_list;//all the fruit list
	private ArrayList<ghosts> ghost_list;//all the ghost list
	private ArrayList<Box> box_list ;
	private player Player; //our player
	public BufferedImage myImage;//the image of ariel 
	public void paint(Graphics g){//the paint function that draw all the packmans from the list and all the fruit too and draw the path for every packman
		g.drawImage(myImage, 0, 0,getWidth(), getHeight(),null);
		Drawall();
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
		ghost_list = new ArrayList<ghosts>();
		box_list = new ArrayList<Box>();
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
		MenuItem clear = new MenuItem("Clear");
		MenuItem run = new MenuItem("Run");
		MenuItem run2 = new MenuItem("Run With Player");
		MenuItem save = new MenuItem("Save");
		MenuItem import1 = new MenuItem("Import");
		MenuItem fruit = new MenuItem("Fruit");
		MenuItem packman = new MenuItem("Packman");
		MenuItem pathKML = new MenuItem("Path to KML");
		MenuItem player = new MenuItem("Player");
		MenuItem ghosts = new MenuItem("Ghosts");
		MenuItem box = new MenuItem("Box");
		imp.add(import1);//add the import item to the import list
		File.add(save);//add the save item to the file list
		File.add(clear);//add the clear item to the file list
		File.add(exit);//add the exit item to the file list
		PathD.add(PathDR);//add the Draw path item to the Path list
		PathD.add(run);//add the Run item to the Path list
		PathD.add(run2);//add the Run with player item to the Path list
		PathD.add(pathKML);//add the Path to KML item to the Path list
		Game.add(fruit);//add the fruit item to the Game list
		Game.add(packman);//add the packman item to the Game list
		Game.add(player);//add the player item to the Game list
		Game.add(ghosts);//add the ghosts item to the Game list
		Game.add(box);//add the Box item to the Game list
		setMenuBar(MB);//set the menu bar
		import1.addActionListener(new java.awt.event.ActionListener() {//add action by clicking on the import item
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				PClicked=false;
				FClicked=false;
				playerClicked=false;
				ghostsClicked=false;
				CLEARC=true;
				pac_list.clear();
				fruit_list.clear();
				ghost_list.clear();
				Player = null;
				DrawIsClicked=false;
				box_list.clear();
				repaint();
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
				playerClicked=false;
				ghostsClicked=false;
				boxClicked=false;
			}
		});
		fruit.addActionListener(new java.awt.event.ActionListener() {//add action to fruit item that fruit is clicked and now you can draw fruits on the frame
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				FClicked=true;
				PClicked=false;
				playerClicked=false;
				ghostsClicked=false;
				boxClicked=false;
			}
		});
		clear.addActionListener(new java.awt.event.ActionListener() {//add action to clear item and now the frame is cleared by clearing the lists of the packmans and the fruits
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				PClicked=false;
				FClicked=false;
				playerClicked=false;
				ghostsClicked=false;
				boxClicked=false;
				CLEARC=true;
				pac_list.clear();
				fruit_list.clear();
				ghost_list.clear();
				Player = null;
				DrawIsClicked=false;
				box_list.clear();
				repaint();
			}
		});
		run.addActionListener(new java.awt.event.ActionListener() {//add action to run item that starts the game and shows the paths for every packman and start moving the packmans
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				PClicked=false;
				FClicked=false;
				playerClicked=false;
				ghostsClicked=false;
				boxClicked=false;
				CLEARC=false;
				if(pac_list.isEmpty()&&fruit_list.isEmpty()) 
					JOptionPane.showMessageDialog(null, "Error! Can't run without any packmans and fruits!");//show message if the fruit list is empty and the packman
				else if(fruit_list.isEmpty()) 
					JOptionPane.showMessageDialog(null, "Error! Can't run without any fruits!");
				else if(pac_list.isEmpty())
					JOptionPane.showMessageDialog(null, "Error! Can't run without any packmans!");
				else {//start the game
					Coord_Game cg=new Coord_Game();
					newPath=new ShortestPathAlgo(pac_list,fruit_list);
					index=0;
					PathDraw();
				}
			}
		});
		run2.addActionListener(new java.awt.event.ActionListener() {//add action to run with player item that starts the game 
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				PClicked=false;
				FClicked=false;
				playerClicked=false;
				ghostsClicked=false;
				boxClicked=false;
				CLEARC=false;
				if(Player==null && fruit_list.isEmpty()) 
					JOptionPane.showMessageDialog(null, "Error! Can't run without any player and fruits!");//show message if the fruit list is empty and the packman
				else if(fruit_list.isEmpty()) 
					JOptionPane.showMessageDialog(null, "Error! Can't run without any fruits!");//show message if the fruit list is empty and the packman
				else if(Player==null) 
					JOptionPane.showMessageDialog(null, "Error! Can't run without player!");//show message if the fruit list is empty and the packman
				else {//start the game
					while(fruit != null) {
						Playerpath n = new Playerpath(pac_list, fruit_list,ghost_list, Player);
						pac_list = n.getPac_list();
						fruit_list = n.getFruit_list();
						ghost_list = n.getGhost_list();
						repaint();
					}
				}
			}
		});

		PathDR.addActionListener(new java.awt.event.ActionListener() {//add action to path draw item that only shows the path without running it
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if(pac_list.isEmpty()&&fruit_list.isEmpty()) 
					JOptionPane.showMessageDialog(null, "Error! Can't run without any packmans and fruits!");//show message if the fruit list is empty and the packman
				else if(!pac_list.isEmpty()&&fruit_list.isEmpty()) 
					JOptionPane.showMessageDialog(null, "Error! Can't run without any fruits!");
				else if(!fruit_list.isEmpty()&&pac_list.isEmpty())
					JOptionPane.showMessageDialog(null, "Error! Can't run without any packmans!");
				else {
					DrawIsClicked=true;
					copy2=new ShortestPathAlgo(pac_list,fruit_list);
					DrawLines();
				}
			}
		});
		pathKML.addActionListener(new java.awt.event.ActionListener() {//add action to path to kml that is saves the game to kml file and run it there
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				String s=JOptionPane.showInputDialog(null, "Enter a Name for the file");
				Path2KML2 p=new Path2KML2(pac_list,fruit_list);
				p.write(s);
			}
		});
		player.addActionListener(new java.awt.event.ActionListener() {//add action to player item that player is clicked and now you can draw players on the frame
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				PClicked=false;
				FClicked=false;
				playerClicked=true;
				ghostsClicked=false;
				boxClicked=false;
			}
		});
		ghosts.addActionListener(new java.awt.event.ActionListener() {//add action to ghost item that ghost is clicked and now you can draw ghosts on the frame
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				PClicked=false;
				FClicked=false;
				playerClicked=false;
				ghostsClicked=true ;
				boxClicked=false;
			}
		});
		box.addActionListener(new java.awt.event.ActionListener() {//add action to box item that box is clicked and now you can draw boxes on the frame
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				PClicked=false;
				FClicked=false;
				playerClicked=false;
				ghostsClicked=false;
				boxClicked=true;
			}
		});	
	}
	public void DrawLines() {//this function is draw the lines for every packman path
		Graphics g=getGraphics();
		Coord_Game cg=new Coord_Game();
		for (int i=0;i<copy2.all_path.size();i++) {
			g.setColor(Color.RED);
			for(int j =0 ; j< copy2.all_path.get(i).fruitToEat.size()-1 ;j++ ) {
				Fruit newPoint = copy2.all_path.get(i).fruitToEat.get(j);
				Fruit nextPoint = copy2.all_path.get(i).fruitToEat.get(j+1);
				Point3D local=cg.GpsPixel(newPoint.getFruit(), getHeight(), getWidth());
				Point3D localp=cg.GpsPixel(nextPoint.getFruit(), getHeight(), getWidth());
				//getting the width and the height from the frame size
				g.drawLine((int)(local.x()-0.003*getWidth()),(int)(local.iy()+0.025*getHeight()), (int)(localp.ix()-0.003*getWidth()), (int)(localp.iy()+0.025*getHeight()));
				g.setColor(Color.CYAN);
			}
		}
	}
	private void PathDraw() {//this function is runs the packman by the location he must to step
		copy2=new ShortestPathAlgo(pac_list,fruit_list);
		DrawLines();
		DrawIsClicked=true;
		for (int i=0;i<newPath.all_path.size();i++) {
			//double time1=newPath.path.get(i).totalTime(newPath.path);
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
					for (int j = 0; j < newPath.all_path.get(i).Slope.size(); j++) {
						repaint();
						try {//make the packman sleeps for 100 ms and then start working
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						if(CLEARC)//if the clear item is clicked stop the threads
							break;
						pac_list.get(i).setCoords(new Circle(newPath.all_path.get(i).Slope.get(j),pac_list.get(i).getCoords().get_radius()));
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
			Player = g.getPlayer();
			box_list = g.getBox();
			ghost_list = g.getGhost();
			Drawall();
		}
	}
	private void saveListener(java.awt.event.ActionEvent evt) {//function that can save the packman list and the fruit list to the project
		Game g=new Game();//calling the game class and get the fruit list of the frame and set it on the game and save it by a random name
		g.setFruit(fruit_list);
		g.setPackman(pac_list);
		g.setBox(box_list);
		g.setFruit(fruit_list);
		g.setGhost(ghost_list);
		g.setPlayer(Player);
		g.saveGame(""+(int)(Math.random()*100+1)+".csv");
	}
	public Map(String fileName) throws IOException  {
		super("Packman Game");
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
	public void Drawall() {//function that uses the graphics interface to draw all fruits , packmans , player , ghosts and boxs;
		DrawFruit();
		DrawPackman();
		DrawPlayer();
		DrawGhost();
		Drawboxs();
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
	public void DrawPlayer() {//the same but to player
		if(Player != null) {
			Graphics g=getGraphics();
			Coord_Game cg=new Coord_Game();
			g.setColor(Color.PINK);
			Point3D p=cg.GpsPixel(Player.getCoords().get_cen(),this.getHeight(), this.getWidth());
			g.fillOval((int)(p.x()-0.003*getWidth()), (int)(p.y()+0.025*getHeight()), 15*(int)Player.getCoords().get_radius(), 15*(int)Player.getCoords().get_radius());
		}
	}
	public void DrawGhost() {//the same but to ghosts

		Graphics g=getGraphics();
		Coord_Game cg=new Coord_Game();
		for (ghosts G :ghost_list) {
			g.setColor(Color.red);
			Point3D p=cg.GpsPixel(G.getCoords().get_cen(),this.getHeight(), this.getWidth());
			g.fillOval((int)(p.x()-0.003*getWidth()), (int)(p.y()+0.025*getHeight()), 10*(int)G.getCoords().get_radius(), 10*(int)G.getCoords().get_radius());
		}
	}
	public void Drawboxs() {//the same but to boxs
		Graphics g=getGraphics();
		Coord_Game cg=new Coord_Game();
		for (Box b : box_list) {
			g.setColor(Color.black);
			Point3D p=cg.GpsPixel(b.getMin(),this.getHeight(), this.getWidth());
			Point3D p1=cg.GpsPixel(b.getMax(),this.getHeight(), this.getWidth());

			g.fillRect(Math.min(p.ix() , p1.ix()),Math.min(p.iy() , p1.iy()), Math.abs(p.ix()-p1.ix()) , Math.abs(p.iy()-p1.iy()));
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
				playerClicked=false;
				ghostsClicked=false;
				boxClicked=false;
				pac_list.add(new Packman(new Circle(end,1),1));
				repaint();
			}
			else if(FClicked) {//if the fruit item is clicked then add a fruit to the fruit list and draw it
				PClicked=false;
				playerClicked=false;
				ghostsClicked=false;
				boxClicked=false;
				fruit_list.add(new Fruit(end));
				repaint();
			}
			else if(playerClicked) {//if the player item is clicked then add a player to the fruit list and draw it
				PClicked=false;
				FClicked=false;
				ghostsClicked=false;
				boxClicked=false;
				Player = new player(new Circle(end,1),2);
				repaint();
			}
			else if(ghostsClicked) {//if the ghosts item is clicked then add a ghost to the ghosts list and draw it
				PClicked=false;
				FClicked=false;
				playerClicked=false;
				boxClicked=false;
				ghost_list.add(new ghosts(new Circle(end,1),1));
				repaint();
			}
			else if(boxClicked) {//if the box item is clicked then add a box to the box list and draw it
				PClicked=false;
				FClicked=false;
				playerClicked=false;
				ghostsClicked=false;
				if(min != null) {
					System.out.println("min="+min);
					System.out.println("end="+end);
					box_list.add(new Box(min , end));
					min = null; 
				}
				else
					min = end ;

				repaint();
			}
			else {//just print the clicked coords
				FClicked=false;
				PClicked=false;
				playerClicked=false;
				ghostsClicked=false;
				boxClicked=false;
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

