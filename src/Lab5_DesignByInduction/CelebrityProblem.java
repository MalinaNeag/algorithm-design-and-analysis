package Lab5_DesignByInduction;
import java.util.Set;
import java.util.HashSet;

/*
Implement the solution for the algorithm: 
-The celebrity problem [Manber, chapter 5.5]
*/

public class CelebrityProblem {

    public static int findCelebrity(boolean[][] know) {
        
        int n = know.length;
        Set<Integer> candidates = new HashSet<>();
        
        for (int i = 0; i < n; i++) {
            candidates.add(i);
        }
        
        while (candidates.size() > 1) {
            int i = candidates.iterator().next();
            candidates.remove(i);
            int j = candidates.iterator().next();
            candidates.remove(j);
            if (know[i][j]) {
                candidates.add(j);
            } else {
                candidates.add(i);
            }
        }
        
        int celebrity = candidates.iterator().next();
        for (int i = 0; i < n; i++) {
            if (i != celebrity && (know[celebrity][i] || !know[i][celebrity])) {
                return -1; // no celebrity found
            }
        }
        return celebrity;
    }

    public static void main(String[] args) {

        boolean[][] know = {{false, true, false}, {false, false, false}, {true, true, false}};
        int celebrity = findCelebrity(know);

        if (celebrity == -1) {
            System.out.println("No celebrity found");
        } else {
            System.out.println("Celebrity found: " + celebrity);
        }
    }

}
