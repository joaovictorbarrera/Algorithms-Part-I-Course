import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;
/*
Shuffling a linked list. 
Given a singly-linked list containing nn items, rearrange the items uniformly at random. 
Your algorithm should consume a logarithmic (or constant) amount of extra memory and run in time proportional to n*log(n) in the worst case. 
 */
public class SinglyLinkedList<Item> implements Iterable<Item> {
    private Node head;
    private int length;
    
    SinglyLinkedList() {
        this.head = null;
        this.length = 0;
    }
    
    private class Node {
        Item item;
        Node next;
        Node() {
            this.item = null;
            this.next = null;
        }
    }

    public Iterator<Item> iterator() {    
        return new NodeIterator();
    }
    
    private class NodeIterator implements Iterator<Item> {
        private Node current = head;
        
        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            this.current = current.next;
            
            return item;
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    
    public int size() {
        return this.length;
    }
    
    public boolean isEmpty() {
        return this.head == null;
    }
    
    public Item get() {
        return head.item;
    }

    public Item getAt(int index) {
        if (index < 0) throw new IllegalArgumentException();
        if (index >= this.length) throw new NoSuchElementException();
        
        Node curr = findNodeAt(index);
        return curr.item;
    }
     
    public void insert(Item item) {
        if (item == null) throw new IllegalArgumentException();
        this.length++;
        Node oldHead = this.head;
        this.head = new Node();
        this.head.item = item;
        this.head.next = oldHead;
        
    }
    
    public void insertAt(Item item, int index) {
        if (index < 0 || item == null) throw new IllegalArgumentException();
        if (index > this.length) throw new IndexOutOfBoundsException();
        
        if (index == 0) {insert(item); return;}
        this.length++;
        
        Node curr = findNodeAt(index - 1);
        Node node = new Node();
        node.item = item;
        node.next = curr.next;
        curr.next = node;
    }
    
    public Item remove() {
        if(isEmpty()) throw new NoSuchElementException();
        Item item = this.head.item;
        this.head = this.head.next;
        this.length--;
        return item;
    }
    
    public Item removeAt(int index) {
        if (index < 0) throw new IllegalArgumentException();
        if (index >= this.length) throw new NoSuchElementException();
        
        if (index == 0) return remove();
        this.length--;
        
        Node curr = findNodeAt(index - 1); 
        Item item = curr.next.item;
        curr.next = curr.next.next;
        
        return item;
    }
    
    private Node findNodeAt(int index) {
        Node curr = this.head;
        for (int i = 0; i < index; i++) {
            curr = curr.next;
        }
        return curr;
    }
    
    public void reverse() {
        Node curr = this.head;
        Node prev = null;
        Node next;
        while(curr != null) {
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        this.head = prev;
    }
    
    public void shuffle() {
        this.head = shuffle(this.head, this.length);
    }
    

    public Node shuffle(Node leftHead, int size) {
        if (isEmpty() || size == 1) return leftHead;
        
        int halfLength = 1;
        Node midNode = leftHead;
        while (halfLength < size/2) {
            midNode = midNode.next;
            halfLength++;
        }
        
        Node rightHead = midNode.next; 
        midNode.next = null;
        
        leftHead = shuffle(leftHead, size/2);
        rightHead = shuffle(rightHead, size - size/2);
        return merge(leftHead, rightHead);
    }
    
    private Node merge(Node lh, Node rh) {
        Node head = new Node();
        Node curr;
        if (StdRandom.uniform(2) == 0) {
            curr = lh;
            head.next = curr;
            lh = lh.next;
        } else {
            curr = rh;
            head.next = curr;
            rh = rh.next;
        }
        
        while (lh != null || rh != null) {
            int rand = StdRandom.uniform(2);
            if (lh == null) {
                curr.next = rh;
                rh = rh.next;
                curr = curr.next;
                // right
            }
            else if (rh == null) {
                curr.next = lh;
                lh = lh.next;
                curr = curr.next;
                // left
            } 
            else if (rand == 0) {
                curr.next = rh;
                rh = rh.next;
                curr = curr.next;
                // right
            }
            else {
                curr.next = lh;
                lh = lh.next;
                curr = curr.next;
                // left
            }
        }
        
        return head.next;
    }
    
    public static void main(String[] args) {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();

        list.insert(4);
        list.insert(3);
        list.insert(2);
        list.insert(1);
        
        System.out.print(" -> ");
        for (int item : list) {
            System.out.print(item + " -> ");
        }
        System.out.println();
        
        int ones = 0;
        int twos = 0;
        int threes = 0;
        int fours = 0;
        for (int i = 0; i < 100000; i++) {
            list.shuffle();
            int item = list.getAt(2);
            switch(item) {
            case 1:
                ones++;
                break;
            case 2:
                twos++;
                break;
            case 3:
                threes++;
                break;
            case 4:
                fours++;
                break;
            }
        }
        System.out.println("Ones: "+ones);
        System.out.println("Twos: "+twos);
        System.out.println("Threes: "+threes);
        System.out.println("Fours: "+fours);
               
        System.out.print(" -> ");
        for (int item : list) {
            System.out.print(item + " -> ");
        }
    }
}
