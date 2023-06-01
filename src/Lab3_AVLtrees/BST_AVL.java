/*
Implement the insertion into a balanced AVL tree, and the insertion into a simple BST (if you haven't done it yet for the last homework).
For the 2 different implementations from above, draw a table comparing:
-  the insertion time of the nodes (Insert N=10000 random keys vs Insert N=10000 keys in increasing order)
- search time (search for a node)
*/

import java.util.*;


// The structure used for trees
class Node{    
      
    public int key, h;

    public Node left, right, p;

    public Node(int key) {

        this.key = key;
        left = right = p = null;
        h = 0;
    }

}


// BST tree
class BST {

    private Node root; 

    public BST() {
        root = null;
    }

    // BST insertion
    /*
    // The basic idea is to traverse the tree from the root node to the leaf node, and then insert the new node as the child 
    of the leaf node. During the traversal, we compare the key of the new node with the key of the current node, and if it's 
    greater, we go to the right subtree, otherwise, we go to the left subtree. Once we find a null child, we insert the new node there.
    */
    public void insert(int k) {
        
        Node x, y;
        Node z = new Node(k); // create a new node with the given key
        y = null;
        x = root;

        // Traverse down the tree to find the position to insert the new node
        while (x != null) {
            y = x;
            if (z.key > x.key){
                x = x.right; // go to the right subtree
            }
            else{
                x = x.left; // go to the left subtree
            }
        }

        // Set the parent of the new node to the last node visited during traversal
        z.p = y;

        // If the tree is empty, set the root to the new node
        if (y == null){
            root = z;
        }
        
        else{
            // If the key of the new node is greater than the parent node's key, insert it as the right child
            if (z.key > y.key){
                y.right = z;
            }
            // Otherwise, insert it as the left child
            else{
                y.left = z;
            }   
        }

    }


    // boolean searchNode - returns true if a node with the key value k is found, false otherwise
    public boolean searchNode(int k){
        
        Node node = searchNode(k, root);
        if(node == null)   return false;
        return ((node.key != 0) ? true:false);

    }

    // Node searchNode - recursively searches for a node with the key value k starting at the node passed in as the second parameter
    public Node searchNode(int k, Node node){    

        // If the current node is null or has the key value k, return the current node
        if( (node == null) || (node.key == k) ){
            return node;
        }
        // If the current node's key value is greater than k, search the left subtree
        else{
            if(node.key > k){
                return searchNode(k, node.left);
            } 
            // If the current node's key value is less than k, search the right subtree
            else{
                return searchNode(k, node.right);
            }
        }
    }
}


// AVL tree
class AVL{

    private Node root; 

    // Constructor to initialize an empty AVL tree
    public AVL() {
        root = null;
    }

    public void insert(int k){
        Node node = new Node(k);
        root = insert(root, node);
    }

    // AVL insertion
    public Node insert(Node x, Node z){
        
        // Base case: if x is null, the new node becomes the root of the subtree
        if(x == null){
            z.h = 0; // set height of the new node to 0
            return z;
        }

        // If the new node's key is less than or equal to the current node's key, insert the node in the LEFT subtree
        if (z.key <= x.key) {
            Node y = insert(x.left, z);
            x.left = y; // update left child of x with the new node
            y.p = x; // update parent of the new node
            x.h = y.h + 1; // update height of x
        }

        // Otherwise, recursively insert the node in the right subtree
        else{
            Node y = insert(x.right, z);
            x.right = y;
            y.p = x;
            x.h = y.h + 1;
        }

        // Rebalance the tree if necessary
        x = balancingAVL(x);

        // Update the height of x
        x.h = Math.max(getHeight(x.left), getHeight(x.right)) + 1;

        return x;
    }

    public int getHeight(Node node){
        return (node == null) ? -1 : node.h;
    }


    public Node rotateLeft(Node x){

        Node y = x.right; // Set y as the right child of x.
        Node T = y.left;  // Set T as the left child of y.
        
        // Make y the new root, x its left child and T the right child of x.
        y.left = x;
        x.right = T;

        // Update the heights of the rotated nodes
        x.h = Math.max( getHeight(x.left), getHeight(x.right) ) + 1;
        y.h = Math.max( getHeight(y.left), getHeight(y.right) ) + 1;
        
        return y; // Return the new root of the rotated subtree
    }

    public Node rotateRight(Node x){

        Node y = x.left; // Set y as the left child of x.
        Node T = y.right; // Set T as the right child of y.

        // Make y the new root, x its right child and T the left child of x
        y.right = x;
        x.left = T;

        // Update the heights of the rotated nodes
        x.h = Math.max( getHeight(x.left), getHeight(x.right) ) + 1;
        y.h = Math.max( getHeight(y.left), getHeight(y.right) ) + 1;
        
        return y; // Return the new root of the rotated subtree

    }

    // LR rotation => right rotation first, left rotation after
    public Node rotateLeftRight(Node x){
        x.right = rotateRight(x.right); 
        return rotateLeft(x); 
    }

    // RL rotation => left rotation first, right rotation after
    public Node rotateRightLeft(Node x){
        x.left = rotateLeft(x.left); 
        return rotateRight(x); 
    }

