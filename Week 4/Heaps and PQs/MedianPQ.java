import java.util.NoSuchElementException;
import java.util.Arrays;

import edu.princeton.cs.algs4.StdOut;

public class MedianPQ<Item extends Comparable<Item>> {
    MaxPriorityQueue<Item> maxPQ;
    MinPriorityQueue<Item> minPQ;
    MedianPQ() {
        maxPQ = new MaxPriorityQueue<Item>();
        minPQ = new MinPriorityQueue<Item>();
    }
    
    public void debug() {
        StdOut.println("max: "+Arrays.toString(maxPQ.debug()) + " min: " + Arrays.toString(minPQ.debug()));
    }

    public int size() {
        return maxPQ.size() + minPQ.size();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public void insert(Item item) {
        if(size() == 0) {
            minPQ.insert(item); 
            return;
        }
        
        if (less(item, minPQ.getMin())) {
            maxPQ.insert(item);
            if (maxPQ.size() - minPQ.size() == 2) minPQ.insert(maxPQ.deleteMax());
        }
        else {
            minPQ.insert(item);
            if (minPQ.size() - maxPQ.size() == 2) maxPQ.insert(minPQ.deleteMin());
        }
        
        
    }
    
    public Item getMedian() {
        if (isEmpty()) throw new NoSuchElementException();
    
        if (size() % 2 == 1) {
            return maxPQ.size() - minPQ.size() == 1 ? maxPQ.getMax() : minPQ.getMin(); 
        }
        else return maxPQ.getMax();
    }

    public Item deleteMedian() {
        if (isEmpty()) throw new NoSuchElementException();
        
        if (size() % 2 == 1) {
            return maxPQ.size() - minPQ.size() == 1 ? maxPQ.deleteMax() : minPQ.deleteMin(); 
        }
        else return maxPQ.deleteMax();
    }
    
    private boolean less(Item o1, Item o2) {
        return o1.compareTo(o2) < 0;
    }

    public static void main(String[] args) {
        MedianPQ<Integer> medianPQ = new MedianPQ<>();

        int n = 15;
        for (int i = 1; i <= n; i++) {
            medianPQ.debug();
            medianPQ.insert(i);
        }
        for (int i = 1; i <= n; i++) {
            medianPQ.debug();
            StdOut.println(medianPQ.deleteMedian());
        }
       
        medianPQ.debug();
    }
}
