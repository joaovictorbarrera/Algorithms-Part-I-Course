import java.util.Iterator;

public class QueueLinked<Item> implements Iterable<Item> {
    private Node first = null;
    private Node last = null;
    private int length = 0;
    
    public Iterator<Item> iterator() {
        return new QueueIterator();
    }
    
    private class QueueIterator implements Iterator<Item> {
        private Node current = first;
        
        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }
        
        public boolean hasNext() {
            return current != null;
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private class Node {
        Node next = null;
        Item item = null;
    }

    public int getLength() {
        return this.length;
    }
    
    public Item getLast() {
        return this.last.item;
    }

    public void enqueue(Item item) {
        // add at end of the list
        this.length++;
        Node oldLast = this.last;
        
        this.last = new Node();
        this.last.item = item;
        
        if (this.isEmpty()) this.first = this.last;
        else oldLast.next = this.last;
    }

    public Item dequeue() {
        // remove at start
        if(this.isEmpty()) return null;

        this.length--;
        Item item = this.first.item;
        this.first = this.first.next;

        if(this.isEmpty()) this.last = null;

        return item;
    }

    public boolean isEmpty() {
        return this.first == null;
    }

    public String toString() {
        String string = "[";
        Node x = null;

        if(!this.isEmpty()) {
            string += this.first.item;
            x = this.first.next;
        }
         
        while(x != null) {
            string += ", " + x.item;
            x = x.next;
        }

        string += "]";
        return string;
    }
    
    public static void main(String[] args) {
        int N = 128;

        QueueLinked<Integer> queueLinked = new QueueLinked<>();
        System.out.println("queueLinked: " + queueLinked.toString());
        for(int i = 0; i < N; i++) {
            System.out.println("queued: " + i);
            queueLinked.enqueue(i);
        }

        System.out.println("queueLinked: " + queueLinked.toString());
        System.out.println(queueLinked.getLength());
        
        for(int item : queueLinked) {
            System.out.print(item + " ");
        }

        System.out.println();
        
        for(int i = 0; i < N; i++) {
            System.out.println("dequeued in queueLinked: " + queueLinked.dequeue()) ;
        }
        System.out.println("queueLinked: " + queueLinked.toString());
        System.out.println(queueLinked.getLength());
        
        
    }
}
