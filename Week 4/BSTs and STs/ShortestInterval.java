import java.io.File;
import java.util.Scanner;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.ST;
/*
 Document search. 
 Design an algorithm that takes a sequence of n document words and a sequence of m query words 
 and find the shortest interval in which the m query words appear in the document in the order given. 
 The length of an interval is the number of words in that interval.
 
 We can maintain a symbol table with each unique word as the key, 
 and a queue of indexes in sorted order where that unique word exists. 
 To find the interval, we can iterate over the indexes on the first word of the sequence, 
 and try to form the intervals with the subsequent words. 
 To form an interval, simply get the smallest index that a subsequent word shows up that is bigger than the last. 
 Update the current smallest interval (and bestLow and bestHigh) until every index of the first word has been iterated.
 */
public class ShortestInterval {
    public static void main(String[] args) {
        String path = "C:\\Users\\joaov\\eclipse-workspace\\Week 4 - BSTs and Elementary Symbol Tables\\src\\text.txt";
        
        String[] sequence = new String[] {"lorem", "ipsum"};
        System.out.println(findShortestInterval(path, sequence));
    }
    
    public static String findShortestInterval(String path, String[] sequence) {
        if (sequence.length < 1) return "None";
        ST<String, Queue<Integer>> words = makeTable(path);
        if (words == null) return "None";
        Queue<Integer> first = words.get(sequence[0]);
        if (first == null) return "None";
        int bestLow = Integer.MIN_VALUE;
        int bestHigh = Integer.MIN_VALUE;
        int intervalSize = Integer.MAX_VALUE;
        for(int low : first) {
            int high = low;
            for (int i = 1; i < sequence.length; i++) {
                Queue<Integer> curr = words.get(sequence[i]);
                if (curr == null) return "None";
                high = smallest(high, curr);
                if (high == -1) break;
                if (high - low > intervalSize) break;
            }
            if (high != -1 && high - low < intervalSize) {
                intervalSize = high - low;
                bestLow = low;
                bestHigh = high;
            }
        }
        if (intervalSize != Integer.MAX_VALUE) return String.format("Shortest Interval found: %d (%d - %d)", intervalSize, bestLow, bestHigh);
        else return "None";
    }
    
    public static int smallest(int high, Queue<Integer> curr) {
        for(int index : curr) {
            if (index > high) return index;
        }
        return -1;
    }
    
    public static ST<String, Queue<Integer>> makeTable(String path) {
        try {
            Scanner scanner = new Scanner(new File(path));
            int count = 0;
            ST<String, Queue<Integer>> words = new ST<>();
            while(scanner.hasNext()) {
                String curr = scanner.next();
                if(words.contains(curr)) {
                    Queue<Integer> temp = words.get(curr);
                    temp.enqueue(count);
                } else {
                    Queue<Integer> temp = new Queue<>();
                    temp.enqueue(count);
                    words.put(curr, temp);
                }
                count++;
            }
            return words;
        } catch(Exception e) {
            System.out.println("file not found");
            return null;
        }
    }
}
