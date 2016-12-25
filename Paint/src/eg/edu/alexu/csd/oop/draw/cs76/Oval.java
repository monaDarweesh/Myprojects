package eg.edu.alexu.csd.oop.draw.cs76;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import eg.edu.alexu.csd.oop.draw.IShape;


public class Oval implements IShape {

	 Point place;
	 Color col_out; 
	 Color col_in;
	 int type = 2;
	Map<String, Double> properties = new HashMap<>();
	@Override
	public int get() {
		// TODO Auto-generated method stub
		return 0;
	}
	public int gettype(){
		return this.type;
	}
	public IShape transform(Point v){
		this.setPosition(v);
		this.setProperties(this.getProperties().get("a"),this.getProperties().get("b"));
		return  this;
	}
	public boolean contains(IShape j,Point k){
		if((k.getX() > j.getProperties().get("x") - j.getProperties().get("a")) && (k.getX()<j.getProperties().get("x") + j.getProperties().get("a"))&&(k.getY()>j.getProperties().get("y")-j.getProperties().get("a")&&(k.getY()<j.getProperties().get("y")+j.getProperties().get("a"))))
		{return true;}
		else {
			return false;
		}
	}
	public void setPosition(Point position) {
		this.place = position;

	}
	public void setProperties(Point a, Point b) {

	}
	public Point getPosition() {
		
		return this.place;
	}

	
	public void setProperties(double a,double b) {
	this.properties.put("x", (double)this.getPosition().getX());
	this.properties.put("y", (double)this.getPosition().getY());	
	this.properties.put("a", a);
	this.properties.put("b", b);
	}

	
	public Map<String, Double> getProperties() {
		
		return properties;
	}

	
	public void setColor(Color color) {
		this.col_out = color;

	}

	
	public Color getColor() {
		
		return this.col_out;
	}


	public void setFillColor(Color color) {
		this.col_in = color;

	}

	
	public Color getFillColor() {
		
		return this.col_in;
	}
	public void draw(Graphics canvas) {
		canvas.setColor(this.getColor());
		canvas.drawOval(this.getProperties().get("x").intValue(), this.getProperties().get("y").intValue(), this.getProperties().get("a").intValue(), this.getProperties().get("b").intValue());
		canvas.setColor(this.getFillColor());
		canvas.fillOval(this.getProperties().get("x").intValue(), this.getProperties().get("y").intValue(), this.getProperties().get("a").intValue(), this.getProperties().get("b").intValue());
	}
}
