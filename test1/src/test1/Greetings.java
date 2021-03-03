package test1;

public class Greetings {

	public static void hello(String myName) // MODIFIERS: static, (empty) non-static
	{
		System.out.println("My name is: "+myName);
	}
	
	public static void main(String[] args) 
	{
	// We can call the hello method from this method as if it were a value method, which is in the same class Greetings
		//hello("John"); //hello method has to be static to be called directly.
	}
//Note that main method is not required in a class 
}
