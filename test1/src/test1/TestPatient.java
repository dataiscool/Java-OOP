package test1;

public class TestPatient {

	public static void main(String[] args) {
		
		double[] temps = {23,32,35,40,21};
		Patient a = new Patient(); // This only calls upon the constructor with no inputs
		Patient b = new Patient("Feras",23,temps); //this calls upon the constructor with the 3 inputs
		
		/* By using the non-static Patient class, 
		 * we can create many instances of Patient (aka objects). We can have a patient Feras, a patient John, etc.
		 * the Patient class effectively becomes the blueprint for creating objects. */
		
		a.name = "John"; // As observed, because name is public, it can be changed from this class.
		
		//b.temps[0] = 1; //illegal, temps field is private
		
		System.out.println(a.name);
		
		/* Because these fields are public, we can change them from this class. Regard this as an input file, 
		 * while Patient class is the database. */
		System.out.println("Notice that by using the toString() method, we can call println on an instance: "+b);
	}

}
