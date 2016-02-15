import java.util.Iterator;

import edu.princeton.cs.algs4.StdOut;

/**
 * 
 * @author freedev
 *
 * @param <Item>
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private int tail;
    private int arraySize;
    private int head;
    private int size;
    private Item[] list;

    /**
     * 
     * @author freedev
     *
     */
    private class RQIterator implements Iterator<Item> {

        private Item[] list;
        private int pos;

        @Override
        public boolean hasNext() {
            // TODO Auto-generated method stub
            return (pos < list.length);
        }

        @Override
        public Item next() {
            // TODO Auto-generated method stub
            return list[pos++];
        }

    }

    /**
    * 
    */
    public RandomizedQueue() // construct an empty randomized queue
    {
        head = 0;
        tail = 0;
        size = 0;
        list = (Item[]) new Object[1];
    }

    /**
     * 
     * @return
     */
    public boolean isEmpty() // is the queue empty?
    {
        return (size == 0);
    }

    /**
     * 
     * @return
     */
    public int size() // return the number of items on the queue
    {
        return size;
    }

    private int getArraySize() {
        return 1 << arraySize;
    }

    /**
     * 
     * @param item
     */
    public void enqueue(Item item) // add the item
    {
        int curSize = size();
        if (getArraySize() == curSize) {
            arraySize++;
            StdOut.println("- new size: " + getArraySize());
            Item[] newList = (Item[]) new Object[getArraySize()];
            int pos = 0;
            for (int i = 0; i < size; i++) {
                if (i >= tail)
                    pos = size;
                newList[i + pos] = list[i];
            }
            if (pos > 0)
                head += size;
            list = newList;
        }
        list[tail] = item;
        swap(list, tail, getRandomPos(size, head, tail));
        tail++;
        if (tail == getArraySize()) {
            tail = 0;
        }
        size++;
        printStatus("* enqueue");
    }

    /**
     * 
     * @param pos
     * @return
     */
    private int getArrayPos(int pos) {
        if (pos > tail) {
            return pos - tail;
        }
        return pos;
    }

    /**
     * 
     * @param i
     * @param j
     */
    private void swap(Item[] l, int i, int j) {
        Item a = l[i];
        l[i] = l[j];
        l[j] = a;
    }

    /**
     * 
     * @return
     */
    private int getRandomPos(int s, int h, int t) {
        if (h < t) {
            int pos = (int) (Math.random() * (t - h));
            return getArrayPos(pos + h);
        } else if (h > t) {
            int pos = (int) (Math.random() * (s - h + t));
            if (pos > t)
                return getArrayPos(pos + h);
            return getArrayPos(pos);
        }
        return h;
    }

    /**
     * 
     * @return
     */
    public Item dequeue() // remove and return a random item
    {
        if (size == 0)
            throw new java.util.NoSuchElementException();
        Item item = list[head];
        list[head] = null;
        head++;
        if (head == getArraySize()) {
            head = 0;
        }
        size--;
        if (size == (1 << (arraySize - 2))) {
            arraySize--;
            StdOut.println("- downsize: " + getArraySize());
            Item[] newList = (Item[]) new Object[getArraySize()];
            int pos = 0;
            for (int i = 0; i < getArraySize(); i++) {
                if ((i + getArraySize()) >= head)
                    pos = head;
                newList[i] = list[i + pos];
            }
            if (pos > 0) {
                head = 0;
                tail -= pos;
            }
            list = newList;
        }

        printStatus("dequeue");
        return item;
    }

    /**
     * 
     */
    private void printStatus(String msg) {
        StdOut.println(msg + " - size: " + size() + " head: " + head + " tail: "
                + tail + " list[head]: " + list[head]);
    }

    /**
     * 
     * @return
     */
    public Item sample() // return (but do not remove) a random item
    {
        return list[getRandomPos(size, head, tail)];
    }

    /**
    * 
    */
    public Iterator<Item> iterator() // return an independent iterator over
                                     // items in random order
    {
        RQIterator rqi = new RQIterator();
        rqi.list = (Item[]) new Object[size];
        int pos = 0;
        for (int i = 0; i < size; i++) {
            if (tail > head) {
                rqi.list[i] = list[head + i];
            } else {
                if (i >= tail)
                    pos = (head-tail);
                rqi.list[i] = list[i + pos];
                swap(rqi.list, i, getRandomPos(size, 0, i));
            }
        }
        return rqi;
    }

    /**
     * 
     * @param args
     */
    public static void main(String[] args) // unit testing
    {

    }
}
