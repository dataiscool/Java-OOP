// Code by John Pan 260739619
// Assignment 3 - COMP250

package A3;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException; 


public class CatTree implements Iterable<CatInfo>{
    public CatNode root; // Declare the root of the tree of type CatNode (made public access)
    
    // constructor to initialize the root
    public CatTree(CatInfo c) {
        this.root = new CatNode(c); //creates new object of type CatNode using its constructor. NB it's an inner class
    }
    
    private CatTree(CatNode c) {
        this.root = c;
    }
    
    
    public void addCat(CatInfo c)
    {
        this.root = root.addCat(new CatNode(c)); //adds to the tree a new object of type CatNode. use new keyword because its a class (inner)
    }
    
    public void removeCat(CatInfo c)
    {
        this.root = root.removeCat(c);
    }
    
    public int mostSenior()
    {
        return root.mostSenior();
    }
    
    public int fluffiest() {
        return root.fluffiest();
    }
    
    public CatInfo fluffiestFromMonth(int month) {
        return root.fluffiestFromMonth(month);
    }
    
    public int hiredFromMonths(int monthMin, int monthMax) {
        return root.hiredFromMonths(monthMin, monthMax);
    }
    
    public int[] costPlanning(int nbMonths) {
        return root.costPlanning(nbMonths);
    }
    
    
    // implement iterator abstract method of iterable interface
    // return a cat tree iterator object. Call on an instance of CatTree
    public Iterator<CatInfo> iterator()
    {
        return new CatTreeIterator();
    }
    
    
    class CatNode { //nested class
        
        CatInfo data;
        CatNode senior;
        CatNode same;
        CatNode junior;
        
        public CatNode(CatInfo data) {
            this.data = data;
            this.senior = null;
            this.same = null;
            this.junior = null;
        }
        
        public String toString() {
            String result = this.data.toString() + "\n";
            if (this.senior != null) {
                result += "more senior " + this.data.toString() + " :\n";
                result += this.senior.toString();
            }
            if (this.same != null) {
                result += "same seniority " + this.data.toString() + " :\n";
                result += this.same.toString();
            }
            if (this.junior != null) {
                result += "more junior " + this.data.toString() + " :\n";
                result += this.junior.toString();
            }
            return result;
        }
        
        
        public CatNode addCat(CatNode c) {
        	CatNode tempRoot = new CatNode(null);
        	
            if(c.data.monthHired < this.data.monthHired)
            {
            	if(this.senior == null)
            		this.senior = c;
            	else
            	{
            		this.senior.addCat(c); /*recursion, compare to next cat in parent senior line. 
            		NB that at next recursion, the catToAdd will either be senior, junior or same to that cat
            		* and be placed where it belongs
            		* unless there is already a cat at the next line, in which case another recursion will run
            		*/
            	}
            }
            else if(c.data.monthHired > this.data.monthHired)
            {
            	if(this.junior == null)
            		this.junior = c;
            	else
            		this.junior.addCat(c); //recursion, compare to next cat in parent junior line
            }
            else // c.data.month == this.data.month
            {
            	if(c.data.furThickness > this.data.furThickness)
            	{
            		tempRoot.data = this.data; // create temp var to store current root data
            		this.data = c.data; // change content of current (root position i.e. senior, junior, same. not changed)
            		this.addCat(tempRoot); /* We already know it's same as the added cat, but because the current same line
            		* might be empty, using this.same.addCat might give nullPointerException. Instead just send the previous root 
            		* through addCat again and the below code will take care of putting it in the same line if it is currently empty.
            		*/
            	}
            	else 
            	{
            	if (this.same == null)
            		this.same = c;
            	else
            		this.same.addCat(c); //recursion, compare to next cat in parent same line
            	}
           	}
            return this; // value returned during recursions not used.        }
        }
        
