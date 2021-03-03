package test1;
import java.util.Arrays;

public class ErrorsAndExceptions {

	public static void main(String[] args) {
		
		
		// Note that by throwing an error, the remaining code does not run.
		//Therefore it is better to try and catch the error as below depending on the situation.
		
		//Catching error
		int[] myArr = {1,2,3};
		try
		{
			System.out.println(myArr[4]);
		}
		catch (ArrayIndexOutOfBoundsException myErr)
		{
			System.out.println("Index is not defined. \n");
			myErr.printStackTrace(); //Prints all information related to caught expression
			System.out.println("\n"+myErr +"\n"); //Prints name of exception
		}
		finally 
		{
			System.out.println("Finally block will execute even if an exception occurs that is not caught in the try/catch block \n");
		}
		
		
		//throwing error
				myVal(1);
				//note that throwing an error terminates the execution of the code.

	}
	public static int myVal(int n) 
	{
	if (n<2) 
		{
		throw new IllegalArgumentException ("1 is not a prime number.");
		}
	return n;
	}
		
}
