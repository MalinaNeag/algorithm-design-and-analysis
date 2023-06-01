class Node:
    def __init__(self, key):
        self.left = None
        self.right = None
        self.parent = None
        self.key = key

class BST:
    def __init__(self):

        self.root = None
    
    def insert(self, key):
        if self.root is None:
            # if the tree is empty, set the root to the new node
            self.root = Node(key)
        else:
            # otherwise, call the recursive insert function
            self._insert(key, self.root)
    
    def _insert(self, key, node):
        if key < node.key:
            # if the key is less than the current node's key, 
            # recursively insert it into the left subtree
            if node.left is None:
                # if the left child is None, create a new node
                # and set it as the left child of the current node
                node.left = Node(key)
                node.left.parent = node
            else:
                # otherwise, continue recursion down the left subtree
                self._insert(key, node.left)
        else:
            # if the key is greater than or equal to the current node's key, 
            # recursively insert it into the right subtree
            if node.right is None:
                # if the right child is None, create a new node
                # and set it as the right child of the current node
                node.right = Node(key)
                node.right.parent = node
            else:
                # otherwise, continue recursion down the right subtree
                self._insert(key, node.right)
    
    """
    The printPathFromTo function takes two node objects as input and prints the path from the first 
    node to the second node. It first finds the lowest common ancestor (LCA) of the two nodes using
    the findLowestCommonAncestor function. It then builds the path from the second node to the LCA 
    and prints the path from the root node to the LCA using the printPathFromRoot function. Finally,
    it prints the path from the LCA to the second node in reverse order.
    """
    def printPathFromTo(self, node1, node2):
       
        if node1 is None or node2 is None:
            return
        
        # finds the lowest common ancestor (LCA) of the two nodes
        lca = self.findLowestCommonAncestor(self.root, node1, node2)
        if lca is None:
            return
        
        # builds the path from the second node to the LCA
        path2 = []
        crt = node2
        while crt != lca:
            # iterate from node2 to lca, adding each node to the path2 list
            path2.append(crt)
            crt = crt.parent
        
        self.printPathFromRoot(lca, node1)
        
        while path2:
            # prints the path from the LCA to the second node in reverse order
            print(path2.pop().key, end=' ')
        print()
    
    """
    The findLowestCommonAncestor function takes three node objects as input: the current node, 
    and the two nodes whose LCA we are looking for. It recursively searches the left and right 
    subtrees of the current node to find the LCA. If both nodes are found in different subtrees, 
    the current node is the LCA. If one of the nodes is found in the current node's subtree, the
    search continues in that subtree.
    """
    def findLowestCommonAncestor(self, node, node1, node2):
        if node is None or node == node1 or node == node2:
            return node
        leftLCA = self.findLowestCommonAncestor(node.left, node1, node2)
        rightLCA = self.findLowestCommonAncestor(node.right, node1, node2)
        if leftLCA and rightLCA:
            # if both left and right subtrees contain node1 and node2,
            # return the current node as the lowest common ancestor
            return node
        return leftLCA if leftLCA else rightLCA
    

    """
    The printPathFromRoot function prints the path from the root node to a given node. 
    It recursively searches the left and right subtrees of the root node to find the given node. 
    Once the node is found, the function prints the path from the root to the node by recursively
    printing the keys of the nodes on the path from the root to the given node.
    """
    def printPathFromRoot(self, root, node):
        if root is None:
            return False
        if root == node:
            print(root.key, end=' ')
            return True
        foundInLeft = self.printPathFromRoot(root.left, node)
        foundInRight = self.printPathFromRoot(root.right, node)
        if foundInLeft or foundInRight:
            print(root.key, end=' ')
            return True
        return False

if __name__ == '__main__':
    bst = BST()
    bst.insert(8)
    bst.insert(2)
    bst.insert(1)
    bst.insert(5)
    bst.insert(4)
    bst.insert(7)
    bst.insert(3)
    bst.insert(15)
    bst.insert(10)
    bst.insert(20)
    bst.insert(18)
    bst.insert(22)

   
    node1 = bst.root.right.left
    node2 = bst.root.right.right.left

    print("Path from", node1.key, "to", node2.key, ":")
    bst.printPathFromTo(node1, node2)
