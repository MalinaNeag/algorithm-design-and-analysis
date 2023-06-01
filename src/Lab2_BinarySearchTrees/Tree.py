class Node:
    def __init__(self, data):
        self.left = None
        self.right = None
        self.data = data


# Example of a Binary Search Tree, hardcoded - example taken from from lab materials
root = Node(8)
root.left = Node(2)
root.left.left = Node(1)
root.left.right = Node(5)
root.left.right.left = Node(4)
root.left.right.right = Node(7)
root.left.right.right.right = Node(3)
root.right = Node(15)
root.right.left = Node(10)
root.right.right = Node(20)
root.right.right.left = Node(18)
root.right.right.right = Node(22)


# Check if the tree is perfectly balanced - O(n) - the number of nodes in its left and right subtrees differ by at most one
def is_perfectly_balanced(root) -> bool:
  
    def count_nodes(node) -> int:  # Returns the number of nodes in the subtree rooted at the given node.
        if node is None:
            return 0
        return 1 + count_nodes(node.left) + count_nodes(node.right)

    def is_balanced_helper(node) -> bool:
        if node is None:
            return True
        left_count = count_nodes(node.left)
        right_count = count_nodes(node.right)
        if abs(left_count - right_count) > 1:
            return False
        return is_balanced_helper(node.left) and is_balanced_helper(node.right)

    return is_balanced_helper(root)


# Search closest - O(h), h-height - returns the node containing the key with the value closest to k
def search_closest(root, k: int) -> Node:
   
    def find_closest_helper(node, closest) -> Node:
        
        if node is None:
            return closest
        #If the node is equal with the key we can stop
        if node.data == k:
            return node
        # If the current node is closer to k than the closest node, the closest node is updated
        if abs(node.data - k) < abs(closest.data - k): 
            closest = node
        if node.data > k:
            return find_closest_helper(node.left, closest)
        else:
            return find_closest_helper(node.right, closest)

    return find_closest_helper(root, root)


# Check if exist 2 nodes with sum s - O(N)
"""""
node_set: a set of integers that stores the values of nodes that have been visited so far
The function recursively traverses the BST in an in-order walk, starting from the leftmost
node, and checks whether the difference between the target sum s and the value of the current
node node.data is in the set node_set. If the difference is found, the function returns True.
Otherwise, the value of the current node is added to the set node_set, and the function continues
to search the right subtree of the current node.
"""
def check_exist_two_nodes_with_sum(root: Node, s: int) -> bool:
    
    def search_helper(node: Node, s: int, node_set: set) -> bool: 

        if node is None:
            return False
        if search_helper(node.left, s, node_set):
            return True
        if s - node.data in node_set:
            return True
        node_set.add(node.data)
        return search_helper(node.right, s, node_set)

    node_set = set()
    return search_helper(root, s, node_set)


# Print paths that have the sum of the nodes equal to s
def print_paths_with_sum(root, s):
    def print_paths(node, path, target_sum):

        if node is None:
            return

        # Add current node to the path
        path.append(node.data)

        # Check if current path from root to this node equals target_sum
        current_sum = sum(path)
        if current_sum == target_sum:
            print(path)

        # Recursively print paths for left and right subtrees
        print_paths(node.left, path, target_sum)
        print_paths(node.right, path, target_sum)

        # Remove current node from the path
        path.pop()

    print_paths(root, [], s)


# Print the levels - the keys in the tree in level order (in order of their distance from the root, with nodes on each level in order from left to right).
def print_levels(root):
    # Get the height of the tree
    height = get_height(root)

    # Loop through each level and print the keys of nodes at that level
    for level in range(height):
        print_level(root, level)
        print()

def get_height(node):
    """Helper function to get the height of the tree."""
    if node is None:
        return 0
    else:
        # Compute the height of the tree recursively
        left_height = get_height(node.left)
        right_height = get_height(node.right)
        return max(left_height, right_height) + 1

def print_level(node, level):
    """Helper function to print the keys of nodes at a given level."""
    if node is None:
        return
    elif level == 0:
        # If we've reached the desired level, print the key of the current node
        print(node.data, end=' ')
    else:
        # Recursively print nodes at the next level
        print_level(node.left, level-1)
        print_level(node.right, level-1)


    
def main():

    # Perfectly balanced or not
    print("\n")
    if is_perfectly_balanced(root):
        print("The tree is perfectly balanced")
    else:
        print("The tree is not perfectly balanced")
    is_perfectly_balanced(root)
    print("\n")

    # Search closest - returns the node containing the key with the value closest to k
    k=16
    closest_node = search_closest(root, k)
    print(f"The node closest to {k} is: {closest_node.data}")
    print("\n")

    # Check if exist 2 nodes with sum s
    s = 7
    if check_exist_two_nodes_with_sum(root, s):
        print(f"There exist two nodes in the BST whose sum is equal to {s}")
    else:
        print(f"There do not exist two nodes in the BST whose sum is equal to {s}")
    print("\n")

    # Print the paths that have the sum of the nodes equal to 22
    s = 10
    print(f"Paths that have the sum equal to {s}:")
    print_paths_with_sum(root, s)
    print("\n")

    # Print the keys in the tree in level order
    print("Print the keys in the tree in level order:")
    print_levels(root)
    print("\n")


if __name__ == '__main__':
    main()


