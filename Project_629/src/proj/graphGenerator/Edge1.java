package proj.graphGenerator;


// A class to define source vertex, destination vertex and edgeweight for all vertices in a graph G
public class Edge1 implements Comparable<Edge1>{
	    public int sourceVertex;
	    public int destinationVertex;
	    public int edgeWeight;

	    
	    public Edge1(int sourceVertex, int destinationVertex, int edgeWeight) {
	        this.sourceVertex = sourceVertex;
	        this.destinationVertex = destinationVertex;
	        this.edgeWeight = edgeWeight;
	    }

	    public int getSourceVertex() {
	        return sourceVertex;
	    }
	    
	    public int getDestinationVertex() {
	        return destinationVertex;
	    }

	    public int getEdgeWeight() {
	        return edgeWeight;
	    }
	    
	    @Override
	    public int hashCode() {
	    	final int prime = 31;
	    	int result = 1*sourceVertex + destinationVertex;;
	    	result = prime * result + edgeWeight;
	        return result;
	    }
	    
	    @Override
	    public String toString() {
	        return  "source : " + sourceVertex + ", destination : " + destinationVertex +", edgeWeight : " + edgeWeight ;
	    }


	    @Override
	    public int compareTo(Edge1 e) {
	        int difference = this.edgeWeight - e.edgeWeight;
			return difference;
	    }

	    
}
