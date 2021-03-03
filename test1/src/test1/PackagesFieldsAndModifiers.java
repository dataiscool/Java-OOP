package test1;

public class PackagesFieldsAndModifiers {

	public static void main(String[] args) {
		
		//if you don't import the java.util package beforehand, you have to specify the entire path everytime:
		
		String whatEver;
		
		java.util.Scanner read = new java.util.Scanner(System.in); //create an object "read"
		System.out.println("Type whatever: ");
		whatEver = read.nextLine(); /*how come don't need to specify path? Scanner is a class in java.util,
		and .nextLine() is a method in Scanner class*/
		System.out.println("The value of whatEver is: "+whatEver);

	}

}
