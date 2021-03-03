package test1;

public class Circle extends Shape
{
	//field
	private double radius = 5;
	
	// get methods
	
	public double getRadius()
	{
		return this.radius;
	}
	
	public double getArea() // this method is non-static, refers to specific object of type Cicle, will have access to radius within instance.
	{
		return Math.PI * this.radius * this.radius;
				// to refer to specific object on which the method is called, use this. keyword. No need to pass radius as input.
	}
}
