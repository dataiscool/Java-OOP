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
		int[] memo = new int[num_states]; // use later for memoizing intermediate values when finding min votes
		
		for (int i = 0; i < num_states; i++) //Use to find info required to find min delegates required & build winnable delegates array
		{	
			delsTotal += delegates[i]; // add up total
			
			if (votes_Biden[i] > votes_Trump[i] && votes_Undecided[i] == 0)
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
			
			else if (votes_Biden[i] > votes_Trump[i] && votes_Undecided[i] != 0 && votes_Biden[i] - votes_Trump[i] > votes_Undecided[i])
			{
				delegates2win[delInd] = delegates[i];
				votesRequired[delInd] = (votes_Undecided[i] - (votes_Trump[i] - votes_Biden[i]))/2 + 1;
				delInd++;
				winnableDels += delegates[i];
			}
			memo[i] = -1; //initialize memo function now as we determine the above to avoid doing another iteration later
		}
		
		min2win = delsTotal/2 + 1 - winsB;
		System.out.println("min delegates to win is : "+min2win);
		System.out.println("number of winnable delegates is : "+winnableDels);
		System.out.println("delegates2win: " + Arrays.toString(delegates2win)+ "\nvotesRequired: "+ Arrays.toString(votesRequired));
		if (min2win > winnableDels)
			return -1;
		System.out.println(delInd-1);
		return minVotes(delInd-1, 0, min2win, votesRequired, delegates2win); //Note delInd was incremented an extra time at the end of above loop
		// Now find the combination giving a win with minimum votes
		// Use memoization; stores number of total votes. memo already initialized above
		//int addedDels = 0; //Use to sum up the delegations won
		//int j = 0; //use as index for memo below
		/*while(addedDels < min2win)
		{
			if(votesRequired[j] + memo[j-1] > memo[j+1]) //
				memo[j] = memo[j+1];
		}*/
		//return 1;
		
		//with memo
		
	}
	
	private static int minVotes(int j, int delSum, int min2win, int[] votesReq, int[] dels)
	{
		if (j == -1 && delSum < min2win)
			return 1000000;
		else if (j == -1)
			return 0;
		else {
			//int suminc = delSum + dels[j];
			//System.out.println(suminc);
			return Math.min(votesReq[j] + minVotes(j-1, delSum + dels[j], min2win, votesReq, dels),
					minVotes(j-1, delSum, min2win, votesReq, dels));
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