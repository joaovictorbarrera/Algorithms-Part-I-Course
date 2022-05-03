import edu.princeton.cs.algs4.RedBlackBST;

class GeneralizedQueue<Item> {
    private int index;
    private RedBlackBST<Integer, Item> store;

    GeneralizedQueue() {
        index = 0;
        store = new RedBlackBST<Integer, Item>();
    }

    public void append(Item item) {
        store.put(index++, item);
    }

    public void removeFront() {
        store.deleteMin();
    }

    public Item get(int i) {
        int key = store.rank(i);
        return store.get(key);
    }

    public void delete(int i) {
        store.delete(store.rank(i));
    }
}
