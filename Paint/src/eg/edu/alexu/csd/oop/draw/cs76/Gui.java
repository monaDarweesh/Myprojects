package eg.edu.alexu.csd.oop.draw.cs76;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONException;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.JsonParseException;

import eg.edu.alexu.csd.oop.draw.IShape;
import eg.edu.alexu.csd.oop.draw.cs76.ColorChooserButton.ColorChangedListener;

@SuppressWarnings("serial")
public class Gui extends JFrame {
	JButton  lineBut, circleBut, move, undo, redo, loader, resize, rectangleBut, squareBut, triangleBut, delete, ovalBut,Save,Load;
	ArrayList<IShape> shapes = new ArrayList<IShape>();
    Stack <IShape>  shape_redo = new Stack<IShape> ();
    boolean released = false;
    boolean pressed = false;
    boolean sel = false;
    boolean enableT=false;
    boolean enables=false;
    String path_to_jar="/C:/Users/MonaAlaa/paint.jar";
    Point original = new Point();
    Color Current_Colour;
    IShape r;
	int currentAction = 1;
	int x = 100;
	int y = 200;
	int x1 = 100;
	int y1 = 200;
	public Gui() {
		   setSize(1350, 1400);
		   setLocationRelativeTo(null);
		   setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		   JPanel Buttons = new JPanel();
		   JPanel Colours = new JPanel();
           // Swing box that will hold all the buttons
           Box theBox = Box.createHorizontalBox();
           theBox.setPreferredSize(new Dimension(1350,50));
           // Make all the buttons in makeMeButtons by passing the
           // button icon. 
           ColorChooserButton currentColour = new ColorChooserButton(Color.WHITE);
           currentColour.addColorChangedListener(new ColorChangedListener() {
               @Override
               public void colorChanged(Color newColor) {
                       // do something with newColor ...
            	  Current_Colour = newColor;
               }
           });
           currentColour.setPreferredSize(new Dimension(50,50));
           Colours.add(currentColour, BorderLayout.PAGE_END);
           
           circleBut = makeMeButtons("Circle", 1, new Dimension(40,40));
           lineBut = makeMeButtons("Line", 2, new Dimension(40,40));
           move = makeMeButtons("Move", 3, new Dimension(40,40));
           undo = makeMeButtons("Undo", 4, new Dimension(40,40));
		   redo = makeMeButtons("Redo", 5, new Dimension(40,40));
		   Save = makeMeButtons("Save", 6, new Dimension(40,40));
           Load = makeMeButtons("load", 7, new Dimension(40,40));
		   loader = makeMeButtons("Loader", 8, new Dimension(40,40));
           resize = makeMeButtons("Resize", 9, new Dimension(40,40));
           rectangleBut = makeMeButtons("Rectangle", 10, new Dimension(40,40));
           squareBut = makeMeButtons("Square", 11, new Dimension(40,40));
           triangleBut = makeMeButtons("Triangle", 12,new Dimension(40,40));
           delete = makeMeButtons("Delete", 13, new Dimension(40,40));
           ovalBut = makeMeButtons("Oval", 14,new Dimension(40,40));
          

           addMouseListener(mouseHandler);
           addMouseMotionListener(mouseMotionHandler);
           
			 theBox.add(lineBut);
	         theBox.add(circleBut);
	         theBox.add(ovalBut);
	         theBox.add(rectangleBut);
	         theBox.add(triangleBut);
	         theBox.add(squareBut);
	         theBox.add(resize);
	         theBox.add(delete);
	         theBox.add(undo);
	         theBox.add(redo);
	         theBox.add(move);
	         theBox.add(Save);
			 theBox.add(Load);
			 theBox.add(loader);
	         Buttons.add(theBox);
	         this.add(Colours, BorderLayout.WEST);
	         this.add(Buttons, BorderLayout.BEFORE_FIRST_LINE);
		     repaint();
		     setVisible(true);
		}
	public String getpath(String filepath) {
		filepath = filepath.replaceAll("\\\\", "/");
		String path = "/" + filepath;
		return path;
	}
	public JButton makeMeButtons(String Name, final int actionNum,Dimension d){
     	JButton createButton = new JButton(Name);
     	createButton.setPreferredSize(d);
         // Make the proper actionPerformed method execute when the
         // specific button is pressed
     	createButton.addActionListener(new ActionListener() {
        	 public void actionPerformed(ActionEvent e) {
        		 currentAction = actionNum;
        		 pressed = false;
        		 System.out.println("actionNum: " + actionNum);
        		 repaint();
        	 }
         });
         return createButton;
	 }
	 public static void main(String[] args) {
		 Gui NewWindowForPaint = new Gui();
	 }
	 public void savepath(String path){
			File file = new File("jarpath.txt");

			// if file doesn't exists, then create it
			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			FileWriter fw=null;

		   try {
			fw = new FileWriter(file.getAbsoluteFile());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   BufferedWriter bw = new BufferedWriter(fw);
			
				try {
					bw.write(path);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
			try {
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String readpath(String fileName) throws IOException {
		    BufferedReader br = new BufferedReader(new FileReader(fileName));
		    try {
		        StringBuilder sb = new StringBuilder();
		        String line = br.readLine();
		        while (line != null) {
		            sb.append(line);
		            sb.append("\n");
		            line = br.readLine();
		        }
		        return sb.toString();
		    } finally {
		        br.close();
		    }
		}
	 public void resizeLine(Point prev){
		 prev.move((int)MouseInfo.getPointerInfo().getLocation().getX(),(int)MouseInfo.getPointerInfo().getLocation().getY());
	 }
	 public MouseListener mouseHandler = new MouseAdapter() {
		 @Override
		 public void mouseReleased(MouseEvent e) {
			 if(currentAction == 3) {
					sel = false;
			 }
			 x = e.getX();
			 y = e.getY();
			 released = true;
			 sel = false;
			 repaint();
		 }
		 public IShape setshap(IShape f) {
				return f;
		 }
		 @Override
		 public void mousePressed(MouseEvent e) {
			 original.x = e.getX();
		     original.y = e.getY();
		     if(currentAction == 3) {
		    	 x = e.getX();
				 y = e.getY();
				 Point l = new Point();
				 l.x = e.getX();
				 l.y = e.getY();
				 for (IShape s : shapes){
					 if(s.contains(s, l)){
						 sel = true;
					     r = s;
					     System.out.println(sel);
					 }
				 }
		     } else if(currentAction == 10){
		    	 x1 = e.getX();
		    	 y1 = e.getY();
		    	 x = e.getX();
		    	 y = e.getY();
		    	 pressed = true;
		    	 Point l = new Point();
		    	 l.x = e.getX();
		    	 l.y = e.getY();
		    	 for (IShape s : shapes) {
		    		 if(s.contains(s, l)) {
		    			 sel = true;
		    			 r = s;
		    			 System.out.println(sel);
		    		 }
		    	 }
		    	 repaint();
		     } else if(currentAction == 13){
		    	 x1 = e.getX();
		    	 y1 = e.getY();
		    	 x = e.getX();
		    	 y = e.getY();
		    	 pressed = true;
		    	 Point l = new Point();
		    	 l.x = e.getX();
		    	 l.y = e.getY();
		    	 for (IShape s : shapes) {
		    		 if(s.contains(s, l)) {
		    			 sel = true;
		    			 r = s;
		    			 System.out.println(sel);
		    		 }
		    	 }
		    	 repaint();
		     } else if(currentAction == 9) {
		    	 x = e.getX();
		    	 y = e.getY();
		    	 Point l = new Point();
		    	 l.x = e.getX();
		    	 l.y = e.getY();
		    	 for (IShape s : shapes) {
		    		 if(s.contains(s, l)) {
		    			 sel = true;
		    			 r = s;
		    			 System.out.println(sel);
		    		 }
		    	 }
		     } else {
				x1 = e.getX();
				y1 = e.getY();
				x = e.getX();
		    	y = e.getY();
				pressed = true;
				repaint();
		     }
		 }
	 };   
		public MouseMotionListener mouseMotionHandler= new MouseMotionAdapter() {	
			@Override
			public void mouseDragged(MouseEvent e) {
				if(currentAction==3&&sel==true){
					x = e.getX();
					y = e.getY();
					repaint();
				} else if(currentAction==9&&sel==true){
					x = e.getX();
					y = e.getY();
					repaint();
				} else if(currentAction==10&&sel==true){
					x = e.getX();
					y = e.getY();
					repaint();
				} else {
					x = e.getX();
					y = e.getY();
					repaint();
					}
			}
		};
		public void paint(Graphics g) {
			super.paint(g);
			this.setBackground(Color.WHITE);
			if(currentAction == 1){
				Circle draw_circle = new Circle();
				Point v = new  Point();
			    v.x = x1;
			    v.y = y1;
			    draw_circle.setPosition(v);
			    draw_circle.setFillColor(Current_Colour);
			    draw_circle.setProperties(Math.sqrt(Math.pow(x1 - x,2) + Math.pow(y1 - y, 2)));
			    if(released == true) {
			    	shapes.add(draw_circle);
			    }
			    if (pressed == true) {
			    	draw_circle.draw(g);
			    }
			    for (IShape s : shapes) {
				 s.draw(g);
			    }
				/*	 Point v=new  Point();
			    v.x=x1;
			    v.y=y1;
	 
			URL[] urls=null;
			 try {
				 URL[]  url={new URL("jar:file:"+ "/C:/Users/TGT/Desktop/testloader/classes.jar"+"!/")};
				 urls=url;
				 } catch (MalformedURLException e1){// 
			e1.printStackTrace();}
			URLClassLoader urlClassLoader=URLClassLoader.newInstance(urls);
			Class d=null;
			try {
				d = urlClassLoader.loadClass("eg.edu.alexu.csd.oop.paint.Circle");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Object anotherDynamicObject=null;
			try {
				 anotherDynamicObject = d.newInstance();
			} catch (InstantiationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	 
			try {
				try {
					Class[] cArg = new Class[1];
					cArg[0]=Point.class;
			        d.getMethod("setPosition",cArg).invoke( anotherDynamicObject,v);
					Class[] n=new Class[2];
					n[0]=double.class;
					n[1]=double.class;
					d.getMethod("setProperties",n).invoke( anotherDynamicObject,Math.sqrt(Math.pow(x1-x,2)+Math.pow(y1-y, 2)),Math.sqrt(Math.pow(x1-x,2)+Math.pow(y1-y, 2)));
					 if(released==true){
					shapes.add((eg.edu.alexu.csd.oop.paint.shape) anotherDynamicObject);}
					 if (pressed==true){
						 ((eg.edu.alexu.csd.oop.paint.shape) anotherDynamicObject).draw(g);}
				} 
						   catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.getCause().printStackTrace();
	 
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			} else if(currentAction == 2){
			 Line newline = new Line();
			 Point firstPoint = new  Point();
			 firstPoint.x = x1;
			 firstPoint.y = y1;
			 Point endPoint = new  Point();
			 endPoint.x = x;
			 endPoint.y = y;
			 newline.setColor(Current_Colour);
			 newline.setProperties(firstPoint, endPoint);
			 if(released == true) {
				shapes.add(newline);
			 }
			 if(pressed == true) {
				 newline.draw(g);
			 }
			 for (IShape s : shapes) {
				 s.draw(g);
			 }
				/*	
				 * Point j=new  Point();
			    j.x=x1;
			    j.y=y1;
			    Point l=new  Point();
			    l.x=x;
			    l.y=y;
			    
			URL[] urls=null;
			 try {
				 URL[]  url={new URL("jar:file:"+ "/C:/Users/TGT/Desktop/testloader/classes.jar"+"!/")};
				 urls=url;
				 } catch (MalformedURLException e1){// 
			e1.printStackTrace();}
			URLClassLoader urlClassLoader=URLClassLoader.newInstance(urls);
			Class d=null;
			try {
				d = urlClassLoader.loadClass("eg.edu.alexu.csd.oop.paint.Line");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Object anotherDynamicObject=null;
			try {
				 anotherDynamicObject = d.newInstance();
			} catch (InstantiationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			 
			try {
				try {
			 
					Class[] n=new Class[2];
					n[0]=Point.class;
					n[1]=Point.class;
					d.getMethod("setProperties",n).invoke( anotherDynamicObject,j,l);
					 if(released==true){
					shapes.add((eg.edu.alexu.csd.oop.paint.shape) anotherDynamicObject);}
					 if (pressed==true){
						 ((eg.edu.alexu.csd.oop.paint.shape) anotherDynamicObject).draw(g);}
				} 
						   catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.getCause().printStackTrace();
			 
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		 } else if(currentAction == 3 && sel == true) {
			 Point c = new Point();
			 c.x = x;
			 c.y = y;
			 shapes.remove(r);
			 r = r.transform(c);
			 shapes.add(r);
			 r.draw(g);
		 } else if(currentAction == 4) {
			 shape_redo.push(shapes.get(shapes.size() - 1));
			 shapes.remove(shapes.size() - 1);
		 } else if(currentAction == 5) {
			 shapes.add(shape_redo.pop());
		 } else if(currentAction == 6) {
			 Save saveArrayList = new Save();
			 String message = "where do you want to save this shape window?";
			 JCheckBox checkbox1 = new JCheckBox("jsonfile");
			 JCheckBox checkbox2 = new JCheckBox("xmlfile");
			 Object[] params = {message, checkbox1, checkbox2};
			 int n = JOptionPane.showConfirmDialog(new JFrame(),params, "Disconnect Products", JOptionPane.YES_NO_OPTION);
			 boolean filejson = checkbox1.isSelected();
			 boolean filexml = checkbox2.isSelected();
			 if(filexml == true){
				 saveArrayList.SaveXML(shapes);
			 }
			 if(filejson == true){
				 saveArrayList.savejson(shapes);;
			 }
		 } else if(currentAction == 7) {
			 String message = "whichfile do you want to reload?";
			 JCheckBox checkbox1 = new JCheckBox("jsonfile");
			 JCheckBox checkbox2 = new JCheckBox("xmlfile");
			 Object[] params = {message, checkbox1, checkbox2};
			 int n = JOptionPane.showConfirmDialog(new JFrame(),params, "Disconnect Products", JOptionPane.YES_NO_OPTION);
			 boolean filejson = checkbox1.isSelected();
			 boolean filexml = checkbox2.isSelected();
			 if(filexml == true && n == 0) {
					try {
						shapes = new Load().loadXML();
						 repaint();
					} catch (SAXException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ParserConfigurationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			 } else if(filejson == true && n == 0) {
				 try {
					shapes = new Load().loadjson();

					 repaint();
				} catch (JsonMappingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JsonParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
		 } else if(currentAction == 8) {
			 JFileChooser fileChooser = new JFileChooser(new File("/C:/"));
			 fileChooser.setDialogTitle("save jar");
			 int result = fileChooser.showOpenDialog(this);
			 if (result == JFileChooser.APPROVE_OPTION) {
				 // user selects a file
			 }
			 File selectedFile = fileChooser.getSelectedFile();
			 fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
			 result = fileChooser.showOpenDialog(this);
			 if (result == JFileChooser.APPROVE_OPTION) {
				 selectedFile = fileChooser.getSelectedFile();
				 System.out.println("Selected file: " + selectedFile.getAbsolutePath());
				 String t=getpath(selectedFile.getAbsolutePath());
				 System.out.println(t);
				 path_to_jar=t;
				 savepath(path_to_jar);
			 }
			 JFrame frame = new JFrame("choose classes to load");
			 JPanel panel = new JPanel(new GridLayout(0, 1));
			 Border border = BorderFactory.createTitledBorder("Pizza Toppings");
			 panel.setBorder(border);
			 final JCheckBox check1 = new JCheckBox("Triangle");
			 panel.add(check1);
			 final JCheckBox  check2 = new JCheckBox("square");
			 panel.add(check2);
			 Container contentPane = frame.getContentPane();
			 contentPane.add(panel, BorderLayout.CENTER);
			 frame.setSize(300, 200);
			 frame.setVisible(true);
			 ActionListener actionListener = new ActionListener() {
				 public void actionPerformed(ActionEvent actionEvent) {
					 Object c = (Object)actionEvent.getSource();
					 if(c == check1){
			        	enableT = true;
					 }
					 if(c == check2) {
						 enables = true;
					 }
				 }
			 };
			 check1.addActionListener(actionListener);
			 check2.addActionListener(actionListener);
		 } else if(currentAction == 9 && sel == true) {
			 if(r.gettype() == 1) {
				 g.drawRect(r.getProperties().get("x1").intValue(),r.getProperties().get("y1").intValue() , 5, 5);
				 g.drawRect(r.getProperties().get("x2").intValue(),r.getProperties().get("y2").intValue() , 5, 5);
				 shapes.remove(r);
				 Point k1=new Point();
				 Point k2=new Point();
				 k1.x = x;
				 k1.y = y;
				 k2.x = r.getProperties().get("x2").intValue();
				 k2.y = r.getProperties().get("y2").intValue();
				 r.setProperties(k1,k2);
				 r.setFillColor(Current_Colour);
				 shapes.add(r);
				 repaint();
				 /*}
				 else{
				 	shapes.remove(r);
				 	Point k1=new Point();
				 	Point k2=new Point();
				 	k1.x=x;
				 	k1.y=y;
				 	k2.x=r.getProperties().get("x2").intValue();
				 	k2.y=r.getProperties().get("y2").intValue();
				 	r.setProperties(k2,k1);
				 	shapes.add(r);
				 	repaint();
				 }*/
			 } else if(r.gettype() == 2) {
				 g.drawRect(r.getProperties().get("x").intValue(),r.getProperties().get("y").intValue() , 5, 5);
				 shapes.remove(r);
				 Point k1 = new Point();
				 k1.x = r.getProperties().get("x").intValue();
				 k1.y = r.getProperties().get("y").intValue();
				 r.setPosition(k1);
				 if(r.get() == 0){
					 r.setProperties(Math.sqrt(Math.pow(k1.getX()-x,2)+Math.pow(k1.getY()-y, 2)),Math.sqrt(0.5*Math.pow(k1.getX()-x,2)+Math.pow(k1.getY()-y, 2)));
				 } else {
					 r.setProperties(Math.sqrt(Math.pow(k1.getX()-x,2)+Math.pow(k1.getY()-y, 2)),Math.sqrt(Math.pow(k1.getX()-x,2)+Math.pow(k1.getY()-y, 2)));
				 }
				 shapes.add(r);
				 repaint();
			 } else if(r.gettype() == 3){
				 g.drawRect(r.getProperties().get("x").intValue(),r.getProperties().get("y").intValue() , 5, 5);
				 g.drawRect(r.getProperties().get("x").intValue()+r.getProperties().get("a").intValue(),r.getProperties().get("y").intValue() , 5, 5);
				 g.drawRect(r.getProperties().get("x").intValue(),r.getProperties().get("y").intValue()+r.getProperties().get("b").intValue() , 5, 5);
				 g.drawRect(r.getProperties().get("x").intValue()+r.getProperties().get("a").intValue(),r.getProperties().get("y").intValue()+r.getProperties().get("b").intValue()  , 5, 5);
				 shapes.remove(r);
				 if(x > r.getProperties().get("x")){
					 if(y > r.getProperties().get("y")){
						 if(r.get()== 0){
							 r.setProperties(Math.abs(x-r.getProperties().get("x")),Math.abs(y-r.getProperties().get("y")) );
						 } else if(r.get() == 1){
				    	    r.setProperties(Math.abs(x-r.getProperties().get("x")),Math.abs(x-r.getProperties().get("x")) );
						 }
					 }
				 }
				 shapes.add(r);
				 repaint();
			 } else if(r.gettype() == 4){
			     	g.drawRect(r.getProperties().get("x1").intValue(),r.getProperties().get("y1").intValue() , 5, 5);
			    	g.drawRect(r.getProperties().get("x2").intValue(),r.getProperties().get("y2").intValue() , 5, 5);
			    	g.drawRect(r.getProperties().get("x3").intValue(),r.getProperties().get("y3").intValue() , 5, 5);
			    	shapes.remove(r);
			    	Point k1 = new Point();
			    	Point k2 = new Point();
			    	k1.x = x;
			    	k1.y = y;
			    	k2.y = y;
			    	k2.x = (int) (r.getProperties().get("x1")-(x-r.getProperties().get("x1")));
			    	r.setProperties(k1,k2);
			    	shapes.add(r);
			    	repaint();
			 }
		 } else if (currentAction == 10) {
			 Rectangle b = new Rectangle();
			 Point v = new  Point();
			 v.x = x1;
			 v.y = y1;
			 b.setPosition(v);
			 b.setFillColor(Current_Colour);
			 b.setProperties(Math.abs(x-x1),Math.abs(y-y1));
			 if (released == true) {
			    shapes.add(b);
			 }
			 if (pressed == true) {
				 b.draw(g);
			 }
			 for (IShape s : shapes) {
			    	s.draw(g);
			 }
			 /*	 Point v=new  Point();
			    v.x=x1;
			    v.y=y1;

			URL[] urls=null;
			 try {
				 URL[]  url={new URL("jar:file:"+ "/C:/Users/TGT/Desktop/testloader/classes.jar"+"!/")};
				 urls=url;
				 } catch (MalformedURLException e1){// 
			e1.printStackTrace();}
			URLClassLoader urlClassLoader=URLClassLoader.newInstance(urls);
			Class d=null;
			try {
				d = urlClassLoader.loadClass("eg.edu.alexu.csd.oop.paint.Rectangle");

			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Object anotherDynamicObject=null;
			try {
				 anotherDynamicObject = d.newInstance();
			} catch (InstantiationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			try {
				try {
					Class[] cArg = new Class[1];
					cArg[0]=Point.class;
			        d.getMethod("setPosition",cArg).invoke( anotherDynamicObject,v);
					Class[] n=new Class[2];
					n[0]=double.class;
					n[1]=double.class;
					d.getMethod("setProperties",n).invoke( anotherDynamicObject,Math.abs(x-x1),Math.abs(y-y1));
					 if(released==true){
					shapes.add((eg.edu.alexu.csd.oop.paint.shape) anotherDynamicObject);}
					 if (pressed==true){
						 ((eg.edu.alexu.csd.oop.paint.shape) anotherDynamicObject).draw(g);}
				} 
						   catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.getCause().printStackTrace();

			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		 } else if (currentAction == 11 && enables) {
			 /*Square newsquare = new Square();
		     Point v = new  Point();
		     v.x = x1;
		     v.y = y1;
		     newsquare.setPosition(v);
		     newsquare.setFillColor(Current_Colour);
		     newsquare.setProperties(Math.abs(x-x1));
		   if (released == true) {
			   shapes.add(newsquare);
		   }
		   if (pressed == true) {
			   newsquare.draw(g);
		   }
		   for (IShape s : shapes) {
			   s.draw(g);
		   }*/
			 Point v = new  Point();
			 v.x = x1;
		     v.y = y1;
		     URL[] urls = null;
		     try {
		    	 URL[] url = {new URL("jar:file:" + path_to_jar+"!/")
		    	 };
		    	 urls = url;
		     } catch (MalformedURLException e1){
		    	 e1.printStackTrace();
		     }
		     URLClassLoader urlClassLoader = URLClassLoader.newInstance(urls);
		     Class d = null;
		     try {
		    	 d = urlClassLoader.loadClass("eg.edu.alexu.csd.oop.draw.cs79.Square");
		     } catch (ClassNotFoundException e) {
		    	 // TODO Auto-generated catch block
		    	 e.printStackTrace();
		     }
		     Object anotherDynamicObject = null;
		     try{
		    	 anotherDynamicObject = d.newInstance();
		     } catch (InstantiationException e1) {
		    			 // TODO Auto-generated catch block
		    	 e1.printStackTrace();
		     } catch (IllegalAccessException e1) {
		    			 // TODO Auto-generated catch block
		    	 e1.printStackTrace();
		     }
		     try {
		    	 try {
		    		 Class[] cArg = new Class[1];
		    		 cArg[0] = Point.class;
		    		 d.getMethod("setPosition",cArg).invoke(anotherDynamicObject,v);
		    		 Class[] n = new Class[2];
		    		 n[0] = double.class;
		    		 n[1] = double.class;
		    		 d.getMethod("setProperties",n).invoke( anotherDynamicObject,Math.abs(x - x1),Math.abs(x - x1));
				 if(released == true){
					 shapes.add((eg.edu.alexu.csd.oop.draw.IShape) anotherDynamicObject);}
				 if (pressed == true){
					 ((eg.edu.alexu.csd.oop.draw.IShape) anotherDynamicObject).draw(g);}
		    	 }
		    	 catch (IllegalAccessException e) {
		    		 // TODO Auto-generated catch block
		    		 e.printStackTrace();
		    	 } 
		     } catch (IllegalArgumentException e) {
		    	 // TODO Auto-generated catch block
		    	 e.printStackTrace();
		     } catch (InvocationTargetException e) {
		    	 e.getCause().printStackTrace();
		     } catch (NoSuchMethodException e) {
		    	// TODO Auto-generated catch block
		    	 e.printStackTrace();
		     } catch (SecurityException e) {
		    	// TODO Auto-generated catch block
		    	 e.printStackTrace();
		     }
		 } else if(currentAction == 12 && enableT) {
			 /*Triangle b = new Triangle();
			 Point v = new  Point();
			 v.x = x1;
			 v.y = y1;
			 b.setPosition(v);
			 b.setFillColor(Current_Colour);
			 Point k1 = new Point();
			 Point k2 = new Point();
			 k1.x = x;
		     k1.y = y;
		     if(k1.getX() > v.getX()){
		    	k2.x = (int)(v.getX() - (k1.getX() - v.getX()));
		    	k2.y = (int)k1.getY();
		     } else {
		    	k2.y = (int)k1.getY();
		    	k2.x = (int)(v.getX()+(v.getX()-k1.getX()));
		     }
		     b.setProperties(k1,k2);
		     if(released == true){
		    	 shapes.add(b);
		     }
		     if (pressed == true){
		    	 b.draw(g);
		     }
		     for (IShape s : shapes) {
		    	s.draw(g);
		     }*/
			 Point v = new  Point();
			 v.x = x1;
			 v.y = y1;
			 Point k1 = new Point();
			 Point k2 = new Point();
			 k1.x = x;
			 k1.y = y;
			 if(k1.getX() > v.getX()){
				 k2.x = (int) (v.getX()- (k1.getX()- v.getX()));
			     k2.y = (int) k1.getY();
			 } else {
				 k2.y = (int)k1.getY();
				 k2.x = (int) (v.getX()+(v.getX()-k1.getX()));
			 }	    
			 URL[] urls = null;
			 try {
				 URL[]  url = {new URL("jar:file:" + path_to_jar+"!/")};
				 urls = url;
			 } catch (MalformedURLException e1){// 
				 e1.printStackTrace();
			 }
			URLClassLoader urlClassLoader=URLClassLoader.newInstance(urls);
			Class d = null;
			try {
				d = urlClassLoader.loadClass("eg.edu.alexu.csd.oop.draw.cs79.Triangle");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Object anotherDynamicObject = null;
			try {
				 anotherDynamicObject = d.newInstance();
			} catch (InstantiationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				try {
					Class[] cArg = new Class[1];
					cArg[0] = Point.class;
			        d.getMethod("setPosition", cArg).invoke(anotherDynamicObject,v);
					Class[] n = new Class[2];
					n[0] = Point.class;
					n[1] = Point.class;
					d.getMethod("setProperties",n).invoke( anotherDynamicObject,k1,k2);
					 if(released == true){
					shapes.add((eg.edu.alexu.csd.oop.draw.IShape) anotherDynamicObject);
					 }
					 if (pressed == true){
						 ((eg.edu.alexu.csd.oop.draw.IShape) anotherDynamicObject).draw(g);
					 }
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.getCause().printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 } else if(currentAction == 13 && sel == true) {
			 Point c = new Point();
			 c.x = x;
			 c.y = y;
			 shapes.remove(r);
		 } else if(currentAction == 14) {
			 Oval newoval = new Oval();
			 Point v = new  Point();
			 v.x = x1;
			 v.y = y1;
			 newoval.setPosition(v);
			 newoval.setFillColor(Current_Colour);
			 newoval.setProperties(Math.sqrt(Math.pow(x1 - x,2)+Math.pow(y1 - y, 2)), Math.sqrt(0.5 * Math.pow(x1 - x,2) + Math.pow(y1 - y, 2)));
			 if(released == true) {
			    shapes.add(newoval);
			 }
			 if (pressed == true) {
				 newoval.draw(g);
			 }
			 for (IShape s : shapes){
			    	s.draw(g);
			 }
			 /*	 Point v=new  Point();
			    v.x = x1;
			    v.y = y1;

			URL[] urls = null;
			 try {
				 URL[]  url = {new URL("jar:file:"+ "/C:/Users/TGT/Desktop/testloader/classes.jar"+"!/")};
				 urls = url;
				 } catch (MalformedURLException e1){// 
			e1.printStackTrace();}
			URLClassLoader urlClassLoader=URLClassLoader.newInstance(urls);
			Class d=null;
			try {
				d = urlClassLoader.loadClass("eg.edu.alexu.csd.oop.paint.Oval");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Object anotherDynamicObject=null;
			try {
				 anotherDynamicObject = d.newInstance();
			} catch (InstantiationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			try {
				try {
					Class[] cArg = new Class[1];
					cArg[0]=Point.class;
			        d.getMethod("setPosition",cArg).invoke( anotherDynamicObject,v);
					Class[] n=new Class[2];
					n[0]=double.class;
					n[1]=double.class;
					d.getMethod("setProperties",n).invoke( anotherDynamicObject,Math.sqrt(Math.pow(x1-x,2)+Math.pow(y1-y, 2)),Math.sqrt(0.5*Math.pow(x1-x,2)+Math.pow(y1-y, 2)));
					 if(released==true){
					shapes.add((eg.edu.alexu.csd.oop.paint.shape) anotherDynamicObject);}
					 if (pressed==true){
						 ((eg.edu.alexu.csd.oop.paint.shape) anotherDynamicObject).draw(g);}
				} 
						   catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.getCause().printStackTrace();

			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		 }
		 for (IShape s : shapes) {
			 s.draw(g);
		 }
		 released = false;
	 }
}
