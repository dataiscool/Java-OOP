// Code by John Pan
// February 20, 2020
package A2;
import java.util.Arrays;
import java.util.Random;

public class TrainLine {

	private TrainStation leftTerminus;
	private TrainStation rightTerminus;
	private String lineName;
	private boolean goingRight;
	public TrainStation[] lineMap;
	public static Random rand;

	public TrainLine(TrainStation leftTerminus, TrainStation rightTerminus, String name, boolean goingRight) {
		this.leftTerminus = leftTerminus;
		this.rightTerminus = rightTerminus;
		this.leftTerminus.setLeftTerminal();
		this.rightTerminus.setRightTerminal();
		this.leftTerminus.setTrainLine(this);
		this.rightTerminus.setTrainLine(this);
		this.lineName = name;
		this.goingRight = goingRight;

		this.lineMap = this.getLineArray();
	}

	public TrainLine(TrainStation[] stationList, String name, boolean goingRight)
	/*
	 * Constructor for TrainStation input: stationList - An array of TrainStation
	 * containing the stations to be placed in the line name - Name of the line
	 * goingRight - boolean indicating the direction of travel
	 */
	{
		TrainStation leftT = stationList[0];
		TrainStation rightT = stationList[stationList.length - 1];

		stationList[0].setRight(stationList[stationList.length - 1]);
		stationList[stationList.length - 1].setLeft(stationList[0]);

		this.leftTerminus = stationList[0];
		this.rightTerminus = stationList[stationList.length - 1];
		this.leftTerminus.setLeftTerminal();
		this.rightTerminus.setRightTerminal();
		this.leftTerminus.setTrainLine(this);
		this.rightTerminus.setTrainLine(this);
		this.lineName = name;
		this.goingRight = goingRight;

		for (int i = 1; i < stationList.length - 1; i++) {
			this.addStation(stationList[i]); // creates the doubly linked list using addStation method
		}

		this.lineMap = this.getLineArray();
	}

	public TrainLine(String[] stationNames, String name,
			boolean goingRight) {/*
									 * Constructor for TrainStation. input: stationNames - An array of String
									 * containing the name of the stations to be placed in the line name - Name of
									 * the line goingRight - boolean indicating the direction of travel
									 */
		TrainStation leftTerminus = new TrainStation(stationNames[0]);
		TrainStation rightTerminus = new TrainStation(stationNames[stationNames.length - 1]);

		leftTerminus.setRight(rightTerminus);
		rightTerminus.setLeft(leftTerminus);

		this.leftTerminus = leftTerminus;
		this.rightTerminus = rightTerminus;
		this.leftTerminus.setLeftTerminal();
		this.rightTerminus.setRightTerminal();
		this.leftTerminus.setTrainLine(this);
		this.rightTerminus.setTrainLine(this);
		this.lineName = name;
		this.goingRight = goingRight;
		for (int i = 1; i < stationNames.length - 1; i++) {
			this.addStation(new TrainStation(stationNames[i])); // creates the doubly linked list using addStation method
		}

		this.lineMap = this.getLineArray();

	}

	// adds a station at the last position before the right terminus by updating the left and right nodes
	public void addStation(TrainStation stationToAdd) {
		TrainStation rTer = this.rightTerminus;
		TrainStation beforeTer = rTer.getLeft(); 
		rTer.setLeft(stationToAdd);
		stationToAdd.setRight(rTer);
		beforeTer.setRight(stationToAdd);
		stationToAdd.setLeft(beforeTer);

		stationToAdd.setTrainLine(this);

		this.lineMap = this.getLineArray();
	}

	public String getName() {
		return this.lineName;
	}

	public int getSize() {		
		int count = 0;
		TrainStation tmp = this.leftTerminus;
		
		while (!tmp.getRight().equals(this.rightTerminus))
		{
			count = count + 1; // count stations in between terminals
			tmp = tmp.getRight();
		}
		count = count + 2; //add the terminals
		return count;
	}

	public void reverseDirection() {
		this.goingRight = !this.goingRight;
	}

	// You can modify the header to this method to handle an exception. You cannot make any other change to the header.
	public TrainStation travelOneStation(TrainStation current, TrainStation previous) throws StationNotFoundException {
		if(!current.getLine().equals(this))
		{
			throw new StationNotFoundException(current.getName());
		}
		
		else if (previous == null)
		{
			if (current.hasConnection)
			{
				return current.getTransferStation();
			}
			else
			{
				return this.getNext(current);
			}
		}
		
		else if (!previous.hasConnection && current.hasConnection) // check whether just transfered from previous station
		{
				return current.getTransferStation();
		}
		else if (current.hasConnection && !previous.getTransferStation().equals(current))
		{
			return current.getTransferStation();
		}
		else // case where current station does not have transfer
		{
			return this.getNext(current);
		}
	}

