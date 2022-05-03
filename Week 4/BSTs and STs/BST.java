import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/*
 * Binary Search Tree. Not yet self balancing.
 * Insert: O(h) usually, O(n) worst case, Search: O(h) usually, AVG O(2log n), O(n) worst case.
 * 
 */
public class BST<Key extends Comparable<Key>, Val> {
    private Node root = null;
    private class Node {
        private Node left;
        private Node right;
        private Key key;
        private Val val;

        Node(Key key, Val val) {
            left = null;
            right = null;
            this.key = key;
            this.val = val;
        }
    }
    
    public void deleteMin() {
        this.root = deleteMin(this.root);
    }
    
    private Node deleteMin(Node node) {
        if (node.left == null) return node.right;
        node.left = deleteMin(node.left);
        return node;
    }
    
    public boolean isEmpty() {
        return this.root == null;
    }
    
    public void delete(Key key) {
        if (isEmpty()) return; // tree is empty
        Node parent = null;
        Node curr = this.root;
        while (curr != null) {
            int cmp = key.compareTo(curr.key);
            if (cmp < 0) {
                parent = curr;
                curr = curr.left;
            }
            else if (cmp > 0) {
                parent = curr;
                curr = curr.right;
            }
            else {
                break;
            }
        }
        if (curr == null) return; // item not found
        if (curr.left == null && curr.right == null) { // deleted node has no children
            if (parent == null) this.root = null;
            else {
                parent.left = parent.left == curr ? null : parent.left;
                parent.right = parent.right == curr ? null : parent.right;
            }
        } 
        else if (curr.left != null && curr.right != null) { // deleted node has two children
            if (parent == null) {
                // root equals successor 
                this.root = getSuccessor(curr);
            } else {
                // curr equals successor
                parent.left = parent.left == curr ? getSuccessor(curr) : parent.left;
                parent.right = parent.right == curr ? getSuccessor(curr) : parent.right;
            }
        }
        else  { // deleted node has one child
            if (parent == null) {
                this.root = (curr.left == null ? curr.right : curr.left);
            } else {
                parent.left = parent.left == curr ? (curr.left == null ? curr.right : curr.left) : parent.left;
                parent.right = parent.right == curr ? (curr.left == null ? curr.right : curr.left) : parent.right;
            }
        }
    }
    
    private Node getSuccessor(Node curr) {
        Node subTree = curr.right;
        if (subTree.left == null) {
            curr.right = subTree.right;
            subTree.left = curr.left;
            subTree.right = curr.right;
            return subTree;
        } 
        while (subTree.left.left != null) {
            subTree = subTree.left;
        }
        Node successor = subTree.left;
        subTree.left = subTree.left.right;
        successor.left = curr.left;
        successor.right = curr.right;
        return successor;
    }
    
    public void put(Key key, Val val) {
        if (key == null || val == null) throw new IllegalArgumentException();
        this.root = put(this.root, key, val);
    }

    private Node put(Node curr, Key key, Val val) {
        if (curr == null) return new Node(key, val);
        
        int cmp = key.compareTo(curr.key);
        if(cmp == 0) {
            curr.val = val;
        }
        else if (cmp < 0) {
            curr.left = put(curr.left, key, val);
            
        } else {
            curr.right = put(curr.right, key, val);
        }
        
        return curr;
    }
    
    // find the node with that key
    private Node find(Key key) {
        Node curr = this.root;
        while(curr != null) {
            if (curr.key == key) return curr;
            else if (key.compareTo(curr.key) < 0) curr = curr.left;
            else curr = curr.right;  
        }
        
        return null;
    }

    public Val search(Key key) {
        Node node = find(key);
        return node == null ? null : node.val;
    }
    
    public boolean checkBST() {
        return checkBST(this.root, null, null);
    }
    
    private boolean checkBST(Node node, Key min, Key max) {
        if (node == null) return true;
        if (max != null && node.key.compareTo(max) >= 0 || min != null && node.key.compareTo(min) <= 0) return false;
        return checkBST(node.left, min, node.key) && checkBST(node.right, node.key, max);
    }
    
    public void morrisInOrderTraversal() {
        Node curr = this.root;
        while(curr != null) {
            if (curr.left == null) {
                System.out.print(curr.key + " ");
                curr = curr.right;
            }
            else {
                Node predecessor = findPredecessor(curr);
                if (predecessor.right == null) {
                    predecessor.right = curr;
                    curr = curr.left;
                } else {
                    predecessor.right = null;
                    System.out.print(curr.key + " ");
                    curr = curr.right;
                }
            }
        }
    }
    
    private Node findPredecessor(Node curr) {
        Node temp = curr.left;
        while(temp.right != null && temp.right != curr) {
            temp = temp.right; 
        }
        return temp;
    }
    
    public void printTree() {
        QueueLinked<Node> queue = new QueueLinked<>();
        if (this.root.key == null) return;
        queue.enqueue(this.root);
        Node delimiter = this.root;
        while(!queue.isEmpty()) {
            Node curr = queue.dequeue();
            if (curr.left != null) queue.enqueue(curr.left);
            if (curr.right != null) queue.enqueue(curr.right);
            StdOut.print(curr.key + ":" + curr.val + " ");
            if (curr == delimiter && !queue.isEmpty()) {
                StdOut.println();
                delimiter = queue.getLast();
            }
        }   
    }
    
    public static void main(String[] args) {
        BST<Integer, Integer> tree = new BST<>();
        for (int i = 1; i <= 50; i++) {
            int rand = StdRandom.uniform(51);
            tree.put(rand, i);
        }
        tree.morrisInOrderTraversal();
        StdOut.println();
        StdOut.println(tree.checkBST());
    }
}
