package eg.edu.alexu.csd.oop.draw.cs76;

public class Square extends Rectangle {
	public void setProperties(double a) {
		this.properties.put("x", (double)this.place.x);	
		this.properties.put("y", (double)this.place.y);	
		this.properties.put("a", a);
		this.properties.put("b", a);
	}
}
