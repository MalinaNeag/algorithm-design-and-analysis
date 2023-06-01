/*
Implement a trie tree data structure and add operations to:
-  add a new word to the trie tree
-  print (in alphabetical order) all the words contained in the trie tree
*/


class Node {
    
    public Node[] children;
    public boolean isEndOfWord;
    public final int maxAlphabetChars = 26;

    public Node() {
        isEndOfWord = false;
        children = new Node[maxAlphabetChars];
    }

}

class Trie {
    
    public Node root;
    
    public Trie() {
        root = new Node();
    }

    /*
    * This method is used to insert a new word into the Trie data structure.
    * For example, if we wanted to insert the word "cat" into the Trie, the
    * insert() method would create a node for "c" at index 2, a node for "a"
    * at index 0, and a node for "t" at index 19. The isEndOfWord property of
    * the "t" node would be set to true to indicate that "cat" is ended.
    * */

    public void insert(String word) {

        Node node = root;
        int j;

        for (int i = 0; i < word.length(); i++) {
            // For each character in the word, get its index in the Trie using the ASCII  code
            j = word.charAt(i) - 'a';
            // Check if the child node at index j of the current node is null. If it is = > create a new node
            if (node.children[j] == null) {
                node.children[j] = new Node();
            }
            // Move to the next child node at index j of the current node
            node = node.children[j];
        }
        // Mark the end of the word
        node.isEndOfWord = true;
    }

    public void printWordsAlphabeticalOrder(String word) {
        printWordsAlphabeticalOrder(root, word);
    }

    public void printWordsAlphabeticalOrder(Node node, String word) {

        // Check if the current node is the end of a word => valid word => print it
        if (node.isEndOfWord) {    
            System.out.println(word);
        }

        /*
        *  The for loops through all possible characters in the Trie.
        *  For each character, if a child node exists, it adds the character to the
        *  current word and recursively calls the function with the child node and updated word.
        *  This continues until it reaches the end of the Trie => all valid words have been printed.*/

        for (int i = 0; i < node.maxAlphabetChars; i++) {
            
            if (node.children[i] != null) {
                
                char ch = (char) (i + 'a');
                word += ch;
                printWordsAlphabeticalOrder(node.children[i], word);
                word = word.substring(0, word.length() - 1);
            
            }
        
        }

    }

}

class TrieTree {
    public static void main(String[] args) {
        basicTrieTree();
    }

    public static void basicTrieTree() {

        Trie node = new Trie();

        String[] words = { "all", "alternate", "alternative", "are", "arc", "archbishop", "bear", "beard", "bee",
                "hear", "horse", "horseshoe", "brotherinlaw" };

        for (String word : words) {
            node.insert(word);
        }

        node.printWordsAlphabeticalOrder("");
    }

}
