import java.util.StringJoiner;

import edu.princeton.cs.algs4.StdOut;

public class InsertionSortTest {

    static int countCompare = 0;
    static int countExchange = 0;

    public static void main(String[] argv) {

        String[] s1 = { "LARD", "TOTO", "VAIN", "MUSE", "NOFX", "SOAD", "PINK",
                "CAKE", "MOBY", "WHAM", "NENA", "BUSH", "TOOL", "LIVE", "DEVO",
                "CHER" };

        Boolean[] b1 = { false, true, false, true, false, true, false, true,
                false, true, false, true, false, true , false, true , false, true  };

        Integer[] v1 = { 34, 56, 12, 79, 16, 80, 33, 69, 15, 31, 72, 42 };
        
        printArray(v1);
        
        Merge.sort(v1);

        StdOut.println("size: "+b1.length);
        StdOut.println("countCompare: "+countCompare);
        StdOut.println("countExchange: "+countExchange);
        

//        orderOfGrowth(10);
//        orderOfGrowth(20);
//        orderOfGrowth(30);
//        orderOfGrowth(40);
    }

    /**
     * @param size
     */
    private static void orderOfGrowth(int size) {
        Integer[]list = new Integer[size];
        int j = 1;
        for (int i = 0; i < size; i+=2) {
            list[i] = (j);
            list[i+1] = (j-1);
            j += 2;
        }
        insertionsort(list);
        StdOut.println("size: "+size);
        StdOut.println("countCompare: "+countCompare);
        StdOut.println("countExchange: "+countExchange);
    }

    public static void selectionsort(Comparable[] a) {
        printArray(a);
        int N = a.length;
        for (int i = 0; i < N; i++) {
            int min = i;
            for (int j = i + 1; j < N; j++) {
                if (less(a[j], a[min]))
                    min = j;
            }
            exch(a, i, min);
            printArray(a);
        }
    }

    public static <T> void printArray(T[] a) {
        StringJoiner joiner = new StringJoiner(" ");
        for (T cs : a) {
            joiner.add(cs.toString());
        }
        StdOut.println(joiner);
    }

    public static void insertionsort(Comparable[] a) {
        int N = a.length;
//        printArray(a);
        for (int i = 0; i < N; i++) {
            for (int j = i; j > 0 && less(a[j], a[j - 1]); j--) {
                exch(a, j, j - 1);
//                printArray(a);
            }
        }
    }

    public static void shellsort(Comparable[] a) {
        int N = a.length;

        // 3x+1 increment sequence: 1, 4, 13, 40, 121, 364, 1093, ...
        int h = 1;
        while (h < N / 3)
            h = 3 * h + 1;

        printArray(a);
        while (h >= 1) {
            // h-sort the array
            for (int i = h; i < N; i++) {
                StdOut.println(" i:" + i + " h:" + h);
                for (int j = i; j >= h && less(a[j], a[j - h]); j -= h) {
                    StdOut.println(" j:" + j + " j-h:" + (j - h) + " a[j]:"
                            + a[j] + " a[j-h]:" + a[j - h]);
                    exch(a, j, j - h);
                    printArray(a);
                }
            }
            h /= 3;
        }
    }

    private static boolean less(Comparable v, Comparable w) {
        countCompare++;
        return v.compareTo(w) < 0;
    }

    // exchange a[i] and a[j]
    private static void exch(Object[] a, int i, int j) {
        countExchange++;
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

}
