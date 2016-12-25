package eg.edu.alexu.csd.oop.draw.cs76;

import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.management.RuntimeErrorException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.databind.ObjectMapper;

import eg.edu.alexu.csd.oop.draw.IShape;

public class Save {
	ObjectMapper mapper = new ObjectMapper();
	public void SaveXML(ArrayList<IShape> current_Shapes){
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance() ;
		try {
			//declare the file by using path.
			File paintfile = new File("oop.txt");
			DocumentBuilder saveShapes = factory.newDocumentBuilder() ;
			Document paint = saveShapes.newDocument();
			Element element = paint.createElement("shapes") ;
			paint.appendChild(element) ;
			Element shape ;
			Node type;
			int s ;
			String t;
			for(int i = 0; i < current_Shapes.size(); i++ ){
				shape = paint.createElement("shape");
				Attr num = paint.createAttribute("id");
				num.setValue(Integer.toString(i+1));
				shape.setAttributeNode(num);
				type = paint.createElement("type");
				s = current_Shapes.get(i).gettype();
				t = String.valueOf(s);
				type.appendChild(paint.createTextNode(t));
				shape.appendChild(type);
				Node x = paint.createElement("x");
				try {
				x.appendChild(paint.createTextNode((current_Shapes.get(i).getPosition().x)+""));
				} catch (Exception e) {
					x.appendChild(paint.createTextNode("-1"));	
				}
				shape.appendChild(x) ;
				Node y = paint.createElement("y");
				try{
				y.appendChild(paint.createTextNode((current_Shapes.get(i).getPosition().y+"")));
				}catch(Exception e)
				{
					y.appendChild(paint.createTextNode("-1"));	
				}
				shape.appendChild(y) ;
				
				Node color = paint.createElement("color");
				try{
				color.appendChild(paint.createTextNode(current_Shapes.get(i).getColor().getRGB()+""));
				}catch(Exception e)
				{
					color.appendChild(paint.createTextNode("-1"));	
				}
				shape.appendChild(color) ;
			
				Node fillcolor = paint.createElement("fillcolor");
				try{
				fillcolor.appendChild(paint.createTextNode(current_Shapes.get(i).getFillColor().getRGB()+""));
				}catch(Exception e)
				{
					fillcolor.appendChild(paint.createTextNode("-1"));	
				}
				shape.appendChild(fillcolor) ;
			
				try {
					Map<String,Double> m = current_Shapes.get(i).getProperties() ;
				for(Map.Entry<String, Double> entry : m.entrySet())
				{
					Node prop = paint.createElement(entry.getKey());
					if(entry.getValue() == null)
					{
						prop.appendChild(paint.createTextNode("-1"));		
					}else
						prop.appendChild(paint.createTextNode(entry.getValue()+""));
					
					shape.appendChild(prop) ;
				}
					}catch(Exception e){
					}
				element.appendChild(shape);
			}
			// Save the document to the file
	        TransformerFactory tranFactory = TransformerFactory.newInstance();
	        Transformer aTransformer = tranFactory.newTransformer();
			
	     // format the XML nicely
	        aTransformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");

	        aTransformer.setOutputProperty(
	                "{http://xml.apache.org/xslt}indent-amount", "4");
	        aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");

	        DOMSource source = new DOMSource(paint);
	        try {
	        	// location and name of XML file you can change as per need
	        	FileWriter XmlFileWriter = new FileWriter(paintfile) ;
	            StreamResult result = new StreamResult(XmlFileWriter) ;
	            aTransformer.transform(source, result) ;
	            } catch (IOException e) {
	            	e.printStackTrace();
	            }
	        } catch (ParserConfigurationException e) {
	        	// TODO Auto-generated catch block
	        	e.printStackTrace();
	        	throw new RuntimeErrorException(null) ;
	        } catch (TransformerConfigurationException e) {
	        	// TODO Auto-generated catch block
	        	e.printStackTrace();
			    throw new RuntimeErrorException(null) ;
	        } catch (TransformerException e) {
	        	// TODO Auto-generated catch block
			    e.printStackTrace();
			    throw new RuntimeErrorException(null) ;
	        }
	}

public void savejson(ArrayList<IShape> k){
 
	try {
		mapper.writerWithDefaultPrettyPrinter().writeValue(new File("oopjson.txt"), k);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}	
 
}
