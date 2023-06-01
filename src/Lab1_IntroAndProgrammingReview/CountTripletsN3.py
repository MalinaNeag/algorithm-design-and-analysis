'''
Space Complexity
We are not using any extra data structure to store any intermediate results, and hence the space complexity is O(1).
Time Complexity
We are using 3 loops so the time complexity is O(N3)
'''


class CountTripletsN3:
    def __init__(self, data_path):
        with open(data_path, "r") as txt_file:
            self.input_data = [int(line.strip()) for line in txt_file]

    def countTriplets(self, sum):
        arr = self.input_data
        counter = 0
        for a in range(len(arr)-2):
            for b in range(a+1, len(arr)-1):
                for c in range(b+1, len(arr)):
                    if arr[a] + arr[b] + arr[c] < sum:
                        counter += 1
        return counter