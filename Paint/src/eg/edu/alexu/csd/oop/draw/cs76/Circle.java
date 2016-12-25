package eg.edu.alexu.csd.oop.draw.cs76;

import java.awt.Point;

import eg.edu.alexu.csd.oop.draw.IShape;


public  class Circle extends Oval {
	
public IShape transform(Point v){
		this.setPosition(v);
		this.setProperties(this.getProperties().get("a"));
		return  this;
	}
	public void setProperties(double a) {
		this.properties.put("x", (double)this.place.x);	
		this.properties.put("y", (double)this.place.y);	
		this.properties.put("a", a);
		this.properties.put("b", a);
		
		
	}

}
