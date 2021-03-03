package FinalProject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class MyHashTable<K,V> implements Iterable<HashPair<K,V>>{
    // num of entries to the table
    private int numEntries;
    // num of buckets 
    private int numBuckets;
    // load factor needed to check for rehashing 
    private static final double MAX_LOAD_FACTOR = 0.75;
    // ArrayList of buckets. Each bucket is a LinkedList of HashPair
    private ArrayList<LinkedList<HashPair<K,V>>> buckets; 
    
    // constructor
    public MyHashTable(int initialCapacity) {
        this.numBuckets = initialCapacity;
        this.numEntries = 0; // no entries at start
        this.buckets = new ArrayList<LinkedList<HashPair<K,V>>>(initialCapacity);
        
        // add empty linked list to each bucket
        //creates an empty Linkedlist
        for(int i = 0; i < initialCapacity; i++)
        {
        	buckets.add(new LinkedList<HashPair<K,V>>());
        }
    }
     
    public int size() {
        return this.numEntries;
    }
    
    public boolean isEmpty() {
        return this.numEntries == 0;
    }
    
    public int numBuckets() {
        return this.numBuckets;
    }
    
    /**
     * Returns the buckets variable. Useful for testing  purposes.
     */
    public ArrayList<LinkedList< HashPair<K,V> > > getBuckets(){
        return this.buckets;
    }
    
    /**
     * Given a key, return the bucket position (hashValue) for the key. 
     */
    public int hashFunction(K key) {
        int hashValue = Math.abs(key.hashCode())%this.numBuckets;
        return hashValue;
    }
    
    /**
     * Takes a key and a value as input and adds the corresponding HashPair
     * to this HashTable. Expected average run time  O(1)
     */
    public V put(K key, V value) {
    	
    	HashPair<K,V> tempPair = new HashPair<K,V>(key, value);
    	// get bucket associated with key
    	LinkedList <HashPair<K,V>> currBucket = this.buckets.get(this.hashFunction(key));
    	V tempVal;
    	// get the LinkedList in the bucket containing the key
    	for(HashPair<K,V> pair : currBucket)
    	{
    		if(pair.getKey().equals(key))
    		{
    			// return old value & set new
    			tempVal = pair.getValue();
    			pair.setValue(value);
    			return tempVal;
    		}	
    	}
    	// tempVal above not returned i.e. no value associated to key, then execute below
    	this.numEntries++; // increment to account for new entry
    	// rehash if load factor exceeds maximum load factor
    	if ((double)(numEntries)/numBuckets > MAX_LOAD_FACTOR)
    		this.rehash();
    	// add the pair to updated buckets
    	this.buckets.get(this.hashFunction(key)).add(tempPair);
   		return null;
    }
    
    
    /**
     * Get the value corresponding to key. Expected average runtime O(1)
     */
    
    public V get(K key) {
        // iterate through each HashPair of the obtained bucket (LinkedList)
        for(HashPair<K,V> pair : this.buckets.get(this.hashFunction(key)))
        {
        	if(pair.getKey().equals(key))
        		return pair.getValue();
        }
        // else if nothing was returned i.e. no matching key
    	return null;
    }
    
    /**
     * Remove the HashPair corresponding to key . Expected average runtime O(1) 
     */
    public V remove(K key) {
    	V tempValue;
    	LinkedList<HashPair<K,V>> tempBucket = this.buckets.get(this.hashFunction(key));
    	// iterate through each HashPair of matching bucket (LinkedList)
        for(HashPair<K,V> pair : tempBucket)
        {
        	if(pair.getKey().equals(key))
        	{
        		// get value of pair to be removed
        		tempValue = pair.getValue();
        		// remove pair. Note if bucket had only one pair, size -> 0 (empty list)
        		tempBucket.remove(pair);
        		// update number of entries
        		this.numEntries--;
        		// return value of removed pair
        		return tempValue;
        	}
        }
        // else if nothing was returned, i.e. key not found
    	return null;
    }
    
   
    /** 
     * Method to double the size of the hashtable if load factor increases
     * beyond MAX_LOAD_FACTOR.
     * Made public for ease of testing.
     * Expected average runtime is O(m), where m is the number of buckets
     */
    public void rehash() {
    	int newHashValue;
    	// re-size number of buckets
    	this.numBuckets = 2*this.numBuckets;
    	// create a new table of twice the size
    	ArrayList<LinkedList<HashPair<K,V>>> newBuckets = new ArrayList<LinkedList<HashPair<K,V>>>(numBuckets);
    	
    	// initialize new table with empty buckets in order to change/add buckets at the required index later on
        for(int i = 0; i < this.numBuckets; i++)
        {
        	newBuckets.add(new LinkedList<HashPair<K,V>>());
        }
    		// iterate through each pair of table
    		for(HashPair<K,V> pair : this)
    		{
    			// get a new hashValue (index of new larger table)
    			newHashValue = this.hashFunction(pair.getKey());
    			// this gets the bucket in new table, and appends the HashPair to it
    			newBuckets.get(newHashValue).add(pair);
    		}
        this.buckets = newBuckets;
    }
    
    
    /**
     * Return a list of all the keys present in this hashtable.
     * Expected average runtime is O(m), where m is the number of buckets
     */
    public ArrayList<K> keys() {
        ArrayList<K> keysList = new ArrayList<K>(this.numEntries);
        
        for(HashPair<K,V> pair : this)
        		{
        			keysList.add(pair.getKey());
        		}
        
    	return keysList;
    }
    
    /**
     * Returns an ArrayList of unique values present in this hashtable.
     * Expected average runtime is O(m) where m is the number of buckets
     */
    public ArrayList<V> values() {
        ArrayList<V> valuesList = new ArrayList<V>(this.numBuckets);        		
        MyHashTable<V,K> inverseKeys = new MyHashTable<>(this.numBuckets);
        // populate inversed table
        for(HashPair<K,V> pair : this)
        	inverseKeys.put(pair.getValue(), pair.getKey());
        
        	// if the values array does not already have this value, add it in
        	for(HashPair<K,V> uniquePair : this)
        	{
        		if (inverseKeys.get(uniquePair.getValue()).equals(uniquePair.getKey()))
        				valuesList.add(uniquePair.getValue());
        	}
        return valuesList;
    }
    
    
	/**
	 * This method takes as input an object of type MyHashTable with values that 
	 * are Comparable. It returns an ArrayList containing all the keys from the map, 
	 * ordered in descending order based on the values they mapped to. 
	 * 
	 * The time complexity for this method is O(n^2), where n is the number 
	 * of pairs in the map. 
	 */
    public static <K, V extends Comparable<V>> ArrayList<K> slowSort (MyHashTable<K, V> results) {
        ArrayList<K> sortedResults = new ArrayList<>();
        for (HashPair<K, V> entry : results) {
			V element = entry.getValue();
			K toAdd = entry.getKey();
			int i = sortedResults.size() - 1;
			V toCompare = null;
        	while (i >= 0) {
        		toCompare = results.get(sortedResults.get(i));
        		if (element.compareTo(toCompare) <= 0 )
        			break;
        		i--;
        	}
        	sortedResults.add(i+1, toAdd);
        }
        return sortedResults;
    }
    
    
	/**
	 * This method takes as input an object of type MyHashTable with values that 
	 * are Comparable. It returns an ArrayList containing all the keys from the map, 
	 * ordered in descending order based on the values they mapped to.
	 * 
	 * The time complexity for this method is O(n*log(n)), where n is the number 
	 * of pairs in the map. 
	 */
    
    public static <K, V extends Comparable<V>> ArrayList<K> fastSort(MyHashTable<K, V> results) {
    	ArrayList<K> keyList = new ArrayList<>(results.numEntries);
    	// first store pairs in an array
    	ArrayList<HashPair<K,V>> pairsList = new ArrayList<>(results.numEntries);
    	for(HashPair<K,V> pair : results)
    	{
    		pairsList.add(pair);
    	}  	
    	// mergeSort
    	ArrayList<HashPair<K,V>> sortedList = mergeSort(pairsList);
    	// get keys
    	for(HashPair<K,V> pair : sortedList)
    	{
    		keyList.add(pair.getKey());
    	}
    	return keyList;
    }

    private static <K,V extends Comparable<V>> ArrayList<HashPair<K,V>> mergeSort(ArrayList<HashPair<K,V>> pairsList)
    {
    	int mid;
    	ArrayList<HashPair<K,V>> pairsList1 = new ArrayList<>(pairsList.size());
    	ArrayList<HashPair<K,V>> pairsList2 = new ArrayList<>(pairsList.size());
    	ArrayList<HashPair<K,V>> sortedPairs = new ArrayList<>(pairsList.size());
    	
    	if (pairsList.size() == 1) {
    		//System.out.println("I'm here");
    		return pairsList;
    	}
    	else
    	{
    		mid = (pairsList.size()/2);
    		// separate lists
    		for(int i = 0; i < mid; i++)
    		{
    			pairsList1.add(pairsList.get(i));
    		}
    		for(int i = mid; i < pairsList.size(); i++)
    		{
    			pairsList2.add(pairsList.get(i));
    		}
    		// recursive step
    		//System.out.println("size of pairsList input: "+pairsList.size()+" size of list1: " +pairsList1.size() + " size of list2: "+pairsList2.size());
    		// note that at the last 3 elements, list2 is size 2, list1 is size 1 (due to how for loop is set up), thus do the recursion on list 2 first.
    		pairsList2 = mergeSort(pairsList2);
    		pairsList1 = mergeSort(pairsList1);
    		// merge
    		// pick elements to add until either list is empty
    		
    		// NOTE: Avoid using remove(0) since it takes O(n) for remove first, but O(1) for get(int)
    		int size1 = pairsList1.size();
    		int size2 = pairsList2.size();
    		int ite1 = 0;
    		int ite2 = 0;
    		while(ite1 < size1 && ite2 < size2)
    		{
    			if(pairsList1.get(ite1).getValue().compareTo(pairsList2.get(ite2).getValue()) >= 0)
    				sortedPairs.add(pairsList1.get(ite1++));
    			else
    				sortedPairs.add(pairsList2.get(ite2++));
    		}

    		// add remaining pairs
    		while(ite1 < size1)
    			sortedPairs.add(pairsList1.get(ite1++));
    		while(ite2 < size2)
    			sortedPairs.add(pairsList2.get(ite2++));
   
    		return sortedPairs;
    	}
    }
    
    
    
    @Override
    public MyHashIterator iterator() {
        return new MyHashIterator();
    }   
    
    private class MyHashIterator implements Iterator<HashPair<K,V>> {
    	private ArrayList<HashPair<K,V>> pairEntries;
    	private int currentIndex;
    	
    	private ArrayList<HashPair<K,V>> storeHashPairs(ArrayList<LinkedList<HashPair<K,V>>> hashTable)
    	{
    		for(LinkedList<HashPair<K,V>> bucketList : hashTable)
    		{
    			for(HashPair<K,V> pair : bucketList)
    			{
    				// store every entry one by one in the array. takes O(m) if load factor is respected
    				this.pairEntries.add(pair);
    			}
    		}
    		return this.pairEntries;
    	}
    	
    	/**
    	 * Expected average runtime is O(m) where m is the number of buckets
    	 */
        private MyHashIterator() {
            // Create an array to store all entries in the table
        	this.pairEntries = new ArrayList<HashPair<K,V>>(numEntries);
        	this.pairEntries = storeHashPairs(buckets);
        	this.currentIndex = 0;
        }
        
        @Override
        /**
         * Expected average runtime is O(1)
         */
        public boolean hasNext() {
        	return this.currentIndex < this.pairEntries.size();
        }
        
        @Override
        /**
         * Expected average runtime is O(1)
         */
        public HashPair<K,V> next() {
        	return this.pairEntries.get(currentIndex++);
        }
        
    }
    
   public static void main(String[] args)
    {
    	MyHashTable<Integer, String> tester = new MyHashTable<Integer, String>(8);

    	int temp1 = 3;
    	String temp2 = "Katty";
    	String temp3 = "Jacob";
    	int temp4 = 5;
    	String temp5 = "Manny";
    	int temp6 = 6;
    	String temp7 = "David";
    	int temp8 = 4;
    	String temp9 = "James";
    	int temp10 = 7;
    	String temp11 = "Kelly";
    	int temp12 = 2;
    	String temp13 = "David";
    	
    	

 
    	tester.put(temp1, temp2); 
    	tester.put(temp1, temp3);
    	tester.put(temp4, temp5);
    	tester.put(temp6, temp7);
    	tester.put(temp8, temp9);
    	tester.put(temp10, temp11);
    	tester.put(temp12, temp13);
    	//tester.remove(temp8);
    	
    	ArrayList<LinkedList<HashPair<Integer, String>>> myHashTable = tester.getBuckets();
    	
    	for(LinkedList<HashPair<Integer, String>> myBucket : myHashTable)
    	{
    		for(HashPair<Integer, String> myPair : myBucket)
    		{
    			String a = myPair.getValue();
    			int b = myPair.getKey();
    			System.out.println("key: " +b + ", value: "+a);
    		}
    	}
    	System.out.println("The unsorted keys are: "+tester.keys());
    	ArrayList<Integer> sortedKeysSlow = slowSort(tester);
    	ArrayList<Integer> sortedKeysFast = fastSort(tester);
    	System.out.println("Using slowSort: "+sortedKeysSlow);
    	System.out.println("Using fastSort: "+sortedKeysFast);
    	System.out.println("number of buckets: "+tester.numBuckets);
    	System.out.println("number of entries: "+tester.numEntries);
    	System.out.println();

    	System.out.println((double)(tester.numEntries)/tester.numBuckets);
    }
}
