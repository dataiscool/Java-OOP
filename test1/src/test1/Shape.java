package test1;

public class Shape 
{
	//field
	private String colour;
	
	// get/set methods
	
	public String getColour() //getter
	{
		return this.colour;
	}
	
	public void setColour(String c) //setter
	{
		this.colour = c;
	}
	
	public void displayInfo()
	{
		System.out.println("the shape is a " +this.getColour()+" circle");
	}
}
