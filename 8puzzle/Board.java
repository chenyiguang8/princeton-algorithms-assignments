
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdRandom;

public class Board {
    private final int[][] board;
    private final int n;

    public Board(int[][] blocks) {
        n = blocks.length;
        board = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = blocks[i][j];
            }
        }
    }

    /**
     * board dimension
     * @return board dimension
     */
    public int dimension() {
        return n;
    }

    /**
     * @return number of blocks out of place
     */
    public int hamming() {
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int goal = findGoal(i, j);
                if (board[i][j] != goal && board[i][j] != 0) count++;
            }
        }
        return count;
    }

    /**
     * @return sum of manhattan distance between blocks and goal
     */
    public int manhattan() {
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != 0) {
                    count += computemanhattan(i, j, board[i][j]);
                }
            }
        }
        return count;
    }

    /**
     * compute the manhattan distance
     * @param i the ith row 
     * @param j the jth col
     * @param goal the number
     */
    private int computemanhattan(int i, int j, int goal) {
        int row = (goal - 1) / n;
        int col = goal - row * n - 1;
        int distance = Math.abs(row - i) + Math.abs(col - j);
        return distance;
    }

    /**
     * compute the goal at i row, j col
     * @param ith row
     * @param jth col
     * @return goal
     */
    private int findGoal(int row, int col) {
        if (row == n - 1 && col == n - 1) return 0;
        return (row * n + col + 1);
    }
    /**
     * check to see if this board is the goal board
     * @return true if it's the goal board
     */
    public boolean isGoal() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int goal = findGoal(i, j);
                if (board[i][j] != goal) return false;
            }
        }
        return true;
    }

    /**
     * generate a board that is obtained by exchaning any pair of blocks
     * in this case exchang the first and the second item
     * @return a twin board
     */
    public Board twin() {
        int[][] b = copyOfBoard();
        int first;
        int second;
        while  (true) {
            first = randomPos();
            second = randomPos();
            if (first != second) break;
        }
        exchange(b, first, second);
        Board twin = new Board(b);
        return twin;
    }

    /**
     * generate a random position where there's a block
     * @return random position
     */
    private int randomPos() {
        int pos = 0;
        while (true) {
            pos = StdRandom.uniform(n * n);
            if (isBlock(pos)) break;
        }
        return pos;
    }

    /**
     * check to see if the thing at the pos is a block
     * @return true if it's a block
     */
     private boolean isBlock(int pos) {
        int row = pos / n;
        int col = pos - row * n;
        if (board[row][col] != 0) return true;
        return false;
     }

     /**
      * exchange a pair of blocks
      * @param the board to be exchanged
      * @param pos1
      * @param pos2
      */
     private void exchange(int[][] b, int pos1, int pos2) {
        int row1 = pos1 / n;
        int col1 = pos1 - row1 * n;
        int row2 = pos2 / n;
        int col2 = pos2 - row2 * n;
        b[row1][col1] = board[row2][col2];
        b[row2][col2] = board[row1][col1];
     }

    /**
     * check to see if the board is equal to y
     * @Override
     * @param object y
     * @return ture if they are equal
     */
    public boolean equals(Object y) {
        if (y instanceof Board) {
            Board b = (Board) y;
            return checkEqual(b, this);
        }
        return false;
    }

    /**
     * copy the board into a new board
     * @return a new board.
     */
    private int[][] copyOfBoard() {
        int[][] b = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                b[i][j] = board[i][j];
            }
        }
        return b;
    }
    /**
     * check the two board is the same
     * @param board1
     * @param board2
     * @return return true if they are the same
     */
    private boolean checkEqual(Board board1, Board board2) {
        if (board1.n != board2.n) return false;
        int[][] b1 = board1.board;
        int[][] b2 = board2.board;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (b1[i][j] != b2[i][j]) return false;
            }
        }
        return true;
    }

    /**
     * all neighboring boards
     * @return stack containing all neighboring boards
     */
    public Iterable<Board> neighbors() {
        Stack<Board> stack = new Stack<Board>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 0) {
                    stack = findNeighbors(i, j);
                    break;
                }
            }
        }
        return stack;
    }

    /**
     * find the neighbors at board[i][j]
     * @return stack
     */
    private Stack<Board> findNeighbors(int i, int j) {
        Stack<Board> stack = new Stack<Board>();
        // check above
        if (i - 1 >= 0) {
            int[][] b = copyOfBoard();
            b[i][j] = b[i - 1][j];
            b[i - 1][j] = 0;
            Board newBoard = new Board(b);
            stack.push(newBoard);
        }
        // check right
        if (j + 1 < n) {
            int[][] b = copyOfBoard();
            b[i][j] = b[i][j + 1];
            b[i][j + 1] = 0;
            Board newBoard = new Board(b);
            stack.push(newBoard);
        }
        // check bottom
        if (i + 1 < n) {
            int[][] b = copyOfBoard();
            b[i][j] = b[i + 1][j];
            b[i + 1][j] = 0;
            Board newBoard = new Board(b);
            stack.push(newBoard);
        }
        // check left
        if (j - 1 >= 0) {
            int[][] b = copyOfBoard();
            b[i][j] = board[i][j - 1];
            b[i][j - 1] = 0;
            Board newBoard = new Board(b);
            stack.push(newBoard);
        }
        return stack;
    }

    /**
     * string represention of this board
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", board[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
}