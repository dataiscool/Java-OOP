package AssignOne;

public class FlightReservation extends Reservation
{
	// fields
	private Airport departure;
	private Airport arrival;
	
	//constructors
	public FlightReservation(String name, Airport a, Airport b)
	{
		super(name); // calls the Reservation constructor in Reservation class
		this.departure = a;
		this.arrival = b;
		
		if (a.equals(b))
		{
			throw new IllegalArgumentException("Departure and arrival cannot be the same airport.");
		}
	}
	
	// implement abstract methods from superclass
	public int getCost()
	{
		double miscFees = 5375;
		double fuelCost = Airport.getDistance(this.departure, this.arrival) * 124/167.52;
		int totalCost = (int) Math.ceil(fuelCost + this.departure.getFees() + this.arrival.getFees() + miscFees);
		
		return totalCost;	
	}
	
	public boolean equals(Object a)
	{
		if (a instanceof FlightReservation && this.reservationName().equalsIgnoreCase(((FlightReservation) a).reservationName()) && this.departure.equals(((FlightReservation) a).departure) && this.arrival.equals(((FlightReservation) a).arrival)) 
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
		Airport a = new Airport(32, 52, 2000);
		Airport b = new Airport(800, 880, 2000);
		FlightReservation QWERT = new FlightReservation("John",a, b);
		FlightReservation QWERTY = new FlightReservation("John",a, b);
		System.out.println(QWERT.reservationName());
		System.out.println(QWERT.getCost()+" cents");
		System.out.println(QWERTY.equals(QWERT));
	} */
}
