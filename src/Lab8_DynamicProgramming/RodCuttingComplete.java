package Lab8_DynamicProgramming;
/*
See [CLRS - chapter 15.1]
Given a rod of length n inches and an array of prices that includes prices of selling all pieces of size smaller than n. 
Determine the maximum profit obtainable by cutting up the rod and selling the pieces.

*Example: if length of rod is n=4 and prices={1, 5, 8, 9, 10, 17, 17, 20, 24, 30} the maximum profit is 10 (can be obtained
cutting 2 pieces of length 2).
*Example: if length of rod is n=5 and prices={1, 5, 8, 9, 10, 17, 17, 20, 24, 30} the maximum profit is 13 (can be obtained
cutting a piece of length 2 and a piece of length 3).

There is no correlation between lengths of pieces and their price (the values in array prices are not necessary in increasing
order).

The problem has two requirements versions:
-The Simple result version requires to find out only the value of the maximum profit.
-The Complete result version requires to find also the sequence of cuts that lead to the maximum profit.

You are given the both implementations as recursive algorithms. Apply techniques of Dynamic Programming in order to reduce the
time complexity:
-use memoization
-eliminate recursivity 
*/

/* -----------------THE GIVEN CODE---------------------COMPLETE ROD 
import java.util.ArrayList;
import java.util.List;

public class RodCuttingComplete {

    public static List<Integer> rodCutting(int[] prices, int length) {
        if (length==0) {
            return new ArrayList<Integer>();
        }

        List<Integer> maxPieces=null;
        int maxProfit=Integer.MIN_VALUE;
        for (int i=1; i<=length; i++) {
            List<Integer> pieces=rodCutting(prices, length - i);
            int profit=0;
            for (Integer p:pieces)
                profit=profit+prices[p-1];
            profit=profit+prices[i-1];
            if (profit>maxProfit) {
                maxProfit=profit;
                maxPieces=new ArrayList<Integer>();
                maxPieces.add(i);
                maxPieces.addAll(pieces);
            }
        }

        return maxPieces;
    }
    public static void main(String[] args) {
 
        int[] prices={1, 5, 8, 9, 10, 17, 17, 20, 24, 30};
        int n=5;
 
        Generate input of big size:
        int n=100;
        int [] prices = new int[100]; 
        for (int i=0; i<99; i++)
            prices[i]=i+3;
        
        Improve rodCutting algorithm by Dynamic Programming such that 
        you can use the big size input.
         


         List<Integer>list = rodCutting(prices, n);

         System.out.println("The maximum profit is obtained if cutting pieces of following lengths: " +list );
         int profit=0;
         for (Integer l:list)
             profit=profit+prices[l-1];
         System.out.println("The value of the maximum profit is " + profit );
 
     }
 }*/
 
/*---------------------------THE MODIFIED CODE---------------------- */
/*
The modified code eliminates the recursion used in the given code and uses memoization through the dp array to avoid repeated calculations. 
The time complexity of the modified algorithm is O(n^2), where n is the length of the rod, which is much more efficient than the exponential 
time complexity of the recursive algorithm in the given code. 
*/
import java.util.ArrayList;
import java.util.List;

public class RodCuttingComplete {

    public static List<Integer> rodCutting(int[] prices, int length) {
       
        /*
        We create two arrays: dp and cut. The dp array stores the maximum profit that can be obtained for each rod length, while the cut array
        stores the length of the first cut required to obtain the maximum profit for each rod length. The algorithm iteratively fills the dp 
        and cut arrays for each rod length from 1 to n. 
        */ 
        int[] dp = new int[length + 1];
        int[] cut = new int[length + 1];
        
        /*
        For each rod length, we traverse all possible cuts and calculates the maximum profit that can be obtained by adding the price of the 
        current cut to the maximum profit that can be obtained for the remaining rod length.  
        */
        for (int i = 1; i <= length; i++) {
            int maxProfit = Integer.MIN_VALUE;
            for (int j = 1; j <= i; j++) {
                int profit = prices[j - 1] + dp[i - j];
                /*
                If the calculated profit is greater than the maximum profit obtained so far for the current rod length, we update the maximum 
                profit and the length of the first cut required to obtain it. 
                */
                if (profit > maxProfit) {
                    maxProfit = profit;
                    cut[i] = j;
                }
            }
            dp[i] = maxProfit;
        }

        /*
        Finally, we construct the sequence of cuts that lead to the maximum profit by iterating over the cut array from the end and adding each
        cut length to the result list. The value of the maximum profit is obtained by adding the prices of all cuts in the result list.
        */
        List<Integer> result = new ArrayList<Integer>();
        int len = length;
        while (len > 0) {
            result.add(cut[len]);
            len = len - cut[len];
        }
        return result;
    }
    
    public static void main(String[] args) {
 
        int n=100;
        int [] prices = new int[100];

        for (int i=0; i<99; i++) {
            prices[i]=i+3;
        }

        List<Integer> list = rodCutting(prices, n);

        System.out.println("The maximum profit is obtained if cutting pieces of following lengths: " + list);
        int profit = 0;
        for (Integer l : list)
            profit = profit + prices[l - 1];
        System.out.println("The value of the maximum profit is " + profit);


        list = rodCutting(prices, n);

        System.out.println("The maximum profit is obtained if cutting pieces of following lengths: " + list);
        profit = 0;
        for (Integer l : list)
            profit = profit + prices[l - 1];
        System.out.println("The value of the maximum profit is " + profit);
    }
}