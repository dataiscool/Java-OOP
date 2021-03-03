package test1;

import java.util.Arrays;

public class Patient 
{ //note non-static, access as an instance of the class (object)
	
	// attributes (fields)
	public String name; // don't want this to be static because we have different names
	private int age;
	private double[] temps; 
	
	private boolean hasFever;
	
	private static final double tempFever = 37.5; 
	// Want this to be static, because this is shared by all objects (Regardless of the patient, the fever limit is 37.5)
	// final means can not change its value. static means can be accessed via the class for all objects, non-static means it can be accessed separately for every object
	
	
	// constructors
	
	//use public, because we want to create patients from outside the class
	public Patient() 
		{
		System.out.println("Creating a patient");	
		}
	
	public Patient(String name, int a, double[] t)
	{
		this.name = name; 
		this.age = a;
		this.temps = t; //passes references. Changing the object temps will change the elements in the original array
		System.out.println(name+" "+age+" "+Arrays.toString(temps));
	}
	
	// creates a double, elements are passed & stored in different locations. Original array intact.
	public Patient(double[] t)
	 {
		 this.temps = new double[t.length];
		 for (int i = 0; i < t.length; i++)
		 {
			 this.temps[i] = t[i];
		 }
	 }
	
	 public String toString() //this replaces the original toString method when this class is called on.
	 {
		 return this.name;
	 }
	
	
	// Test the above constructors using the main method below
	
	public static void main(String[] args)
	{
		double[] temps = {38,39.7, 38.5, 37.4, 36.4};
		Patient q = new Patient(); // declares and initializes instance q with Patient() constructor. Runs constructor.
		Patient p = new Patient("John", 35, temps);
		Patient a = new Patient(temps);
		
		a.temps[0] = 1; //object a creates a double of temps, thus the original temps is not altered.
		System.out.println("\n"+Arrays.toString(a.temps)); // instance variable temps 
		System.out.println(Arrays.toString(temps)); // local variable temps from main method
		System.out.println("By creating a copy, the original local array is not modified by changing the object's array\n");
		
		p.temps[0] = 1;
		System.out.println(p.name);
		System.out.println(Arrays.toString(p.temps)); 
		System.out.println(Arrays.toString(temps));
		System.out.println("By passing reference, the original local array's elements can be changed by the object as observed above");
	}
}
