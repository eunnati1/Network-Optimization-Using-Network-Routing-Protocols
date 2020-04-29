
package proj.dijkstraAlgorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;



import proj.graphGenerator.Edge1;
import proj.graphGenerator.GraphGenerator1;

public class KruskalMBP {
	
	//For disjoint set definition
	private int[] parent;  // Stores value of parent vertex for a given vertex v
	private int[] rank;    //Stores the rank for all vertices (Used for path compression)
	
    private int totalVertices;  
    private Queue<Edge1> MST;  //returns maximum spanning tree after kruskal's implementation
    private int[] bandwidth;   //Stores bandwidth values of all vertices
    private int[] parentVertex; // dad of vertex
    private boolean[] colored;   // We assume a vertex is colored if it is already visited
    private int destination;  //destination vertex
    
    private MaxHeap heap;

    
    //Modified Kruskals's algorithm to find the maximum spanning tree for a given connected undirected graph. HeapSort is used to sort edges in decreasing order
    public KruskalMBP(List<Edge1>[] adjList, List<Edge1> allEdges, int source, int destination) {
    	
    	//Initialisation 
    	heap = new MaxHeap();
    	
        totalVertices = adjList.length;  //Returns length of adjList- 
        bandwidth = new int[totalVertices];
        parentVertex = new int[totalVertices];
        colored = new boolean[totalVertices];
        Arrays.fill(parentVertex, -1);
        this.destination = destination;
        

       /* for (int i = 0; i <allEdges.size() ; i++) {
            Edge1 edge = allEdges.get(i);
            System.out.println("Edge-" + i + " source: " + edge.sourceVertex +
                    " destination: " + edge.destinationVertex +
                    " weight: " + edge.edgeWeight);
        }
        
        System.out.println("-----------------------");*/
        
        
 //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
       // Kruskals' Algorithm for Maximum spanning tree
 //---------------------------------------------------------------------------------------------------------------------------------------------       
       
       // STEP 1 OF ALGORITHM -- SORT EDGES
       heap.heapSort(allEdges);  // Sorts edges in a decreasing manner for maximum spanning tree implementation

       
       //STEP 2 OF ALGORITHM -- Initialize an empty list for maximum spanning tree
       MST = new LinkedList<>();
       
       
        /*for (int i = 0; i <abv.length ; i++) {
            
            System.out.println("Edge-" + i + " source: " + abv[i].sourceVertex +
                    " destination: " + abv[i].destinationVertex +
                    " weight: " + abv[i].edgeWeight);
        }*/
      
       
       // STEP 3 OF ALGORITHM -- MAKESET: Create a node whose parent is itself, a root
       makeSet(totalVertices);
           
       // STEP 4 OF ALGORITHM -- For all edges present, for both vertices of an edge find its union by rank and add set to maximum spanning tree list
        int totalEdges = allEdges.size();   // Total number of edges 

        while(totalEdges != 0) {   // While there are edges 
         Edge1 maxValEdge = heap.maximum();   // Find the maximum edges -- first element of max heap
         heap.deleteMaximum1();      // Remove the maximum value from heap
        	//Edge1 maxEdge = heap.extractMaxValueAndDelete1();
         int ui = maxValEdge.getSourceVertex();    // Source vertex of an edge
         int vi = maxValEdge.getDestinationVertex();  // Destination vertex of an edge
         int r1 = find(ui);    // Rank of source
         int r2 = find(vi);    // Rank of dest
         if(r1 != r2) {       // If rank not equal perform union operation
                union(r1,r2);
                MST.add(maxValEdge);   // Add the edge to MST list
            }
            totalEdges--;    
        }
        
  //-------------------------------------------------------------------------------------------------------------------------------------------------------      
        
        List<Edge1>[] mstgrapht = MSTGraph(MST);
        calculateMaximumBandwidth(mstgrapht, source);
        MBP();
       System.out.println("Bandwidth: " + Arrays.toString(bandwidth));
       System.out.println("Maximum Bandwidth found using Kruskal's Algorithm is - "  + bandwidth[destination]);
       
        //System.out.println("Dad: " + Arrays.toString(dad));
       
      //  System.out.println();
    }
    
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // DISJOINT SET METHODS
    //--------------------------------------------------------------------------------------------------------------------------------------------
    public void makeSet(int vertices) {
        parent = new int[vertices];
        rank = new int[vertices];
        
        for(int i = 0; i < vertices; i++) {
            parent[i] = i;                  // Initializes parent pointer to itself
        }
    }
  //-------------------------------------------------------------------------------------------------------------------------------------------------  
    private int find(int v) {   //to find the root of node
        int w = v;
        Queue<Integer> queue = new LinkedList<>();
        while(w != parent[w]) {
            queue.add(w);
            w = parent[w];
        }

        while(! queue.isEmpty()) {   //Path compression added
            int i = queue.remove();
            parent[i] = w;
        }
        return w;
    }
   //-------------------------------------------------------------------------------------------------------------------------------------------------------- 
    public void union(int r1, int r2) {
    	//Union by rank operation
        int rA = find(r1);    // Parent of r1 
        int rB = find(r2);   // Parent of r2

        int R1 = rank[rA];  // Rank of r1
        int R2 = rank[rB];    // Rank of r2

        if(R1 > R2){
            parent[rB] = rA;
        } else if(R1 < R2){
            parent[rA] = rB;
        } else {                // If ranks are equal
            parent[rA] = rB;    // Make rB child of rA
            rank[rB] = rank[rB] +1;        // increment rank of rB by 1
        }
    }
//------------------------------------------------------------------------------------------------------------------------------------------------------------
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////  
    private List<Edge1>[] MSTGraph(Queue<Edge1> edges) {
        @SuppressWarnings("unchecked")
		List<Edge1> [] mstgrapht = (List<Edge1>[]) new ArrayList[totalVertices];
        for(int i = 0; i < totalVertices; i++) {
            List<Edge1> edg = new ArrayList<>();
            mstgrapht[i] = edg;
        }
        for(Edge1 edge: edges) {  //For all edges
            int u = edge.getSourceVertex();
            int v = edge.getDestinationVertex();
            int w = edge.getEdgeWeight();
            Edge1 edge1 = new Edge1(v, u, w );  //Since its an undirected graph we add edgeWeight for reverse combination of vertex also
            mstgrapht[u].add(edge);   // add edges into the adjacency list representation of graph
            mstgrapht[v].add(edge1);   // add reverse
        }

        return mstgrapht;
    }

