import java.util.Arrays;

public class Permutation {
    public static boolean isPermutation(int[] arr1, int[] arr2) {
        if(arr1 == null || arr2 == null) throw new IllegalArgumentException();
        if(arr1.length != arr2.length) return false;
        Arrays.sort(arr1); // any n*log*n sort here
        Arrays.sort(arr2); // any n*log*n sort here
        for (int i = 0; i < arr1.length; i++) {
            if(arr1[i] != arr2[i]) {
                return false;
            }
        }
        
        return true;
    }
    
    public static void main(String[] args) {
        final int N = 50;
        int[] arr1 = new int[N];
        int[] arr2 = new int[N];
        
        for(int i = 0; i < arr1.length; i++) {
            arr1[i] = i;
            arr2[i] = i;
        }
        
        for(int i = 0; i < arr2.length; i++) {
            int indexToSwap = (int)(Math.random() * (i + 1));
            int temp = arr2[indexToSwap];
            arr2[indexToSwap] = arr2[i];
            arr2[i] = temp;
        }
        
        System.out.println(Arrays.toString(arr1));
        System.out.println(Arrays.toString(arr2));

        System.out.println(isPermutation(arr1, arr2));
    }
}
