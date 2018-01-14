
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private DListNode head;
    private int size;

    // inner node class.
    private class DListNode {
        Item item;
        DListNode prev;
        DListNode next;
    }

    public Deque() {
        DListNode sentinel = new DListNode();
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        sentinel.item = null;
        head = sentinel;
        size = 0;
    }

    /**
     * return an iterator over items.
     */
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    // inner iterator class 
    private class ListIterator implements Iterator<Item> {
        public DListNode current;

        public ListIterator() {
            current = head.next;
        }

        public boolean hasNext() {
            return current != head;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
        
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException(); 
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    /**
     * check if the deque is empty.
     * @return true if it is empty, otherwise return false.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * return the number of the items on the deque.
     */
    public int size() {
        return size;
    }

    /**
     * add the item to the front.
     * @param item to be added.
     */
    public void addFirst(Item item) {
        if (item == null) throw new NullPointerException();
        DListNode node = new DListNode();
        node.item = item;
        node.prev = head;
        node.next = head.next;
        head.next = node;
        node.next.prev = node;
        size++;
    }

    /**
     * add teh item to the end.
     * @param item
     */
    public void addLast(Item item) {
        if (item == null) throw new NullPointerException();
        DListNode node = new DListNode();
        node.item = item;
        node.prev = head.prev;
        node.next = head;
        node.prev.next = node;
        head.prev = node;
        size++;
    }

    /**
     * remove and return the item from the front.
     * @return item from the front.
     */
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        DListNode first = head.next;
        head.next = first.next;
        first.next.prev = head;
        size--;
        return first.item;
    }

    /**
     * remove and return the last item.
     * @return item from the end.
     */
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();
        DListNode last = head.prev;
        head.prev = last.prev;
        last.prev.next = head;
        size--;
        return last.item;
    }
}