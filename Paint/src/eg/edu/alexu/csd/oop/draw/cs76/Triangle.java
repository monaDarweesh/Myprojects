package eg.edu.alexu.csd.oop.draw.cs76;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import eg.edu.alexu.csd.oop.draw.IShape;

public class Triangle implements IShape {
	Point place;
	Color col_out; 
	Color col_in;
	Map<String, Double> properties=new HashMap<>();
	public int gettype() {
		
		return 4;
	}
	public IShape transform(Point v) {
		int length = Math.abs((this.getProperties().get("y2").intValue() - this.getProperties().get("y1").intValue())); 
		int base = Math.abs((this.getProperties().get("x2").intValue() - this.getProperties().get("x3").intValue()));
		this.setPosition(v);
		Point k1=new Point();
		Point k2=new Point();
		k1.x=(int) (v.getX()+base/2);
		k1.y=(int) (v.getY()+length);
		k2.y=(int) (v.getY()+length);
		k2.x=(int) (v.getX()-base/2);
		this.setProperties(k1, k2);
		return this;
	}

	@Override
	public boolean contains(IShape j, Point k) {
		if(k.getY()>this.getProperties().get("y1")&&k.getY()<this.getProperties().get("y2")&&k.getX()>this.getProperties().get("x3")&&k.getX()<this.getProperties().get("x2")){
			return true;
		}
		else {
		return false;}
	}

	public void setPosition(Point position) {
		this.place=position;

	}

	public Point getPosition() {
		// TODO Auto-generated method stub
		return this.place;
	}
	public void setProperties(double a, double b) {
		// TODO Auto-generated method stub
	}
	public void setProperties(Point a, Point b) {
		this.properties.put("x1", this.place.getX());
		this.properties.put("y1", this.place.getY());
		this.properties.put("x2", a.getX());
		this.properties.put("y2", a.getY());
		this.properties.put("x3", b.getX());
		this.properties.put("y3", b.getY());

	}
	public Map<String, Double> getProperties() {	
		return this.properties;
	}
	public void setColor(Color color) {
		this.col_out = color;

	}
	public Color getColor() {
		// TODO Auto-generated method stub
		return this.col_out;
	}

	public void setFillColor(Color color) {
		this.col_in = color;
	}

	public Color getFillColor() {
		// TODO Auto-generated method stub
		return this.col_in;
	}
	public void draw(Graphics canvas) {
		int [] pointx = {this.getProperties().get("x1").intValue(),this.getProperties().get("x2").intValue(),this.getProperties().get("x3").intValue()};
		int [] pointy = {this.getProperties().get("y1").intValue(),this.getProperties().get("y2").intValue(),this.getProperties().get("y3").intValue()};
		canvas.setColor(this.getFillColor());
		canvas.fillPolygon(pointx, pointy, 3);
		canvas.setColor(this.getColor());
		canvas.drawPolygon(pointx, pointy, 3);
	}
	@Override
	public int get() {
		// TODO Auto-generated method stub
		return 0;
	}
}
