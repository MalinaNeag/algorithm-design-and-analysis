package Lab5_DesignByInduction;
import java.util.*;

/*
Implement the solution for the algorithm: 
-The skyline problem [Manber, chapter 5.6]
*/

class Building {
    int left;
    int right;
    int height;
    
    public Building(int left, int right, int height) {
        this.left = left;
        this.right = right;
        this.height = height;
    }
}

class Skyline {
    
    List<int[]> points;
    
    public Skyline() {
        this.points = new ArrayList<>();
    }
    
    public Skyline(List<int[]> points) {
        this.points = points;
    }
    
    public void append(int x, int y) {
        if (this.points.isEmpty()) {
            this.points.add(new int[]{x, y});
            return;
        }
        int[] lastPoint = this.points.get(this.points.size() - 1);
        if (lastPoint[1] == y) {
            return;
        }
        if (lastPoint[0] == x) {
            lastPoint[1] = Math.max(lastPoint[1], y);
        } else {
            this.points.add(new int[]{x, y});
        }
    }
    
    public Skyline merge(Skyline other) {
        
        List<int[]> mergedPoints = new ArrayList<>();
        
        int i = 0, j = 0;
        int h1 = 0, h2 = 0;
        
        while (i < this.points.size() && j < other.points.size()) {
            int[] p1 = this.points.get(i);
            int[] p2 = other.points.get(j);
            int x = 0, y = 0;
            if (p1[0] < p2[0]) {
                x = p1[0];
                h1 = p1[1];
                y = Math.max(h1, h2);
                i++;
            } else if (p1[0] > p2[0]) {
                x = p2[0];
                h2 = p2[1];
                y = Math.max(h1, h2);
                j++;
            } else {
                x = p1[0];
                h1 = p1[1];
                h2 = p2[1];
                y = Math.max(h1, h2);
                i++;
                j++;
            }
            mergedPoints.add(new int[]{x, y});
        }
        while (i < this.points.size()) {
            mergedPoints.add(this.points.get(i));
            i++;
        }
        while (j < other.points.size()) {
            mergedPoints.add(other.points.get(j));
            j++;
        }
        return new Skyline(mergedPoints);
    }
}

public class SkylineProblem {
    
    public static Skyline skyline(List<Building> buildings, int left, int right) {
        
        if (left == right) {
            Skyline skyline = new Skyline();
            Building b = buildings.get(left);
            skyline.append(b.left, b.height);
            skyline.append(b.right, 0);
            return skyline;
        }
        
        int middle = left + (right - left) / 2;
        Skyline skyline1 = skyline(buildings, left, middle);
        Skyline skyline2 = skyline(buildings, middle + 1, right);
        return skyline1.merge(skyline2);
    }
    
    public static void main(String[] args) {
        
        List<Building> buildings = new ArrayList<>();
        
        buildings.add(new Building(1, 3, 4));
        buildings.add(new Building(2, 4, 2));
        buildings.add(new Building(5, 6, 3));
        buildings.add(new Building(7, 8, 4));
        buildings.add(new Building(9, 10, 2));
        
        Skyline skyline = skyline(buildings, 0, buildings.size() - 1);
        
        for (int[] point : skyline.points) {
            System.out.println("(" + point[0] + ", " + point[1] + ")");
        }
    }
}    