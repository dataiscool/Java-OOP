// Code by John Pan
// February 20, 2020
package A2;

public class TrainNetwork {
	final int swapFreq = 2;
	TrainLine[] networkLines;

    public TrainNetwork(int nLines) {
    	this.networkLines = new TrainLine[nLines]; // creates an array of n train lines
    }
    
    public void addLines(TrainLine[] lines) {
    	this.networkLines = lines;
    }
    
    public TrainLine[] getLines() {
    	return this.networkLines;
    }
    
    public void dance() {
    	System.out.println("The tracks are moving!");
    	for (int i = 0; i < this.networkLines.length; i++)
    	{
    		this.networkLines[i].shuffleLine();
    	}
    }
    
    public void undance() {
    	for (int i = 0; i < networkLines.length; i++)
    	{
    		this.networkLines[i].sortLine();
    	}
    }
    
    public int travel(String startStation, String startLine, String endStation, String endLine) {
    	
    	TrainLine curLine = null; //use this variable to store the current line.
    	TrainStation curStation = null; //use this variable to store the current station. 
    	TrainStation prevStation = null;
    	TrainStation tmp = null;
    	TrainStation arrivalStation = null;
    	TrainLine arrivalLine = null;
    	
    	int hoursCount = 0;
    	
    	System.out.println("Departing from "+startStation);
    	
    	// catch potential exceptions so that code doesn't crash.
    	try 
    	{
    	curLine = this.getLineByName(startLine); // obtains the line that matches the start line
    	curStation = curLine.findStation(startStation); // obtains station matching startStation name
    	} catch (StationNotFoundException e)
    	{
    		System.out.println("Oh no! Departure station " + startStation + " is not found in the line " + startLine);
    		return 0;
    	} catch (LineNotFoundException e)
    	{
    		System.out.println("Oh no! Departure Line "+startLine+" is not found within the network.");
    		return 0;
    	}
    	try
    	{
    		arrivalLine = this.getLineByName(endLine); // obtain line of arrival to see whether it exists
        	arrivalStation = arrivalLine.findStation(endStation); // obtain station of arrival
    	} catch (StationNotFoundException e)
    	{
    		System.out.println("Oh no! Destination station " + endStation + " is not found in the line " + endLine);
    		return 0;
    	} catch (LineNotFoundException e)
    	{
    		System.out.println("Oh no! Destination Line " + endLine + " is not found within the network.");
    		return 0;
    	}
    	
    	while(!(curLine.equals(arrivalLine) && curStation.equals(arrivalStation))) {
    		tmp = curStation;
    		curStation = curLine.travelOneStation(curStation, prevStation);
    		prevStation = tmp; // update previous station to the previous 'current' station
    		curLine = curStation.getLine();
    		hoursCount++; // increment hour elapse
    		
    		if(hoursCount == 168) {
    			System.out.println("Jumped off after spending a full week on the train. Might as well walk.");
    			return hoursCount; // this will break the loop if we reach 168 hours first
    		}
    		
    		if(hoursCount%2 == 0) // every two hours, the lines in the network dance
    		{
    			this.dance();
    		}
    		
    		//prints an update on your current location in the network.
	    	System.out.println("Traveling on line "+curLine.getName()+":"+curLine.toString());
	    	System.out.println("Hour "+hoursCount+". Current station: "+curStation.getName()+" on line "+curLine.getName());
	    	System.out.println("=============================================");
	    	
    		}
	    	
	    	System.out.println("Arrived at destination after "+hoursCount+" hours!");
	    	return hoursCount;
    }
    
    
    //you can extend the method header if needed to include an exception. You cannot make any other change to the header.
    public TrainLine getLineByName(String lineName) throws LineNotFoundException {
    	int ind = 0; // to capture index of matching line, otherwise throw
    	boolean ok = false;
    	
    	for (int i = 0; i < this.networkLines.length; i++)
    	{
    		if (this.networkLines[i].getName().equalsIgnoreCase(lineName))
    		{
    			ok = true;
    			ind = i;
    		}
    	}
    	if (!ok)
    	{
    		throw new LineNotFoundException(lineName);
    	}
    	else
    	{
    		return networkLines[ind];
    	}
    }
    
  //prints a plan of the network for you.
    public void printPlan() {
    	System.out.println("CURRENT TRAIN NETWORK PLAN");
    	System.out.println("----------------------------");
    	for(int i=0;i<this.networkLines.length;i++) {
    		System.out.println(this.networkLines[i].getName()+":"+this.networkLines[i].toString());
    		}
    	System.out.println("----------------------------");
    }
}

//exception when searching a network for a LineName and not finding any matching Line object.
class LineNotFoundException extends RuntimeException {
	   String name;

	   public LineNotFoundException(String n) {
	      name = n;
	   }

	   public String toString() {
	      return "LineNotFoundException[" + name + "]";
	   }
	}