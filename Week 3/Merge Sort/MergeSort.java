import java.util.Arrays;
import java.util.Comparator;

import edu.princeton.cs.algs4.SortingUtil;

/* The idea is that two sorted halves can be "merged".
 * 
 * Method 1:
 * Recursively reduces array in halves until 2 items can be compared, then merges.
 * Goes up a level, merges, and so on.
 * WOYXELGYU
 * WOYXE LGYU
 * WO YX E LG YU
 * OW XY E GL UY
 * OW EXY GLUY
 * EOWXY GLUY
 * EGLOUWXYY
 * 
 * Method 2:
 * Iteratively sorts (merges) sub-arrays of two elements, then merges with its neighbor
 * until fully sorted.
 * WOYXELGYU
 * WO YX EL GY U
 * OW XY EL GY U
 * OWXY EL GUY
 * OWXY EGLUY
 * EGLOUWXYY
 */ 
public class MergeSort extends SortingUtil {
    
    public static void sortRecursive(Comparator<?> c, Object[] arr, Object[] aux, int low, int high) {
        // base case: if less than 2 items, return.
        if (low >= high) return;
        // finds mid without overflowing
        int mid = low + (high - low) / 2;
        // sorts left half
        sortRecursive(c, arr, aux, low, mid);
        // sorts right half
        sortRecursive(c, arr, aux, mid + 1, high);
        // merge both halves
        merge(c, arr, aux, low, mid, high);
    }
    
    public static void sortRecursive(Object[] arr, Comparator<?> c) {
        // be sure to create auxiliary array outside of recursive method
        // to avoid inefficiency bugs
        Object[] aux = new Object[arr.length];
        sortRecursive(c, arr, aux, 0, arr.length - 1);
    }
    
    public static void sortRecursive(Object[] arr) {
        sortRecursive(arr, NATURAL_ORDER);
    }
    
    public static void sortIterative(Object[] arr, Comparator<?> c) {
        Object[] aux = new Object[arr.length];
        // log n upper for loop (iterator pointer doubles each time)
        for (int half = 1; half < arr.length; half += half) {
            System.out.println(Arrays.toString(arr));
            int subArrayLength = half + half;
            // about n iterations
            for (int low = 0; low < arr.length - half; low += subArrayLength) {
                // mid is left preferable
                int mid = low + half - 1;
                // to account for odd size arrays, the high index is the minimum value
                // of last index of a sub-array or the end of the array (in case it overflows)
                int high = Math.min(low + subArrayLength - 1, arr.length - 1);
                merge(c, arr, aux, low, mid, high);
            }
        }
    }
    
    public static void merge(Comparator<?> c, Object[] arr, Object[] aux, int low, int mid, int high) {
//        System.out.println(String.format("Called Merge. low: %d mid: %d high: %d", low, mid, high));
        
        // copy this portion to the auxiliary array
        for (int i = low; i <= high; i++) {
            aux[i] = arr[i];
        }
        // declares pointers for each half
        int leftPointer = low;
        int rightPointer = mid + 1;
        for (int mainPointer = low; mainPointer <= high; mainPointer++) {
            // left half has been exhausted
            if (leftPointer > mid)                              arr[mainPointer] = aux[rightPointer++];
            // right half has been exhausted
            else if (rightPointer > high)                       arr[mainPointer] = aux[leftPointer++];
            // item on the left is smaller than item on the right
            else if (less(c, aux[leftPointer], aux[rightPointer])) arr[mainPointer] = aux[leftPointer++];
            // item on the right is smaller than item on the left
            else                                                arr[mainPointer] = aux[rightPointer++];
        }
        
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static boolean less(Comparator c, Object i, Object j) {
        return c.compare(i, j) < 0;
    }
}
