
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * 
 */

/**
 * @author freedev
 *
 */
public class Subset {

    /**
     * @param args
     */
    public static void main(String[] args) {
        if (args.length > 0) {
            RandomizedQueue<String> rq = new RandomizedQueue<>();
            int nParams = Integer.parseInt(args[0], 10);
            while (!StdIn.isEmpty()) {
               String p = StdIn.readString();
               if (p != null && p.trim().length() > 0)
               rq.enqueue(p);
            }
            while (!rq.isEmpty() && (nParams-- > 0)) {
                StdOut.println(rq.dequeue());
            }
        }
    }

}
