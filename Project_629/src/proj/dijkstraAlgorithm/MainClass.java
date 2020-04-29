package proj.dijkstraAlgorithm;

import java.util.List;
import java.util.Random;

import proj.graphGenerator.Edge1;
import proj.graphGenerator.GraphGenerator1;

public class MainClass {

	private void printMaxBWPath(Random r, GraphGenerator1 graph) {
		for (int i = 0; i < 5; i++) {
		int s = r.nextInt(graph.VERTICES);
		int t = r.nextInt(graph.VERTICES);
		System.out.println("Source Vertex is " + s + ", Destination Vertex is " + t);
		System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		long startTime = System.currentTimeMillis();
		MainClass o = new MainClass();
		o.TestAlgo(s, t, graph, 1);
		long endTime = System.currentTimeMillis();
		
		System.out.println("Dijkstra Algorithm Without Using Heap for MBP is completed in  " + (endTime - startTime) / 1000.0 + " seconds ");
		System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		startTime = System.currentTimeMillis();
		MainClass d = new MainClass();
		d.TestAlgo(s, t, graph, 2);
		endTime = System.currentTimeMillis();
		
		System.out.println("Dijkstra Algorithm Using Heap(MAX) for MBP is completed in " + (endTime - startTime) / 1000.0 + " seconds ");
		System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		startTime = System.currentTimeMillis();
		MainClass m = new MainClass();
		m.TestAlgo(s, t, graph, 3);
		endTime = System.currentTimeMillis();
		
		System.out.println("Kruskal's Algorithm for MBP is completed in " + (endTime - startTime) / 1000.0 + " seconds");
		System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
	}
}

	public void TestAlgo(int startVertex, int endVertex, GraphGenerator1 g, int algorithmType) {

		List<Edge1>[] adjList = g.getAdjList();

		if (algorithmType == 1) {
			new DijkstraWithoutUsingHeap(adjList, startVertex, endVertex);
		} else if (algorithmType == 2) {
			new DijkstraUsingMaxHeap(adjList, startVertex, endVertex);
		} else if (algorithmType == 3) {
			new KruskalMBP(adjList, g.getAllEdges(), startVertex, endVertex);
		} else {
			return;
		}
	}

	public static void main(String[] args) {
		
		System.out.println("===========================================================================================================================================================================================================================================");
		System.out.println(" UNNATI ERAMANGALATH ");
		System.out.println(" UIN : 930001393 ");
		System.out.println("=======================================================================================================================================================================================================================================================");
		for (int i = 0; i < 5; i++) {
		long startTime = System.currentTimeMillis();
		GraphGenerator1 sparseGraph = new GraphGenerator1(1);
		long endTime = System.currentTimeMillis();
		
		System.out.println("Sparse Graph (Having 6 as vertex degree) is created in " + (endTime - startTime) / 1000.0 + " seconds");
		System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

		startTime = System.currentTimeMillis();
		GraphGenerator1 denseGraph = new GraphGenerator1(2);
		endTime = System.currentTimeMillis();
		System.out.println("Dense Graph (having about 20 edges per vertex) is created in " + (endTime - startTime) / 1000.0 + " seconds");
		Random r = new Random();
		MainClass M = new MainClass();
		
			System.out.println("=======================================================================================================================================================================================================================================================");
			System.out.println("Sparse Graph G1: ");
			//System.out.println("Unnati ");
			M.printMaxBWPath(r, sparseGraph);
			System.out.println("=======================================================================================================================================================================================================================================================");
			System.out.println("Dense Graph G2: ");
			M.printMaxBWPath(r, denseGraph);
		}
		
	}
}