    /* 
    Balance the AVL tree by rotate the nodes based on their height difference.
    It takes a node x as input and returns the root of the balanced subtree.
    */
    public Node balancingAVL(Node x){

        int heightLeft = getHeight(x.left);
        int heightRight = getHeight(x.right);

        // If the height difference is within the allowed range => return the node as it is
        if( Math.abs(heightLeft - heightRight) <= 1 ){
            return x;
        }

    
        else{
             // If the height difference > 1 => rotations to balance the tree
            if (heightLeft > heightRight){

                // If the left subtree is taller => get the left child node of x
                Node y = x.left;

                // Check if the height of the left child's left subtree is < than the height of its right subtree
                if ( getHeight(y.left) < getHeight(y.right))  return rotateRightLeft(x);

                // If not => right rotation on x 
                else return rotateRight(x);

            }

            else{

                // If the right subtree is taller => get the right child node of x
                Node y = x.right;

                // Check if the height of the right child's left subtree is > than the height of its right subtree
                if ( getHeight(y.left) > getHeight(y.right))  return rotateLeftRight(x);
                
                // If not left => rotation on x 
                else return rotateLeft(x);

            }

        }

    }

    // The functions are the same like the search functions for BST
    public boolean searchNode(int k){
        Node node = searchNode(k, root);
        if(node == null)   return false;
        return ((node.key != 0) ? true:false);
    }

    public Node searchNode(int k, Node node){    

        if( (node == null) || (node.key == k) ){
            return node;
        }

        else{
            if(node.key > k){
                return searchNode(k, node.left);
            }
            else return searchNode(k, node.right);
        }

    }

}


class BST_AVL{

    public static void main(String[]args){

        BST bstRandom = new BST();
        BST bstOrdered = new BST();
        AVL avlRandom = new AVL();
        AVL avlOrdered = new AVL();

        ArrayList<Integer> arrRandom = new ArrayList<>(10001);
        HashSet<Integer> arrOrdered = new HashSet<>(10001);
        
        generateArrays(arrRandom); 

        //BST:
        System.out.println("--------------------\n");
        System.out.println("BST - random keys\n");
        System.out.println("--------------------\n");
        System.out.println("Insertion and searching time for 10000 random keys: \n");
        timeForInsertionBST(arrRandom, bstRandom);
        timeForSearchingBST(bstRandom);
        System.out.println();


        //AVL:
        System.out.println("--------------------\n");
        System.out.println("AVL - random keys\n");
        System.out.println("--------------------\n");
        System.out.println("Insertion and searching time for 10000 random keys: \n");
        timeForInsertionAVL(arrRandom, avlRandom);
        timeForSearchingAVL(avlRandom);
        System.out.println();
        
        generateArrays(arrOrdered);

        //BST ordered keys:
        System.out.println("--------------------\n");
        System.out.println("BST - ordered keys\n");
        System.out.println("--------------------\n");
        System.out.println("Insertion and searching times for 10000 ordered keys: \n");
        timeForInsertionBST(arrOrdered, bstOrdered);
        timeForSearchingBST(bstOrdered);
        System.out.println();


        //AVL ordered keys:
        System.out.println("--------------------\n");
        System.out.println("AVL - ordered keys\n");
        System.out.println("--------------------\n");
        System.out.println("Insertion and searching times for 10000 ordered keys: \n");
        timeForInsertionAVL(arrOrdered, avlOrdered);
        timeForSearchingAVL(avlOrdered);
        System.out.println();
    

    }


    public static void generateArrays(AbstractCollection<Integer> arr){

        Random rand = new Random();

        for(int i = 0; i < 10000; i++){
            arr.add(rand.nextInt(10000));
        }

    }


    public static void timeForInsertionBST(AbstractCollection<Integer> arr, BST bst){

        long start = System.nanoTime();

        for(Integer keys: arr){
            bst.insert(keys);    
        }

        long stop = System.nanoTime();

        long timeComplexity = stop - start;

        System.out.println("Time taken to insert 10000 nodes: " + timeComplexity + " nanoseconds.");
        

    }


    public static void timeForSearchingBST(BST bst){

        long start = System.nanoTime();

        System.out.println("Node with the key '1967' was found: " + bst.searchNode(1967));

        long stop = System.nanoTime();

        long timeComplexity = stop - start;

        System.out.println("Time taken to search for a node: " + timeComplexity + " nanoseconds.");


    }


    public static void timeForInsertionAVL(AbstractCollection<Integer> arr, AVL avl){

        long start = System.nanoTime();

        for(Integer keys: arr){
            avl.insert(keys);    
        }

        long stop = System.nanoTime();
        long timeComplexity = stop - start;

        System.out.println("Time taken to insert 10000 nodes: " + timeComplexity  + " nanoseconds.");
        

    }


    public static void timeForSearchingAVL(AVL avl){

        long start = System.nanoTime();

        System.out.println("Node with the key '2136' was found: " + avl.searchNode(1967));

        long stop = System.nanoTime();
        long timeComplexity = stop - start;

        System.out.println("Time taken to search for a node: " + timeComplexity + " nanoseconds.");

    }

}
