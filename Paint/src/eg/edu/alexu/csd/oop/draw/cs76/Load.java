package eg.edu.alexu.csd.oop.draw.cs76;

import java.awt.Color;
import java.awt.Point;
import java.awt.Shape;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonParseException;

import eg.edu.alexu.csd.oop.draw.IShape;

public class Load {
	ObjectMapper mapper = new ObjectMapper();

	@SuppressWarnings("null")
	public ArrayList<IShape> loadXML() throws SAXException, ParserConfigurationException {
		ArrayList<IShape> Saved_window = new ArrayList<IShape>() ;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance() ;
		IShape shape ;
		try {
			factory.setIgnoringComments(true);
		    factory.setIgnoringElementContentWhitespace(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse("oop.txt") ;
			NodeList shapes = document.getElementsByTagName("shape");
			Element attribute ;
			//Element attribute;
			Color colour, fillcolour ;
			System.out.println(shapes.getLength());
			for(int j = 0; j < shapes.getLength(); j++) {
				Node node = shapes.item(j);
				attribute = (Element) node;
				Point p = new Point();
				String type1 = null;
				if(attribute.getNodeType() == Node.ELEMENT_NODE){
					p.x = (int) Double.parseDouble(attribute.getElementsByTagName("x").item(0).getTextContent());
					p.y = (int) Double.parseDouble(attribute.getElementsByTagName("y").item(0).getTextContent());
				    colour = Color.black;
				    fillcolour = Color.black;
					type1 = attribute.getElementsByTagName("type").item(0).getTextContent();
					if(type1.equals("1")){
						shape = new Line();
						Point a = new Point();
						Point b = new Point();
						a.x = (int) Double.parseDouble(attribute.getElementsByTagName("x1").item(0).getTextContent());
						a.y = (int) Double.parseDouble(attribute.getElementsByTagName("y1").item(0).getTextContent());
						b.x = (int) Double.parseDouble(attribute.getElementsByTagName("x2").item(0).getTextContent());
						b.y = (int) Double.parseDouble(attribute.getElementsByTagName("y2").item(0).getTextContent());
						shape.setProperties(a,b);
						Saved_window.add(shape);
						}
					if(type1.equals("2")){
						double a = Double.parseDouble(attribute.getElementsByTagName("a").item(0).getTextContent());
						double b = Double.parseDouble(attribute.getElementsByTagName("b").item(0).getTextContent());
						if(a == b){
							Circle newcircle = new Circle();
							newcircle .setPosition(p);
							newcircle.setProperties(a);	
							shape = newcircle;
							Saved_window.add(shape);
						}else{
							shape = new Oval();
							shape.setPosition(p);
							shape.setProperties(a,b);	
							Saved_window.add(shape);
						}
					} else if(type1.equals("3")){
						double a = Double.parseDouble(attribute.getElementsByTagName("a").item(0).getTextContent());
						double b = Double.parseDouble(attribute.getElementsByTagName("b").item(0).getTextContent());
						if(a == b){
							Square newsquare = new Square();
							newsquare.setPosition(p);
							newsquare.setProperties(a);
				            shape = newsquare;
							Saved_window.add(shape);
						} else {
							shape = new Rectangle();
							shape.setPosition(p);
							shape.setProperties(a, b);
							Saved_window.add(shape);
						}
					} else if(type1.equals("4")) {
						shape = new Triangle();
						Point firstcorner = new Point();
						Point secondcorner = new Point();
						shape.setPosition(p);
						firstcorner.x = (int) Double.parseDouble(attribute.getElementsByTagName("x2").item(0).getTextContent());
						firstcorner.y = (int) Double.parseDouble(attribute.getElementsByTagName("y2").item(0).getTextContent());
						secondcorner.x = (int) Double.parseDouble(attribute.getElementsByTagName("x3").item(0).getTextContent());
						secondcorner.y = (int) Double.parseDouble(attribute.getElementsByTagName("x3").item(0).getTextContent());
						shape.setProperties(firstcorner, secondcorner);
						Saved_window.add(shape);
					}
				}
			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Saved_window;
	}
	@SuppressWarnings("unchecked")
	public ArrayList<IShape> loadjson() throws JSONException, JsonParseException, JsonMappingException, IOException{
		ArrayList<IShape> savedFile = new ArrayList<IShape>();
		JSONParser parser = new JSONParser();
		Stack<String> h = new Stack<String>();
		try {
			Object obj = parser.parse(new FileReader("oopjson.txt"));
			JSONObject jsonObject = new JSONObject( obj);
	        String g = obj.toString();
	        int count = 0;
	        int i = 0;
	        String[]arr = new String[30];
	        while(count<g.length()) {
	        	if(g.charAt(count) == '{'){
	        		h.push("{");
	        	} else if(g.charAt(count) == '}') {
	        		h.pop();
	        		if(h.isEmpty()) {
	        			i++;
	        		}
	        	}
	        	if(i < arr.length && !String.valueOf(g.charAt(count)).equals("[")) {
	        	  arr[i] = arr[i] + g.charAt(count);
	        	}
	        	count++;
	        }
	        for(i = 0;i < arr.length; i++) {
	        	  arr[i] = arr[i] + "}";
	        	  for(int j = 0; j < arr[i].length(); j++){
	        		  if(arr[i].charAt(j) == '{') {
	        			  arr[i] = arr[i].substring(j);
	        			  j = arr[i].length();
	        		  }
	        	  }
	        }
	        for(i = 0; i < size(arr) - 1; i++) {
	          JSONObject json = new JSONObject(arr[i]);
	          int type = json.getInt("type");
	          if(type == 1){
	        	  String f = arr[i].toString();
	        	  Line v = mapper.readValue(f, Line.class);
	        	  savedFile.add(v);
	          }
	          if(type == 2){
	        	  String f = arr[i].toString();
	        	  Oval v = mapper.readValue(f, Oval.class);
	        	  savedFile.add(v);
	          }
	          if(type == 3){
	        	  String f = arr[i].toString();
	        	  Rectangle v = mapper.readValue(f, Rectangle.class);
	        	  savedFile.add(v);
	          }
	          if(type == 4){
	        	  URL[] urls = null;
	        	  /*Triangle v = mapper.readValue(f, Triangle.class);
	        	  savedFile.add(v);*/
	        	  String f = arr[i].toString();
	     		 try {
	     			 URL[] url = {new URL("jar:file:" +"/C:/Users/MonaAlaa/paint.jar" +"!/")};
	     			 urls = url;
	     		 } catch (MalformedURLException e1){
	     				 e1.printStackTrace();
	     		 }
	     		URLClassLoader urlClassLoader = URLClassLoader.newInstance(urls);
	     		Class d1 = null;
	     		try {
	     			d1 = urlClassLoader.loadClass("eg.edu.alexu.csd.oop.draw.cs79.Triangle");
	     		} catch (ClassNotFoundException e) {
	     			// TODO Auto-generated catch block
	     			e.printStackTrace();
	     		}
	     		Object anotherDynamicObject = null;
	     		try {
	     			 anotherDynamicObject = d1.newInstance();
	     		} catch (InstantiationException e1) {
	     			// TODO Auto-generated catch block
	     			e1.printStackTrace();
	     		} catch (IllegalAccessException e1) {
	     			// TODO Auto-generated catch block
	     			e1.printStackTrace();
	     		}
	     		anotherDynamicObject = mapper.readValue(f,d1);
	     		savedFile.add((IShape) anotherDynamicObject);
	          }
	        }
		} catch (Exception e) {
	          e.printStackTrace();
	      }
		return savedFile;
	}
	public int size(String[]k) {
		for(int i = 0; i < k.length; i++) {
			if(k[i].equals("null}")) {
				return i;
			}
		}
		return 0;
	}
}
