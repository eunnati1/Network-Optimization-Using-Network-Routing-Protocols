package proj.dijkstraAlgorithm;

import java.util.List;


import proj.graphGenerator.Edge1;
import proj.graphGenerator.GraphGenerator1;

public class MaxHeap {
	
	 //The vertices of a graph are named by integers 0, 1, : : :, 4999
	//The heap is given by an array H[5000], where each element H[i] gives the name of
	//a vertex in the graph;
	
    private static int[] H;
    private static int[] indexVal; //stores the heap index of every element in the heap.
    private static Edge1[] edgeSort;

    //The vertex "values" are given in another array D[5000]. Thus, to find the value of
   // a vertex H[i] in the heap, we can use D[H[i]].
    
    private static int[] D;
    private static int size;
    private int maxsize; //Represents the capacity of the heap
	
    public MaxHeap() {
		// TODO Auto-generated constructor stub
	}

    public MaxHeap(int maxsize) {
    	this.maxsize = maxsize;
    	indexVal = new int[maxsize];
        H = new int[maxsize];
        D = new int[maxsize];
    }

    

	/*public MaxHeap(int maxsize) 
    { 
        this.maxsize = maxsize; 
        this.size = 0; 
        Heap = new int[this.maxsize + 1]; 
        Heap[0] = Integer.MAX_VALUE; 
        //System.out.println("UNNNNN " +  Heap[0]); 
    } */
    // To find parent position
    private int parent(int pos) {
        if(pos == 0) {
        	return -1;
        }
        if((pos - 1)/2 >= 0) {
        	
            return (pos - 1)/2;
        }
        
        return -1;
    } 
    
    //To find left child position
    private static int leftChild(int pos) {
    	
        int i = 2 * pos + 1;
        
        if (i < size) {
        	return i;
        }else {
        	return -1;
        }
       
     }
   //To find right child position
     private static int rightChild(int pos) {
    	 
         int i = 2 * pos + 2;
         
         if (i < size) {
         	return i;
         }else {
         	return -1;
         }
        
     }
// If heap is empth
     private boolean isEmpty() {
         return size == 0;
     }
     
     // Returns true of given vertex is leaf 
     private boolean isLeaf(int pos) 
     { 
         if (pos >= (size / 2) && pos <= size) { 
             return true; 
         } 
         return false; 
     } 
     // SWAP NODES
     private void swap(int fpos, int spos, int[] Heap) {
         int tmp ;
         tmp = Heap[fpos];
         Heap[fpos] = Heap[spos];
         Heap[spos] = tmp;
     }
    
   
   //Tp extract maximum value vertex
    public int extractMax() {
        if(isEmpty()) {
            return -1;
        } else {
            return H[0];
        }
    }
    //For modified Dijkstra
  public void deleteMaximum() {
    	
        if(isEmpty())
        	return;
        H[0] = H[size - 1];
        indexVal[H[0]] = 0;
        
        maxHeapify(0);
        size--;
    }
  
    /*public void insert(int element) 
    { 
        Heap[++size] = element; 
  
        // Traverse up and fix violated property 
        int current = size; 
        while (Heap[current] > Heap[parent(current)]) { 
            swap(current, parent(current)); 
            current = parent(current); 
        } 
    } */
//To nsert into an array
    public void insert(int vertex, int weight) {
    	
        if(size == maxsize - 1) {
        	return;
        }

       size++;
        H[size-1] = vertex;
        indexVal[vertex] = size - 1; // vertex 5 having 0 value indicating index position
        D[vertex] = weight;  //index 5 having weight 7
        heapify(size - 1, weight);
    }
// Heap sort for Kruskal
    public Edge1[] heapSort(List<Edge1> edgeList) {
        int n = edgeList.size();     //Length of list //24
        size = n;
        edgeSort = edgeList.toArray(new Edge1[n]);
        Edge1[] res = null;
        for (int i = n/2; i >= 0; i--) {  // will run 12 times
        	//res = maxHeapify1(i);
        	 res = fixHeapify(i);
        }
		return res;
    }
    public static Edge1[] heapSortTest(List<Edge1> edgeList) {
        int n = edgeList.size();     //Length of list //24
        size = n;
        edgeSort = edgeList.toArray(new Edge1[n]);
        Edge1[] res = null;
        for (int i = n/2; i >= 0; i--) {  // will run 12 times
        	//maxHeapify1(i);
        	//arrA[0] is a root of the heap and is the max element in heap
            Edge1 x = edgeSort[0];
            edgeSort[0] = edgeSort[i];
            edgeSort[i] = x;

            // call max heapify on the reduced heap
           res = sinkTest(edgeSort, i, 0);
        	 //sink(i);
        }
		return res;
    }
    
