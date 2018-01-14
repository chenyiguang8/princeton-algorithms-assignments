
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> q = new RandomizedQueue<String>();
        int n = Integer.parseInt(args[0]);
        while (!StdIn.isEmpty()) {
            String str = StdIn.readString();
            q.enqueue(str);
        }
        Iterator<String> it = q.iterator();
        for (int i = 0; i < n; i++) {
            StdOut.println(it.next());
        }
    }
}
 
 