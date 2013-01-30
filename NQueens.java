/* Calculate number of Solutions for N-Queens problem
 */
package nqueens;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 *
 * @author Julien Schmidt <mail at julienschmidt.com>
 */
public class NQueens {
    public static int reCount = 0;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int n = 0;

        // Loop until a valid value for n is entered
        // N=1 isn't possible because of the separately treatment of the first
        // queen
        while (n < 2) {
            try {
                System.out.println("Please insert a integer value for N >1:");
                BufferedReader br = new BufferedReader(
                    new InputStreamReader(System.in)
                );
                n = Integer.parseInt(br.readLine());
            } catch (NumberFormatException ex) {
                System.out.println("Not a number !");
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        System.out.println("Calculating " + n + "-Queens ...");

        int[] board = new int[n];

        long start, end;
        int solutions;

        // START MEASURING
        start = System.currentTimeMillis();
        solutions = solveNQueens(n);
        end = System.currentTimeMillis();
        // END MEASURING

        // Print result
        System.out.println("" + n + "-Queens: Found " +
            solutions + " Solutions in " + (end - start) + "ms!");
        //System.out.println("" + n + "-Queens: Found " + solutions +
        //  " Solutions in " + (end-start) + "ms! ["+reCount+" executions]");
    }

    /**
     * Calculates number of possible solutions for n queens on n*n sized board
     *
     * @param n queens count
     * @return  solutions count
     */
    private static int solveNQueens(int n) {
        //reCount++;
        int sol = 0;
        int med = n >> 1; // n/2
        int col;

        // For every distinct solution exists another symmetric solution
        // mirrored around the Y-axis (middle).
        // So it is still possible to get all solutions by calculating only
        // combinations with the first queen placed on the left half.
        for (int y = 0; y < med; y++) {
            col = 1 << y; // set bit on index y

            // Count solutions twice (solution + mirrored solution).
            // col >>> 1 | shift col values 1 index to right and discards
            //      remainder (unsigned right shift).
            // col << 1 | shift col values 1 index to left.
            sol += 2 * findPos(1, n, col, col >>> 1, col << 1);
        }

        // If n is odd, the middle column must be treaded separately, because
        // the solution mirrored around the Y-axis is the same es the solution
        // itself
        if (1 == ((n) & 1)) { // (n) & 1 <=> n%2, 1 if n is odd
            col = 1 << med; // set bit on index med

            // col >>> 1 | shift col values 1 index to right and discards
            //      remainder (unsigendes right shift).
            // col << 1 | shift col values 1 index to left.
            sol += findPos(1, n, col, col >>> 1, col << 1);
        }

        return sol;
    }

    /**
     * Rekursive method to find all possible places for the next queen
     *
     * @param max   Total queens count
     * @param num   Count of queens already placed
     * @param bmCol BitMask for columns with existing queens
     * @param bmDL  BitMask for diagonals from (top) left with existing queens
     * @param bmDR  BitMask for diagonals from (top) right with existing queens
     * @return      number of solutions found
     */
    private static int findPos(int num, int max, int bmCol, int bmDL, int bmDR) {
        int sol = 0;
        int mask = bmCol | bmDL | bmDR;
        int y, col;

        // Try all y-positions
        for (y = 0; y < max; y++) {
            col = 1 << y;

            // Queen can't be placed in the same column or a diagonal with an
            // existing queen
            if(0 != (col & mask))
                continue;

            // Check if there are no more queens to be placed
            if (num+1 == max) {
                // No more queens, increment solutions count
                sol++;
            }  else {
                // More queens must be placed, make a recursive call for the
                // next queen
                sol += findPos(
                    num+1,
                    max,
                    bmCol | col,
                    (bmDL | col) >>> 1,
                    (bmDR | col) << 1
                );
            }
        }

        return sol;
    }
}
