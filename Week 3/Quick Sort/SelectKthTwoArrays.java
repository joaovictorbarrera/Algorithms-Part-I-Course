import java.util.Arrays;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class SelectKthTwoArrays {
    
    static int count = 0;
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Comparable selectKth(Comparable[] a1, Comparable[] a2, int k) {
        int n = a1.length;
        int m = a2.length;
        
        if (k < 1 || k > n + m) throw new IllegalArgumentException("invalid k !");
        if (n > m) return selectKth(a2, a1, k);
        
        // if k is bigger than a2, then sets the minimum elements we can pick from a1 to k-m
        int low = Math.max(0, k-m);
        // if k is smaller than a1, sets the maximum elements we can pick from a1 to k
        int high = Math.min(k, n);
        
        while(low <= high) { // just so no infinite loop, will always finish anyways 
            count++;
            int mid1 = low + (high - low) / 2;
            int mid2 = k - mid1;
            Comparable left1 = mid1 == 0 ? Integer.MIN_VALUE : a1[mid1 - 1]; // left item on a1
            Comparable left2 = mid2 == 0 ? Integer.MIN_VALUE : a2[mid2 - 1]; // left item on a2
            Comparable right1 = mid1 == n ? Integer.MAX_VALUE : a1[mid1]; // right item on a2
            Comparable right2 = mid2 == n ? Integer.MAX_VALUE : a2[mid2]; // right item on a2
            
            // general case l1 <= r2 AND l2 <= r1
            if (left1.compareTo(right2) <= 0 && left2.compareTo(right1) <= 0) {
                // returns max of lefts
                return left1.compareTo(left2) > 0 ? left1 : left2;
            }
            else if (left1.compareTo(right2) > 0) {
                high = mid1 - 1;
            }
            else { // left2 > right 1
                low = mid1 + 1;
            }
        }
        
        
        throw new NoSuchElementException();
    }
    
    public static void main(String[] args) {

        final int n = 1001;
        
        Integer[] a1 = new Integer[StdRandom.uniform(n)];
        Integer[] a2 = new Integer[StdRandom.uniform(n)];
        int k = StdRandom.uniform(a1.length + a2.length) + 1;
        
        for (int i = 0; i < a1.length; i++) {
            a1[i] = StdRandom.uniform(n);
        }
        
        for (int i = 0; i < a2.length; i++) {
            a2[i] = StdRandom.uniform(n);
        }
        
        ThreeWayQuickSort.sort(a1);
        ThreeWayQuickSort.sort(a2);
        
        
        StdOut.println("K: "+k);
        StdOut.println(Arrays.toString(a1));
        StdOut.println(Arrays.toString(a2));

        StdOut.println(k+"th element: " + selectKth(a1, a2, k));
        StdOut.println("count: " + count);
    }
}

/*

always leave one item

K: 64
[2, 10, 11, 12, 15, 16, 17, 17, 19, 21, 31, 33, 36, 37, 44, 44, 46, 55, 56, 57, 58, 60, 60, 67, 72, 75, 79, 83, 83, 84, 96, 98]
[1, 4, 4, 6, 7, 8, 11, 12, 14, 15, 16, 17, 22, 25, 26, 28, 29, 32, 34, 35, 37, 37, 40, 43, 47, 48, 49, 52, 52, 53, 55, 58, 60, 61, 61, 64, 65, 65, 69, 69, 70, 72, 73, 75, 75, 75, 78, 82, 84, 85, 86, 86, 87, 91, 95, 98, 98, 98, 100]
Initial mid 1: 31
Initial mid 2: 31
*/

/*
k = 64;
        StdOut.println("K: "+k);
        a1 = new Integer[] {2, 10, 11, 12, 15, 16, 17, 17, 19, 21, 31, 33, 36, 37, 44, 44, 46, 55, 56, 57, 58, 60, 60, 67, 72, 75, 79, 83, 83, 84, 96, 98};
        a2 = new Integer[] {1, 4, 4, 6, 7, 8, 11, 12, 14, 15, 16, 17, 22, 25, 26, 28, 29, 32, 34, 35, 37, 37, 40, 43, 47, 48, 49, 52, 52, 53, 55, 58, 60, 61, 61, 64, 65, 65, 69, 69, 70, 72, 73, 75, 75, 75, 78, 82, 84, 85, 86, 86, 87, 91, 95, 98, 98, 98, 100};
        StdOut.println(Arrays.toString(a1));
        StdOut.println(Arrays.toString(a2));

        StdOut.println(k+"th element: " + selectKth(a1, a2, a1.length, a2.length, k));
        StdOut.println("count: " + count); 
*/
