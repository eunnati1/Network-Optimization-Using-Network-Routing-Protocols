package proj.dijkstraAlgorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import proj.dijkstraAlgorithm.DijkstraWithoutUsingHeap.VertexStatus;
import proj.graphGenerator.Edge1;
import proj.graphGenerator.GraphGenerator1;

public class DijkstraUsingMaxHeap {
	
	public enum VertexStatus {
	    unseen, // Vertices at infinity
	    fringe, // Vertices one step from the vertex under consideration
	    intree;  // Vertex inside heap/consideration
	}

	private int[] parentVertex;  // Array to store dad values
	private VertexStatus[] status; // To indicate current status of vertex
	private int[] bandwidth;    // To store bandwidth/weight values
	   
   private MaxHeap heap;   // Max heap object to call maxheap implementations for fringe values stored in heap
	  
   private int dest;  // destination vertex
// Algorithm to find the maximum bandwidth path using modified Dijkstra's algorithm using heap data structure
   public DijkstraUsingMaxHeap(List<Edge1>[] adjList, int src, int dest) {
	 this.dest = dest;
	 //Initialising arrays to accommodate all vertices
	 status = new VertexStatus[adjList.length];
	 parentVertex = new int[adjList.length];
	 bandwidth = new int[adjList.length];
	 heap = new MaxHeap(adjList.length);

	 // Initialization step 1-3 of Algorithm
	        
	 Arrays.fill(parentVertex, -1); // No prior parent
	 Arrays.fill(status, VertexStatus.unseen);  //initialize status of all vertices to infinity/unseen
	 status[src] = VertexStatus.intree;   // //initialize status of source vertex to in-tree

	  bandwidth[src] = Integer.MAX_VALUE;    // Let maximum bandwidth of source be infinity
	        
	   for(Edge1 edgeVal: adjList[src]) {           // For each edge from source to nearby vertices [s,w]
	     int w = edgeVal.getDestinationVertex();  // Fetch destination vertex w
	     status[w] = VertexStatus.fringe;       //status[w] = fringe;
	     int wt = edgeVal.getEdgeWeight();       // wt[w] = weight(s,w);
	     parentVertex[w] = src;         // dad[w] = s
	     bandwidth[w] = wt;
	     heap.insert(w, wt);           // Insert into heap
	  }

	 //Step 4 of Algorithm
	        
	while( status[dest] == VertexStatus.fringe || status[dest] != VertexStatus.intree) {    // While there are fringes
	          //  int v = heap.extractMaxValueAndDelete();  
	         //   heap.deleteMaximum();

	            int v = heap.maximum1(); //Picked the best fringe having maximum value
	            heap.deleteMaximum();  // Remove v from heap
	            
	            
	            status[v] = VertexStatus.intree;    // status[v]
	            for(Edge1 edgeVal: adjList[v]) {   // For each edge from v to nearby vertices [v,w]
	                int w = edgeVal.getDestinationVertex();   //Fetch destination vertex w
	                int wt = edgeVal.getEdgeWeight();   //weight[v,w]
	                if(status[w] == VertexStatus.unseen) {//if w is not a fringe
	                    status[w] = VertexStatus.fringe;  //if w is not a fringe
	                    parentVertex[w] = v;    // Let v be the parent of w
	                    bandwidth[w] = Math.min(bandwidth[v], wt);  // Cal bandwith of w as the min of maximum bandwidth of v and w
	                    heap.insert(w, bandwidth[w]);  //Add the vetex into heap
	                } else if(status[w] == VertexStatus.fringe && bandwidth[w] < Math.min(bandwidth[v], wt)) { //If w is already a fringe
	                    heap.delete(w);  //Remove w from heap
	                    bandwidth[w] = Math.min(bandwidth[v], wt);  //Recalculate its bandwidth
	                    parentVertex[w] = v;  //Upate parent vertex
	                    heap.insert(w, bandwidth[w]);   // Add back the updated vertex into heap
	                }
	            }
	        }
	        MBP();
	        System.out.println("Bandwidth: " + Arrays.toString(bandwidth));
	        System.out.println("Maximum Bandwidth of Dijkstra implemented with heap is -  "  + bandwidth[dest]);
	     
	     
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
	    	//    g.printEdges();
	    	DijkstraUsingMaxHeap maxHeap = new DijkstraUsingMaxHeap(adjList, 2,5); 
	        
	       
	       maxHeap.MBP();
	    } 
}
