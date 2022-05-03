import java.util.Arrays;
import edu.princeton.cs.algs4.SortingUtil;
import edu.princeton.cs.algs4.StdOut;

public class HeapSort extends SortingUtil {
    public static void sort(Comparable[] arr) {
        int size = arr.length;
        // heap construction
        for (int k = size/2; k >= 1; k--) {
            sink(arr, k, size);
        }
        // sorting heap
        while (size > 1) {
            swap(arr, 1, size);
            sink(arr, 1, --size);
        }
    }
    
    private static void sink(Comparable[] arr, int k, int size) {
        while (2*k <= size) {
            int j = 2*k;
            if (j < size && less(arr, j, j+1)) j++;
            if (!less(arr, k, j)) break;
            swap(arr, k, j);
            k = j;
        }
    }
    
    private static boolean less(Comparable[] arr, int i, int j) {
        return arr[i - 1].compareTo(arr[j - 1]) < 0;
    }
    
    private static void swap(Comparable[] arr, int i, int j) {
        Comparable temp = arr[i - 1];
        arr[i - 1] = arr[j - 1];
        arr[j - 1] = temp;
    }
    
    public static void main(String[] args) {
        Integer[] arr = new Integer[50];
        for(int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
        StdOut.println(Arrays.toString(arr));
        sort(arr);
        StdOut.println(Arrays.toString(arr));
    }
}
