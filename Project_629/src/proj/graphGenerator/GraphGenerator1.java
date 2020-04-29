package proj.graphGenerator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class GraphGenerator1 {
	
	public static final int VERTICES = 5000;      // Graph having 5000 vertices
	private List<Edge1> addEdges;   // add edges to list -- duplicates allowed--in order
    private Set<Integer> edges;  //hashset to store edge key -- unordered -- unique value edgeWeights
	private List<Edge1>[] adjacencyList;  // add vertices and edges to form adjacency list of graph
      
    private int vertices;   // no. of vertices
    
    Random random = new Random();
    
    int randomEdgeWeightLimit;
    
    public GraphGenerator1(int Gi) {   // Gi (G1 = Sparse) or (G2 = Dense) 
    	vertices = VERTICES;
        generateRandomG(Gi);     //
        }

    
    public GraphGenerator1(int Gi, int vertices) {
        this.vertices = vertices;
        generateRandomG(Gi);
    }

    @SuppressWarnings("unchecked")
	private void generateRandomG(int Gi) {
       
    	edges = new HashSet<>();
        adjacencyList = (List<Edge1>[]) new ArrayList[vertices];
        addEdges = new ArrayList<>();
        
        randomEdgeWeightLimit = random.nextInt(100) + 1500; //Limiting maximum edgeWeight 

        for (int i = 0; i < vertices; i++) {
        	adjacencyList[i] = new ArrayList<Edge1>();  // Has adj list containing all vertices
        }
        if(Gi == 1) {                   // Sparse Graph
        	Graph1(adjacencyList, vertices);
        } else {                     //Dense Graph
        	Graph2(adjacencyList, vertices);
        }
    }

    
     // G1: Random Undirected Sparse Graph (Having average vertex degree 6).
     
    private void Graph1(List<Edge1>[] adj, int n) {
        int totlEdgs = 3 * n;   //Total edges = 15000  // Avg degree =  edges*2/vertices
        connectVertices(adj, n);  // Make a graph that connects all vertices
        int remaningEdges = totlEdgs - n; //After creating a cycle, n edges used already -- 5000 used remaining 10000

        while(remaningEdges > 0) {
            int x = random.nextInt(n);  // returns a random number between 0 and 5000
            int y = x;
            while(y == x) {
                y = random.nextInt(n);
            }
            //Method 1 :: checking by iterating through the list of edges and checking if edge is already added or not 
                boolean ifedge = completeIteration(x,y);
    
            	if(ifedge != false) { 
                int randWeight = random.nextInt(randomEdgeWeightLimit) + 1;  ////To make edgeweights non zero 
                Edge1 e = new Edge1(x, y, randWeight);
                Edge1 f = new Edge1(y, x, randWeight);
                adjacencyList[x].add(e);
                adjacencyList[y].add(f);
                addEdges.add(e);
                remaningEdges--;
            }
        }
    }

    
     // G2: Dense Graph - Each vertex adjacent to about 20% of other vertices
     
    private void Graph2(List<Edge1>[] adj, int n) {
    	connectVertices(adj, n);  // Make a connected graph
        for(int i = 0; i < n; i++) {
            for(int j = i  + 1; j < n; j++) {
                int randN = random.nextInt(100);   // Select an int randomly between 1 and 100
                int edgeKey = uniqueKeyValue(i, j); //  calculate its unique value
                if(!edges.contains(edgeKey) && randN < 20) {  // no multiple edge and number picked less than 20
                    int rdWeight =  random.nextInt(randomEdgeWeightLimit) + 1;
                    Edge1 e = new Edge1(i, j, rdWeight);
                    Edge1 f = new Edge1(j, i, rdWeight);
                    adjacencyList[i].add(e);
                    adjacencyList[j].add(f);
                    addEdges.add(e);
                }
            }
        }
    }

  // To ensure that the graph is connected
    private void connectVertices(List<Edge1>[] adj, int n) {
        for(int i = 0; i < n - 1 ; i ++) {
            int r1 = random.nextInt(randomEdgeWeightLimit) + 1;
            Edge1 e = new Edge1(i, i + 1, r1);
            Edge1 f = new Edge1(i+1, i, r1);
            adj[i].add(e);
            adj[i+1].add(f);
            addEdges.add(e);
            storeEdgeSet(i, i + 1);
        }
        int r2 = random.nextInt(randomEdgeWeightLimit) + 1;
        Edge1 e2 = new Edge1(n - 1,1, r2);
        Edge1 f2 = new Edge1(1, n - 1, r2);
        adj[n - 1].add(e2);
        adj[1].add(f2);
        addEdges.add(e2);
        storeEdgeSet(n - 1, 1);
    }

    private int uniqueKeyValue(int s, int t) {
        int uniquek=  250 * s + t;                    // Unique value generated for each edge
        return uniquek;
    }
    private void storeEdgeSet(int s, int t) {
        int k1 = uniqueKeyValue(s, t);
        int k2 = uniqueKeyValue(t, s);
        edges.add(k1);     //unique value of s and t is added to hashset
        edges.add(k2);
    }
    public void printEdges() {
       /* for(int i = 0; i< vertices; i++) {
            for(Edge1 eds: adjacencyList[i]) {
                System.out.println(eds);
            }
        }*/
         for (int i = 0; i <addEdges.size() ; i++) {
        Edge1 edge = addEdges.get(i);
        System.out.println("Edge-" + i + " source: " + edge.sourceVertex +
                " destination: " + edge.destinationVertex +
                " weight: " + edge.edgeWeight);
         }
        
        
        
        
    }

    public List<Edge1>[] getAdjList(){
        return adjacencyList;
    }

    public List<Edge1> getAllEdges() {
        return addEdges;
    }
    
   private Boolean completeIteration(int s, int t  ) {
	   int edeg;
    	for (int i = 0; i <addEdges.size() ; i++) {
        Edge1 edge = addEdges.get(i);
       
		if(edge.sourceVertex == s && edge.destinationVertex == t) {
			edeg = edge.edgeWeight;                      // Edge already exists
			return false;   // edge value present
		}
		    
    	}
		return true;
    }
    

   
    public static void main(String[] args) {
        GraphGenerator1 g = new GraphGenerator1(1, 5);
        g.printEdges();
    }

   
}
