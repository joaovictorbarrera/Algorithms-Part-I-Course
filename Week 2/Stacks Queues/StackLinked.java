import java.util.Iterator;

public class StackLinked<Item> implements Iterable<Item>{
    private Node first = null;
    private int length = 0;
    
    public Iterator<Item> iterator() {
        return new StackIterator();
    }
    
    private class StackIterator implements Iterator<Item> {
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

    public int getLength() {
        return this.length;
    }

    private class Node {
        Node next = null;
        Item item = null;
    }

    public boolean isEmpty() {
        return this.first == null;
    }

    public void push(Item item) {
        this.length++;
        Node oldFirst = this.first;
        this.first = new Node();
        this.first.item = item;
        this.first.next = oldFirst;
    }

    public Item pop() {
        if(this.isEmpty()) return null;

        this.length--;
        Item itemPopped = this.first.item;
        this.first = this.first.next;
        return itemPopped;
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

        StackLinked<Integer> stackLinked = new StackLinked<>();
        System.out.println("stackLinked: " + stackLinked.toString());
        for(int i = 0; i < N; i++) {
            System.out.println("pushed: " + i);
            stackLinked.push(i);
        }

        System.out.println("stackLinked: " + stackLinked.toString());
        System.out.println(stackLinked.getLength());
        
        for(int item : stackLinked) {
            System.out.print(item + " ");
        }
        
        System.out.println();

        for(int i = 0; i < N; i++) {
            System.out.println("popped: " + stackLinked.pop()) ;
        }
        System.out.println("stackLinked: " + stackLinked.toString());
        System.out.println(stackLinked.getLength());
    }

    public Item getTop() {
        if(this.isEmpty()) return null;
        return this.first.item;
    }
}
