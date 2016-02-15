import java.util.Iterator;

/**
 * 
 */

/**
 * @author freedev
 *
 */
public class Deque<Item> implements Iterable<Item> {

    private LLNode head;
    private LLNode tail;
    private int size;
    
    /**
     * 
     * @author freedev
     *
     */
    private class LLNode implements Iterator<Item>
    {
        LLNode prev;
        LLNode next;
        Item data;

        /**
         * 
         * @param e
         */
        public LLNode(Item e) 
        {
            this.data = e;
            this.prev = null;
            this.next = null;
        }

        /**
         * 
         * @param e
         * @param p
         * @param n
         */
        public LLNode(Item e, LLNode p, LLNode n) 
        {
            this.data = e;
            this.prev = p;
            this.next = n;
        }

        
        /**
         * 
         */
        @Override
        public boolean hasNext() {
            return (next.next != null);
        }

        /**
         * 
         */
        @Override
        public Item next() {
            if (data == null)
                throw new java.util.NoSuchElementException();
            Item d = data;
            next = next.next;
            data = next.data;
            return d;
        }
        
    }

    /**
     * 
     */
    public Deque() {
        head = new LLNode(null);
        tail = new LLNode(null);
        head.next = tail;
        tail.prev = head;
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
    }
    
    /**
     * 
     * @return
     */
    public boolean isEmpty()                 // is the deque empty?
    {
        return size == 0;
    }
    
    /**
     * 
     * @return
     */
    public int size()                        // return the number of items on the deque
    {
        return this.size;
    }
    
    /**
     * 
     * @param item
     */
    public void addFirst(Item item)          // add the item to the front
    {
        addNode(head, head.next, item); 
    }

    /**
     * Insert a node between two nodes
     * @param _head The prev node 
     * @param _tail The next node 
     * @param node The node to add
     */
    private void addNode(LLNode _head, LLNode _tail, Item element) 
    {       
        if (element == null)
            throw new NullPointerException();
        LLNode node = new LLNode(element, _head, _tail);
        _head.next = node;
        _tail.prev = node;
        size++;
    }
    
    /**
     * 
     * @param item
     */
    public void addLast(Item item)           // add the item to the end
    {
        addNode(tail.prev, tail, item);
    }
    
    /**
     * 
     * @return
     */
    public Item removeFirst()                // remove and return the item from the front
    {
        if (head.next != tail) {
            size--;
            LLNode l = head.next;
            head.next = l.next;
            head.next.prev = head;
            return l.data;
        }
        throw new java.util.NoSuchElementException();
    }
    
    /**
     * 
     * @return
     */
    public Item removeLast()                 // remove and return the item from the end
    {
        if (tail.prev != head) {
            size--;
            LLNode l = tail.prev;
            tail.prev = l.prev;
            tail.prev.next = tail;
            return l.data;
        }
        throw new java.util.NoSuchElementException();
    }

    /**
     * 
     */
    
    @Override
    public Iterator<Item> iterator() {
        if (head.next != tail) {
            LLNode l = new LLNode(head.next.data);
            l.next = head.next;
            return l;
        }
        LLNode l = new LLNode(head.data);
        l.next = head.next;
        return l;
    }

}
