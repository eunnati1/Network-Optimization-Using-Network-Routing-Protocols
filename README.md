# Network-Optimization-Using-Network-Routing-Protocols
CSCE 629 Analysis of Algorithms Course Project 
Implemented routing algorithms to address the Maximum Bandwidth Path problem where the requirement is to find a path of maximum bandwidth between two vertices (source and destination vertex) in each weighted undirected randomly generated graph G. This algorithm can be used for the optimization of network routing protocols.

Generated two kinds of a random undirected graph of 5000 vertices each such that the first kind has an average vertex degree of 6 (Sparse Graph) and the second kind has each vertex adjacent to about 20% of the other vertices that are randomly chosen (Dense Graph).

The following 3 different approaches were implemented to find  the maximum Bandwidth Path which was tested on 5 pairs of randomly generated graphs for selected source-destination vertices with the analysis presented based on their running times:
• An algorithm based on a modification of Dijkstra's algorithm without using a heap structure.
• An algorithm based on a modification of Dijkstra's algorithm using a heap structure for fringes. 
• An algorithm based on a modification of Kruskal's algorithm, in which the edges are sorted by Heapsort. 

Algorithms were implemented in the JAVA programming language.
