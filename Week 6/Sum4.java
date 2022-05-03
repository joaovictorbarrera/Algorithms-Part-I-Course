import java.util.Arrays;
import java.util.HashMap;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
public class Sum4 {
    /*
    Question 1
    4-SUM. Given an array a[] of n integers, the 4-SUM problem is to determine 
    if there exist distinct indices i, j, k, and l such that a[i] + a[j] = a[k] + a[l]. 
    Design an algorithm for the 4-SUM problem that takes time proportional to n^2 
    (under suitable technical assumptions).
     */
    public static boolean isThere4Sum(int[] vals) {
        HashMap<Integer, int[]> map = new HashMap<>();
        for(int i = 0; i < vals.length; i++) {
            for(int j = i + 1; j < vals.length; j++) {
                int sum = vals[i] + vals[j];
                if (map.containsKey(sum) && !intersect(map.get(sum), i, j)) {
                    StdOut.println("sum:"+sum); return true;
                }
                map.putIfAbsent(sum, new int[] {i, j});
            }
        }
        return false;
    }
    
    public static boolean intersect(int[] pair, int k, int l) {
        return k == pair[0] || k == pair[1] || l == pair[0] || l == pair[1];
    }
    
    public static void main(String[] args) {
        final int N = 15;
        int[] vals = new int[N];
        for(int i = 0; i < N; i++) {
            vals[i] = StdRandom.uniform(N*100);
        }
        StdOut.println(Arrays.toString(vals));
        StdOut.println(isThere4Sum(vals));
    }
}

/*
Question 2
Describe what happens if you override hashCode() but not equals():
If there are collisions, you will possibly be retrieving the collided object and not the correct one.

Describe what happens if you override equals() but not hashCode():
Java will use its pre-built hashing function, treating different objects with the same value as being different. 
You will get repeated keys all over the place.

Describe what happens if you override hashCode() but implement 
public boolean equals(OlympicAthlete that) instead of public boolean equals(Object that):
In this case, you won't be overriding the inherited class method from the Object class, 
meaning HashMaps won't use the correct method to make comparisons. 
It will use the default equals() method and completely ignore the implemented one. 
This is because HashMaps and HashSets treat keys as Object objects because you can't have an array of generics.
 */
