import java.util.Arrays;
import java.util.Comparator;

import edu.princeton.cs.algs4.SortingUtil;
import edu.princeton.cs.algs4.StdOut;

public class ThreeWayQuickSort extends SortingUtil {
    public static void sort(Object[] arr) {
        sort(arr, NATURAL_ORDER);
    }
    
    public static void sort(Object[] arr, Comparator<?> c) {
        sort(arr, c, 0, arr.length - 1);
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void sort(Object[] arr, Comparator c, int low, int high) {
        if (high <= low) return;
        int lt = low, gt = high;
        int i = low;
        Object k = arr[low];
        while(i <= gt) {
            int cmp = c.compare(arr[i], k);
            if (cmp < 0)        swap(arr, lt++, i++);
            else if (cmp > 0)   swap(arr, gt--, i);
            else                i++;
        }
        
        sort(arr, c, low, lt - 1);
        sort(arr, c, gt + 1, high);
    }
    
    private static void swap(Object[] arr, int i, int j) {
        Object temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    
    public static void main(String[] args) {
        Integer[] arr = new Integer[100];
        for(int i = 0; i < arr.length; i++) {
            arr[i] = i % 6;
        }
//        StdOut.println(Arrays.toString(arr));
        sort(arr);
        StdOut.println(Arrays.toString(arr));
    }
}
