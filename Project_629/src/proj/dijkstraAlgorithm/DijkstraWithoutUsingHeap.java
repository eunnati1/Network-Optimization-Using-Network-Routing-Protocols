package proj.dijkstraAlgorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


import proj.graphGenerator.Edge1;
import proj.graphGenerator.GraphGenerator1;

public class DijkstraWithoutUsingHeap {
	
	public enum VertexStatus {
	    unseen, // Vertices at infinity
	    fringe, // Vertices one step from the vertex under consideration
	    intree;  // Vertex inside heap/consideration
	}

private int[] parentVertex;  // Array to store dad values
private VertexStatus[] status; // To indicate current status of vertex
private int[] bandwidth;    // To store bandwidth/weight values
	   
ArrayList<Integer> arrli = new ArrayList<Integer>(); // To store edge values 
	  
private int dest;  // destination vertex

// Algorithm to find the maximum bandwidth path using modified Dijkstra's algorithm without using heap data structure
public DijkstraWithoutUsingHeap(List<Edge1>[] adjList, int src, int dest) {
  this.dest = dest;
  status = new VertexStatus[adjList.length];
  parentVertex = new int[adjList.length];
  bandwidth = new int[adjList.length];
//    heap = new MaxHeap(adjList.length);

   // Initialization step 1-3 of algorithm
	        
	 Arrays.fill(parentVertex, -1); // No prior parent
	 Arrays.fill(status, VertexStatus.unseen);  //initialize status of all vertices to infinity/unseen
	 status[src] = VertexStatus.intree;    //initialize status of source vertex to in-tree
     bandwidth[src] = Integer.MAX_VALUE;    // Let maximum bandwidth of source be infinity
	        //Step 3 of algorithm
	  for(Edge1 edgeVal: adjList[src]) {           // For each edge from source to nearby vertices [s,w]
	    int w = edgeVal.getDestinationVertex();   // Fetch destination vertex w
	    status[w] = VertexStatus.fringe;       //status[w] = fringe;
	    int wt = edgeVal.getEdgeWeight();       // wt[w] = weight(s,w);
	    parentVertex[w] = src;         // dad[w] = s
	    bandwidth[w] = wt;
	    // heap.insert(w, wt);           // Insert into heap
	    arrli.add(w);   // Add into arraylist
	        }

	        //Step 4 of Algorithm
	        
	 while( status[dest] == VertexStatus.fringe || status[dest] != VertexStatus.intree) {    // While there are fringes
	    int v = Collections.max(arrli); //Picked the best fringe having maximum edge value
	     for(Integer vertex: arrli ) {    // For all vertices in arraylist
	           if(bandwidth[vertex] > bandwidth[v]) { // If bw of that vertex is greater than the best fringe
	               v = vertex;    //Replace best fringe by that value
	              }
	         }
	     int idx =arrli.indexOf(v);
	      arrli.remove(idx);   // Remove v from arraylist

	     status[v] = VertexStatus.intree;    // status[v]
	            
	      for(Edge1 edgeVal: adjList[v]) {   // For each edge from v to nearby vertices [v,w]
	      int w = edgeVal.getDestinationVertex(); //Fetch destination vertex w
	      int wt = edgeVal.getEdgeWeight();   //weight[v,w]
	      if(status[w] == VertexStatus.unseen) {  //if w is not a fringe
	         status[w] = VertexStatus.fringe;    //Make w a fringe
	         parentVertex[w] = v;        // Let v be the parent of w
	         bandwidth[w] = Math.min(bandwidth[v], wt);  // Cal bandwith of w as the min of maximum bandwidth of v and w
	         //  heap.insert(w, bandwidth[w]);
	        arrli.add(w);   //Add the vetex into arraylist
	    } else if(status[w] == VertexStatus.fringe && bandwidth[w] < Math.min(bandwidth[v], wt)) { //If w is already a fringe
	          // heap.delete(w);
	          arrli.remove(arrli.indexOf(w));   //Remove w from list
	          bandwidth[w] = Math.min(bandwidth[v], wt); //Recalulate its bandwidth
	          parentVertex[w] = v;   //Upate parent vertex
	        // heap.insert(w, bandwidth[w]);
	          arrli.add(w);      // Add back into arraylist
	            }
	        }
	    }
	  MBP();   //Function to print the path 
	  
System.out.println("Bandwidth: " + Arrays.toString(bandwidth));	  
System.out.println("Maximum Bandwidth of Dijkstra without using heap is - "  + bandwidth[dest]);

}

     private void MBP() {
	        int v = dest;
	        ArrayList<Integer> arrli1 = new ArrayList<Integer>();
	        while(v >= 0) {
	           arrli1.add(v);
	           v = parentVertex[v];
	        }
	        for(int i =0 ; i<arrli1.size();i++) {
	        	System.out.print(arrli1.get(i)+ " -> ");
	        }
	        System.out.println();
	    }
	    
	    
	    public static void main(String[] arg) 
	    { 
	    	
	    	  List<Edge1>[] adjList;
	    	  GraphGenerator1 g = new GraphGenerator1(1, 8);
	    	    adjList = g.getAdjList();
	    //	List<Edge1> adjList = new List<Edge1>();
	    	   // g.printAllEdges();
	    	    DijkstraWithoutUsingHeap arrli = new DijkstraWithoutUsingHeap(adjList, 2,5); 
	        
	       
	      // arrli.MBP();
	       System.out.println();
	    } 
}
