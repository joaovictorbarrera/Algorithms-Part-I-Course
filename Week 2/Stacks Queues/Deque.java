import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int length;

    public Deque() {
        this.first = null;
        this.last = null;
        this.length = 0;
    }

    private class Node {
        Node prev;
        Node next;
        Item item;

        Node() {
            this.item = null;
            this.next = null;
            this.prev = null;
        }
    }

    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();

        this.length++;
        Node oldFirst = this.first;
        this.first = new Node();
        this.first.item = item;
        this.first.next = oldFirst;

        if (oldFirst != null) {
            oldFirst.prev = this.first;
        }
        else {
            this.last = this.first;
        }
    }

    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        
        this.length++;
        Node oldLast = this.last;

        this.last = new Node();
        this.last.item = item;
        this.last.prev = oldLast;

        if (this.isEmpty()) {
            this.first = this.last;
        }
        else {
            oldLast.next = this.last;
        }
    }

    // is the deque empty?
    public boolean isEmpty() {
        return this.first == null;
    }

    // return the number of items on the deque
    public int size() {
        return this.length;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (this.isEmpty()) throw new NoSuchElementException();

        this.length--;
        Item item = this.first.item;
        this.first = this.first.next;

        if (this.isEmpty()) {
            this.last = null;
        }
        else {
            this.first.prev = null;
        }

        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (this.isEmpty()) throw new NoSuchElementException();
        
        this.length--;
        Item itemRemoved = this.last.item;
        this.last = this.last.prev;

        if (this.last == null) {
            this.first = null;
        }
        else {
            this.last.next = null;
        }
        
        return itemRemoved;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            
            Item item = current.item;
            current = current.next;
            return item;
        }

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();

        StdOut.println("First Length: " + deque.size());
        StdOut.println("Is it empty? " + deque.isEmpty());

        int count = 0;
        while (count < 1000) {
            int random = (int) (StdRandom.uniform() * 500);
            if (random < 250) {
                deque.addFirst(random);
            } 
            else if (random < 500) {
                deque.addLast(random);
            }

            count++;
        }

        StdOut.println("Second Length: " + deque.size());
        StdOut.println("Is it empty? " + deque.isEmpty());

        int count2 = 0;
        while (count2 < 995) {
            int random = (int) (StdRandom.uniform() * 500);
            if (random < 250) {
                deque.removeFirst();
            } 
            else if (random < 500) {
                deque.removeLast();
            }

            count2++;
        }

        StdOut.println("Items:");
        for (int item : deque) {
            StdOut.println(item);
        }

        StdOut.println("Third Length: " + deque.size());
        StdOut.println("Is it empty? " + deque.isEmpty());
        deque.removeFirst();
        deque.removeLast();
        deque.removeFirst();
        deque.removeLast();
        deque.removeFirst();
        StdOut.println("Fourth Length: " + deque.size());
        StdOut.println("Is it empty? " + deque.isEmpty());
    }

}