    private Edge1[] fixHeapify(int index) {
        while(index < size) {
            int biggerChild = greaterWeightEdge(leftChild(index),
                    rightChild(index));

            if(biggerChild == -1 || edgeSort[index].compareTo(edgeSort[biggerChild]) >= 0) {
                break;
            }
            swap1(index, biggerChild, edgeSort);
            index = biggerChild;
        }
		return edgeSort;
    }
    
    private static Edge1[] sinkTest(Edge1[] edgeSort2,int heapSize, int i) {
    	
    	
    	int largest = i; // Initialize largest as root
        int leftChildIdx  = leftChild(i); // left = 2*i + 1
        int rightChildIdx  = rightChild(i); // right = 2*i + 2

        // If left child is larger than root
        if (leftChildIdx  < heapSize && edgeSort2[leftChildIdx].edgeWeight > edgeSort2[largest].edgeWeight)
            largest = leftChildIdx ;

        // If right child is larger than largest so far
        if (rightChildIdx  < heapSize && edgeSort2[rightChildIdx].edgeWeight > edgeSort2[largest].edgeWeight)
            largest = rightChildIdx ;

        // If largest is not root
        if (largest != i) {
        	Edge1 swap = edgeSort2[i];
        	edgeSort2[i] = edgeSort2[largest];
        	edgeSort2[largest] = swap;

            // Recursive call to  heapify the sub-tree
        	sinkTest(edgeSort2, heapSize, largest);
            
         
        }
    	
        return edgeSort2;
		
    }
//Delete from Heap
    public void delete(int vertex) {
        int index = indexVal[vertex];
        if(index == -1) return;
        H[index] = H[size - 1];
        indexVal[H[index]] = index;
        
        maxHeapify(index);
        heapify(index, D[H[index]]);
        
        size --;
    }
    
    private void heapify(int idx, int edgeWeight) {
        while (idx > 0) {
        	
            if(D[H[parent(idx)]] >= edgeWeight) {
                break;
            } else {
                swap(idx, parent(idx), H);
                indexVal[H[idx]] = idx;
                indexVal[H[parent(idx)]] = parent(idx);
            }
            idx = parent(idx);
        }
    }

   

    private void maxHeapify(int idx) {
        while(idx < size) {
            int largerChild = largerNode(leftChild(idx),
                    rightChild(idx));

            if(largerChild == -1 || D[H[idx]] >= D[H[largerChild]]) {
                break;
            }
            swap(idx, largerChild, H);
            indexVal[H[idx]] = idx;
            indexVal[H[largerChild]] = largerChild;
            idx = largerChild;
        }
    }
    
    private void swap1(int a, int b, Edge1[] arr) {
        Edge1 temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }
    
    
    private int greaterWeightEdge(int i, int j) {
        if(j == -1 && i == -1) {
            return -1;
        } else if(j == -1) {
            return i;
        }
        return edgeSort[i].compareTo(edgeSort[j]) >=0 ? i : j;
    }
    private int largerNode(int i, int j) {
        if(j == -1 && i == -1) {
            return -1;
        } else if(j == -1) {
            return i;
        }
        else if(D[H[i]] >= D[H[j]]) {
           return i;
        }else 
        	return j;
    }

   
 // Remove an element from max heap 
    public int extractMaxValueAndDelete() 
    { 
        int popped = H[0]; 
        H[0] = H[size--]; 
        maxHeapify(0); 
        return popped; 
    } 
    
   

