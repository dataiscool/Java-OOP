package FinalProject;

import java.util.ArrayList;

public class Twitter {
	private MyHashTable<String, ArrayList<Tweet>> tweetsDateIndex;
	private MyHashTable<String, Tweet> tweetsAuthorIndex;
	private MyHashTable<String, Integer> trendingTopics;
	private ArrayList<String> stopWords;
	
	// O(n+m) where n is the number of tweets, and m the number of stopWords
	public Twitter(ArrayList<Tweet> tweets, ArrayList<String> stopWords) {
		this.tweetsDateIndex = new MyHashTable<>(tweets.size());
		this.tweetsAuthorIndex = new MyHashTable<>(tweets.size());
		this.trendingTopics = new MyHashTable<>(tweets.size());
		this.stopWords = stopWords;
		
		// add Tweet to twitter
		for(Tweet tweet : tweets)
		{
			this.addTweet(tweet);
		}
	}
	
	
    /**
     * Add Tweet t to this Twitter
     * O(1)
     */
	public void addTweet(Tweet t) {
		// trendingTopics() *****************************************************
		// use Integer object, because .get() might return null.
		Integer count;
		ArrayList<String> noDupWordList = new ArrayList<>();
		ArrayList<String> wordList = getWords(t.toString());
		
		// NOTE: the operations below on words is UpperBounded (limit of words per Tweet), thus ignore time complexity
		// convert all words to LowerCase
		ArrayList<String> lowercaseWordList = new ArrayList<>();
		for(String word : wordList)
		{
			lowercaseWordList.add(word.toLowerCase());
		}
		
		ArrayList<String> lowercaseStopWordList = new ArrayList<>();
		for(String stopWord: this.stopWords)
		{
			lowercaseStopWordList.add(stopWord.toLowerCase());
		}
	     
		// check duplicates
		for(String possibleDuplicate : lowercaseWordList)
		{
			// if new list does not contain word & that it isn't a stop word, add it in.
			if(!noDupWordList.contains(possibleDuplicate) && !lowercaseStopWordList.contains(possibleDuplicate))
				noDupWordList.add(possibleDuplicate);
		}
		
		// now populate allTweets table with only Trending words
		for(String word : noDupWordList)
		{
			count = this.trendingTopics.get(word.toLowerCase());
			if(count == null) // word not in table
				this.trendingTopics.put(word.toLowerCase(), 1);
			else // word already in table
				this.trendingTopics.put(word.toLowerCase(), ++count);
		}
		
		// DATE INDEX*******************************************************************
		
		// if current date doesn't exist in table, add new arraylist
		if(this.tweetsDateIndex.get(t.getDateAndTime().substring(0, 10)) == null)
		{
			ArrayList<Tweet> newTweetListByDate = new ArrayList<>();
			newTweetListByDate.add(t);
			this.tweetsDateIndex.put(t.getDateAndTime().substring(0, 10), newTweetListByDate);
		}
		else // current date exists in table, add Tweet to corresponding ArrayList
		{
			this.tweetsDateIndex.get(t.getDateAndTime().substring(0, 10)).add(t);
		}
		
		// AUTHOR INDEX*****************************************************************
		
		// if tweet's author is new, add directly
		if(this.tweetsAuthorIndex.get(t.getAuthor()) == null || this.tweetsAuthorIndex.get(t.getAuthor()).compareTo(t) < 0)
			this.tweetsAuthorIndex.put(t.getAuthor(), t);
		// OR if tweet's author exists and tweet is more recent, replace previously stored tweet
	}
	

    /**
     * Search this Twitter for the latest Tweet of a given author.
     * If there are no tweets from the given author, then the 
     * method returns null. 
     * O(1)  
     */
    public Tweet latestTweetByAuthor(String author) {
    	return this.tweetsAuthorIndex.get(author);
    }


    /**
     * Search this Twitter for Tweets by `date' and return an 
     * ArrayList of all such Tweets. If there are no tweets on 
     * the given date, then the method returns null.
     * O(1)
     */
    
    public ArrayList<Tweet> tweetsByDate(String date /*NOTE THAT THIS DATE IS YYYY-MM-DD*/) {
    	return this.tweetsDateIndex.get(date);
    }
    
	/**
	 * Returns an ArrayList of words (that are not stop words!) that
	 * appear in the tweets. The words should be ordered from most 
	 * frequent to least frequent by counting in how many tweet messages
	 * the words appear. Note that if a word appears more than once
	 * in the same tweet, it should be counted only once. 
	 */
    public ArrayList<String> trendingTopics() {
    	for(int i = 0; i<4; i++)
    	{
    		System.out.println("Word: "+ MyHashTable.fastSort(this.trendingTopics).get(i)+", number of times: "+this.trendingTopics.get(MyHashTable.fastSort(this.trendingTopics).get(i)));
    	}
    	System.out.println("number of i: " + this.trendingTopics.get("i"));
    	return MyHashTable.fastSort(this.trendingTopics);  	
    }
    
    
    
    /**
     * An helper method you can use to obtain an ArrayList of words from a 
     * String, separating them based on apostrophes and space characters. 
     * All character that are not letters from the English alphabet are ignored. 
     */
    private static ArrayList<String> getWords(String msg) {
    	msg = msg.replace('\'', ' ');
    	String[] words = msg.split(" ");
    	ArrayList<String> wordsList = new ArrayList<String>(words.length);
    	for (int i=0; i<words.length; i++) {
    		String w = "";
    		for (int j=0; j< words[i].length(); j++) {
    			char c = words[i].charAt(j);
    			if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))
    				w += c;
    			
    		}
    		wordsList.add(w);
    	}
    	return wordsList;
    }

   /* public static void main(String[] args)
    {
    	String a = "Beta";
    	String b = "beta is a good Person";
    	ArrayList<String> words = getWords(b);
    	ArrayList<String> lowercaseList = new ArrayList<>();
    	for(String word : words)
    	{
    		lowercaseList.add(word.toLowerCase());
    	}
    	System.out.println(lowercaseList);
    	
    	ArrayList<Integer> intList = new ArrayList<>();
    	intList.add(3);
    	intList.add(4);
    	System.out.println(a.equals(b));
    	
    }*/
    

}
