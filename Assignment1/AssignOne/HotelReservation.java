package AssignOne;

public class HotelReservation extends Reservation
{
	// field
	private Hotel place;
	private String roomType;
	private int nightStays;
	private int pricePerNRB;
	
	// constructors
	public HotelReservation(String name, Hotel place, String roomType, int nightStays)
	{
		super(name); //call constructor of the superclass
		this.place = place;
		this.roomType = roomType;
		this.nightStays = nightStays;
		
		this.place.reserveRoom(this.roomType);
	}
	
	// get/set methods
	public int getNumOfNights()
	{
		return this.nightStays;
	}
	
	// implement abstract methods
	public int getCost()
	{
		Room a = new Room(roomType);
		this.pricePerNRB = a.getPrice();
		//int price = place.reserveRoom(this.roomType);
		int subtotal = this.pricePerNRB*this.nightStays;
		return subtotal;
	}
	
	public boolean equals(Object a)
	{
		if(a instanceof HotelReservation && this.reservationName().equalsIgnoreCase(((HotelReservation) a).reservationName()) && this.place.equals(((HotelReservation) a).place) && this.nightStays == ((HotelReservation) a).nightStays && this.getCost() == ((HotelReservation) a).getCost())
				{
					return true;
				}
		else
		{
			return false;
		}
	}
	
	// test
	/* public static void main(String[] args)
	{
		Room[] ritz = new Room[4];
		ritz[0] = new Room("queen");
		ritz[1] = new Room("double");
		//ritz[1].changeAvailability();
		ritz[2] = new Room("king");
		ritz[3] = new Room("queen");
		
		Hotel Ritz = new Hotel(ritz, "Ritz-Carlton");
		
		// Reserve two rooms at the same hotel Ritz (same instance)
		HotelReservation a = new HotelReservation("John", Ritz, "queen", 2);
		HotelReservation b = new HotelReservation("John", Ritz, "queen", 2);
		
		System.out.println(a.equals(b));
		
	} */
	

}
