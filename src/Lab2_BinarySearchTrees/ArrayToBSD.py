class Node:
    def __init__(self, data):
        self.data = data
        self.left = None
        self.right = None

""""
The approach used to construct the balanced BST is to start by selecting the middle element of the sorted array,
which will become the root of the BST. This guarantees that the tree will be balanced since the left subtree will
contain elements less than the root, and the right subtree will contain elements greater than the root.

The function then recursively creates the left and right subtrees of the root by passing the left and right halves
of the array, respectively, to the arrayToBalancedBSD function. This process continues until there are no more elements
in the array, at which point the function returns None.
"""
# Creates a balanced BST from a sorted array
def arrayToBalancedBSD(array):
    if not array:
        return None
    mid = len(array) // 2 
    root = Node(array[mid]) # the root is equal to the middle element of the array 
    root.left = arrayToBalancedBSD(array[:mid])
    root.right = arrayToBalancedBSD(array[mid+1:])
    return root

# Inorder walk
def print_inorder(node):
    if node:
        print_inorder(node.left)
        print(node.data, end=" ")
        print_inorder(node.right)


def main():
    array = [1, 2, 3, 4, 5, 6, 7, 8, 9]
    root = arrayToBalancedBSD(array)
    print_inorder(root)

if __name__ == "__main__":
    main()
