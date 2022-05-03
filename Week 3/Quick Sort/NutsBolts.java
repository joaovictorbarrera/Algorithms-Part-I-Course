import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class NutsBolts implements Iterable<NutsBolts.Pair> {
    private final Pair[] pairs;
    public int loops = 0;
    
    NutsBolts(Nut[] nuts, Bolt[] bolts) {
        if (nuts == null || bolts == null || nuts.length != bolts.length) 
            throw new IllegalArgumentException();
        StdRandom.shuffle(nuts);
        StdRandom.shuffle(bolts);
        this.pairs = makePairs(nuts, bolts, nuts.length);
    }
    
    private Pair[] makePairs(Nut[] nuts, Bolt[] bolts, int size) {
        Pair[] combinedPairs = new Pair[size];
        sort(nuts, bolts, 0, size-1);
        StdOut.println("sorted nuts: "+Arrays.toString(nuts));
        StdOut.println("sorted bolts: "+Arrays.toString(bolts));
        for (int i = 0; i < size; i++) {
            combinedPairs[i] = new Pair(nuts[i], bolts[i]);
        }
        return combinedPairs;
    }
    
    private void sort(Nut[] nuts, Bolt[] bolts, int low, int high) {
        if (high <= low) return;
        int lt = low, gt = high;
        int i = low;
        Bolt pivotBolt = bolts[low];
//        StdOut.println("pivotBolt: "+pivotBolt.getSize());
        while(i <= gt) {
            loops++;
            int cmp = nuts[i].compareTo(pivotBolt);
            if (cmp < 0)        swap(nuts, lt++, i++);
            else if (cmp > 0)   swap(nuts, gt--, i);
            else                i++;
//            StdOut.println("nuts: "+Arrays.toString(nuts));
        }
        
        Nut pivotNut = nuts[lt];
//        StdOut.println("pivotNut: "+pivotNut.size);
        lt = low;
        gt = high;
        i = low;
        while(i <= gt) {
            loops++;
            int cmp = pivotNut.compareTo(bolts[i]);
            if (cmp > 0)        swap(bolts, lt++, i++);
            else if (cmp < 0)   swap(bolts, gt--, i);
            else                i++;
//            StdOut.println("bolts: "+Arrays.toString(bolts));
        }
        
        sort(nuts, bolts, low, lt - 1);
        sort(nuts, bolts, gt + 1, high);
 
    } 
    
    private static void swap(Object[] arr, int i, int j) {
        Object temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    
    public Pair getPairAt(int i) {
        if (i < 0 || i > pairs.length) throw new NoSuchElementException();
        return pairs[i];
    }
    
    static class Pair {
        public final Nut nut;
        public final Bolt bolt;
        Pair(Nut nut, Bolt bolt) {
            this.nut = nut;
            this.bolt = bolt;
        }
        
        public String toString() {
            return String.format("[Nut: %s, Bolt: %s]", this.nut, this.bolt);
        }
    }
    
    static class Nut {
        private final int size;
        Nut(int size) {
            this.size = size;
        }
        
        public String toString() {
            return this.size + "";
        }
        
        public int compareTo(Bolt bolt) {
            if (this.size > bolt.getSize()) return 1;
            else if (this.size < bolt.getSize()) return -1;
            else return 0;
        }
    }

    static class Bolt {
        private final int size;
        Bolt(int size) {
            this.size = size;
        }
        
        public String toString() {
            return this.size + "";
        }
        
        public int getSize() {
            return this.size;
        }
    }

    @Override
    public Iterator<Pair> iterator() {
        return new PairIterator();
    }
    
    private class PairIterator implements Iterator<Pair> {
        private int current;
        PairIterator() {
            this.current = 0;
        }

        public boolean hasNext() {
            return current < pairs.length;
        }

        public NutsBolts.Pair next() {
            if (!hasNext()) throw new NoSuchElementException();
            return pairs[current++];
        }
    }
    
    public static void main(String[] args) {
        final int n = 16;
        Nut[] nuts = new Nut[n];
        Bolt[] bolts = new Bolt[n];
        for(int i = 0; i < n; i++) {
            nuts[i] = new Nut(i);
            bolts[i] = new Bolt(i);
        }
        NutsBolts sorted = new NutsBolts(nuts, bolts);
        StdOut.println(sorted.getPairAt(10));
        for (Pair pair : sorted) {
            StdOut.println(pair);
        }
        StdOut.println("loops: "+sorted.loops);
    }
}
