package Lab6_PrimsAlgorithm;
// Implement Prim's algorithm for MST using distance arrays.

/*
The input graph is represented as a two-dimensional array and the algorithm works by 
maintaining an array of distances and a boolean array to keep track of visited nodes. 
It selects the node with the minimum distance value and marks it as visited. Then, for 
all the unvisited neighbors of the selected node, it updates their distance values if 
the distance to the selected node plus the weight of the edge between them is less than 
their current distance value. Finally, the algorithm prints the MST.  
*/

import java.util.Arrays;

public class Prim {
    public static void main(String[] args) {
        // Define the input graph
        int[][] graph = {
            {0, 5, 6, 0},
            {5, 0, 9, 0},
            {6, 9, 0, 4},
            {0, 0, 4, 0}
        };

        // Initialize the data structures
        int numVertices = graph.length;
        int[] parent = new int[numVertices];
        int[] distance = new int[numVertices];
        boolean[] visited = new boolean[numVertices];
        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[0] = 0;

        // Compute the MST using Prim's algorithm
        for (int i = 0; i < numVertices - 1; i++) {
            int u = getMin(distance, visited);
            visited[u] = true;
            for (int v = 0; v < numVertices; v++) {
                if (graph[u][v] != 0 && !visited[v] && graph[u][v] < distance[v]) {
                    parent[v] = u;
                    distance[v] = graph[u][v];
                }
            }
        }

        // Print the MST
        printMST(parent, graph);
    }

    private static int getMin(int[] distance, boolean[] visited) {
        int minDistance = Integer.MAX_VALUE;
        int minVertex = -1;
        for (int v = 0; v < distance.length; v++) {
            if (!visited[v] && distance[v] < minDistance) {
                minDistance = distance[v];
                minVertex = v;
            }
        }
        return minVertex;
    }

    private static void printMST(int[] parent, int[][] graph) {
        System.out.printf("\nThe MST using Prim's algorithm is:\n\n");
        for (int i = 1; i < parent.length; i++) {
            int u = parent[i];
            int weight = graph[i][u];
            System.out.printf("%d - %d -> weight = %d\n", u+1, i+1, weight);
        }
    }
}