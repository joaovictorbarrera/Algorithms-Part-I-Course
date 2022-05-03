import edu.princeton.cs.algs4.StdRandom;
import java.util.ArrayList;

public class Board {
    private final int[][] tiles;
    private Board twinBoardCached;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles == null || tiles.length != tiles[0].length || tiles.length < 2 || tiles.length > 128)
            throw new IllegalArgumentException();
        this.tiles = copy(tiles);
        this.twinBoardCached = null;
    }

    // string representation of this board
    public String toString() {
        StringBuilder display = new StringBuilder();
        display.append(Integer.toString(dimension()));
        int maxNumberPossible = (int) Math.pow(dimension(), 2) - 1;
        int space = String.valueOf(maxNumberPossible).length() + 1;

        for (int i = 0; i < this.tiles.length; i++) {
            display.append("\n");
            for (int j = 0; j < this.tiles[0].length; j++) {
                int curr = this.tiles[i][j];
                int spacesCurr = space - String.valueOf(curr).length();
                for (int k = 0; k < spacesCurr; k++) {
                    display.append(" ");
                }
                display.append(curr);
            }
        }
        return display.toString();
    }

    // number of tiles out of place
    public int hamming() {
        int count = 0;
        for (int i = 0; i < this.tiles.length; i++) {
            for (int j = 0; j < this.tiles[0].length; j++) {
                int curr = this.tiles[i][j];
                if (curr != 0 && i * dimension() + j + 1 != curr)
                    count++;
            }
        }
        return count;
    }

    // board dimension() n
    public int dimension() {
        return this.tiles.length;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int sum = 0;
        for (int i = 0; i < this.tiles.length; i++) {
            for (int j = 0; j < this.tiles[0].length; j++) {
                int curr = this.tiles[i][j];
                if (curr != 0) {
                    int desiredRow = (curr - 1) / dimension();
                    int desiredCol = (curr - 1) % dimension();
                    int verticalDis = Math.abs(i - desiredRow);
                    int horizontalDis = Math.abs(j - desiredCol);
                    sum += verticalDis + horizontalDis;
                }
            }
        }
        return sum;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this)
            return true;
        if (y == null || y.getClass() != this.getClass())
            return false;

        Board other = (Board) y;
        if (this.dimension() != other.dimension())
            return false;

        for (int i = 0; i < this.tiles.length; i++) {
            for (int j = 0; j < this.tiles[0].length; j++) {
                if (this.tiles[i][j] != other.tiles[i][j])
                    return false;
            }
        }

        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        // find blank tile location
        int blankRow = -1;
        int blankColumn = -1;
        for (int i = 0; i < this.tiles.length; i++) {
            for (int j = 0; j < this.tiles[0].length; j++) {
                if (this.tiles[i][j] == 0) {
                    blankRow = i;
                    blankColumn = j;
                    break;
                }
            }
        }
        if (blankRow < 0 || blankColumn < 0)
            throw new IllegalArgumentException("No blank tile found.");
        // check directions it can swap
        boolean up = blankRow > 0;
        boolean right = blankColumn < dimension() - 1;
        boolean down = blankRow < dimension() - 1;
        boolean left = blankColumn > 0;
        ArrayList<Board> neighborsList = new ArrayList<>();
        // construct boards with those swaps
        if (up) {
            Board neigh = makeNeighbor(blankRow, blankColumn, blankRow - 1, blankColumn);
            neighborsList.add(neigh);
        }

        if (right) {
            Board neigh = makeNeighbor(blankRow, blankColumn, blankRow, blankColumn + 1);
            neighborsList.add(neigh);
        }

        if (down) {
            Board neigh = makeNeighbor(blankRow, blankColumn, blankRow + 1, blankColumn);
            neighborsList.add(neigh);
        }

        if (left) {
            Board neigh = makeNeighbor(blankRow, blankColumn, blankRow, blankColumn - 1);
            neighborsList.add(neigh);
        }

        return neighborsList;
    }

    private int[][] copy(int[][] arr) {
        int[][] arrCopy = new int[arr.length][arr.length];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                arrCopy[i][j] = arr[i][j];
            }
        }
        return arrCopy;
    }

    private Board makeNeighbor(int r1, int c1, int r2, int c2) {
        Board neigh = new Board(this.tiles);
        neigh.swap(r1, c1, r2, c2);
        return neigh;
    }

    private void swap(int r1, int c1, int r2, int c2) {
        int temp = tiles[r1][c1];
        tiles[r1][c1] = tiles[r2][c2];
        tiles[r2][c2] = temp;
    }
    
    private Board makeTwin() {
        Board twinBoard = new Board(this.tiles);
        // find a random tile that is not blank
        int tileRow = 0;
        int tileCol = 0;
        do {
            tileRow = StdRandom.uniform(dimension());
            tileCol = StdRandom.uniform(dimension());
        } while (twinBoard.tiles[tileRow][tileCol] == 0);

        boolean swapped = false;
        while (!swapped) {
            // 0 up | 1 right | 2 down | 3 left
            int randDirection = StdRandom.uniform(4);
            switch (randDirection) {
            case 0:
                if (tileRow > 0 && twinBoard.tiles[tileRow - 1][tileCol] != 0) {
                    twinBoard.swap(tileRow, tileCol, tileRow - 1, tileCol);
                    swapped = true;
                }
                break;
            case 1:
                if (tileCol < twinBoard.dimension() - 1 && twinBoard.tiles[tileRow][tileCol + 1] != 0) {
                    twinBoard.swap(tileRow, tileCol, tileRow, tileCol + 1);
                    swapped = true;
                }
                break;
            case 2:
                if (tileRow < twinBoard.dimension() - 1 && twinBoard.tiles[tileRow + 1][tileCol] != 0) {
                    twinBoard.swap(tileRow, tileCol, tileRow + 1, tileCol);
                    swapped = true;
                }
                break;
            case 3:
                if (tileCol > 0 && twinBoard.tiles[tileRow][tileCol - 1] != 0) {
                    twinBoard.swap(tileRow, tileCol, tileRow, tileCol - 1);
                    swapped = true;
                }
                break;
            }
        }
        return twinBoard;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        if (this.twinBoardCached == null) {
            this.twinBoardCached = makeTwin();
        }
            
        return this.twinBoardCached;
    }
}
