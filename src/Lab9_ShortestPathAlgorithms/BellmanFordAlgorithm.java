package Lab9_ShortestPathAlgorithms;/*
Shortest paths in directed graph with negative weights - Bellman-Ford algorithm
The prices for traveling by bus on direct connections between pairs of cities are known. For certain connections, however, 
the local tourist office offers promotion vouchers that may be even bigger than the price of the corresponding bus ticket.
Implement a travel planner for planning a journey from a source city to a destination city, using buses such that the total
cost of the journey is minimum.

The program reads the graph of cities and direct connections from a text file with following format:
- first line contains N, the number of cities
- second line contains M, the number of direct connections
- the next M lines contain triplets of the form city1 city2 cost, where city1 and city2 are integer numbers in the range 0
to N-1 and cost is the difference price-voucher, and can be a positive or negative integer number
Read from standard input the numbers identifying the source and the destination cities.

Print the cheapest travel route (sequence of cities) from source to destination and its total price. 
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class BellmanFordAlgorithm {
    private int numCities;
    private int numConnections;
    private List<Edge> edges;
    private int[] distances;
    private int[] previous;

    public static void main(String[] args) {
        BellmanFordAlgorithm bellmanFord = new BellmanFordAlgorithm();
        bellmanFord.readGraphFromFile("negative_weights_100_600.txt");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the source city: ");
        int source = scanner.nextInt();
        System.out.print("Enter the destination city: ");
        int destination = scanner.nextInt();

        bellmanFord.calculateShortestPaths(source);
        bellmanFord.printShortestPath(destination);

        scanner.close();
    }

    // Read the graph from a file
    public void readGraphFromFile(String fileName) {
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);

            // Read the number of cities and connections
            numCities = scanner.nextInt();
            numConnections = scanner.nextInt();

            edges = new ArrayList<>(numConnections);

            // Read the connections and their costs
            for (int i = 0; i < numConnections; i++) {
                int city1 = scanner.nextInt();
                int city2 = scanner.nextInt();
                int cost = scanner.nextInt();
                edges.add(new Edge(city1, city2, cost));
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + fileName);
        }
    }

    // Calculate the shortest paths using the Bellman-Ford algorithm
    public void calculateShortestPaths(int source) {
        distances = new int[numCities];
        previous = new int[numCities];
        Arrays.fill(distances, Integer.MAX_VALUE);
        Arrays.fill(previous, -1);
        distances[source] = 0;

        // Relax the edges multiple times to find the shortest paths
        for (int i = 0; i < numCities - 1; i++) {
            for (Edge edge : edges) {
                int u = edge.getSource();
                int v = edge.getDestination();
                int weight = edge.getCost();

                // Relax the edge if a shorter path is found
                if (distances[u] != Integer.MAX_VALUE && distances[u] + weight < distances[v]) {
                    distances[v] = distances[u] + weight;
                    previous[v] = u;
                }
            }
        }

        // Check for negative cycles
        for (Edge edge : edges) {
            int u = edge.getSource();
            int v = edge.getDestination();
            int weight = edge.getCost();

            // If a shorter path is found, there is a negative cycle
            if (distances[u] != Integer.MAX_VALUE && distances[u] + weight < distances[v]) {
                System.err.println("Graph contains a negative cycle.");
                return;
            }
        }
    }

    // Print the shortest path and its total price
    public void printShortestPath(int destination) {
        if (previous[destination] == -1) {
            System.out.println("No path exists from source to destination.");
            return;
        }

        List<Integer> path = new ArrayList<>();
        int city = destination;
        while (city != -1) {
            path.add(city);
            city = previous[city];
        }
        Collections.reverse(path);

        System.out.print("Shortest path: ");
        for (int i = 0; i < path.size(); i++) {
            System.out.print(path.get(i));
            if (i != path.size() - 1) {
                System.out.print(" -> ");
            }
        }
        System.out.println();

        System.out.println("Total price: " + distances[destination]);
    }

    // This class represents an edge in the graph, storing the source, destination, and cost of the edge
    private static class Edge {
        private int source;
        private int destination;
        private int cost;

        public Edge(int source, int destination, int cost) {
            this.source = source;
            this.destination = destination;
            this.cost = cost;
        }

        public int getSource() {
            return source;
        }

        public int getDestination() {
            return destination;
        }

        public int getCost() {
            return cost;
        }
    }
}

