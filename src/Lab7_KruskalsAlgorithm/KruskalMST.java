package Lab7_KruskalsAlgorithm;
/*
Implement Kruskal's algorithm to determine the Minimum Spanning Tree Forest of a weighted undirected graph with N nodes. 
The graph may be disconnected. Use a adiacency structure with lists to represent the graph and Disjoint Sets with weighted union 
over linked lists as auxiliary structure.

The input is given in a text file with following format:
The first line contains the total number of nodes, N.
The second line contains the total number of edges, M.
The next lines contain the edges, given as triplets of three numbers. Number1 and number2 are integers in the range 0..N-1, representing
node ID's and number3 is a positive number representing the weight of the edge.

The output of the program is the list of trees forming the minimum spanning tree forest. For each tree, give its cost and the set of 
edges which are part of the tree.
*/

import java.io.*;
import java.util.*;


// The class Edge represents an edge in the graph. It implements the Comparable interface to enable sorting of edges based on their weights. 
class Edge implements Comparable<Edge> {
    int src, dest, weight;

    public Edge(int src, int dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }
    // The compareTo method compares two edges based on their weights. 
    public int compareTo(Edge other) {
        return Integer.compare(weight, other.weight);
    }
}


// The class KruskalMST reads the graph input from a file and stores the edges in a list. 
class KruskalMST {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("100_300.txt"));

        int n = Integer.parseInt(br.readLine());
        int m = Integer.parseInt(br.readLine());

        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            String[] parts = br.readLine().split(" ");
            int src = Integer.parseInt(parts[0]);
            int dest = Integer.parseInt(parts[1]);
            int weight = Integer.parseInt(parts[2]);
            edges.add(new Edge(src, dest, weight));
        }
        br.close();

        // It then sorts the list of edges based on their weights. 
        Collections.sort(edges);
    
        
        // It initializes two arrays: parent and rank, to keep track of the parent and rank of each vertex in the graph. 
        int[] parent = new int[n];
        int[] rank = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 0;
        }

        /*
        It then iterates over the sorted list of edges, and for each edge, it checks if its source and destination vertices 
        belong to the same tree in the forest (i.e., have the same root vertex). If they do not belong to the same tree, it
        adds the edge to the MST and merges the two trees by calling the union method. 
        */
        List<List<Edge>> mstForest = new ArrayList<>();
        int mstWeight = 0;
        List<Edge> mst = new ArrayList<>();
        for (Edge edge : edges) {
            int u = edge.src;
            int v = edge.dest;

            int rootU = findSet(u, parent);
            int rootV = findSet(v, parent);

            if (rootU != rootV) {
                mstWeight += edge.weight;
                mst.add(edge);
                union(rootU, rootV, parent, rank);
            }
        }
        mstForest.add(mst);

        //  Finally, it prints the MST forest and the total weight of the MST.
        System.out.println("Minimum Spanning Tree Forest:");
        for (List<Edge> tree : mstForest) {
            int treeWeight = 0;
            System.out.println("Tree:");
            for (Edge edge : tree) {
                System.out.println(edge.src + " - " + edge.dest + " (" + edge.weight + ")");
                treeWeight += edge.weight;
            }
            System.out.println("Tree weight: " + treeWeight);
        }
        System.out.println("Total weight of forest: " + mstWeight);
    }

    // The findSet method is a helper method used by the union method to find the root vertex of a given vertex in the forest.
    // It uses path compression to optimize the search process.
    static int findSet(int x, int[] parent) {
        if (parent[x] != x) {
            parent[x] = findSet(parent[x], parent);
        }
        return parent[x];
    }

    // The union method uses the rank array to ensure that the smaller tree is merged with the larger tree.
    static void union(int x, int y, int[] parent, int[] rank) {
        if (rank[x] < rank[y]) {
            parent[x] = y;
        } else if (rank[x] > rank[y]) {
            parent[y] = x;
        } else {
            parent[y] = x;
            rank[x]++;
        }
    }
}

