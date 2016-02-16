import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import junit.framework.Assert;

public class RandomizedQueueTest {

    @Test
    public void sampleTest() {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();

        for (int i = 0; i < 100000; i++) {
            rq.enqueue(i);
        }

        for (int i = 0; i < 100000; i++) {
            Assert.assertNotNull(rq.sample());
        }
    }

    @Test
    public void sampleTest2() {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        
        int size = 3;
        
        for (int i = 0; i < size; i++) {
            rq.enqueue(i);
        }
        
        int[] count = new int[size];
        
        for (int i = 0; i < 100000; i++) {
            Integer c = rq.sample();
            count[c] ++;
            Assert.assertNotNull(c);
        }
        
        for (int i = 0; i < size; i++) {
            Assert.assertTrue(count[i] > 0);
        }
        
    }

    @Test
    public void randomEnqueueDequeTest() {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();

        for (int i = 0; i < 1000000; i++) {
            if (StdRandom.uniform() > 0.7)
                rq.enqueue(i);
            if (!rq.isEmpty() && StdRandom.uniform() <= 0.7)
                rq.dequeue();
            if (!rq.isEmpty())
                Assert.assertNotNull(rq.sample());
        }

        for (int i = 0; i < 1000000; i++) {
            if (StdRandom.uniform() > 0.2)
                rq.enqueue(i);
            if (!rq.isEmpty() && StdRandom.uniform() <= 0.2)
                rq.dequeue();
            if (!rq.isEmpty())
                Assert.assertNotNull(rq.sample());
        }

        while (!rq.isEmpty()) {
            rq.dequeue();
            if (!rq.isEmpty())
                Assert.assertNotNull(rq.sample());
        }
    }

    @Test
    public void enqueueTest() {

        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        List<Integer> list = new ArrayList<>();
        int size = 5;

        // rq.enqueue(0);
        // rq.enqueue(1);
        // rq.enqueue(2);
        // rq.enqueue(3);
        // StdOut.println("size: "+ rq.size());
        // StdOut.println(rq.dequeue());
        // StdOut.println("size: "+ rq.size());
        // StdOut.println(rq.dequeue());
        // StdOut.println("size: "+ rq.size());
        // StdOut.println(rq.dequeue());
        // StdOut.println("size: "+ rq.size());
        // StdOut.println(rq.dequeue());
        // StdOut.println("size: "+ rq.size());
        // rq.enqueue(0);
        // rq.enqueue(1);
        // rq.enqueue(2);
        // rq.enqueue(3);
        // StdOut.println("size: "+ rq.size());
        // StdOut.println(rq.dequeue());
        // StdOut.println("size: "+ rq.size());

        for (int i = 0; i < size; i++) {
            rq.enqueue(i);
            if (i % 5 == 0)
                list.add(rq.dequeue());
        }
        // for (int i = 0; i < 10000; i++) {
        // if (StdRandom.uniform() > 0.3)
        // rq.enqueue(i);
        //// if (!rq.isEmpty() && StdRandom.uniform() > 0.7)
        //// list.add(rq.dequeue());
        // }
        while (!rq.isEmpty()) {
            list.add(rq.dequeue());
        }
        list.sort(Integer::compareTo);
        for (int i = 0; i < size; i++) {
            Assert.assertEquals("", (Integer) i, (Integer) list.get(i));
        }

    }

    @Test
    public void iteratorTest() {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        List<Integer> list = new ArrayList<>();
        int size = 1000000;

        for (int i = 0; i < size; i++) {
            rq.enqueue(i);
            if (!rq.isEmpty() && i % 5 == 0)
                list.add(rq.dequeue());
        }

        Iterator<Integer> ii = rq.iterator();

        while (ii.hasNext()) {
            list.add(ii.next());
        }

        list.sort(Integer::compareTo);
        for (int i = 0; i < size; i++) {
            Assert.assertEquals("", (Integer) i, (Integer) list.get(i));
        }
    }

    @Test
    public void iteratorTest2() {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        List<Integer> list = new ArrayList<>();
        int size = 1000000;

        for (int i = 0; i < size; i++) {
            if (StdRandom.uniform() > 0.5) {
                rq.enqueue(i);
                list.add(i);
            }
            if (!rq.isEmpty() && StdRandom.uniform() > 0.3)
                rq.dequeue();
        }

        for (int i = 0; i < size; i++) {
            if (StdRandom.uniform() > 0.3) {
                rq.enqueue(i);
                list.add(i);
            }
            if (!rq.isEmpty() && StdRandom.uniform() > 0.3)
                rq.dequeue();
        }

        Iterator<Integer> ii = rq.iterator();

        while (ii.hasNext()) {
            list.add(ii.next());
        }

    }
}
