package test1;

public class HelloWorld {

	public static void main (String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Hello"); // println prints a new line
		System.out.print("hi"); // print does no print new line
		System.out.println(" <- see, no new line");
		
		int myNum1;
		int myNum2;
		myNum1 = 10;
		myNum2 = 3;
		int myIntDivision = myNum1 / myNum2; // truncates to the nearest 0
		int myRemainder = myNum1 % myNum2; // yields the remainder
		int myNum3 = Math.abs(-234);
		
		System.out.println(myNum3);
		System.out.println(myIntDivision);
		System.out.println(myRemainder); 
		System.out.println(2+3+"5"); /* When the + is used between numbers, it acts as arithmetic
		 operation. When used between strings, it will concatenate the strings */
		
		System.out.println("5"+2+3); // Note that expressions are evaluated left to right, thus, this expression becomes a string
		
		System.out.println(myNum1 + "" + myNum2 + "" + myNum3); // Concatenates the 3 numbers by using a sandwich of strings.
		
		//Loops
		
		int i = 0;
		
		while(i<5){ //Note that it is case sensitive, "While" returns error.
			i=i+1;
		}
		for(i=1;i<5;++i) {
			System.out.println("The value of i during this iteration is:"+i);
		}
		
	}
	
}
