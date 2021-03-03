import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class US_elections {

	public static int solution(int num_states, int[] delegates, int[] votes_Biden, int[] votes_Trump, int[] votes_Undecided){
		//System.out.println(num_states + Arrays.toString(delegates) + Arrays.toString(votes_Biden));
		
		// first find delegates required
		int delsTotal = 0; //sum up total delegates in this file
		int winsB = 0; //number of delegates Biden already won
		int[] delegates2win = new int[num_states]; // delegates that can be won for this state. Use delInd to index 
		int[] votesRequired = new int[num_states]; // min votes to win this state. Also use delInd to index
		int delInd = 0; //use to index delegates2win (states) & votes Required
		int winnableDels = 0; //number of delegates that are winnable in a given state (tru undecided voters)
		int min2win = 0; //Minimum delegates to win for Biden
		
		for (int i = 0; i < num_states; i++) //Use to find info required to find min delegates required & build winnable delegates array
		{	
			delsTotal += delegates[i]; // add up total
			
			if (votes_Biden[i] > votes_Trump[i] && votes_Undecided[i] == 0)
				winsB += delegates[i];
			
			else if (votes_Biden[i] > votes_Trump[i] && votes_Undecided[i] != 0 && votes_Biden[i] - votes_Trump[i] > votes_Undecided[i])
				winsB += delegates[i];
			
			else if (votes_Biden[i] == votes_Trump[i] && votes_Undecided[i] != 0)
			{
				delegates2win[delInd] = delegates[i];
				votesRequired[delInd] = votes_Undecided[i] / 2 + 1;
				delInd++;
				winnableDels += delegates[i];
				
			}
				
			else if (votes_Biden[i] < votes_Trump[i] && votes_Undecided[i] != 0 && votes_Trump[i] - votes_Biden[i] < votes_Undecided[i])
			{
				delegates2win[delInd] = delegates[i];
				int tbDiff = votes_Trump[i] - votes_Biden[i];
				votesRequired[delInd] = tbDiff + (votes_Undecided[i] - tbDiff)/2 + 1;
				delInd++;
				winnableDels += delegates[i];
			}
			
			else if (votes_Biden[i] > votes_Trump[i] && votes_Undecided[i] != 0 && votes_Biden[i] - votes_Trump[i] <= votes_Undecided[i])
			{
				delegates2win[delInd] = delegates[i];
				votesRequired[delInd] = (votes_Undecided[i] - (votes_Biden[i] - votes_Trump[i]))/2 + 1;
				delInd++;
				winnableDels += delegates[i];
			}
		}
		
		min2win = delsTotal/2 + 1 - winsB;
		System.out.println("Total delegates: "+delsTotal+"\nBiden has already won: "+winsB+"\nMin delegates to win is : "+min2win + " ,if < 0, then Biden does not need any extra votes.");
		System.out.println("number of winnable delegates is : "+winnableDels);
		System.out.println("delegates that still can be won: " + Arrays.toString(delegates2win)+ "\nMin votes required to win the delegates: "+ Arrays.toString(votesRequired));
		if (min2win > winnableDels)
			return -1;
		//System.out.println(delInd-1);
		// Now find the combination giving a win with minimum votes
		System.out.println("value of j: " + delInd);
		int ok = minVotes(delInd-1, 0, min2win, votesRequired, delegates2win); //Note delInd was incremented an extra time at the end of above loop
		System.out.println("done, and ok is: " + ok);
		return 1;
	}
	
	private static int minVotes(int j, int delSum, int min2win, int[] votesReq, int[] dels)
	{
		if (j == -1 && delSum < min2win)
			return Integer.MAX_VALUE; // Since votes can go in millions, use max value to avoid confusion with actual votes
		else if (j == -1)
			return 0;
		else {
			//System.out.println(j);
			int returnVal1 = minVotes(j-1, delSum + dels[j], min2win, votesReq, dels); // this val for including current state
			int returnVal2 = minVotes(j-1, delSum, min2win, votesReq, dels); // this val for excluding current state
			if (returnVal1 == Integer.MAX_VALUE) // since adding to max value may result in undefined behaviour, use max value directly
				return returnVal2;
			else
			return Math.min(votesReq[j] + returnVal1,
					returnVal2);
			//System.out.println(ok);
		}
	}

	public static void main(String[] args) {
	 try {
			String path = args[0];
      File myFile = new File(path);
      Scanner sc = new Scanner(myFile);
      int num_states = sc.nextInt();
      int[] delegates = new int[num_states];
      int[] votes_Biden = new int[num_states];
      int[] votes_Trump = new int[num_states];
      int[] votes_Undecided = new int[num_states];	
      for (int state = 0; state<num_states; state++){
    	  delegates[state] =sc.nextInt();
    	  votes_Biden[state] = sc.nextInt();
    	  votes_Trump[state] = sc.nextInt();
    	  votes_Undecided[state] = sc.nextInt();
      }
      sc.close();
      int answer = solution(num_states, delegates, votes_Biden, votes_Trump, votes_Undecided);
      	System.out.println(answer);
    	} catch (FileNotFoundException e) {
      	System.out.println("An error occurred.");
      	e.printStackTrace();
    	}
  	}

}