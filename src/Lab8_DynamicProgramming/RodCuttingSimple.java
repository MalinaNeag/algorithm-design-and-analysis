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


/* -----------------THE GIVEN CODE----------------------------------------------------------------------------------SIMPLE ROD 
import java.util.ArrayList;
import java.util.List;

public class RodCuttingSimple {

    public static int rodCutting(int[] prices, int length) {
        if (length==0) {
            return 0;
        }

        int maxProfit=Integer.MIN_VALUE;
        for (int i=1; i<=length; i++) {
            maxProfit = Math.max(maxProfit, prices[i-1] + rodCutting(prices, length - i));
        }

        return maxProfit;
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
         

         int maxProfit = rodCutting(prices, n);

         System.out.println("The maximum profit from cutting a rod of length " + n + " is: " + maxProfit);
     }
 }
 --------------------------------------------THE MODIFIED CODE------------------------------------------------------------------*/

/*
To apply Dynamic Programming, we can use memoization technique to store the results of already calculated subproblems and use them to avoid
redundant calculations. Also, we can eliminate recursion by using a bottom-up approach to calculate the solution iteratively. 
*/
import java.util.HashMap;
import java.util.Map;

public class RodCuttingSimple {

    
    public static int rodCutting(int[] prices, int length) {
        // The rodCutting method now takes an additional argument memo which is a map to store the results of already calculated subproblems. 
        Map<Integer, Integer> memo = new HashMap<>();
        // The method starts with an empty memo containing only the base case (length=0, profit=0)
        memo.put(0, 0);

        // It then iterates from length=1 to the required length and for each length, it calculates the maximum profit that can be obtained by 
        // cutting the rod into pieces of different lengths.
        for (int i = 1; i <= length; i++) {
            int maxProfit = Integer.MIN_VALUE;
            for (int j = 1; j <= i; j++) {
                int profit = prices[j - 1] + memo.get(i - j);
                maxProfit = Math.max(maxProfit, profit);
            }
            // We store the result in the memo to avoid redundant calculations. 
            memo.put(i, maxProfit);
        }

        return memo.get(length);
    }

    public static void main(String[] args) {
     
        int n=100;
        int [] prices = new int[100];

        for (int i=0; i<99; i++) {
            prices[i]=i+3;
        }

        int maxProfit = rodCutting(prices, n);

        System.out.println("The maximum profit from cutting a rod of length " + n + " is: " + maxProfit);
    }
}