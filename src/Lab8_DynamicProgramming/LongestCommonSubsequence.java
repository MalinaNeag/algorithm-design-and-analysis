package Lab8_DynamicProgramming;
/*
The given code is an implementation of the Longest Common Subsequence (LCS) problem using dynamic programming. The LCS problem is defined as 
finding the longest subsequence that is common between two given strings.

The LCS method takes two input strings W1 and W2 and their lengths x and y respectively. It creates an LCS_matrix of size (x+1) x (y+1) to store
the lengths of LCS for each substring. It then fills up the LCS_matrix using nested for-loops that traverse through the lengths of both strings.

The if block inside the nested loop initializes the first row and column of the LCS_matrix to 0, because an empty string has no common subsequence
with any other string.

The else block first checks if the characters at the current positions in both strings match. If they match, the length of the LCS for this substring
is incremented by 1, which is stored in the LCS_matrix. If they don't match, the length of LCS for this substring is set to the maximum length of 
LCS of the two substrings with one character removed from each string.

After the LCS_matrix is filled, the method calculates the length of LCS from the bottom-right corner of the LCS_matrix and stores it in LCS_len. 
It then creates a character array LCS_seq to store the actual LCS subsequence characters.

The while loop traverses the LCS_matrix from the bottom-right corner to the top-left corner to find the actual LCS subsequence. It checks the value
of the LCS_matrix at each position and moves the traversal pointer accordingly to keep track of the actual LCS subsequence characters.

Finally, the method prints the original two strings and the LCS subsequence, as well as the length of the LCS subsequence.
*/


class LongestCommonSubsequence {
  
    static void LCS(String W1, String W2, int x, int y) {
      int[][] LCS_matrix = new int[x + 1][y + 1];
    
      for (int i = 0; i <= x; i++) {
        for (int j = 0; j <= y; j++) {
          if (i == 0 || j == 0) {
            LCS_matrix[i][j] = 0;
          } else {
            if (W1.charAt(i - 1) == W2.charAt(j - 1)) {
              LCS_matrix[i][j] = LCS_matrix[i - 1][j - 1] + 1;
            } else {
              LCS_matrix[i][j] = Math.max(LCS_matrix[i - 1][j], LCS_matrix[i][j - 1]);
            }
          }
        }
      }
    
      int LCS_len = LCS_matrix[x][y];
      char[] LCS_seq = new char[LCS_len + 1];
      LCS_seq[LCS_len] = '\0';
      int index = 0;
    
      int i = x, j = y;
      while (i >= 1 && j >= 1) {
        int v = LCS_matrix[i][j];
    
        while (i > 1 && LCS_matrix[i - 1][j] == v) {
          i--;
        }
        while (j > 1 && LCS_matrix[i][j - 1] == v) {
          j--;
        }
    
        if (v > 0) {
          LCS_seq[LCS_len - index - 1] = W1.charAt(i - 1);
        }
    
        index++;
        i--;
        j--;
      }
    
      System.out.print("First string : " + W1 + "\nSecond string : " + W2 + "\nLCS subsequence: ");
      for (int k = 0; k <= index; k++) {
        System.out.print(LCS_seq[k]);
      }
       
      System.out.println("\nLCS subsequence length: " + LCS_matrix[x][y]);
    }
    
    public static void main(String[] args) {
      String W1 = "GXTXAYB";
      String W2 = "AGGTAB";
    
      int x = W1.length();
      int y = W2.length();
    
      LCS(W1, W2, x, y);
    }
  }
  