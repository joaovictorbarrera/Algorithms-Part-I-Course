import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int head;
    private int tail;
    private int length;
    private Item[] arr;

    public RandomizedQueue() {
        this.head = 0;
        this.tail = 0;
        this.length = 0;
        this.arr = (Item[]) new Object[1];
    }

    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        private final int[] indexes;
        private int current;

        public ArrayIterator() {
            this.current = 0;
            this.indexes = findAllAvailableIndexes();
        }

        private int[] findAllAvailableIndexes() {
            RandomizedQueue<Integer> randomize = new RandomizedQueue<>();

            for (int i = head; i < length + head; i++) {
                randomize.enqueue(i % arr.length);
            }
            
            int[] randomIndexes = new int[length];
            for (int i = 0; i < randomIndexes.length; i++) {
                randomIndexes[i] = randomize.dequeue();
            }

            return randomIndexes;
        }

        public boolean hasNext() {
            return current < indexes.length;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();    
            return arr[indexes[current++]];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public int size() {
        return this.length;
    }

    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();

        this.length++;
        this.arr[this.tail] = item;
        this.tail++;
        maintenanceEnqueue();
    }

    private void maintenanceEnqueue() {
        if (this.length == this.arr.length) {
            resize(this.arr.length * 2);
            this.head = 0;
            this.tail = this.arr.length / 2;
        } 
        else if (this.tail == this.arr.length) {
            this.tail = 0; 
        }  
    }

    private int getRandomIndex() {
        return ((int) (StdRandom.uniform() * this.length + this.head)) % this.arr.length;
    }

    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();

        int randomIndex = getRandomIndex();
        Item item = this.arr[randomIndex];
        this.arr[randomIndex] = this.arr[this.head];
        this.arr[this.head] = null;
        this.head++;
        this.length--;

        maintenanceDequeue();

        return item;
    }

    private void maintenanceDequeue() {
        if (this.length == this.arr.length / 4) {
            this.resize(this.arr.length / 2);
            this.head = 0;
            this.tail = this.arr.length / 2;
        } 
        else if (this.head == this.arr.length) {
            this.head = 0;
        }

        if (this.tail == this.head) {
            this.tail = 0;
            this.head = 0;
        }
    }

    private void resize(int size) {
        Item[] newArray = (Item[]) new Object[size];

        for (int i = this.head; i < this.length + this.head; i++) {
            newArray[i - this.head] = this.arr[i % this.arr.length];
        }

        this.arr = newArray;
    }

    public boolean isEmpty() {
        return this.length < 1;
    }

    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        int randomIndex = getRandomIndex();
        return this.arr[randomIndex];
    }

    public static void main(String[] args) {
        int n = 128;
        RandomizedQueue<Integer> randQueue = new RandomizedQueue<>();
        StdOut.println("Empty Length: " + randQueue.size());
        StdOut.println("Is it empty?: " + randQueue.isEmpty());
  
        for (int i = 0; i < n; i++) {
            randQueue.enqueue(i);
        }
        
        StdOut.print("Full: ");
        for (int item : randQueue) {
            StdOut.print(item + " ");
        }
        StdOut.println("\nFull Length: " + randQueue.size());
        StdOut.println("Full Sample: " + randQueue.sample());
        StdOut.println("Is it empty?: " + randQueue.isEmpty());
        
        StdOut.print("Random dequeues: ");
        for (int i = 0; i < n / 2; i++) {
            StdOut.print(randQueue.dequeue() + " ");
        }
        
        StdOut.print("\nHalved: ");
        for (int item : randQueue) {
            StdOut.print(item + " ");
        }
        StdOut.println("\nHalved Length: " + randQueue.size());
        StdOut.println("Halved Sample: " + randQueue.sample());
        StdOut.println("Is it empty?: " + randQueue.isEmpty());
        
        while (randQueue.size() > 0) {
            randQueue.dequeue();
        }
        
        StdOut.println("Empty Length: " + randQueue.size()); 
        StdOut.println("Is it empty?: " + randQueue.isEmpty());
    }
}