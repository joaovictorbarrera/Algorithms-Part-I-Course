import java.util.Arrays;
import java.util.Iterator;

public class StackArray<Item> implements Iterable<Item> {
    @SuppressWarnings("unchecked")
    private Item[] arr = (Item[]) new Object[1];
    private int nextIndex = 0;

    public String debugStats() {
        return String.format("array: [%s] nextIndex: [%s]", Arrays.toString(this.arr), this.nextIndex); 
    }
    
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }
    
    private class ArrayIterator implements Iterator<Item> {
        private int current = 0;
        
        public Item next() {
            return arr[current++];
        }
        
        public boolean hasNext() {
            return current < nextIndex;
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public String toString() {
        String display = "[";
        if(!isEmpty()) display += this.arr[0];
        for(int i = 1; i < this.nextIndex; i++) {
            display += ", " + this.arr[i];
        }
        display += "]";
        return display;
    }

    public Item pop() {
        if(isEmpty()) return null;

        Item poppedItem = this.arr[--this.nextIndex];
        this.arr[this.nextIndex] = null;
        if(this.nextIndex == this.arr.length/4) resize(this.arr.length/2);

        return poppedItem;
    }

    public void push(@SuppressWarnings("unchecked") Item... args) {
        for(Item item : args) {
            if(this.nextIndex == this.arr.length) this.resize(this.arr.length * 2);
            this.arr[this.nextIndex++] = item;
        }
    }

    private void resize(int size) {
        @SuppressWarnings("unchecked")
        Item[] newArray = (Item[]) new Object[size];
        for(int i = 0; i < this.nextIndex; i++) {
            newArray[i] = this.arr[i];
        }
        this.arr = newArray;
    }

    public int getLength() {
        return this.nextIndex;
    }
    
    public boolean isEmpty() {
        return this.nextIndex < 1;
    }
    
    public static void main(String[] args) {
        int N = 128;

        StackArray<Integer> stackArray = new StackArray<>();
        System.out.println("stackArray: " + stackArray.toString());
        for(int i = 0; i < N; i++) {
            System.out.println("pushed: " + i);
            stackArray.push(i);
        }

        System.out.println("stackArray: " + stackArray.toString());
        System.out.println(stackArray.getLength());
        
        for(int item : stackArray) {
            System.out.print(item + " ");
        }
        
        System.out.println();

        for(int i = 0; i < N; i++) {
            System.out.println("popped: " + stackArray.pop()) ;
        }
        System.out.println("stackArray: " + stackArray.toString());
        System.out.println(stackArray.getLength());
    }
}