    private void calculateMaximumBandwidth(List<Edge1>[] adjList, int s) {
    	bandwidth[s] = Integer.MAX_VALUE;  // Initialize source value as infinity
        DFS(adjList, s);
    }

    // DFS TO FIND MAXIMUM BANDWIDTH FROM MAXIMUM SPANNING TREE
    private void DFS(List<Edge1>[] adjList, int v) {
    	
    	colored[v] = true;  //If the vertex is visited, return true
        if(v == destination) return;  //If vertex has reached the destination vertex, exit the function
        
        for(Edge1 edg: adjList[v]) {
            int w = edg.getDestinationVertex();   // for an edge [v,w] where w is the destination edge
            int weight = edg.getEdgeWeight();   // edgeWeight of [v,w] vertex
            if(!colored[w]) {  // if not visited 
            	parentVertex[w] = v;  //let v be the dad of w
                if(weight < bandwidth[v]) {    
                	bandwidth[w] = weight;
                } else {
                	bandwidth[w] = bandwidth[v];
                }
                DFS(adjList, w);  //Recursively call till all vertices are visited
            }
        }
    }
//-----------------------------------------------------------------------------------------------------------------------------------------------------
    private void MBP() {
        int v = destination;
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
    	  GraphGenerator1 g = new GraphGenerator1(1,5);
    	    adjList = g.getAdjList();
    	    List<Edge1> allEdges = g.getAllEdges();
    //	List<Edge1> adjList = new List<Edge1>();
    	 //   g.printAllEdges();
    	    KruskalMBP maxHeap = new KruskalMBP(adjList, allEdges,2,3); 
        
       
       maxHeap.MBP();
    } 
}
