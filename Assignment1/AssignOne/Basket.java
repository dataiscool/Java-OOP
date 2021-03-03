package AssignOne;

public class Basket 
{
	// fields
	private Reservation[] r;
	
	// constructors
	public Basket()
	{
		this.r = new Reservation[0]; // use dynamic memory allocation
	}
	
	// methods
	public Reservation[] getProducts()
	{
		Reservation[] resCopy = new Reservation[this.r.length];
		
		for (int i = 0; i < this.r.length; i++)
		{
			resCopy[i] = this.r[i];
		}
		
		return resCopy;
	}
	
	public int add(Reservation newResEntry)
	{
		Reservation[] newArray = new Reservation[this.r.length+1]; // create new array of same size as input + 1.
		
		for (int i = 0; i < this.r.length; i++) // copy over old array to new one
		{
			newArray[i] = this.r[i]; 
		}
		
		newArray[this.r.length] = newResEntry; // insert new entry at the end of the list
		this.r = newArray; // update the field with the new array
		
		return r.length;
	}
	
	public boolean remove(Reservation removedEntry)
	{
		int j = 0; // use this to index new array
		int count = 0; // use this to retain whether match was found
		int findMatch = 0; // use this to store the index of the match
		Reservation[] newArray = new Reservation[this.r.length-1];
		
		for(int i = 0; i < this.r.length; i++) 
		{
			if (this.r[i].equals(removedEntry))
			{
				count = 1;
				findMatch = i;
				break;
			}
		}
		
		// the below loop runs if a match was found. copy over the elements except for the match
		if(count == 1)
		{
		for(int k = 0; k < this.r.length; k++)	
			{
				if(k != findMatch) 
				{
					newArray[j] = this.r[k];
					j = j+1;
				}
			}
		
			this.r = newArray;
			return true;
		}
		else
		{
			return false;
		}
		
	}
	
	public void clear()
	{
		this.r = new Reservation[0];
	}
	
	public int getNumOfReservations()
	{
		return this.r.length;
	}
	
	public int getTotalCost()
	{
		int totalCost = 0;
		for (int i = 0; i < this.r.length; i++)
		{
			// Implements either getCost() from HotelReservation, BnbReservation or FlightReservation.
			// Object type of the reservation determines which getCost method is called. (Polymorphism)
			// Implemented by the abstract getCost method in Reservation class.
			totalCost = totalCost + r[i].getCost(); 
		}
		return totalCost;
	}
	
	// test
	/*public static void main(String[] args) 
	{
		Room[] ritz = new Room[4];
		ritz[0] = new Room("queen");
		ritz[1] = new Room("double");
		ritz[2] = new Room("king");
		ritz[3] = new Room("queen");
		
	Hotel Ritz = new Hotel("Ritz-Carlton", ritz);
	Reservation newResEntry = new BnBReservation("John", Ritz, "queen", 2); // implicit upcasting of BnBReservation to Reservation
	Reservation newResEntry2 = new BnBReservation("John", Ritz, "double", 2);
	Basket basketOne = new Basket();
	
	int size = basketOne.add(newResEntry);
	int size2 = basketOne.add(newResEntry2);
	int size3 = basketOne.add(newResEntry2);
	int size4 = basketOne.add(newResEntry);
	boolean removeOperation = basketOne.remove(newResEntry2);
	Reservation[] resList = basketOne.getProducts();
	
			System.out.println(basketOne.getNumOfReservations());
			System.out.println( ((BnBReservation) resList[2]).getCost()); 
			// use type casting to use the getCost() method found in subclass. Note that it is not required, since we used abstract in Reservation superclass and can use polymorphism
			System.out.println(basketOne.getTotalCost());
	} */
}
