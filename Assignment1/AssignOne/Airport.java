package AssignOne;

public class Airport 
{
	// Field
	private int x;
	private int y;
	private int fees;
	
	// Constructor 
	public Airport(int x, int y, int fees)
	{
		this.x = x;
		this.y = y;
		this.fees = fees;
	}
	
	// accessors
	public int getFees()
	{
		return this.fees;
	}
	
	// custom methods
	public static int getDistance(Airport a, Airport b)
	{
		int dist;
		int xDiff = a.x - b.x;
		int yDiff = a.y - b.y;
		
		dist = (int) Math.ceil(Math.pow((Math.pow(xDiff, 2)+Math.pow(yDiff, 2)) , 0.5));
		
		return dist;
	}
	
	// test
	
	/* public static void main(String[] args)
	{
		Airport a = new Airport(5, 7, 30);
		Airport b = new Airport(86, 32, 60);
		
		System.out.println(Airport.getDistance(a, b));
	} */
}
