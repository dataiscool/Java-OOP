import java.util.*;

public class BellmanFord{

    private int[] distances = null;
    private int[] predecessors = null;
    private int source;

    class BellmanFordException extends Exception{
        public BellmanFordException(String str){
            super(str);
        }
    }

    class NegativeWeightException extends BellmanFordException{
        public NegativeWeightException(String str){
            super(str);
        }
    }

    class PathDoesNotExistException extends BellmanFordException{
        public PathDoesNotExistException(String str){
            super(str);
        }
    }

    BellmanFord(WGraph g, int source) throws NegativeWeightException{
        /* Constructor, input a graph and a source
         * Computes the Bellman Ford algorithm to populate the
         * attributes 
         *  distances - at position "n" the distance of node "n" to the source is kept
         *  predecessors - at position "n" the predecessor of node "n" on the path
         *                 to the source is kept
         *  source - the source node
         *
         *  If the node is not reachable from the source, the
         *  distance value must be ********Integer.MAX_VALUE ******** (infinity)
         */
    	
    	// Weight of each edge must correspond to that of the shortest path to source
    	// Initialization
    	this.distances = new int[g.getNbNodes()];
    	this.predecessors = new int [g.getNbNodes()]; // Note this is more than required, can lead to unexpected error. Redefine later when number of nodes on path is known.
    	
    	//System.out.println("Initialization");
        // initialize each vertex
    	ArrayList<Edge> edges = g.getEdges();
    	for (Edge e : edges) {
    		this.distances[e.nodes[0]] = Integer.MAX_VALUE;
    		this.distances[e.nodes[1]] = Integer.MAX_VALUE;
    		this.predecessors[e.nodes[0]] = Integer.MAX_VALUE;
    		this.predecessors[e.nodes[1]] = Integer.MAX_VALUE;
    	}
    	this.distances[source] = 0;
    	this.source = source;
    	
    	//System.out.println("Initialized");
    	
    	//Bellman-Ford
    	for (int i = 0; i < g.getNbNodes() - 1; i++) {
    		for (Edge e : edges) {
    			this.relax(e.nodes[0], e.nodes[1], e.weight);
    		}
    	}
    	for (Edge e : edges) {
    		if (this.distances[e.nodes[1]] > this.distances[e.nodes[0]] + e.weight)
    			throw new NegativeWeightException("There is a negative weight cycle.");
    	}
    }

    public int[] shortestPath(int destination) throws PathDoesNotExistException{
        /*Returns the list of nodes along the shortest path from 
         * the object source to the input destination
         * If not path exists an Error is thrown
         */
    	int size = this.predecessors.length;
    	int count = 1; // Use to determine size of new path array containing exactly the path elements. Start at 1 since Destination already added.
    	
    	//System.out.println(size);
    	
    	int[] temp = new int[size]; // use more than required length for now
    	temp[size-1] = destination;
    	
    	for (int i = size - 2; i >= 0; i--) {
    		temp[i] = this.predecessors[temp[i+1]];
    		count++;
    		if (temp[i] == this.source)
    			break;
    		
    		//System.out.println("shortestPath added node to temp (excluding destination): "+temp[i]);
    	}
    	
    	int[] path = new int[count]; // copy over the path without other garbage (predecessor was initialized to be larger than required with infinity)
    	for (int i = count - 1, j = size - 1; i >= 0; i--, j--) {
    		path[i] = temp[j];
    		//System.out.println("node copied to path (excluding destination): "+ path[i]);
    	}
    	
    	if (path[0] != this.source)
    		throw new PathDoesNotExistException("This path does not exist.");
    	
    	return path; // If no error then there is path.
    }

    private void relax(int u, int v, int w) {
    	if (this.distances[v] > this.distances[u] + w) {
    		this.distances[v] = this.distances[u] + w;
    		this.predecessors[v] = u;
    	}
    }
    public void printPath(int destination){
        /*Print the path in the format s->n1->n2->destination
         *if the path exists, else catch the Error and 
         *prints it
         */
        try {
            int[] path = this.shortestPath(destination);
            for (int i = 0; i < path.length; i++){
                int next = path[i];
                if (next == destination){
                    System.out.println(destination);
                }
                else {
                    System.out.print(next + "-->");
                }
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public static void main(String[] args){

        String file = args[0];
        WGraph g = new WGraph(file);
        try{
            BellmanFord bf = new BellmanFord(g, g.getSource());
            bf.printPath(g.getDestination());
        }
        catch (Exception e){
            System.out.println(e);
        }

   } 
}

