import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class DecimalDominant {
    public static Integer[] find(Integer[] arr, int k) {
        ArrayList<Integer> list = new ArrayList<>();
        HashMap<Integer, Integer> map = new HashMap<>();
        int n = arr.length;
        
        for (int i = 0; i < n; i++) {
            if (map.containsKey(arr[i])) map.put(arr[i], map.get(arr[i]) + 1);
            else map.put(arr[i], 1);
        }
        
        for (int key : map.keySet()) {
            if (map.get(key) >= n/k) {
                list.add(key);
            }
        }
        
        StdOut.println(map);
        return list.toArray(new Integer[] {});
    }
    
    public static void main(String[] args) {
        // O(2n) worst case
        // O(n) auxiliar space
        int n = 1000;
        int k = 10;
        Integer[] arr = new Integer[n];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = StdRandom.uniform(11);
        }
        StdOut.println(Arrays.toString(arr));
        StdOut.println(Arrays.toString(find(arr, k)));
    }
}