        public CatNode removeCat(CatInfo c) {

        	if(this.data.equals(c))
        		{
        		//case 1
        			if(this.same != null)
        			{		
        				this.data = this.same.data; // put the current same cat data into the removed previous cat root node
        				
        				//add senior and junior of the previous same node
        				if(this.senior != null && this.same.senior != null) // check for null pointer exception
        					this.senior.addCat(this.same.senior); 
        				else if(this.senior == null && this.same.senior != null)
        					this.senior = this.same.senior; // if no current senior branch, copy over the senior branch of new root
        				// else if the new root to be has no senior branch, do nothing
        				
        				if(this.junior != null && this.same.junior != null)
        					this.junior.addCat(this.same.junior);
        				else if(this.junior == null && this.same.junior != null)
        					this.junior = this.same.junior;
        				
        				this.same = this.same.same; // update the same lineage of the new root
        			}
        			
        			//case 2
        			else if(this.same == null && this.senior != null)
        			{
        				CatNode tempJunior = new CatNode(null);
        				tempJunior = this.junior; //could be null
        				
        				this.data = this.senior.data; //switch over the data of new root
        				
        				// attach all previous branches of the senior line that became the new root
        				this.same = this.senior.same;
        				this.junior = this.senior.junior;
        				this.senior = this.senior.senior; // update senior line last, otherwise we lose the nodes of same and junior
        				
        				//add junior
        				if(this.junior != null && tempJunior != null)
        					this.junior.addCat(tempJunior); // use addCat algorithm if there is already a junior branch
        				else if(this.junior == null && tempJunior != null)
        					this.junior = tempJunior; // copy over directly if new root (senior) has no junior branch
        				// if junior was null, nothing to add
        				
        				// note same line of original root is null
        			}
        			
        			//case 3
        			else if(this.same == null && this.senior == null && this.junior != null)
        			{
        				this.data = this.junior.data;
        				this.same = this.junior.same;
        				this.senior = this.junior.senior;
        				this.junior = this.junior.junior;
        			}
        			
        			//case where node to remove is a leaf
        			else if(this.same == null && this.senior == null && this.junior == null)
        			{
        				return null; // if no nodes, then it is a leaf, return null that will be caught by isLeaf.
        			}
        		}
        		else
        		{
        			CatNode isLeaf = new CatNode(null);
        			
        			if(this.senior != null)
        			{
        				isLeaf = this.senior.removeCat(c); // recursion to traverse tree, catch return value
        				if(isLeaf == null) // if returned null, means deleted item was a leaf. set its node to null
        					this.senior = null;
        			}
        			if(this.same != null)
        			{
        				isLeaf = this.same.removeCat(c);
        				if(isLeaf == null)
        					this.same = null;
        			}
        			if(this.junior != null)
        			{
        				isLeaf = this.junior.removeCat(c);
        				if(isLeaf == null)
        					this.junior = null;
        			}
        		}
        	
        	return this; // DON'T FORGET TO MODIFY THE RETURN IF NEED BE
        }
        
        
        public int mostSenior() {
        	//recursion in the senior branch to find most senior
        	
            if(this.senior != null)
            	return this.senior.mostSenior();
            else
            	return this.data.monthHired;
        }
        
        public int fluffiest() {
        	
        	int bigFluff = this.data.furThickness; 
        	// bigFluff tracks fluffiest value at each iteration and stores that value.
        	
        	// traverse the tree to find fluffiest cat
        	if(this.senior != null)
        	{
        		if(bigFluff < this.senior.fluffiest()) // compares fluffiness of every cat in the line
        			bigFluff = this.senior.data.furThickness; // update bigFluff every time cat is fluffier
        	}
        	
        	if(this.same != null)
        	{
        		if(bigFluff < this.same.fluffiest())
        			bigFluff = this.same.data.furThickness;
        	}
        	
        	if(this.junior != null)
        	{
        		if(bigFluff < this.junior.fluffiest())
        			bigFluff = this.junior.data.furThickness;
        	}
        		return bigFluff;
        }
        
        
        public int hiredFromMonths(int monthMin, int monthMax) {
        	int count = 0;
        	
            if(monthMin > monthMax)
            	return 0;
            
            // use recursion to traverse tree.
            
            // case where node is within range
            else if(this.data.monthHired > monthMin && this.data.monthHired < monthMax || this.data.monthHired == monthMin || this.data.monthHired == monthMax)
            {
            	count = 1;
            	if(this.senior != null)
            	{
            			count = count + this.senior.hiredFromMonths(monthMin, monthMax);
            	}
            	
            	if(this.same != null)
            	{
            			count = count + this.same.hiredFromMonths(monthMin, monthMax);
            	}
            	
            	if(this.junior != null)
            	{
            			count = count + this.junior.hiredFromMonths(monthMin, monthMax);
            	}
            }
            
            // case where node not within range
            else
            {
            	count = 0;
            	if(this.senior != null)
            	{
            			count = count + this.senior.hiredFromMonths(monthMin, monthMax);
            	}
            	
            	if(this.same != null)
            	{
            			count = count + this.same.hiredFromMonths(monthMin, monthMax);
            	}
            	
            	if(this.junior != null)
            	{
            			count = count + this.junior.hiredFromMonths(monthMin, monthMax);
            	}
            	
            }
            return count; 
            
        }
        
