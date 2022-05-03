import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.Scanner;
public class Concordance {
    public static void main(String[] args) throws FileNotFoundException {
        String path = "C:\\Users\\joaov\\eclipse-workspace\\Week 6 - Hashing\\src\\book.txt";
        snippet(path);
    }
    
    public static void snippet(String path) throws FileNotFoundException{
        String[] book = buildIndexedBook(path);
//        System.out.println(book[0]);
        HashMap<String, TreeSet<Integer>> st = buildST(book);
        
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("Input word:");
            String query = scanner.next();
            if (query.equals("exit")) {
                scanner.close();
                break;
            }
            find(st, query, book);
        }
        scanner.close();
    }
    
    public static HashMap<String, TreeSet<Integer>> buildST(String[] book) throws FileNotFoundException {
        HashMap<String, TreeSet<Integer>> st = new HashMap<>();
        
        for(int index = 0; index < book.length; index++) {
            String curr = book[index];
            if (!st.containsKey(curr)) {
                st.put(curr, new TreeSet<Integer>());
            }
            TreeSet<Integer> indices = st.get(curr);
            indices.add(index);
            index++;
        }
        
        return st;
    }
    
    public static void find(HashMap<String, TreeSet<Integer>> st, String key, String[] book) {
        if (st.containsKey(key)) {
            int count = 0;
            for(int index : st.get(key)) {
                int low = index - 4;
                int high = index + 4;
                if (low < 0) low = 0;
                if (high > book.length - 1) high = book.length - 1;
                System.out.print("\"..."+book[low]);
                for (int i = low + 1; i <= high; i++) {
                    System.out.print(" " + book[i]);
                }
                System.out.print("\"...\n");
                count++;
            }
            System.out.println("\n"+count+" records associated with the word '"+ key +"'\n");
        } else {
            System.out.println("Not on the book.\n");
        }
    }
    
    public static String[] buildIndexedBook(String path) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(path));
        ArrayList<String> words = new ArrayList<>();
        while(scanner.hasNext()) {
            words.add(scanner.next());
        }
        scanner.close();
        return words.toArray(new String[] {});
    }
}


