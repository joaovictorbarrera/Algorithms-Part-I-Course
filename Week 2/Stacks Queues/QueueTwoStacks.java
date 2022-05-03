import java.util.Iterator;

public class QueueTwoStacks<Item> implements Iterable<Item>{
    private StackLinked<Item> stack1 = new StackLinked<>();
    private StackLinked<Item> stack2 = new StackLinked<>();
    private int length = 0;
    
    public Iterator<Item> iterator() {
        return new QueueIterator();
    }
    
    private class QueueIterator implements Iterator<Item> {
        private int iterator = 0;
        private Item[] memoized = null;
        
        
        public boolean hasNext() {
            return iterator < length;
        }

        public Item next() {
            if (memoized == null) memoize();
            return memoized[iterator++];
        }
        
        @SuppressWarnings("unchecked")
        private void memoize() {
            this.memoized = (Item[]) new Object[stack1.getLength() + stack2.getLength()];
            
            int i = 0;
            for(Item item : stack2) {
                this.memoized[i] = item;
                i++;
            }
            
            Item[] temp = (Item[]) new Object[stack1.getLength()];
            int j = 0;
            for(Item item : stack1) {
                temp[j] = item;
                j++;
            }
            while(j > 0) {
                this.memoized[i] = temp[j - 1];
                i++;
                j--;
            }
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    
    public void enqueue(Item item) {
        this.length++;
        stack1.push(item);
    }
    
    public Item dequeue() {
        if(isEmpty()) return null;
        this.length--;
                
        if(stack2.getLength() == 0) {
            while(stack1.getLength() > 0) {
                stack2.push(stack1.pop());
                
            }
        }
        return stack2.pop();
    }
    
    public boolean isEmpty() {
        return this.length < 1;
    }
    
    public int getLength() {
        return this.length;
    }
    
    public String toString() {
        String string = "[";
        
        for(Item item : stack2) {
            string += item + ", ";
        }
        
        @SuppressWarnings("unchecked")
        Item[] temp = (Item[]) new Object[stack1.getLength()];
        int i = 0;
        for(Item item : stack1) {
            temp[i] = item;
            i++;
        }
        while(i > 0) {
            string += temp[i - 1] + ", ";
            i--;
        }
        
        if(!string.equals("[")) string = string.substring(0, string.length() - 2);
        string += "]";
        return string;
    }
    
    public static void main(String[] args) {
        QueueTwoStacks<Integer> queueTwoStacks = new QueueTwoStacks<>();
        int N = 128;
        
        System.out.println("queueTwoStacks: " + queueTwoStacks.toString());
        for(int i = 0; i < N; i++) {
            System.out.println("queued: " + i);
            queueTwoStacks.enqueue(i);
        }

        System.out.println("queueTwoStacks: " + queueTwoStacks.toString());
        System.out.println(queueTwoStacks.getLength());
        
        for(int item : queueTwoStacks) {
            System.out.print(item + " ");
        }

        System.out.println();
        
        for(int i = 0; i < N; i++) {
            System.out.println("dequeued in queueTwoStacks: " + queueTwoStacks.dequeue()) ;
        }
        System.out.println("queueTwoStacks: " + queueTwoStacks.toString());
        System.out.println(queueTwoStacks.getLength());
    }
}