        public CatInfo fluffiestFromMonth(int month) {
        	
            if(this.data.monthHired == month) // if the months are the same, then this is it chief (the first one in the same line is always the fluffiest)
            	return this.data;
            
            else
            {
            	if(month < this.data.monthHired) // cat is older
            	{
            		if(this.senior != null)
            			return this.senior.fluffiestFromMonth(month);	
            		else
            			return null; // if there are no other cats in the senior line, then no such cat exists in the tree
            	}
          
            	else // cat is younger
            	{
            		if(this.junior != null)
            			return this.junior.fluffiestFromMonth(month);	
            		else
            			return null;
            	}
            }
        }
        
        public int[] costPlanning(int nbMonths) {
        	CatTree catTree = new CatTree(this); // use private CatTree overloaded method to create a new tree using 'this' root
        	int[] costPlan = new int[nbMonths];
        	int cost = 0;
        	//CatTreeIterator it = new CatTreeIterator();
        	//CatInfo cat1 = it.next();
        	//System.out.println("Cat1 is" + cat1.toString());
        	
        	for(int i = 243; i < 243+nbMonths; i++)
        	{
        		// for each cat in the tree
        		for(CatInfo cat : catTree) // iterator will iterate over each cat data of the cat tree. (Next() method of iterator returns a CatTree).
        		{
        			//System.out.println(cat.toString());
        			if(cat.nextGroomingAppointment == i)
        				cost = cost + cat.expectedGroomingCost;
        		}
        		costPlan[i-243] = cost;
        		cost = 0;
        	}
            return costPlan;
        }
        
    }
    
    // note this cannot be called on an object of CatTree (outer class)
    private class CatTreeIterator implements Iterator<CatInfo> {
    	// First use ArrayList to store all cats in the tree
    	private ArrayList<CatInfo> cats = new ArrayList<CatInfo>();
    	private int currentIndex;
    	
    	private ArrayList<CatInfo> storeCatNodeValues(CatNode root)
    	{
    		treeTravel(root);
    		return this.cats; // returns ArrayList of CatInfo
    	}
    	
    	private void treeTravel(CatNode node)
    	{
    		if(node != null)
    		{
    			// use in order traversal to get sr to jr directly
    			treeTravel(node.senior);
    			treeTravel(node.same); // if same, thinner fur should be accessed first
    			this.cats.add(node.data); // add to the ArrayList cats
    			treeTravel(node.junior);
    		}
    	}
    	
        
        public CatTreeIterator() {
        	//initialize the cats arraylist
        	// call the tree to arraylist conversion. Note that inner class can access any field of the outer class. Here we access "root"
        	this.cats = this.storeCatNodeValues(root); // note we are in inner class iterator, this refers to instance of iterator, thus this.root is wrong, there is no field root in iterator class
        	this.currentIndex = 0;
        }
        
        public CatInfo next(){
            return this.cats.get(currentIndex++); 
            /* post-incrementation. currentIndex increments after the statement is executed.
             * this serves to return current cat, and then increments to next cat. */
        }
        
        public boolean hasNext() {
        	return this.currentIndex < cats.size(); 
        	/* if currentIndex that was previously incremented by next is smaller than size,
        	 * then currentIndex is the index of next element. */
        }
    }
    


public static void main(String[] args) 
{
	System.out.println("ok");/*	CatInfo a = new CatInfo("A", 87, 50, 243, 40);
	CatInfo b = new CatInfo("B", 85, 100, 246, 30);
	CatInfo c = new CatInfo("C", 86, 70, 248, 10);
	CatInfo e = new CatInfo("E", 86, 85, 245, 20);
	CatInfo f = new CatInfo("F", 88, 120, 247, 20); // not registering
	CatInfo newRoot = new CatInfo("N",87,70,243,40);
	
	CatTree t = new CatTree(a);
	t.addCat(b);
	t.addCat(c);
	t.addCat(e);
	t.addCat(f);
	t.addCat(newRoot);
	//t.removeCat(c);
	//System.out.println(t.root.toString());
	//int n = t.hiredFromMonths(87, 88);
	int[] plan = t.costPlanning(5);
	System.out.println(java.util.Arrays.toString(plan));
*/
}

}
