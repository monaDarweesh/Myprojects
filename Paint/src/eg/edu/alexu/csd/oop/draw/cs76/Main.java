package eg.edu.alexu.csd.oop.draw.cs76;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class Main extends JFrame {
	
		public static void main(String[] args) {
			
			JFrame frame = new JFrame("here");
		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    Gui p = new Gui();
		   /* addMouseListener(p.mouseHandler) ;
			addMouseMotionListener(p.mouseMotionHandler) ;*/
		    frame.add(p);
		    frame.setSize(600, 600);
		    frame.setVisible(true);    
		}
	}
