package GUI;
import static org.junit.Assert.assertNotNull;

import java.awt.BorderLayout;
import java.awt.Color;
/**
 * Code taken from: https://javatutorial.net/display-text-and-graphics-java-jframe
 * 
 */
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
public class JFrmaeGraphics extends JPanel{


	public void paint(Graphics g){
		Image backgroundImage=null;
		try {
			backgroundImage = javax.imageio.ImageIO.read(new File("Ariel1.png"));
			g.drawImage(backgroundImage, 0, 0,this.getWidth(),this.getHeight(), this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int w = this.getWidth();
		int h = this.getHeight();
		g.setColor(Color.red);
		g.fillOval(w/3, h/3, 10, 10);
		g.fillOval(w/2, h/2, 10, 10);
		new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Graphics g=getGraphics();
				g.setColor(Color.GREEN);
				g.fillOval(e.getX(), e.getY(), 10, 10);
			}
		};
	}

	public static void main(String[] args) throws IOException{
		JFrame frame= new JFrame("JavaTutorial.net");	
		frame.getContentPane().add(new JFrmaeGraphics());
		frame.setSize(1433, 642);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setName("JFrame example");
	}
}