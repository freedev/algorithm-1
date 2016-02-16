import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

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
            if (pos >= list.length)
                throw new NoSuchElementException();
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
        list = createArray(1);
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

    /**
     * 
     * @return
     */
    private int getArraySize() {
        return 1 << arraySize;
    }

    /**
     * 
     * @param item
     */
    public void enqueue(Item item) // add the item
    {
        // StdOut.println("enqueue - head: "+head+ " tail: "+tail);
        int curSize = size();
        if (getArraySize() == curSize) {
            arraySize++;
            Item[] newList = createArray(getArraySize());
            if (head < tail) {
                for (int i = 0; (i + head) < tail; i++) {
                    newList[i] = list[i + head];
                }
                tail = tail - head;
                head = 0;
            } else {
                for (int i = 0; i < tail; i++) {
                    newList[i] = list[i];
                }
                for (int i = head; i < list.length; i++) {
                    newList[i + list.length] = list[i];
                }
                head = head + list.length;
            }
            list = newList;
        }
        list[tail] = item;
        if (size > 0) {
            int rPos = getRandomPos(size, head, tail);
            swap(list, tail, rPos);
        }
        tail++;
        if (tail == getArraySize()) {
            tail = 0;
        }
        size++;
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
        int pos = StdRandom.uniform(s);
        if (h < t) {
            return (pos + h);
        } else {
            if (pos > t)
                return pos + (h - t - 1);
        }
        return pos;
    }

    /**
     * 
     * @return
     */
    public Item dequeue() // remove and return a random item
    {
        // StdOut.println("dequeue - head: "+head+ " tail: "+tail);
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
            Item[] newList = createArray(getArraySize());
            if (tail > head) {
                for (int i = 0; (head + i) < tail; i++) {
                    newList[i] = list[head + i];
                }
                tail = tail - head;
                head = 0;
            } else {
                for (int i = 0; i < tail; i++) {
                    newList[i] = list[i];
                }
                for (int i = head; i < list.length; i++) {
                    newList[i - newList.length] = list[i];
                }
                head -= newList.length;
            }
            list = newList;
        }

        return item;
    }

    /**
     * 
     * @return
     */
    public Item sample() // return (but do not remove) a random item
    {
        if (size == 0)
            throw new java.util.NoSuchElementException();
        int pos = 0;
        if (size > 1) {
            pos = getRandomPos(size, head, tail - 1);
            if (pos >= list.length) {
                StdOut.println(pos + " size: " + size + " head: " + head
                        + " tail: " + tail);
            } else if (list[pos] == null) {
                StdOut.println(pos + " size: " + size + " head: " + head
                        + " tail: " + tail);
            }
            return list[pos];
        }
        return list[head];
    }

    /**
     * 
     * @param s
     * @return
     */
    private Item[] createArray(int s) {
        return (Item[]) new Object[s];
    }

    /**
    * 
    */
    public Iterator<Item> iterator() // return an independent iterator over
                                     // items in random order
    {
        RQIterator rqi = new RQIterator();
        rqi.list = createArray(size);
        int pos = 0;
        for (int i = 0; i < size; i++) {
            if (tail > head) {
                rqi.list[i] = list[head + i];
            } else {
                if (i >= tail)
                    pos = (head - tail);
                rqi.list[i] = list[i + pos];
            }
            if (i > 1)
                swap(rqi.list, i, getRandomPos(i, 0, i - 1));
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
