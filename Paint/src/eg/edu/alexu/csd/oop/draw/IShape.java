package eg.edu.alexu.csd.oop.draw;

import java.awt.Point;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public interface IShape {
	public int get();
	public  int gettype();
	@JsonIgnore
	public IShape transform(Point v);
	@JsonIgnore
	public boolean contains(IShape j,Point k);
	@JsonIgnore
	public void setPosition(java.awt.Point position);
	public java.awt.Point getPosition();
	@JsonIgnore
	public void setProperties(double a,double b);
	@JsonIgnore
	public void setProperties(Point a,Point b);
	public java.util.Map<String, Double> getProperties();
	@JsonIgnore
	public void setColor(java.awt.Color color);
	@JsonIgnore
	public java.awt.Color getColor();
	@JsonIgnore
	public void setFillColor(java.awt.Color color);
	@JsonIgnore
	public java.awt.Color getFillColor();
	@JsonIgnore
	public void draw(java.awt.Graphics canvas);
	//public Object clone() throws CloneNotSupportedException;
}