    /* A utility function to print array of size N */
    static void printArray(int arr[], int n) 
    { 
        for (int i = 0; i < n; ++i) 
            System.out.print(arr[i] + " "); 
  
        System.out.println(); 
    } 
    
    public static void print() 
    { 
        for (int i = 0; i <= size / 2; i++) { 
            System.out.print(" PARENT : " + D[i/2] + " LEFT CHILD : " + 
                      D[2 * i+1] + " RIGHT CHILD :" + D[2 * i + 2]); 
            System.out.println(); 
        } 
    } 
    static void printArraySort(Edge1[] edgeSort2, int n) 
    { 
        for (int i = 0; i < n; ++i) 
            System.out.print(edgeSort2[i] + " "); 
  
        System.out.println(); 
    } 
    //FOR KRUSKAL
    public Edge1 maximum() {
		 if(isEmpty()) {
	            return null;
	        } else {
	            return edgeSort[0];
	        }
	} 
    //For modified Dijkstra
    public int maximum1() {
        if(isEmpty()) {
            return -1;
        } else {
            return H[0];
        }
    }

    //FOR KRUSKAL
    public void deleteMaximum1() {
		// TODO Auto-generated method stub
		 if(isEmpty()) return;
	        edgeSort[0] = edgeSort[size - 1];
	        size--;
	        fixHeapify(0);
	}

    
    public static void main(String[] arg) 
    { 
        System.out.println("The Max Heap is "); 
        MaxHeap maxHeap = new MaxHeap(10); 
        
       
        
        //Insertion Operation
        maxHeap.insert(5, 7); 
        maxHeap.insert(3,10); 
        maxHeap.insert(7,109); 
       
        
        
        printArray(H,10);
        printArray(D,10);
        printArray(indexVal,10);
  
        maxHeap.print(); 
        System.out.println("The max val is " + maxHeap.extractMaxValueAndDelete()); 
      //  System.out.println("AFTER REMOVING 100"); 
        printArray(H,10);
        printArray(D,10);
        printArray(indexVal,10);
        maxHeap.print(); 
       // maxHeap.insert(100); 
       // System.out.println("The max val is without removing from heap " + Heap[1]); 
        
        
/* for (int i = 1; i <= size; i++) { 
            
            System.out.println( Heap[i] ); 
        } */
        
      
        System.out.println( "/////////////////////////////////////////");
      //  int res= maxHeap.del(2);
        maxHeap.print(); 
        
/*for (int i = 1; i <= size; i++) { 
            
            System.out.println( Heap[i] ); 
        } */
        System.out.println( "/////////////////////////////////////////");
        System.out.println( "/////////////////////////////////////////");
        List<Edge1>[] adjList;
  	  GraphGenerator1 g = new GraphGenerator1(1, 4);
  	    adjList = g.getAdjList();
  	    
  	  maxHeap.heapSort(adjList[2]);
  /*	 maxHeap.print(); 
  	printArray(H,10);
    printArray(D,10);
    printArray(indexVal,10);
    printArraySort(edgeSort,3);  // sorting the edge weights
*/    
    
    List<Edge1> allEdges = g.getAllEdges();
    
    Edge1[]  abv = heapSortTest(allEdges);

    for (int i = 0; i <abv.length ; i++) {
        
        System.out.println("Edge-" + i + " source: " + abv[i].sourceVertex +
                " destination: " + abv[i].destinationVertex +
                " weight: " + abv[i].edgeWeight);
    }
    }

	
	
   
}
