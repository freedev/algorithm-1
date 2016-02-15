import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;

import edu.princeton.cs.algs4.StdOut;

public class DequeTest {

    @Test
    public void sizeAddFirstRemoveFirstTest() {
        Deque<Long> d = new Deque<>();
        
        Long l = new Long(13);
        
        Assert.assertEquals(0, d.size());
        d.addFirst(10L);
        Assert.assertEquals(1, d.size());
        d.addFirst(11L);
        Assert.assertEquals(2, d.size());
        d.addFirst(12L);
        Assert.assertEquals(3, d.size());
        d.addFirst(13L);
        Assert.assertEquals(4, d.size());
        
        l = 13L;
        Assert.assertEquals(l, d.removeFirst());
        Assert.assertEquals(3, d.size());
        l = 12L;
        Assert.assertEquals(l, d.removeFirst());
        Assert.assertEquals(2, d.size());
        l = 11L;
        Assert.assertEquals(l, d.removeFirst());
        Assert.assertEquals(1, d.size());
        l = 10L;
        Assert.assertEquals(l, d.removeFirst());
        Assert.assertEquals(0, d.size());
        
        try {
            d.removeFirst() ;
            Assert.assertEquals("Shouldn't reach this line", 0, 1);
        } catch (java.util.NoSuchElementException e) {
        }
        
        
    }

    @Test
    public void sizeAddLastRemoveLastTest() {
        Deque<Long> d = new Deque<>();
        
        Long l = new Long(13);
        
        Assert.assertEquals(0, d.size());
        d.addLast(10L);
        Assert.assertEquals(1, d.size());
        d.addLast(11L);
        Assert.assertEquals(2, d.size());
        d.addLast(12L);
        Assert.assertEquals(3, d.size());
        d.addLast(13L);
        Assert.assertEquals(4, d.size());
        
        l = 13L;
        Assert.assertEquals(l, d.removeLast());
        Assert.assertEquals(3, d.size());
        l = 12L;
        Assert.assertEquals(l, d.removeLast());
        Assert.assertEquals(2, d.size());
        l = 11L;
        Assert.assertEquals(l, d.removeLast());
        Assert.assertEquals(1, d.size());
        l = 10L;
        Assert.assertEquals(l, d.removeLast());
        Assert.assertEquals(0, d.size());

        try {
            d.removeLast() ;
            Assert.assertEquals("Shouldn't reach this line", 0, 1);
        } catch (java.util.NoSuchElementException e) {
        }
        

    }
    
    @Test
    public void testIterator() {
        
        Deque<Long> d = new Deque<>();
        
        Long l = new Long(13);
        
        Assert.assertEquals(0, d.size());
        d.addLast(10L);
        Assert.assertEquals(1, d.size());
        d.addLast(11L);
        Assert.assertEquals(2, d.size());
        d.addLast(12L);
        Assert.assertEquals(3, d.size());
        d.addLast(13L);
        Assert.assertEquals(4, d.size());

        Iterator<Long> i = d.iterator();
        
        while (i.hasNext()) {
            StdOut.println(i.next());
        }
        
        try {
            i.next() ;
            Assert.assertEquals("Shouldn't reach this line", 0, 1);
        } catch (java.util.NoSuchElementException e) {
        }
        
    }

}
