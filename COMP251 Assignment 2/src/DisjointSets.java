import java.io.*;
import java.util.*;


/****************************
*
* COMP251 template file
*
* Assignment 2, Question 1
*
*****************************/


public class DisjointSets {

    private int[] par;
    private int[] rank;
    
    /* constructor: creates a partition of n elements. */
    /* Each element is in a separate disjoint set */
    DisjointSets(int n) {
        if (n>0) {
            par = new int[n];
            rank = new int[n];
            for (int i=0; i<this.par.length; i++) {
                par[i] = i;
            }
        }
    }
    
    public String toString(){
        int pari,countsets=0;
        String output = "";
        String[] setstrings = new String[this.par.length];
        /* build string for each set */
        for (int i=0; i<this.par.length; i++) {
            pari = find(i);
            if (setstrings[pari]==null) {
                setstrings[pari] = String.valueOf(i);
                countsets+=1;
            } else {
                setstrings[pari] += "," + i;
            }
        }
        /* print strings */
        output = countsets + " set(s):\n";
        for (int i=0; i<this.par.length; i++) {
            if (setstrings[i] != null) {
                output += i + " : " + setstrings[i] + "\n";
            }
        }
        return output;
    }
    
    /* find resentative of element i */
    public int find(int i) {
    	if (par[i] == i) {
    		return i;
    	} else {
    		par[i] = find(par[i]); // path compression
    		return par[i];
    	}        
    }

    /* merge sets containing elements i and j */
    public int union(int i, int j) {
    	int pI = find(i); // get local version of roots to increase efficiency
    	int pJ = find(j);
    	
    	if (pI != pJ) { // Ensure that they are not already in the same set
    		if (rank[i] > rank[j]) { // merge j into i
    			par[pJ] = pI;
    			return pI;
    		} 
    		else if (rank[i] < rank[j]) { // merge i into j
    			par[pI] = pJ;
    			return pJ;
    		} 
    		else { // ranks are equal, merge i into j and increase rank of j
    			par[pI] = pJ;
    			rank[j]++;
    			return pJ;
    		}
    	}
    	else
    		return pI;
        
    }
    
    public static void main(String[] args) {
        
        DisjointSets myset = new DisjointSets(6);
        System.out.println(myset);
        System.out.println("-> Union 2 and 3");
        myset.union(2,3);
        System.out.println(myset);
        System.out.println("-> Union 2 and 3");
        myset.union(2,3);
        System.out.println(myset);
        System.out.println("-> Union 2 and 1");
        myset.union(2,1);
        System.out.println(myset);
        System.out.println("-> Union 4 and 5");
        myset.union(4,5);
        System.out.println(myset);
        System.out.println("-> Union 3 and 1");
        myset.union(3,1);
        System.out.println(myset);
        System.out.println("-> Union 2 and 4");
        myset.union(2,4);
        System.out.println(myset);
    }

}
