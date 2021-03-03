import java.util.*;
import java.io.File;

public class FordFulkerson {

	public static ArrayList<Integer> pathDFS(Integer source, Integer destination, WGraph graph){
		ArrayList<Integer> path = new ArrayList<Integer>();
		ArrayList<Integer> neighbors = new ArrayList<Integer>(); 
		ArrayList<Integer> visited = new ArrayList<Integer>();
		Stack<Integer> pathS = new Stack<Integer>();
		Integer cur;
		
		pathS.push(source);
		while (!pathS.empty()) {
			cur = pathS.pop();
			visited.add(cur);
			if (cur == destination) { // check first since destination does not have neighbors
				path.add(cur);
				return path;
			}
			neighbors = getNeighbors(cur, graph, visited); // find neighbors of cur
			if (neighbors != null) { // current path seems legit
				// add cur to path
				path.add(cur);
			
				// visit
				for (Integer node : neighbors) {
					//if (!(visited.contains(node))) { // make sure we're not stuck in a self-loop. Note this condition is redundant, it has been taken care of by neighbours directly.
						pathS.push(node);  				// Checking visited condition here does not work, 
														// because if added node only has visited nodes, then we have added a dead-end node into our path. 
														// Thus this condition needs to be checked above by neighbours directly.
						//System.out.println(node);	
					//}
				}
			}
		} // not returned then did not find path to destination
		path.clear();
		return path;
		/*if (path.contains(destination)) {
			if (path.indexOf(destination) == path.size()-1) // last element is destination
				return path;
			else {
				path.clear();
				return path;
			}
		}
		else {
		path.clear();
		return path;
		}*/
	}

	private static ArrayList<Integer> getNeighbors(int node, WGraph graph, ArrayList<Integer> visited) {
		ArrayList<Integer> neighbors = new ArrayList<Integer>();
		int neighborCount = 0;
		for (int nbr = 0; nbr < graph.getNbNodes(); nbr++) {
			Edge e = graph.getEdge(node, nbr);
			if (e != null && !visited.contains(nbr)) { //if the edge returned is not null, then nbr is a neighbor of node. Do not include as valid neighbour if already visited, otherwise will be pushed onto stack.
				neighbors.add(nbr);
				neighborCount++;
			}
		}
		if (neighborCount > 0)
			return neighbors;
		else
			return null; // to tell pathFDS that no neighbors found (dead end)
	}

