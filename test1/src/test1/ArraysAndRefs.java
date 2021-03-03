package test1;

public class ArraysAndRefs {

	public static void main(String[] args) {
		
		double[]myArr = {1.0,2.0,3.0}; // If we know the values before the program runs, can directly populate.
		System.out.println("The third value in the array is: "+ myArr[2]);
	
	// If the values are unknown, use the 'new' operator:
	
		String [] days = new String [7]; // creates an array of 7 elements.
		
		// or write this way:
		int num_days = 7;
		String[] week;
		week = new String[num_days];
		
		System.out.println("No values are assigned to the Str type array 'week', thus default value is: "+week[0]);
		
		System.out.println("Note that modulus can yield negative numbers: "+ -3%5);
		
		// test adjDiff method
		
		int [] adjArr = {1,2,3,4};
		adjDiff(adjArr);
		
		// NullPointerException
		// Cannot access information through a variable with value null.
		int [] blank = null;
		// The below 2 yield null pointer exception
		//System.out.println(blank.length);
		//System.out.println(blank[0]);
		
		// Test returnLargeVal
		int [] myArr1 = {5,3,2,5,10,22};
		System.out.println("Largest value of the array is: "+ returnLargeVal(myArr1));
		
		// Test returnPrimes method
		
		int n = 10;
		int[] returnMyArr = returnPrimes(n); 
		for (int i = 0 ; i < n ; i++)
		{
			System.out.println(returnMyArr[i]);
		}
	}

	public static void adjDiff(int[] a) {
		for(int i=0; i < a.length-1; i++) {
			System.out.println(a[i+1]-a[i]);
		}
	}
	/*Write a method that takes an array of integers as input and returns the
	largest value in the array.*/
	
	public static int returnLargeVal(int[] a) {
		int maxVal = a[0];
		for(int i=0; i<a.length-1; i++) {
			if(a[i+1]>a[i]) {
				maxVal = a[i+1];
			}
		}
		return maxVal;
		
		/*Write a method that takes a positive integer n as input and returns an array
		containing the first n primes.*/
		
	}
	
	/*Write a method that takes a positive integer n as input and returns an array
	containing the first n primes.*/
	
	public static int[] returnPrimes(int n) 
	{

		int [] myPrime = new int[n]; // Declare the array myPrime
		myPrime[0] = 2; // Initialize the first prime number.
		
		if (n == 1) // If n = 1, then there's only the first prime number to show for.
		{
			return myPrime;
		}
		else // for n > 1, run iterations below to populate myPrime array with the first n prime numbers.
		{
			
			int num = 3; // Second prime number
			int count = 1;
			
			while (count < n) // counts the number of prime numbers
			{

				int divCount = 0; 
				
					for ( int i = 2 ; i < num ; i++) // Divides each number 'num' by a loop of integers excluding 1 and itself
					{
						if (num % i == 0) // If 'num' is divisible by an integer other than 1 and itself, then not a prime number
						{
						divCount++; // Counts number of divisors of 'num' excluding 1 and the number itself
						break;
						}
					}
				
					if (divCount == 0) // if no other divisors, then 'num' is a prime number 
					{
						myPrime[count] = num; 
						System.out.print((count == n-1) + " "); //check if statement below - debug
						if (count == n-1) // note that it isnt good form to write both returns in if statements. The second return should be in the else block for an if/else.					
						{
							System.out.println("\n"+"The 2nd return is executed.");
							return myPrime; 						}
						count++; // count current iteration if 'num' is prime number
					}
					num++; // check next number
			}
		}
		System.out.println("The 3rd return is executed."); /* Notice that this str is not printed. The 2nd return is executed, 
		but the compiler will not run without the return below, 
		because having two returns in two if statements is unsafe and may return nothing.*/
		return myPrime;
	}
}

