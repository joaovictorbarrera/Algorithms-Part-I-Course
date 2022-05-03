/*
Counting inversions. 
An inversion in an array a[] is a pair of entries a[i] and a[j] such that i < j but a[i] > a[j]. 
Given an array, design a linearithmic algorithm to count the number of inversions.
 */

public class CountInversions {
    static int counter = 0;
    static long memoryUsed;
    public static int countInversions(int[] arr) {
        int count = partition(arr, 0, arr.length-1);
        return count;
    }
    
    public static int partition(int[] arr, int low, int high) {
        if (low >= high) return 0;
        int mid = low + (high - low) / 2;
        int count = 0;
        
        count += partition(arr, low, mid);
        count += partition(arr, mid + 1, high);
        count += merge(arr, low, mid, high);
        
        return count;
    }
    
    public static int merge(int[] arr, int low, int mid, int high) {
        // size of left sub-array
        int[] aux = new int[mid - low + 1];
        
        int leftPointer = low;
        int rightPointer = mid+1;
        int auxPointer = 0;
        int writeRight = mid+1;
        int count = 0;
        while (leftPointer <= mid || rightPointer <= high) {
            counter++;
            if(auxPointer < aux.length) {
                if (leftPointer > mid)                          aux[auxPointer++] = arr[rightPointer++];
                else if (rightPointer > high)                   aux[auxPointer++] = arr[leftPointer++];
                else if (arr[leftPointer] < arr[rightPointer])  aux[auxPointer++] = arr[leftPointer++];
                else {
                    /* Trick:
                     * Because we know both sides are sorted,
                     * when we find a number on the right that's smaller than the number on the right
                     * we add the amount of remaining elements on the left, instead of 1.
                     *  [7 8 | 1 2]
                     *  count: 2 | [7 8 | 2]
                     *  count: 4 | [7 8 |]
                     *  ------------------------
                     *  [2 | 1]
                     *  count: 1 | [2 |]
                     */
                    count += mid - leftPointer + 1;
                    aux[auxPointer++] = arr[rightPointer++];
                }
            }
            else {
                if (leftPointer > mid)                          arr[writeRight++] = arr[rightPointer++];
                else if (rightPointer > high)                   arr[writeRight++] = arr[leftPointer++];
                else if (arr[leftPointer] < arr[rightPointer])  arr[writeRight++] = arr[leftPointer++];
                else {
                    count += mid - leftPointer + 1;
                    arr[writeRight++] = arr[rightPointer++];
                }
            }
        }
        
        // transfer all items from aux to arr
        for (int i = 0; i < auxPointer; i++) {
            arr[i + low] = aux[i];
        }
        
        aux = null;
        System.gc();
        System.runFinalization();
        
        return count;
    }
     
    public static void main(String[] args) {
        System.out.println(countInversions(new int[] {16,15,14,13,12,11,10,9,8,7,6,5,4,3,2,1}));
        System.out.println("n*log n swaps:" + counter);
    }
}
