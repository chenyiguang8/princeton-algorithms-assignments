
import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size;
    private Item[] a;

    public RandomizedQueue() {
        a = (Item[]) new Object[2];
        size = 0;
    }

    // implement the iterator method.
    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    private class RandomIterator implements Iterator<Item> {
        int[] shuffle = new int[size];
        int current;

        public RandomIterator() {
            for (int i = 0; i < size; i++) {
                shuffle[i] = i;
            }
            StdRandom.shuffle(shuffle);
            current = 0;
        }

        public boolean hasNext() {
            return current < size;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = a[shuffle[current]];
            current++;
            return item;
        }
    }

    /**
     * check whether the queue is empty.
     * @return true if the queue is empty.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * return the number of the items.
     * @return size.
     */
    public int size() {
        return size;
    }

    /**
     * add the item to the end.
     * @param item
     */
    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException();
        if (size == a.length) resize(a.length * 2);
        a[size++] = item;
    }

    /**
     * remove and return a random item.
     * @return a random item.
     */
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        int n = StdRandom.uniform(size);
        Item item = a[n];

        if (n != size - 1) a[n] = a[size - 1];
        // to avoid loitering.
        a[size - 1] = null;
        size--;
        // shrink the array if necessary.
        if (size > 0 && size == a.length / 4) {
            resize(a.length / 2);
        }
        return item;
    }

    /**
     * return (but do not remove) a item.
     * @return a random item.
     */
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        int n = StdRandom.uniform(size);
        return a[n];
    }

    /**
     * create an array of the given length. 
     * and copy all elements of the old array to new one.
     * @param length the new array's length.
     */
    private void resize(int length) {
        Item[] temp = (Item[]) new Object[length];
        for (int i = 0; i < size; i++) {
            temp[i] = a[i];
        }
        a = temp;
    }
}