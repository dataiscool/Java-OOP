package test1;

public class RemoteGreeting 
{
	public static void main (String[] args)
	{
		//STATIC modifier
	// Note that this RemoteGreeting is found in the same package as Greetings, no need to import:
		
		Greetings.hello("John"); // Specify class in which the hello method is found: Greetings
		/* Note that the hello method in Greetings class takes 1 string input and return void. 
		 * Further note that the hello method has modifier static, which means it is not belonging to an object */
		
		
		//NON-STATIC modifier
		//If you remove the static modifier, the method hello will have to be called on an object

		Greetings g;
		g = new Greetings(); // Create a new object named g of type Greetings
		g.hello("John"); 
		/* hello is a method part of the non-static class Greetings, 
		 * therefore have to call it on the object g of that class.*/
		
	}
}
