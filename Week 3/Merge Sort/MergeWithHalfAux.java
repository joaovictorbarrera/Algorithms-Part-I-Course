import java.util.Arrays;
import java.util.Comparator;
/*
Merging with smaller auxiliary array. 
Suppose that the sub-array a[0] to a[n-1] is sorted and the sub-array a[n] to a[2*n-1] is sorted. 
How can you merge the two sub-arrays so that a[0] to a[2*n-1] is sorted using an auxiliary array of length n (instead of 2n)?
 */
public class MergeWithHalfAux extends MergeSort {
    public static void sortRecursive(Comparator<?> c, Object[] arr, int low, int high) {
        // base case: if less than 2 items, return.
        if (low >= high) return;
        // finds mid without overflowing
        int mid = low + (high - low) / 2;
        // sorts left half
        sortRecursive(c, arr, low, mid);
        // sorts right half
        sortRecursive(c, arr, mid + 1, high);
        // merge both halves
        merge(c, arr, low, mid, high);
    }
    
    public static void sortRecursive(Object[] arr, Comparator<?> c) {
        sortRecursive(c, arr, 0, arr.length - 1);
    }
    
    public static void sortRecursive(Object[] arr) {
        sortRecursive(arr, NATURAL_ORDER);
    }
    
    public static void sortIterative(Object[] arr) {
        sortIterative(arr, NATURAL_ORDER);
    }
    
    public static void sortIterative(Object[] arr, Comparator<?> c) {
        // log n upper for loop (iterator pointer doubles each time)
        for (int half = 1; half < arr.length; half += half) {
            int subArrayLength = half + half;
            System.out.println(Arrays.toString(arr) + " Section size: " + subArrayLength);
            // about n iterations
            for (int low = 0; low < arr.length - half; low += subArrayLength) {
                int mid = low + half - 1;
                int high = Math.min(low + subArrayLength - 1, arr.length - 1);
                merge(c, arr, low, mid, high);
            }
        }
        System.out.println(Arrays.toString(arr));
    }
    
    public static void merge(Comparator<?> c, Object[] arr, int low, int mid, int high) {
        // size of left sub-array
        Object[] aux = new Object[mid - low + 1];

        int leftPointer = low;
        int rightPointer = mid + 1;
        int writeAux = 0;
        int writeRight = mid + 1;
        
        while(leftPointer <= mid || rightPointer <= high) { 
            if(writeAux < aux.length) {
                if (leftPointer > mid)                                 aux[writeAux++] = arr[rightPointer++];
                else if (rightPointer > high)                          aux[writeAux++] = arr[leftPointer++];
                else if (less(c, arr[leftPointer], arr[rightPointer])) aux[writeAux++] = arr[leftPointer++];
                else                                                   aux[writeAux++] = arr[rightPointer++];
            } else {
                if (leftPointer > mid)                                 arr[writeRight++] = arr[rightPointer++];
                else if (rightPointer > high)                          arr[writeRight++] = arr[leftPointer++];
                else if (less(c, arr[leftPointer], arr[rightPointer])) arr[writeRight++] = arr[leftPointer++];
                else                                                   arr[writeRight++] = arr[rightPointer++];
            }
        }
        
        for(int i = low; i <= mid; i++) {
            arr[i] = aux[i - low];
        }
        
    }
    
    
    public static void main(String[] args) {
        final int n = 15;
        Integer[] arr = new Integer[n];
        
        for(int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * n * 2) - n;
//            arr[i] = arr.length - i;
        } 
        Object[] toSort = (Object[]) arr;
        MergeWithHalfAux.sortIterative(toSort);
        MergeWithHalfAux.sortRecursive(toSort);
        arr = (Integer[]) toSort;
        
    }
}
