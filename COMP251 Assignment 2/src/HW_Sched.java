import java.util.*;

class Assignment implements Comparator<Assignment>{
	int number;
	int weight;
	int deadline;
	
	
	protected Assignment() { //creates empty Assignment
	}
	
	protected Assignment(int number, int weight, int deadline) {
		this.number = number;
		this.weight = weight;
		this.deadline = deadline;
	}
	
	
	
	/**
	 * This method is used to sort to compare assignment objects for sorting. 
	 */
	@Override 	  	

	public int compare(Assignment a1, Assignment a2) {
		// maximize sum of weights. 
		// Sort by decreasing weight. If equal, take closest deadline
		if (a1.weight == a2.weight ) { 
			if (a1.deadline < a2.deadline) 
				return -1; 
			else if (a1.deadline > a2.deadline) 
				return 1;
			else
				return 0; // both wt and deadline equal
		}
		else if (a2.weight < a1.weight)
			return -1; 
		else 
			return 1; 
	}
}

public class HW_Sched {
	// Fields
	ArrayList<Assignment> Assignments = new ArrayList<Assignment>();
	int m;
	int lastDeadline = 0;
	
	// Constructor
	// Constructor takes in an array of weights and deadlines
	// Note that indices of each array refer to individual homeworks
	// e.g. homework 2 is defined as deadlines[2] and weights[2]
	// Assignments ArrayList will contain each homework and their corresponding number, weight and deadline
	protected HW_Sched(int[] weights, int[] deadlines, int size) {
		for (int i=0; i<size; i++) {
			Assignment homework = new Assignment(i, weights[i], deadlines[i]);
			this.Assignments.add(homework);
			if (homework.deadline > lastDeadline) {
				lastDeadline = homework.deadline;
			}
		}
		m =size;
	}
	
	
	/**
	 * 
	 * @return Array where output[i] corresponds to the assignment 
	 * that will be done at time i.
	 */
	public int[] SelectAssignments() {
		//TODO Implement this
		
		//Sort assignments
		//Order will depend on how compare function is implemented
		Collections.sort(Assignments, new Assignment()); // Note Assignment implements Comparator
		
		/*for (int i = 0; i < Assignments.size(); i++) {
		System.out.println(Assignments.get(i).number);
		}
		*/
		
		// If homeworkPlan[i] has a value -1, it indicates that the 
		// i'th timeslot in the homeworkPlan is empty NOTE: i is the timeslot
		//homeworkPlan contains the homework schedule between now and the last deadline
		int[] homeworkPlan = new int[lastDeadline];
		for (int i=0; i < homeworkPlan.length; ++i) {
			homeworkPlan[i] = -1;
		}
		
		int time = 0; // use to track current time

		// Now add to homeworkPlan using greed
		// check if homeworkPlan[hwk.deadline - 1] = -1 (empty)
		// greedy algo has put highest weight first, so if slot already filled, then it's a greedy choice (valid) => put it in a previous time slot if available
		for (Assignment hwk : this.Assignments) { // Assignments sorted above by dec. weight
			time = hwk.deadline - 1;
			while (time >= 0) {
				if (homeworkPlan[time] == -1) { //  if this slot empty, then add current greedy choice
				homeworkPlan[time] = hwk.number;
				break;
				}
			time--; // keep looking for prior empty slots if this one not available.
			}
		}	
		return homeworkPlan;
	}
   /* public static void main(String[] args) {
    	Integer d1 = 1;
    	Integer d2 = 3;
    	//System.out.println(d1.compareTo(d2));
    	int[] deadline = {3,1,2,1,3};
    	int[] weight = {23,60,14,25,7};
    	HW_Sched werks = new HW_Sched(weight, deadline, 5);
    	System.out.println(Arrays.toString(werks.SelectAssignments())); // this works. Uncomment print statement above to try
    	//[ (0, 23, 3), (1, 60, 1) , (2, 14, 2), (3, 25, 1), (4, 7, 3) ]
		
    	//This is the typical kind of input you will be tested with. The format will always be the same
		//Each index represents a single homework. For example, homework zero has weight 23 and deadline t=3.
		int[] weights = new int[] {23, 60, 14, 25, 7}; 
		int[] deadlines = new int[] {3, 1, 2, 1, 3};
		int m = weights.length;
		
		//This is the declaration of a schedule of the appropriate size
		HW_Sched schedule =  new HW_Sched(weights, deadlines, m);
		
		//This call organizes the assignments and outputs homeworkPlan
		int[] res = schedule.SelectAssignments();
		System.out.println(Arrays.toString(res));
    } */
}
	
