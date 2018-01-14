
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;

public class Solver {
    private final Board initialBoard;
    private SearchNode result;

    // inner class 
    private class SearchNode implements Comparable<SearchNode> {
        Board b;
        int moveMade;
        SearchNode prev;
        int priority;

        public SearchNode(Board b, int moveMade, SearchNode prev) {
            this.b = b;
            this.moveMade = moveMade;
            this.prev = prev;
            priority = b.manhattan() + moveMade;
        }

        /** Override method */
        public int compareTo(SearchNode node) {
            if (this.priority > node.priority) return 1;
            if (this.priority < node.priority) return -1;
            return 0;
        }
    }

    // constructor find a solution to the initial board
    public Solver(Board initial) {
        if (initial == null) throw new NullPointerException();
        initialBoard = initial;
        isSolvable();
    }

    /**
     * check to see if the initial board is solvable.
     * @return true if the initial board is solvable
     */
    public boolean isSolvable() {
        Board twin = initialBoard.twin();
        MinPQ<SearchNode> q1 = new MinPQ<SearchNode>();
        MinPQ<SearchNode> q2 = new MinPQ<SearchNode>();
        SearchNode node1 = new SearchNode(initialBoard, 0, null);
        SearchNode node2 = new SearchNode(twin, 0, null);
        q1.insert(node1);
        q2.insert(node2);
        while (true) {
            if (!q1.isEmpty()) {
                SearchNode node = q1.delMin();
                if (node.b.isGoal()) { 
                    result = node;
                    return true;
                }
                nextBoard(q1, node);
            }
            if (!q2.isEmpty()) {
                SearchNode node = q2.delMin();
                if (node.b.isGoal()) {
                    result = null;
                    return false;
                }
                nextBoard(q2, node);
            }
        }
    }

    /**
     * find all the neighbors of the board and insert them into the priority 
     * queue
     * @param q priority queue
     * @param node the deleted search node
     */
    private void nextBoard(MinPQ<SearchNode> q, SearchNode node) {
        Iterator<Board> it = node.b.neighbors().iterator();
        int move = node.moveMade + 1;
        while (it.hasNext()) {
            Board b = it.next();
            if (node.prev == null || !b.equals(node.prev.b)) {
                SearchNode newNode = new SearchNode(b, move, node);
                q.insert(newNode);
            }
        }
    }

    /**
     * @return the min number of moves to solve the initial board
     * return -1 if unsolvable
     */
    public int moves() {
        if (result == null) return -1;
        return result.moveMade;
    }

    /**
     * @return sequence of moves in a shortest solution
     * return null if unsolvable
     */
    public Iterable<Board> solution() {
        Stack<Board> stack = new Stack<Board>();
        SearchNode currentNode = result;
        while (currentNode != null) {
            stack.push(currentNode.b);
            currentNode = currentNode.prev;
        }
        return stack;
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }


}