import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import junit.framework.Assert;

public class RandomizedQueueTest {
    
    @Test
    public void enqueueTest() {
        
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        List<Integer> list = new ArrayList<>();
        
        for (int i = 0; i < 1000; i++) {
            rq.enqueue(i);
            if (i % 7 == 0 || i % 5 == 0)
                list.add(rq.dequeue());
        }
        while (!rq.isEmpty()) {
            list.add(rq.dequeue());
        }
        list.sort(Integer::compareTo);
        for (int i = 0; i < 1000; i++) {
            Assert.assertEquals("", (Integer)i, (Integer)list.get(i));
        }

    }

    @Test
    public void iteratorTest() {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        List<Integer> list = new ArrayList<>();
        
        for (int i = 0; i < 1000; i++) {
            rq.enqueue(i);
        }
        
        Iterator<Integer> l = rq.iterator();
        while (l.hasNext()) {
            list.add(l.next());
        }
        list.sort(Integer::compareTo);
        for (int i = 0; i < 1000; i++) {
            Assert.assertEquals("", (Integer)i, (Integer)list.get(i));
        }
    }

}
