package eg.edu.alexu.csd.oop.draw.cs76;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import eg.edu.alexu.csd.oop.draw.IShape;

public class Rectangle implements IShape {
	Point place;
	Color col_out; 
	Color fillColor;
	int type = 3;
	Map<String, Double> properties=new HashMap<>();
	
	public int get() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int gettype() {
		// TODO Auto-generated method stub
		return this.type;
	}

	@Override
	public IShape transform(Point v) {
		this.setPosition(v);
		this.setProperties(this.getProperties().get("a"),this.getProperties().get("b"));
		return this;
	}
	@Override
	public boolean contains(IShape j, Point k) {
		if(k.getX()>this.getProperties().get("x")){
			if(k.getX()<(this.getProperties().get("x").intValue()+this.getProperties().get("a"))&&(k.getY()>this.getProperties().get("y"))&&(k.getY()<(this.getProperties().get("y")+this.getProperties().get("b")))){
				return true;
			}
			else {
				return false;
			}
		}
		else if(k.getX()<this.getProperties().get("x")){
			if(k.getX()>(this.getProperties().get("x").intValue()-this.getProperties().get("a"))&&(k.getY()>this.getProperties().get("y"))&&(k.getY()<(this.getProperties().get("y")+this.getProperties().get("b")))){
				return true;
			}
			else {
				return false;
			}
		}
		else{
		return false;}
	}

	@Override
	public void setPosition(Point position) {
		this.place=position;

	}

	@Override
	public Point getPosition() {
		
		return this.place;
	}

	@Override
	public void setProperties(double a, double b) {
		this.properties.put("x", (double)this.getPosition().getX());	
		this.properties.put("y", (double)this.getPosition().getY());	
		this.properties.put("a", a);
		this.properties.put("b", b);

	}

	@Override
	public void setProperties(Point a, Point b) {
		// TODO Auto-generated method stub

	}

	@Override
	public Map<String, Double> getProperties() {
		
		return this.properties;
	}

	@Override
	public void setColor(Color color) {
		this.col_out=color;

	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return this.col_out;
	}

	@Override
	public void setFillColor(Color color) {
		this.fillColor=color;

	}

	@Override
	public Color getFillColor() {
		// TODO Auto-generated method stub
		return this.fillColor;
	}

	@Override
	public void draw(Graphics canvas) {
		canvas.setColor(fillColor);
		canvas.setColor(this.getColor());
		canvas.fillRect(this.getProperties().get("x").intValue(), this.getProperties().get("y").intValue(), this.getProperties().get("a").intValue(), this.getProperties().get("b").intValue());
		canvas.drawRect(this.getProperties().get("x").intValue(), this.getProperties().get("y").intValue(), this.getProperties().get("a").intValue(), this.getProperties().get("b").intValue());
	}
}