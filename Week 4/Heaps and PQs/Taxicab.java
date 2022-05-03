import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import edu.princeton.cs.algs4.StdOut;

public class Taxicab implements Comparable<Taxicab>{
    private final int i;
    private final int j;
    private final long sum;
    Taxicab(int i, int j) {
        this.i = i;
        this.j = j;
        this.sum = (long) (Math.pow(i, 3) + Math.pow(j, 3));
    }
    
    public int compareTo(Taxicab that) {
        if (this.sum < that.sum) return -1;
        else if (this.sum > that.sum) return 1;
        else if (this.i < that.i) return -1;
        else if (this.i > that.i) return 1;
        else return 0;
    }
    
    public String toString() {
        return i + "^3 + " + j + "^3";
    }

    public static void main(String[] args) {
        int n = 32;
        Taxicab[][] result = findTaxicabV2(n);
        StdOut.println(Arrays.deepToString(result));
    }
    
    public static Integer[] findTaxicabV1(int n) {
        ArrayList<Integer> result = new ArrayList<>();
        ArrayList<Integer> temp = new ArrayList<>();
        
        // n^2
        for(int i = 1; i <= n; i++) {
            for(int j = i + 1; j <= n; j++) {
                temp.add((int) (Math.pow(i, 3) + Math.pow(j, 3)));
            }
        }
        // n^2 log n^2
        Collections.sort(temp);
        
        for(int i = 1; i < temp.size(); i++) {
            if (temp.get(i).compareTo(temp.get(i - 1)) == 0) {
                result.add(temp.get(i));
            }
        }
        
        StdOut.println(temp);
        
        return result.toArray(new Integer[] {});
    }
    
    public static Taxicab[][] findTaxicabV2(int n) {
        MinPriorityQueue<Taxicab> minPQ = new MinPriorityQueue<>();
        List<Taxicab[]> result = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            minPQ.insert(new Taxicab(i, i));
        }
        int run = 1;
        Taxicab prev = new Taxicab(0, 0);
        while(!minPQ.isEmpty()) {
            Taxicab curr = minPQ.deleteMin();
            
            if (prev.sum == curr.sum) {
                run++;
                if (run == 2) {
                    StdOut.print(prev.sum + " = " + prev);
                    StdOut.print(" = " + curr);
                    result.add(new Taxicab[] {prev, curr});
                }
                
            }
            else {
                if (run > 1) StdOut.println(); // extraneous
                run = 1;
            }
            prev = curr;
            
            if (curr.j < n) minPQ.insert(new Taxicab(curr.i, curr.j + 1));
        }
        if (run > 1) StdOut.println(); // extraneous
        return result.toArray(new Taxicab[][] {});
    }
}
