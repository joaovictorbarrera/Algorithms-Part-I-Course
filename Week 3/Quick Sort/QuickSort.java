import java.util.Arrays;
import java.util.Comparator;

import edu.princeton.cs.algs4.SortingUtil;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class QuickSort extends SortingUtil {
    
    public static void sort(Object[] arr) {
        sort(arr, NATURAL_ORDER);
    }
    
    public static void sort(Object[] arr, Comparator<?> c) {
        StdRandom.shuffle(arr);
        sort(arr, c, 0, arr.length - 1);
    }
    
    public static void sort(Object[] arr, Comparator<?> c, int low, int high) {
        if(low >= high) return;
        int mid = partition(arr, c, low, high);
        sort(arr, c, low, mid - 1);
        sort(arr, c, mid + 1, high);
    }
    
    private static int partition(Object[] arr, Comparator<?> c, int low, int high) {
        Object k = arr[low];
        int i = low + 1;
        int j = high;
        while(true) {
            while(less(c, arr[i], k)) {
                i++;
                if (i == high + 1) break;
            }
            
            while(less(c, k, arr[j])) {
                j--;
                if (j == low) break;
            }
            
            if(j <= i) break;
            swap(arr, i, j);
        }
        
        swap(arr, low, j);
        
        return j;
    }
    
    private static void swap(Object[] arr, int i, int j) {
        Object temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static boolean less(Comparator c, Object o1, Object o2) {
        return c.compare(o1, o2) < 0;
    }
    
    public static void main(String[] args) {
        Integer[] arr = new Integer[50];
        for(int i = 0; i < arr.length; i++) {
            arr[i] = i - arr.length/2;
        }
        StdOut.println(Arrays.toString(arr));
        sort(arr, BACKWARDS_ORDER);
        StdOut.println(Arrays.toString(arr));
    }
}
