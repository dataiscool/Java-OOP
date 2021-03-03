package test1;

public class TestShapes {
public static void main(String[] args)
{
	Shape s = new Shape(); //default constructor
	s.setColour("Green"); 
	System.out.println(s.getColour());
	
	Circle c = new Circle();
	System.out.println(c.getRadius());
	
	// Because Shape is the superclass of Circle, we can use the inherited methods and fields. 
	// Cannot call a subclass method on a superclass object however.
	
	c.setColour("red");
	System.out.println(c.getColour); 
	
	Shape[] shapes = {s,c,t};
	for(int = 0; i < shapes.length; i++)
	{
		Shape myShape = shapes[i];
				myShape.displayInfo();
	}
}
}
