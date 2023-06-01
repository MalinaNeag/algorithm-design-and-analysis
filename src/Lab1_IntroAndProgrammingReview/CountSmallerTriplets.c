/* REQUIREMENT
Write an efficient program that reads the elements of the array from a text file and uses the function to count triplets
for the value of V. The format of the input files is the following: the first line contains the number of integers, and
the next lines contain the integers, one on each line.Implement the operation CountSmallerTriplets that, given an array of
distinct integers and a value V, finds how many triplets of elements from this array have the sum less than V. Measure and
display the runtime of the count function. Do not include the time for reading the file in the measurement.
*/

/***********************************************************************************************************************
The brute-force implementation of the CountSmallerTriplets problem has a time complexity of O(n^3), where n is the length of 
the input array, because we need to iterate through all possible triplets of elements. This approach becomes impractical for 
large input sizes, that is why I implemented the following method: the input array is sorted in ascending order, and then two 
pointers are used to iterate through the array and count the number of triplets whose sum is less than V. This approach 
takes O(n^2) time, since we perform two nested loops to iterate through all possible pairs of elements, and then use two
pointers to iterate through the remaining elements to find the third element that completes the triplet.
*/

#include <stdio.h>
#include <stdlib.h>
#include <time.h> 


int compare(const void *a, const void *b) { // The compare function used by qsort
    return (*(int *)a - *(int *)b);
}

int CountSmallerTriplets(int arr[], int n, int V) {
    int count = 0;
    for (int i = 0; i < n - 2; i++) { // Parse the array: each element as the first element of the triplet
        int j = i + 1; // j-pointer to the element after the current i
        int k = n - 1; // k-pointer to the last element of the array
        while (j < k) { // Iterate until the two pointers meet in the middle
            if (arr[i] + arr[j] + arr[k] < V) { // Check whether the sum of the three elements is less than V
                count += k - j; // If so, increment the counter by the number of valid triplets that can be formed with j as the middle element
                j++; // Move j to the next element
            }
            else {
                k--; // If the sum is not less than V , move k to the previous element
            }
        }
    }
    return count; 
}


int main() {
    
    int n, V;
   
    printf("Please, choose V: ");
    scanf("%d", &V);

    FILE *file = fopen("4Kint_1.txt", "r"); // Read the size of the array
    fscanf(file, "%d", &n); 
    
    int arr[n]; 
    for (int i = 0; i < n; i++) { // Read the elements of the array
        fscanf(file, "%d", &arr[i]);
    }

    qsort(arr, n, sizeof(int), compare);

    clock_t start = clock();
    CountSmallerTriplets(arr, n, V);
    clock_t end = clock();
    double time = ((double)(end - start)) / CLOCKS_PER_SEC;
    
    printf("CountSmallerTriplets took %f seconds\n", time);

    /*
    For V=0 and the smallest input file (4Kint_1.txt), I obtained aprox. 0.03 seconds
    For V=0 and the largest input file (50Kint_1.txt), I obtained aprox. 5 seconds
    */

    fclose(file);
    
    return 0;
}




