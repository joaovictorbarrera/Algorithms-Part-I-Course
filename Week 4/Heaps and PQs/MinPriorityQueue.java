import java.util.Arrays;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;

public class MinPriorityQueue<Item extends Comparable<Item>> {
    private Item[] arr;
    private int heapSize;
    
    @SuppressWarnings("unchecked")
    MinPriorityQueue() {
        this.arr = (Item[]) new Comparable[1];
        this.heapSize = 0;
    }
    
    public Item getAt(int index) {
        return arr[index];
    }
    
    public Item[] debug() {
        return arr;
    }
    
    public int size() {
        return heapSize;
    }
    
    public boolean isEmpty() {
        return heapSize == 0;
    }
    
    @SuppressWarnings("unchecked")
    private void checkResize() {
        if(heapSize > arr.length - 1) updateArray(arr.length * 2);
        else if (heapSize + 1 <= arr.length / 4) updateArray(arr.length / 2);
    }
    
    @SuppressWarnings("unchecked")
    private void updateArray(int size) {
        Item[] temp = (Item[]) new Comparable[size];
        
        int len = Math.min(size, arr.length);
        
        for (int i = 0; i < len; i++) {
            temp[i] = arr[i];
        }
        arr = temp;
    }
    
    public Item getMin() {
        if (isEmpty()) throw new NoSuchElementException();
        return arr[1];
    }
    
    public void insert(Item item) {
        heapSize++;
        checkResize();
        arr[heapSize] = item;
        swim(heapSize);
    }
    
    public Item deleteMin() {
        if (isEmpty()) throw new NoSuchElementException();
        Item max = arr[1];
        swap(1, heapSize);
        heapSize--;
        sink(1);
        arr[heapSize + 1] = null;
        checkResize();
        return max;
    }
    
    private void swim(int pos) {
        // while not the root node
        // AND parent is smaller than the child
        while(pos > 1 && greater(pos/2, pos)) {
            // swap child with parent
            swap(pos, pos/2);
            // sets pos to the parent for next iteration
            pos = pos/2;
        }
    }
    
    private void sink(int pos) {
        // while there's a child on the left
        while (2*pos <= heapSize) {
            // get left child pos
            int childPos = 2*pos;
            // if there could be a child on the right
            // compare two childs and change pos to which one is largest
            if (childPos < heapSize && greater(childPos, childPos+1)) childPos++;
            // if the parent is not smaller than the biggest child, break. Base case.
            if (!greater(pos, childPos)) break;
            // Otherwise, swap parent with biggest child
            swap(pos, childPos);
            // cascade down by setting the next pos to the childpos
            pos = childPos;
        }
    }
    
    private boolean greater(int i, int j) {
        return arr[i].compareTo(arr[j]) > 0;
    }
    
    private void swap(int i, int j) {
        Item temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    
    public static void main(String[] args) {
        MinPriorityQueue<Integer> minPQ = new MinPriorityQueue<>();

        int n = 63;
        for(int i = 1; i <= n; i++) {
            StdOut.println(Arrays.toString(minPQ.debug()));
            minPQ.insert(i);
        }
        
        for(int i = 1; i <= n; i++) {
            StdOut.println(Arrays.toString(minPQ.debug()));
            minPQ.deleteMin();
        }
        StdOut.println(Arrays.toString(minPQ.debug()));
    }
}

