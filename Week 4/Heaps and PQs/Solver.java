import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stack;

public class Solver {
    private boolean solvable;
    private SearchNode goal;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException("input is null");
        MinPQ<SearchNode> mainPQ = new MinPQ<>();
        MinPQ<SearchNode> twinPQ = new MinPQ<>();
        mainPQ.insert(new SearchNode(initial, null));
        twinPQ.insert(new SearchNode(initial.twin(), null));
        boolean alternate = true;
        while (!mainPQ.isEmpty()) {
            if (alternate) {
                SearchNode min = mainPQ.delMin();
                if (min.board.isGoal()) {
                    this.goal = min;
                    this.solvable = true;
                    break;
                }

                for (Board neigh : min.board.neighbors()) {
                    if (min.previous == null || !neigh.equals(min.previous.board)) {
                        mainPQ.insert(new SearchNode(neigh, min));
                    }
                }
                alternate = false;
            } else {
                SearchNode min = twinPQ.delMin();
                if (min.board.isGoal()) {
                    this.goal = null;
                    this.solvable = false;
                    break;
                }

                for (Board neigh : min.board.neighbors()) {
                    if (min.previous == null || !neigh.equals(min.previous.board)) {
                        twinPQ.insert(new SearchNode(neigh, min));
                    }
                }
                alternate = true;
            }
        }
    }

    private class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private final SearchNode previous;
        private final int moves;
        private final int manhattan;

        SearchNode(Board board, SearchNode previous) {
            this.board = board;
            this.previous = previous;
            this.moves = previous == null ? 0 : previous.moves + 1;
            this.manhattan = board.manhattan();
        }

        @Override
        public int compareTo(Solver.SearchNode other) {
            int priority = this.manhattan + this.moves;
            int otherPriority = other.manhattan + other.moves;

            if (priority > otherPriority)
                return 1;
            else if (priority < otherPriority)
                return -1;
            else if (this.manhattan > other.manhattan)
                return 1;
            else if (this.manhattan < other.manhattan)
                return -1;
            return 0;
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return this.solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (goal == null)
            return -1;
        return goal.moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (goal == null)
            return null;
        Stack<Board> stack = new Stack<>();
        SearchNode curr = goal;
        stack.push(curr.board);
        while (curr.previous != null) {
            stack.push(curr.previous.board);
            curr = curr.previous;
        }
        return stack;
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

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

/*
 * int[][] board2 = new int[][] { {0,2}, {1,3} }; int[][] solved3 = new int[][]
 * { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } }; int[][] board3 = new int[][] { {
 * 6, 4, 7 }, { 8, 5, 0 }, { 3, 2, 1 } }; int[][] board4 = new int[][] { {
 * 10,9,3,0 }, { 2,5,15,7 }, { 8,14,1,11 }, { 12,13,4,6 } }; // int[][] board4 =
 * new int[][] { // { 15, 14, 8, 12 }, // { 10, 11, 9, 13 }, // { 2, 6, 5, 1 },
 * // { 3, 7, 4, 0 } // }; // int[][] board4 = new int[][] { // { 0, 14, 13, 12
 * }, // { 11, 9, 10, 4 }, // { 7, 5, 6, 8 }, // { 3, 2, 1, 15 } // }; int[][]
 * solved5 = new int[][] { {1,2,3,4,5}, {6,7,8,9,10}, {11,12,13,14,15},
 * {16,17,18,19,20}, {21,22,23,24,0} }; Solver solver = new Solver(new
 * Board(board3)); if (solver.isSolvable()) { for(Board step :
 * solver.solution()) { StdOut.println(step); } StdOut.println("Solution takes "
 * + solver.moves() + " moves."); } else { StdOut.println("Impossible Puzzle!");
 * } // Board testSolved5 = new Board(solved5); // Board testBoard3 = new
 * Board(board3); // Board testSolved3 = new Board(solved3); //
 * StdOut.println(testSolved5); // StdOut.println(testSolved5.twin()); // for
 * (Board neighbor : testBoard4.neighbors()) { // StdOut.println("neighbor:");
 * // StdOut.println(neighbor); // for(Board neighborNeighbor :
 * neighbor.neighbors()) { // StdOut.println("neighbor of neighbor:"); //
 * StdOut.println(neighborNeighbor); // } // } //
 * StdOut.println(testBoard4.hamming()); //
 * StdOut.println(testBoard4.manhattan());
 */
