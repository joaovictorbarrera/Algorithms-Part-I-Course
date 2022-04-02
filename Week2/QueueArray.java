import java.util.Arrays;
import java.util.Iterator;

public class QueueArray<Item> implements Iterable<Item> {
    private int head = 0;
    private int tail = 0;
    private int length = 0;
    @SuppressWarnings("unchecked")
    private Item[] arr = (Item[]) new Object[1];
    
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }
    
    private class ArrayIterator implements Iterator<Item> {
        private int current = head;
        public boolean hasNext() {
            return current < length + head;
        }

        public Item next() {
            return arr[current++ % arr.length];
        }
        
        public void remove() { throw new UnsupportedOperationException(); }
    }
    
    public int getLength() {
        return this.length;
    }

    public String debugStats() {
        return String.format("array: [%s] head: [%s] tail: [%s] length: [%s]", 
                Arrays.toString(this.arr), this.head, this.tail, this.length);
    }

    public void enqueue(@SuppressWarnings("unchecked") Item... args) {
        for(Item item : args) {
            this.length++;
            this.arr[this.tail] = item;
            this.tail++;
            if(this.length == this.arr.length) {
                resize(this.arr.length * 2);
                this.head = 0;
                this.tail = this.arr.length / 2;
            }
            else if(this.tail == this.arr.length) this.tail = 0;
        } 
    }

    public Item dequeue() {
        if(isEmpty()) return null;
        this.length--;

        Item item = this.arr[this.head];
        this.arr[this.head] = null;
        this.head++;
        if(this.length == this.arr.length/4) {
            this.resize(this.arr.length/2);
            this.head = 0;
            this.tail = this.arr.length/2;
        }  
        else if(this.head == this.arr.length) {
            this.head = 0;
        }

        if(this.tail == this.head) {
            this.tail = 0;
            this.head = 0;
        }
        return item;
    }

    private void resize(int size) {
        @SuppressWarnings("unchecked")
        Item[] newArray = (Item[]) new Object[size]; 
        
        for(int i = this.head; i < this.length + this.head; i++) {
            newArray[i - this.head] = this.arr[i % this.arr.length];
        }

        this.arr = newArray;
    }
    
    public boolean isEmpty() {
        return this.length < 1;
    }

    public String toString() {
        String string = "[";
        if(!isEmpty()) string += this.arr[this.head];

        for(int i = this.head + 1; i < this.length + this.head; i++) {
            string += ", " + this.arr[i % this.arr.length];
        }

        string += "]";
        return string;
    }
    
    public static void main(String[] args) {
        int N = 127;

        QueueArray<Integer> queueArray = new QueueArray<>();
        System.out.println("queueArray: " + queueArray.toString());
        for(int i = 0; i < N; i++) {
            System.out.println("pushed: " + i);
            queueArray.enqueue(i);
        }

        System.out.println("queueArray: " + queueArray.toString());
        System.out.println(queueArray.getLength());
        for(int i = 0; i < N/2; i++) {
            System.out.println("popped: " + queueArray.dequeue()) ;
        }
        System.out.println("queueArray: " + queueArray.toString());
        System.out.println(queueArray.getLength());
        for(int i = 0; i < N/3; i++) {
            queueArray.enqueue(i);
        }
        System.out.println("queueArray: " + queueArray.toString());
        System.out.println(queueArray.getLength());
        
        for(int item : queueArray) {
            System.out.print(item + " ");
        }
        
        System.out.println();

        for(int i = 0; i < N; i++) {
            System.out.println("popped: " + queueArray.dequeue()) ;
        }
        System.out.println("queueArray: " + queueArray.toString());
        System.out.println(queueArray.getLength());
    }
}