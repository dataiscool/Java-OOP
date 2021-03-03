package test1;

public class PrimDataTypes {

	public static void main(String[] args) {
		// Test output difference between double and int
		
		int myIntDiv = 4/5;
		double myDoubDiv = 4.00/5.00;
		double myDoubDiv2 = 4/5;
		// Escape sequences: 
		char myLetter = 'a'; // Character literals are in SINGLE quotes, and are composed of SINGLE letters.
		char newLine = '\n'; // this prints as new line
		char singleQuote = '\''; // this prints as single quotation mark
		char myTab = '\t'; // this prints as tab
				
		System.out.println("This rounds down the answer to the nearest integer: " + myIntDiv);
		System.out.println("This keeps the floating point: " +myDoubDiv);
		System.out.println("This does not compute as floating point despite being type double, because all operands are integers. Wrong answer: " + myDoubDiv2); 
		System.out.println(myTab + "" + myLetter + "" + newLine + "" + singleQuote);
		
		// Characters and typecasting
		
		char myLetter2 = (char)(myLetter + 1); 
		/* Java converts myLetter to integer type and performs the arith. operation. 
		 * It is then typecasted into char before being assigned to myLetter2
		 */
		System.out.println("My second letter is: " + myLetter2);
		
		// Test value method charRightShift
		
		System.out.println("Send 'b' and n = 2 to charRightShift and get back: "+ charRightShift('b',10));
		
		// Strings
		
		String myStr = "Hello";
		String myStr1 = "hello";
		String myStr2 = "uWu";
		
		System.out.println(myStr.equals(myStr1)); // This compares myStr to myStr1 case-sensitive
		System.out.println(myStr2.length()); //.length() does not have any inputs, it simply returns the number of chars in the string.
		System.out.println(myStr2.charAt(2) == 'W'); //.charAt returns the char at given index
		
		// Test value method passMatch
		System.out.println(passMatch("password"));
		
		// Test value method vowelMatch
		System.out.println(vowelMatch("McDonalds",3));
	}
	
/* This method should take in a character and an integer and return the character located n-integers down. If char is not 
	a lowercase letter, return as is.*/
	
	public static char charRightShift(char myLetter, int n) {
		char myShiftedChar;
		if (myLetter >= 'a' && myLetter <= 'z') {
			myShiftedChar = (char)(myLetter + n);
		} else {
			myShiftedChar = myLetter;
		}
		return myShiftedChar; 
	}
	/*Write a method that takes a String as input and prints true if the
String received is equal to a password (you, the programmer, can
choose the password). The method should print false otherwise.*/
		
		public static boolean passMatch(String attPwd) {
			String myPwd = "password";
			if (attPwd == myPwd) {
				return true;			
			} else {
				return false;
			}
		}
		/*Write a method that takes a String s and an int i as input. The
		method should return true if the character at index i is a vowel,
		false otherwise.*/
		
		public static boolean vowelMatch(String s, int i) {
			if (s.charAt(i) == 'a' || s.charAt(i) =='e' || s.charAt(i) =='i' ||s.charAt(i) == 'o' || s.charAt(i) =='u' ||s.charAt(i) == 'y') {
				return true;
			}else {
				return false;
			}
}
}





