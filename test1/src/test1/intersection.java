package test1;

import java.util.Arrays;

public class intersection {

	public static void main(String[] args) 
{
		// take two integer arrays as input and output an array of the intersection (see lecture on prim. data types.
		// Assume input arrays don't have duplicates
int[] x = {1,2,3,4,5,6};
int[] y = {2,9,4,5,10};
		System.out.println(Arrays.toString(getIntersectionOne(x, y))); //or can use a loop to print each element
}	
	
	public static int[] getIntersectionOne(int[] a, int[] b)
	{
		
	
	// To determine efficiency, assume a and b same size --> n
	//figure out size of the output array
		
	int size = 0;
	for(int i=0; i<a.length; i++) {
		for(int j=0; j<b.length; j++) {
			if(a[i] == b[j]) { //n^2
			
				size++; // count number of intersecting values, because no duplicate, we can break once found intersect. 
				break; //best case with break: 1+2+3+...+n = n(n+1)/2. they both grow with n^2, for large input, no difference
			// if the array is sorted, we can use binary search to accelerate the process (n * log n)
			}
		}
	}
	
	//create array
	int[] c = new int [size];
	
	//populate the array
	int index = 0;
	for(int i = 0; i<a.length; i++)
{
		for(int j=0; j<b.length; j++) 
		{
			if(a[i] == b[j]) 
			{
				c[index] = a[i];
				index++;
				break;
			}
		}
}

		
		return c; 			
	}

}
