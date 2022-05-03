import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class AVL_BST<Key extends Comparable<Key>, Val> {
    public Node root = null;
    public int rotations = 0;
    private class Node {
        Key key;
        Val val;
        Node left = null;
        Node right = null;
        int height;
        
        Node(Key key, Val val) {
            this.key = key;
            this.val = val;
        }
        
        @SuppressWarnings("unused")
        public void debug() {
            StdOut.println("key: "+key);
            StdOut.println("val: "+val);
            StdOut.println("left: "+(left == null ? "null" : left.key));
            StdOut.println("right: "+(right == null ? "null" : right.key));
            StdOut.println("height: "+height);
        }
    }
    
    private int height(Node node) {
        return node == null ? -1 : node.height;
    }
    
    private void updateHeight(Node node) {
        node.height = 1 + Math.max(height(node.left), height(node.right));
    }
    
    private int bFactor(Node node) {
        return height(node.left) - height(node.right);
    }
    
    public void put(Key key, Val val) {
        if (key == null) throw new IllegalArgumentException();
        root = put(root, key, val);
    }
    
    public Node put(Node curr, Key key, Val val) {
        if (curr == null) return new Node(key, val);
        
        int cmp = key.compareTo(curr.key);
        if (cmp == 0) curr.val = val;
        else if (cmp < 0) {
            curr.left = put(curr.left, key, val);
        } else {
            curr.right = put(curr.right, key, val);
        }
        return rebalance(curr);
    }
    
    private Node rebalance(Node curr) {
        updateHeight(curr);
        int currBFactor = bFactor(curr);
        
        if (currBFactor > 1) {
            if (height(curr.left.left) > height(curr.left.right)) {
                curr = rotateRight(curr);
            } else {
                curr.left = rotateLeft(curr.left);
                curr = rotateRight(curr);
            }
        } else if (currBFactor < -1) {
            if (height(curr.right.right) > height(curr.right.left)) {
                curr = rotateLeft(curr);
            } else {
                curr.right = rotateRight(curr.right);
                curr = rotateLeft(curr);
            }
        }
        
        return curr;
    }
    
    private Node rotateLeft(Node curr) {
        rotations++;
        Node child = curr.right;
        curr.right = child.left;
        child.left = curr;
        updateHeight(curr);
        updateHeight(child);
        return child;
    }
    
    private Node rotateRight(Node curr) {
        rotations++;
        Node child = curr.left;
        curr.left = child.right;
        child.right = curr;
        updateHeight(curr);
        updateHeight(child);
        return child;
    }
    
    public boolean contains(Key key) {
        Node curr = this.root;
        while(curr != null) {
            if (curr.key == key) return true;
            else if (key.compareTo(curr.key) < 0) curr = curr.left;
            else curr = curr.right;  
        }
        
        return false;
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
            StdOut.print(curr.key + ":" + bFactor(curr) + " ");
            if (curr == delimiter && !queue.isEmpty()) {
                StdOut.println();
                delimiter = queue.getLast();
            }
        }   
    }
    
    // in order (depth first)
    public Iterable<Key> keys() {
        QueueLinked<Key> q = new QueueLinked<Key>();
        inorder(this.root, q);
        return q;
    }
    
    private void inorder(Node curr, QueueLinked<Key> q) {
        if (curr == null) return;
        inorder(curr.left, q);
        q.enqueue(curr.key);
        inorder(curr.right, q);
    }
    
    // by level (breadth first)
    public Iterable<Key> levels() {
        QueueLinked<Key> q = new QueueLinked<Key>();
        breadthFirstTraversal(q);
        return q;
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
    
    private void breadthFirstTraversal(QueueLinked<Key> q) {
        QueueLinked<Node> n = new QueueLinked<>();
        if (this.root != null) n.enqueue(this.root);
        while(!n.isEmpty()) {
            Node curr = n.dequeue();
            q.enqueue(curr.key);
            if (curr.left != null) n.enqueue(curr.left);
            if (curr.right != null) n.enqueue(curr.right);
        }
    }
    
    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException();
        root = delete(root, key);
    }
    
    private Node delete(Node curr, Key key) {
        if (curr == null) return curr;
        int cmp = key.compareTo(curr.key);
        if(cmp < 0) {
            curr.left = delete(curr.left, key);
        }
        else if(cmp > 0) {
            curr.right = delete(curr.right, key);
        }
        else {
            if (curr.left == null || curr.right == null) {
                curr = curr.left == null ? curr.right : curr.left;
            } else {
                Node leftMost = curr.right;
                while (leftMost.left != null) {
                    leftMost = leftMost.left;
                }
                curr.key = leftMost.key;
                curr.right = delete(curr.right, curr.key);
            }
        }
        
        if (curr != null) {
            return rebalance(curr);
        }
        
        return curr;
    }
    
    public static void main(String[] args) {
        AVL_BST<Integer, Integer> tree = new AVL_BST<>();
        int n = 10000000;
        for(int i = 1; i <= n; i++) {
            int rand = StdRandom.uniform(Integer.MAX_VALUE);
            tree.put(rand, rand);
        }
        StdOut.println((double)tree.rotations/n + "\n");
        
        
//        for(int i = 1; i <= n; i++) {
//            int rand = StdRandom.uniform(100);
//            tree.delete(rand);
//            rand = StdRandom.uniform(100);
//            tree.put(rand, rand);
//        }
        
//        tree.printTree(); 
//        StdOut.println();
//        tree.root.print();
//        tree.root.print();
//        for(int i = 1; i <= n*2; i++) {
//            if (!tree.search(i)) {
//                StdOut.println(i + " was not found in the tree.");
//            }
//        }
    }
}
