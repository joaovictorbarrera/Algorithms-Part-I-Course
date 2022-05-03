import java.util.Arrays;

public class DutchFlag {
    static int swapCount = 0;
    static int colorCount = 0;
    
    static class Bucket {
        public Pebble pebble;
        Bucket(String color) {
            if(!color.equals("White") && !color.equals("Red") && !color.equals("Blue")) throw new IllegalArgumentException();
            this.pebble = new Pebble(color);
        }
        
        @Override
        public String toString() {
            return String.format("%s-Pebble-Bucket", pebble.color);
        }
        
        class Pebble {
            public String color;
            Pebble(String color) {
                this.color = color;
            }
        }
    }
    
    public static void swap(Bucket[] arr, int i, int j) {
        Bucket.Pebble temp = arr[i].pebble;
        arr[i].pebble = arr[j].pebble;
        arr[j].pebble = temp;
        swapCount++;
    }
    
    public static String color(Bucket[] arr, int i) {
        colorCount++;
        return arr[i].pebble.color;
    }
    
    public static void sortBuckets(Bucket[] arr) {
        int redPointer = 0;
        int bluePointer = arr.length - 1;
        int i = 0; 
        while(i <= bluePointer) {
            String currentColor = color(arr, i);
            if(currentColor.equals("Red")) {
                swap(arr, i, redPointer);
                redPointer++;
                i++;
            }
            else if(currentColor.equals("Blue")) {
                swap(arr, i, bluePointer);
                bluePointer--;
                // do not increment i because we don't know
                // what was swapped from the end to i
            } else {
                // white pebble
                i++;
            }
        }
    }
    
    public static String generateColor() {
        int random = (int) (Math.random() * 3);
        if (random == 0) return "White";
        else if (random == 1) return "Red";
        else return "Blue";
    }
    
    public static Bucket[] makeBuckets(int n) {
        Bucket[] buckets = new Bucket[n];
        for (int i = 0; i < buckets.length; i++) {  
            String generatedColor = generateColor();
            buckets[i] = new Bucket(generatedColor);
        }
        return buckets;
    }
    
    public static void main(String[] args) {
        final int n = 20;
        Bucket[] buckets = makeBuckets(n);
        System.out.println(Arrays.toString(buckets));
        sortBuckets(buckets);
        System.out.println(Arrays.toString(buckets));
        System.out.println(String.format("Swap Calls: %d | Color Calls: %d", swapCount, colorCount));
    }
}
