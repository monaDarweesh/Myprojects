package eg.edu.alexu.csd.oop.draw.cs76;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import eg.edu.alexu.csd.oop.draw.IShape;

public class Line implements IShape {

	private Color line_color;
	
	Map<String, Double> properties = new HashMap<>();
	
	public int gettype() {
		return 1;
	}
	
	public boolean contains (IShape j, Point pressed_point) {
		int Total_distance = (int) Math.sqrt(Math.pow(this.getProperties().get("x1").intValue() - this.getProperties().get("x2").intValue(), 2) +
				Math.pow(this.getProperties().get("y1").intValue() - this.getProperties().get("y2").intValue(), 2));
		
		int dis1 = (int) Math.sqrt(Math.pow(this.getProperties().get("x1").intValue() - (int)pressed_point.getX(), 2) 
				       + Math.pow(this.getProperties().get("y1").intValue() - (int)pressed_point.getY(), 2));
		
		int dis2 = (int) Math.sqrt(Math.pow((int)pressed_point.getX()-this.getProperties().get("x2").intValue(), 2)
				       + Math.pow((int)pressed_point.getY()-this.getProperties().get("y2").intValue(), 2));
		
		if(dis1 + dis2 == Total_distance) {
			return true;
		}
		else {
			return false;
		}
	}
	public IShape transform(Point v) {

		double distance = Math.sqrt(Math.pow(this.getProperties().get("x1") - this.getProperties().get("x2"), 2) +
				          Math.pow(this.getProperties().get("y1") - this.getProperties().get("y2"), 2));
		
		double slope = (this.getProperties().get("y1") - this.getProperties().get("y2"))
				/ (this.getProperties().get("x1") - this.getProperties().get("x2"));
		Point k1 = new Point();
		Point k2 = new Point();
		
		if((int)slope <= 0) {
			k1.x = (int) (v.getX() - 0.5 * distance*Math.cos(Math.atan(Math.abs(slope))));
		    k1.y = (int) (v.getY() - 0.5 * distance*Math.sin(Math.atan(Math.abs(slope))));
		    k2.x = (int) (v.getX() + 0.5 * distance*Math.cos(Math.atan(Math.abs(slope))));
		    k2.y = (int) (v.getY() + 0.5 * distance*Math.sin(Math.atan(Math.abs(slope))));}
		else {
			k1.x = (int) (v.getX() + 0.5 * distance*Math.cos(Math.atan(Math.abs(slope))));
			k1.y = (int) (v.getY() - 0.5 * distance*Math.sin(Math.atan(Math.abs(slope))));
			k2.x = (int) (v.getX() - 0.5 * distance*Math.cos(Math.atan(Math.abs(slope))));
			k2.y = (int) (v.getY() + 0.5 * distance*Math.sin(Math.atan(Math.abs(slope))));
		}
		this.setProperties(k1,k2);
		return  this;
	}
	@Override
	public void setProperties(double a, double b) {
	}
	public void setProperties(Point a, Point b) {
		this.properties.put("x1", (double)a.getX());
		this.properties.put("y1", (double)a.getY());
		this.properties.put("x2", (double)b.getX());
		this.properties.put("y2", (double)b.getY());
	}
	@Override
	public Map<String, Double> getProperties() {
		// TODO Auto-generated method stub
		return properties ;
	}
	
	@Override
	public void setColor(Color color) {
		line_color = color;
	}
	
	@Override
	public Color getColor() {
		return line_color;
	}

	@Override
	public void setFillColor(Color color) {
		// TODO Auto-generated method stub
	}

	@Override
	public Color getFillColor() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void draw(Graphics canvas) {
		canvas.setColor(this.getColor());
		canvas.drawLine(this.getProperties().get("x1").intValue(),
				this.getProperties().get("y1").intValue(),
				this.getProperties().get("x2").intValue(),
				this.getProperties().get("y2").intValue());
	}
	public void setPosition(Point position) {
	}
	@Override
	public Point getPosition() {
		return null;
	}
	@Override
	public int get() {
		// TODO Auto-generated method stub
		return 0;
	}
}
