import java.util.Arrays;
import java.util.Comparator;

import edu.princeton.cs.algs4.SortingUtil;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class QuickSelect extends SortingUtil {
    
    public static Object kthLargestElement(Comparator<?> c, Object[] arr, int k) {
        if (k > arr.length || k < 1) throw new IllegalArgumentException();
        StdRandom.shuffle(arr);
        int low = 0;
        int high = arr.length - 1;
        // transform k into index
        // 10th largest in 100 item array -> k = 89
        k = arr.length - k;
        while (high > low) {
            int j = partition(c, arr, low, high);
            if (j < k) low = j + 1;
            else if (j > k) high = j - 1;
            else return arr[k]; 
        }
        return arr[k];
    }
    
    public static Object[] selectKLargestSection(Object[] arr, int k) {
        return selectKSection(NATURAL_ORDER, arr, k);
    }
    
    public static Object[] selectKSmallestSection(Object[] arr, int k) {
        return selectKSection(BACKWARDS_ORDER, arr, k);
    }
    
    public static Object[] selectKSection(Comparator<?> c, Object[] arr, int k) {
        if (k > arr.length || k < 0) throw new IllegalArgumentException();
        if (k == arr.length) return arr.clone();
        
        Object[] clone = arr.clone();
        Object[] kLargest = new Object[k];
        kthLargestElement(c, clone, k + 1);
        
        for (int i = 0; i < kLargest.length; i++) {
            kLargest[i] = clone[clone.length - k + i];
        }
        
        return kLargest;
    }
    
    private static int partition(Comparator<?> c, Object[] arr, int low, int high) {
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
        Integer[] arr = new Integer[100];
        for(int i = 0; i < arr.length; i++) {
            arr[i] = i - arr.length/2;
        }
        StdOut.println(Arrays.toString(arr));
//        StdOut.println(kthLargestElement(arr, 50));
        StdOut.println(Arrays.toString(selectKSmallestSection(arr, 5)));
        StdOut.println(Arrays.toString(selectKLargestSection(arr, 5)));
        StdOut.println(Arrays.toString(selectKSection(ABSOLUTE_ORDER, arr, 5)));
    }
}
