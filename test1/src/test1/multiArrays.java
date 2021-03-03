package test1;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Random;


public class multiArrays {

	public static void main(String[] args) {
		int[] numbers = new int [3];
		System.out.println(numbers); //prints reference to the address of the array
		
		int[][] myMultArr = new int [3][]; // Create multidimensional array
		int[] arr1 = {3,2,5};
		int[] arr2 = new int[2];
		
		//populated multidimensional array
		
		myMultArr[0] = arr1;
		myMultArr[1] = arr2;
		myMultArr[2] = new int [1]; // Have to initialize this cell first as it is currently type array storing null. 
		myMultArr[2][0] = 3; // Once initialized, can now give value
		
		System.out.println(Arrays.toString(arr2));
		System.out.println(Arrays.deepToString(myMultArr));
		
		//Scanner
		
		int age;
		Scanner read = new Scanner(System.in);
		
		System.out.println("What is your age?");
		age = read.nextInt();
		System.out.println("Your age is:"+age);

		
		// call myRand()
		
		myRand();
		
	}
	/*Write two methods that display 10 random integers between 0 and
	50. One method should a Random object without seed, the other
	should take a seed as input and use a Random object with that
	seed.*/
	
	public static void myRand()
	{
		int seed = 88;
		Random randNum = new Random();
		for(int i = 0 ; i < 10 ; i++)
		{
			System.out.println(randNum.nextInt(50));
		}
	}

}
