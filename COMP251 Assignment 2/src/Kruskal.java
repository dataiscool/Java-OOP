import java.util.*;

public class Kruskal{
	
    public static WGraph kruskal(WGraph g){
    	// union nodes if their edge is light? determine if edge is safe (not repeated?)
    	// if two nodes are already connected, not safe edge => don't add edge
    	
        WGraph mst = new WGraph(); // Creates new empty graph to build mst
    	ArrayList<Edge> edgesToEval = g.listOfEdgesSorted(); // Get list of edges in increasing order
        DisjointSets ds = new DisjointSets(g.getNbNodes()); // Create new disjoint set to track edges
    	
    	for (Edge e : edgesToEval) { // iterate thru the edges of the graph in increasing order
    		if (IsSafe(ds, e)) { // Do below if edge safe
    			mst.addEdge(e); // add this edge since safe (i.e. components not connected)
    			ds.union(e.nodes[0], e.nodes[1]); // connect the components & repeat steps again for next edge
    		}
    	}
    	
    	return mst;
    }

    public static Boolean IsSafe(DisjointSets p, Edge e){
    	// safe edge = edge between different components
    	
    	if (p.find(e.nodes[0]) == p.find(e.nodes[1])) { // then same component
    		return false;
    		}
    	else // different component
    		return true;
    }

    public static void main(String[] args){

        String file = args[0];
        WGraph g = new WGraph(file);
        WGraph t = kruskal(g);
        System.out.println(t);

   } 
}
