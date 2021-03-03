import java.io.*;
import java.util.*;

public class main {

	public static void main(String[] args) {
	//TODO:build the hash table and insert keys using the insertKeyArray function.
		
		int z;
		int x=0;
		z = x++;
		System.out.println(z);
		
		// Using Open_Addressing
		Open_Addressing openTable = new Open_Addressing(10, 6, -1);
		// Creates a table with 32 slots, seed = 8, A = -1 so that it is auto generated.
		System.out.println("Table size m: "+Open_Addressing.power2(5));
		System.out.println("Number of collisions entering key = 8: "+openTable.insertKey(8));
		System.out.println("Number of collisions entering key = 3: "+openTable.insertKey(3));
		System.out.println("Number of collisions entering key = 4: "+openTable.insertKey(4));
		System.out.println("Number of collisions entering key = 5: "+openTable.insertKey(5));
		System.out.println("Number of collisions entering key = 2: "+openTable.insertKey(2));
		System.out.println("Number of collisions entering key = 1: "+openTable.insertKey(1));
		System.out.println("Number of collisions entering key = 6: "+openTable.insertKey(6));
		System.out.println("Number of collisions entering key = 7: "+openTable.insertKey(7));
		System.out.println("Number of collisions entering key = 9: "+openTable.insertKey(9));
		
		System.out.println("Number of slots visited when removing 7: "+openTable.removeKey(7));
		System.out.println("Number of slots visited when attempting to remove 7 again: "+openTable.removeKey(7));
		System.out.println("Re-entering key 7 in table; # of collisions: "+openTable.insertKey(7)+"\n");
		
		// Using Chaining
		Chaining chainTable = new Chaining(10, 6, -1);
		// Creates a table with 8 slots, seed = 8, A = -1 so that it is auto generated.
		System.out.println("Number of collisions entering key = 8: "+chainTable.insertKey(8));
		System.out.println("Number of collisions entering key = 3: "+chainTable.insertKey(3));
		System.out.println("Number of collisions entering key = 4: "+chainTable.insertKey(4));
		System.out.println("Number of collisions entering key = 5: "+chainTable.insertKey(5));
		System.out.println("Number of collisions entering key = 2: "+chainTable.insertKey(2));
		System.out.println("Number of collisions entering key = 1: "+chainTable.insertKey(1));
		System.out.println("Number of collisions entering key = 6: "+chainTable.insertKey(6));
		System.out.println("Number of collisions entering key = 7: "+chainTable.insertKey(7));
		System.out.println("Number of collisions entering key = 9: "+chainTable.insertKey(9));
		
		// Compare both
		int[] keyArray = new int[8];
		for (int i = 0; i<8; i++) {
			keyArray[i] = i+1;
		}
		System.out.println("\nThe keyArray is: "+Arrays.toString(keyArray));
		Open_Addressing openTable1 = new Open_Addressing(10, 6, -1);
		System.out.println("The number of collisions for open addressing is: "+openTable1.insertKeyArray(keyArray));
		Chaining chainTable1 = new Chaining(10, 6, -1);
		System.out.println("The number of collisions for chaining is: "+chainTable1.insertKeyArray(keyArray));
		
	}

}