	// You can modify the header to this method to handle an exception. You cannot make any other change to the header.
	public TrainStation getNext(TrainStation station) throws StationNotFoundException {

		if(station.getLine().equals(this))
		{
		if(this.goingRight == true)
		{
			if(station.isRightTerminal() == true) // if the current station is a terminal, turn around.
			{
				this.reverseDirection();
				return station.getLeft();
			}
			else 
				{
				return station.getRight();
				}
		}
		else // if not going right, must be going left
		{
			if(station.isLeftTerminal() == true)
			{
				this.reverseDirection();
				return station.getRight();
			}
			else
				{
				return station.getLeft();
				}
		}
		}
		else
		{
			throw new StationNotFoundException(station.getName());
		}
	}

	// You can modify the header to this method to handle an exception. You cannot make any other change to the header.
	public TrainStation findStation(String name) throws StationNotFoundException {
		
		boolean ok = false;
		int ind = 0;

		for(int i = 0; i < lineMap.length ; i++)
		{ // lineMap is an array of TrainStation's
			if (this.lineMap[i].getName().equalsIgnoreCase(name))
			{
				ok = true;
				ind = i;
			}
		}
		if (!ok)
		{
			throw new StationNotFoundException(name);
		}
		else
			{
			return this.lineMap[ind]; 
			}
	}

	public void sortLine() {
		int n = this.getSize();
		int i = 1;
		int j;
		TrainStation prev = this.leftTerminus;
		TrainStation current = prev.getRight();
		TrainStation tmp = null;
		TrainStation tmp1 = null;
		//TrainStation tmp2 = this.leftTerminus; 
		/* note that we are giving the reference stored in leftTerminus to tmp2. If we change a right station by using
		 * setRight(), then both tmp2 and leftTerminus will see this change. HOWEVER,
		 * When we give new references to leftTerminus i.e. leftTerminus = current below,
		 * the references of leftTerminus are changed, but tmp2 is still referring to the object previously 
		 * referred to by the old leftTerminus. This screws up our sort algorithm below. We need tmp2
		 * to refer to the same object as leftTerminus, thus place this assignment at the end of the loop
		 * */
		
		while(i < n)
		{
			//System.out.println("I am here");
			j = i;
			while((j > 0) && (current.getName().compareToIgnoreCase(prev.getName()) < 0)) // if current's letter smaller, returns neg.
			{
				//case where there are only two stations
				if(prev.isLeftTerminal() && current.isRightTerminal())
				{
					current.setLeftTerminal();
					current.setLeft(null);
					current.setRight(prev);
					this.leftTerminus = current;
					
					prev.setRightTerminal();
					prev.setRight(null);
					prev.setLeft(current);
					this.rightTerminus = prev;
				}
				// case where prev is the left terminal
				if(prev.isLeftTerminal())
				{
					tmp = current.getRight(); // store the block that comes after the current one
					current.setLeftTerminal();
					current.setLeft(null);
					current.setRight(prev);
					
					prev.setLeft(current);
					prev.setRight(tmp);
					prev.setNonTerminal();
					tmp.setLeft(prev); // re-attach the node of the block that came after current
					this.leftTerminus = current;
				}
				// case where the current is the right terminal
				else if(current.isRightTerminal())
				{
					tmp = prev.getLeft(); // store the block that comes before the 2nd to last
					prev.setRightTerminal();
					prev.setRight(null);
					prev.setLeft(current);
					
					current.setLeft(tmp);
					current.setRight(prev);
					current.setNonTerminal();
					tmp.setRight(current); // re-attach the node of the block that came before prev
					this.rightTerminus = prev;
				}
				// case where none of the pair is a terminal
				else
				{
					tmp = current.getRight(); // store the references of the blocks not being shifted
					tmp1 = prev.getLeft();
					
					current.setRight(prev);
					current.setLeft(tmp1);
					
					prev.setLeft(current);
					prev.setRight(tmp);
					
					// re-attach nodes of the blocks that didn't shift
					tmp.setLeft(prev);
					tmp1.setRight(current);
				}
				j = j-1;
				// Compare current to what is before it (set as prev)
				prev = current.getLeft();
				//System.out.println(current.getName());
			}
			i = i + 1;
			// Set next current value depending on i.
			int count = 0;
			TrainStation tmp2 = this.leftTerminus;
			for (int z = 0; z < i; z++)
			{
				if (tmp2.getRight() == null)
				{
					break;
				}
				else 
					{
					tmp2 = tmp2.getRight();
					count = count +1;
					}
			}
			current = tmp2;
			prev = current.getLeft();
			tmp2 = this.leftTerminus; // reset to 0
			/*System.out.println(this.toString());
			System.out.println("next iteration: \nprev: "+prev.getName()+"\ncurrent: "+current.getName());
			System.out.println(count);*/
			
		}
		this.lineMap = this.getLineArray(); // bug occurs here
		
	}

