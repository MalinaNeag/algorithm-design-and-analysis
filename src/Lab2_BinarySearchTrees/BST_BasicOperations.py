class Node:
    def __init__(self, data):
        self.left = None
        self.right = None
        self.data = data


# Insert nodes        
def insertNode(root, data):
    if not root:
        return Node(data)
    if data < root.data:
        root.left = insertNode(root.left, data)
    else:
        root.right = insertNode(root.right, data)
    return root

# Inorder walk
def print_inorder(node):
    if node:
        print_inorder(node.left)
        print(node.data, end=" ")
        print_inorder(node.right)

# Preorder walk
def print_preorder(node):
    if node:
        print(node.data, end=" ")
        print_preorder(node.left)
        print_preorder(node.right)

# Postorder walk
def print_postorder(node):
    if node:
        print_postorder(node.left)
        print_postorder(node.right)
        print(node.data, end=" ")

# Print leaf nodes
def print_leaf(node):
    if node:
        if node.left is None and node.right is None:
            print(node.data)
        print_leaf(node.left)
        print_leaf(node.right)

def main():

    root = None
    values = [1, 2, 3, 4, 5, 7, 8, 10, 15, 18, 20, 22]
    
    for value in values:
        root = insertNode(root, value)
    print("Inorder traversal: ")
    print_inorder(root)

  
    print("\n")
    
    print("Preorder traversal: ")
    print_preorder(root)
    print("\n")
    
    print("Postorder traversal: ")
    print_postorder(root)
    print("\n")
    
    print("Leaf nodes: ")
    print_leaf(root)

if __name__ == '__main__':
    main()