	public static String fordfulkerson( WGraph graph){
		String answer="";
		int maxFlow = 0;
		
		// keep below a copy of original graph
		WGraph oG = new WGraph(graph);
		
		// find bottleneck and build first flow
		ArrayList<Integer> path1 = pathDFS(graph.getSource(), graph.getDestination(), graph);
		
		//System.out.println(path1.toString());
		
		int beta = findBeta(path1, graph);
		maxFlow += beta;
		//System.out.println("First bottleneck =" +beta);
		// now add to flow in our original graph G
		// since we only have 1 parameter weight, we will subtract from weight the flow. 
		// if weight == 0, then that branch is maxed out.
		// take Cf = weight after subtraction if fwd, and Cf = value subtracted if bkwd
		int currWt = 0;
		for (int i = 0; i < path1.size() - 1; i++) {
			currWt = graph.getEdge(path1.get(i), path1.get(i+1)).weight;
			graph.setEdge(path1.get(i), path1.get(i+1), currWt - beta);
		}
		// Build residual graph
		WGraph resG = buildRG(graph, oG);
		
		//augment, find path in residual, find beta, and subtract it from graph
		ArrayList<Integer> pathR = pathDFS(graph.getSource(), graph.getDestination(), resG); //graph and resG have same source/sink
		
		while (!pathR.isEmpty()) {
			
			//System.out.println("pathR : "+pathR.toString());
			
			int betaR = findBeta(pathR, resG);
			maxFlow += betaR;
			
			WGraph edgesP = new WGraph(); // Save path as a graph 
			for (int i = 0; i < pathR.size() - 1; i++)  {
				edgesP.addEdge(new Edge(pathR.get(i), pathR.get(i+1), resG.getEdge(pathR.get(i), pathR.get(i+1)).weight));
			}
			//System.out.println("edgesP: \n"+edgesP.toString() + "\n");
			// Use P from above to augment path
			ArrayList<Edge> edgesR = edgesP.getEdges();
			for (Edge eR : edgesR) {
				if (eR.weight > 0) { // Have forward edge
					currWt = graph.getEdge(eR.nodes[0], eR.nodes[1]).weight;
					graph.setEdge(eR.nodes[0], eR.nodes[1], currWt - betaR); // Note our weight in the graph contains remaining space, not flow. Convert later
				}
				else { //backward, since the residual edge does not exist in graph
					currWt = graph.getEdge(eR.nodes[1], eR.nodes[0]).weight;
					graph.setEdge(eR.nodes[1], eR.nodes[0], currWt - betaR); // Note betaR negative, our weight in the graph contains remaining space, not flow. Convert later
				}
			}
			resG = buildRG(graph, oG); // Update residual graph
			pathR = pathDFS(graph.getSource(), graph.getDestination(), resG); // get next path
		}
		
		// compare with original graph when no more paths in residual, then convert remaining space to flow by doing oG weight - remaining space
		ArrayList<Edge> edgesFinal = graph.getEdges(); 
		for (Edge eF : edgesFinal) {
			currWt = graph.getEdge(eF.nodes[0], eF.nodes[1]).weight;
			graph.setEdge(eF.nodes[0], eF.nodes[1], oG.getEdge(eF.nodes[0], eF.nodes[1]).weight - currWt);
		}
		
		answer += maxFlow + "\n" + graph.toString();	
		return answer;
	}
	
	private static WGraph buildRG(WGraph graph, WGraph originalGraph) {
		WGraph resG = new WGraph();
		ArrayList<Edge> edges1 = graph.getEdges(); // updated edges
		// find residual capacity by comparing updated graph to original
		for (Edge e1 : edges1) {
			if (e1.weight > 0) { // f(e) < c(e) => c(e) - f(e) = weight > 0. Note if weight = 0, then only backward edge
				resG.addEdge(e1);
				//resG.setEdge(e1.nodes[0], e1.nodes[1], e1.weight);
			}
			if (e1.weight < originalGraph.getEdge(e1.nodes[0], e1.nodes[1]).weight) { // weight = c(e) - f(e) < c(e) => f(e) > 0 
				// Create backward edge
				Edge bkEdge = new Edge(e1.nodes[1], e1.nodes[0],  -(originalGraph.getEdge(e1.nodes[0], e1.nodes[1]).weight - e1.weight)); // backward edge
				resG.addEdge(bkEdge);
				
				/*if (e1.weight == 0) { //have full flow, no forward edge, delete by changing to negative nodes, render it unreachable
					resG.getEdge(e1.nodes[0], e1.nodes[1]).nodes[0] = -1;
					resG.getEdge(e1.nodes[0], e1.nodes[1]).nodes[1] = -1;
				}*/
			}
		}
		//System.out.println(resG.toString());
		return resG;
	}
	
	private static int findBeta(ArrayList<Integer> path, WGraph graph) {
		int beta = graph.getEdge(path.get(0), path.get(1)).weight;
		//System.out.println("Initial beta = " + beta);
		int temp;
		for (int i = 1; i < path.size() - 1; i++) {
			
			temp = graph.getEdge(path.get(i), path.get(i+1)).weight;
			if (temp < beta)
				beta = temp;
			//System.out.println("ite "+ i+" : beta = " + beta);
		}
		return beta;
	}

	 public static void main(String[] args){
		 String file = args[0];
		 File f = new File(file);
		 WGraph g = new WGraph(file);
	         System.out.println(fordfulkerson(g));
	 }
}
