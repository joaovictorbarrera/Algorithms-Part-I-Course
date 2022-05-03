public class RedBlackTree<Key extends Comparable<Key>, Val> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;
    private Node root;
    
    RedBlackTree() {
        this.root = null;
    }
    
    public void put(Key key, Val val) {
        this.root = put(this.root, key, val);
    }
    
    public Node put(Node h, Key key, Val val) {
        if (h == null) return new Node(key, val, RED);
        
        int cmp = key.compareTo(h.key);
        if      (cmp < 0) h.left = put(h.left, key, val);
        else if (cmp > 0) h.right = put(h.right, key, val);
        else              h.val = val;
        
        if (isRed(h.right) && !isRed(h.left))    h = rotateLeft(h); // red pointing to the right
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h); // unbalanced 4 node (red left, red left left)
        if (isRed(h.left) && isRed(h.right))     flipColors(h); // 4 node needs splitting
        
        return h;
    }
    
    private boolean isRed(Node x) {
        if (x == null) return false;
        return x.color == RED;
    }
    
    // h is black, left is red, right is red
    private void flipColors(Node h) {
        h.color = RED;
        h.left.color = BLACK;
        h.right.color = BLACK;
    }
    
    // h is black, left is black, right is red
    private Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        return x;
    }
    
    // h is black, left is red, right is black
    private Node rotateLeft (Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        return x;
    }
    
    private class Node {
        Key key;
        Val val;
        Node right;
        Node left;
        boolean color;
        Node(Key key, Val val, boolean color) {
            this.key = key;
            this.val = val;
            this.color = color;
        }
    }
    
    public static void main(String[] args) {
    }
}
