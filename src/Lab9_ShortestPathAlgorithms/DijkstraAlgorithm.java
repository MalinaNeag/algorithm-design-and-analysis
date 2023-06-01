package Lab9_ShortestPathAlgorithms;/*
The ticket prices for traveling by bus on direct connections between pairs of cities are known. Implement a travel planner for planning a 
journey from a source city to a destination city, using buses, such that the total cost of the journey is minimum.
The program reads the graph of cities and direct connections from a text file with following format:
- first line contains N, the number of cities
- second line contains M, the number of direct connections
- the next M lines contain triplets of the form city1 city2 price, where city1 and city2 are integer numbers in the range 0 to N-1 and price
is an integer number
Read from standard input the numbers identifying the source and the destination cities.
Print the cheapest travel route (sequence of cities) from source to destination and its total price. 
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class DijkstraAlgorithm {
    private int numCities;
    private int numConnections;
    private List<List<Edge>> graph;
    private int[] distances;
    private int[] previous;

    public static void main(String[] args) {
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm();
        dijkstra.readGraphFromFile("positive_weights_5_15.txt"); 

        // Read the source and destination from STDIN
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the source city: ");
        int source = scanner.nextInt();
        System.out.print("Enter the destination city: ");
        int destination = scanner.nextInt();

        dijkstra.calculateShortestPaths(source);
        dijkstra.printShortestPath(destination);

        scanner.close();
    }

    public void readGraphFromFile(String fileName) {
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);

            // Read the number of cities and direct connections
            numCities = scanner.nextInt();
            numConnections = scanner.nextInt();

            // Initialize the graph as a list of adjacency lists
            graph = new ArrayList<>(numCities);
            for (int i = 0; i < numCities; i++) {
                graph.add(new ArrayList<>());
            }

            // Read the connections and their prices
            for (int i = 0; i < numConnections; i++) {
                int city1 = scanner.nextInt();
                int city2 = scanner.nextInt();
                int price = scanner.nextInt();
                graph.get(city1).add(new Edge(city2, price));
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + fileName);
        }
    }

    public void calculateShortestPaths(int source) {
        distances = new int[numCities];
        previous = new int[numCities];
        Arrays.fill(distances, Integer.MAX_VALUE);
        Arrays.fill(previous, -1);

        PriorityQueue<Edge> minHeap = new PriorityQueue<>();
        distances[source] = 0;
        minHeap.offer(new Edge(source, 0));

        while (!minHeap.isEmpty()) {
            Edge current = minHeap.poll();
            int currentCity = current.getDestination();

            if (current.getWeight() > distances[currentCity]) {
                continue; // Skip if a shorter path to currentCity has been found
            }

            // Explore all neighbors of the current city
            for (Edge neighbor : graph.get(currentCity)) {
                int newDistance = distances[currentCity] + neighbor.getWeight();

                 // If a shorter path to the neighbor is found, update the distance and previous city
                if (newDistance < distances[neighbor.getDestination()]) {
                    distances[neighbor.getDestination()] = newDistance;
                    previous[neighbor.getDestination()] = currentCity;
                    minHeap.offer(new Edge(neighbor.getDestination(), newDistance));
                }
            }
        }
    }

    public void printShortestPath(int destination) {
        if (previous[destination] == -1) {
            System.out.println("No path exists from source to destination.");
            return;
        }

        // Reconstruct the shortest path from the destination to the source
        List<Integer> path = new ArrayList<>();
        int city = destination;
        while (city != -1) {
            path.add(city);
            city = previous[city];
        }
        Collections.reverse(path);

        // Print the shortest path and its total price
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

    
    // This class represents a directed edge in the graph, storing the destination city and weight (price) of the edge. 
    private static class Edge implements Comparable<Edge> {
        private int destination;
        private int weight;

        public Edge(int destination, int weight) {
            this.destination = destination;
            this.weight = weight;
        }

        public int getDestination() {
            return destination;
        }

        public int getWeight() {
            return weight;
        }

        @Override
        public int compareTo(Edge other) {
            return Integer.compare(weight, other.weight);
        }
    }
}
