// WRITE NAME AND SHIT


package AssignOne;

public abstract class Reservation 
{
	// fields
	private String name;
	
	//constructors
	public Reservation(String name)
	{
		this.name = name;
	}
	public final String reservationName() // does not allow subclass to override
	{
		return this.name;
	}
	
	// to be implemented in FlightReservation and HotelReservation classes.
	public abstract int getCost(); 
	public abstract boolean equals(Object r); 
	//override Object .equals method for Reservation type objects. same signature and return type.

}