	public TrainStation[] getLineArray() {
		
		TrainStation[] tmp = new TrainStation[this.getSize()];
		
		tmp[0] = this.leftTerminus;
		tmp[this.getSize()-1] = this.rightTerminus;
		
		for (int i = 1; i < this.getSize() - 1; i++)
		{
			tmp[i] = tmp[i-1].getRight();
		}
		return tmp;
	}

	// this should not be called from outside (private). the Lines should be shuffled along with the map using shuffleLine()
	private TrainStation[] shuffleArray(TrainStation[] array) {
		Random rand = new Random();
		
		for (int i = 0; i < array.length; i++) {
			int randomIndexToSwap = rand.nextInt(array.length);
			TrainStation temp = array[randomIndexToSwap];
			array[randomIndexToSwap] = array[i];
			array[i] = temp;
		}
		this.lineMap = array;
		return array;
	}

	public void shuffleLine() {

		// you are given a shuffled array of trainStations to start with
		TrainStation[] lineArray = this.getLineArray();
		TrainStation[] shuffledArray = shuffleArray(lineArray);
		
		// need to set each TrainStation's left and right to the correct ones
		
		// unset previous terminals 
		this.leftTerminus.setNonTerminal();
		this.rightTerminus.setNonTerminal();
		
		// set new terminals
		this.leftTerminus = shuffledArray[0];
		this.rightTerminus = shuffledArray[shuffledArray.length-1];
		this.leftTerminus.setLeftTerminal();
		this.rightTerminus.setRightTerminal();
		
		// set the nodes of the terminals
		this.leftTerminus.setRight(shuffledArray[1]);
		this.leftTerminus.setLeft(null); // remove previous node 
		this.rightTerminus.setLeft(shuffledArray[shuffledArray.length-2]);
		this.rightTerminus.setRight(null); // remove previous node
		
		// set the nodes of the remaining stations
		for (int i = 1; i < shuffledArray.length - 1; i++)
		{
			shuffledArray[i].setRight(shuffledArray[i+1]);
			shuffledArray[i].setLeft(shuffledArray[i-1]);	
		}
	}

	public String toString() {
		TrainStation[] lineArr = this.getLineArray();
		String[] nameArr = new String[lineArr.length];
		for (int i = 0; i < lineArr.length; i++) {
			nameArr[i] = lineArr[i].getName();
		}
		return Arrays.deepToString(nameArr);
	}

	public boolean equals(TrainLine line2) {

		// check for equality of each station
		TrainStation current = this.leftTerminus;
		TrainStation curr2 = line2.leftTerminus;

		try {
			while (current != null) {
				if (!current.equals(curr2))
					return false;
				else {
					current = current.getRight();
					curr2 = curr2.getRight();
				}
			}

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public TrainStation getLeftTerminus() {
		return this.leftTerminus;
	}

	public TrainStation getRightTerminus() {
		return this.rightTerminus; 
		
	}

/*public static void main(String[] args)
{
	// creating line 1
			TrainStation s1 = new TrainStation("1.Little Whinging");
			TrainStation s6 = new TrainStation("6.St Mungo's");

			s1.setRight(s6);
			s6.setLeft(s1);

			TrainLine l1 = new TrainLine(s1, s6, "Scarlet", true);

			TrainStation s2 = new TrainStation("2.Wizard Hat");
			l1.addStation(s2);
			TrainStation s3 = new TrainStation("3.Hogsmeade");
			l1.addStation(s3);
			TrainStation s4 = new TrainStation("4.Diagon Alley- 1/3");
			l1.addStation(s4);
			TrainStation s5 = new TrainStation("5.Ziagon Alley- 1/3");
			l1.addStation(s5);*/
			
			/*System.out.println(l1.getSize());
			System.out.println(l1.toString());
			l1.shuffleLine();
			System.out.println(l1.getSize());
			System.out.println(l1.toString());
			l1.shuffleLine();
			System.out.println(l1.getLeftTerminus().isLeftTerminal());
			System.out.println(l1.getLeftTerminus().getLeft());
			System.out.println("\n2nd station is: " + l1.getLeftTerminus().getRight().getName());
			System.out.println("Left terminal is: "+l1.getLeftTerminus().getName());
			System.out.println("\n Problem is definitely within sortLine \n");
			System.out.println("\nLine before sort is: "+l1);
			System.out.println("\nLine during sorting:\n");
			l1.sortLine();
			System.out.println(l1.toString());

}*/
}

//Exception for when searching a line for a station and not finding any station of the right name.
class StationNotFoundException extends RuntimeException {
	String name;

	public StationNotFoundException(String n) {
		name = n;
	}

	public String toString() {
		return "StationNotFoundException[" + name + "]";
	}
}


