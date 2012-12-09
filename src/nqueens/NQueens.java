/* Calculate number of Solutions for N-Queens problem
 */
package nqueens;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * N-Queens Solver
 * 
 * @author  Julien Schmidt (student4801) <mail at julienschmidt.com>
 * @version 08.12.2012
 */
public class NQueens {
    //private static long reCount;
    
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

        long start, end;
        int solutions;
        
        // START MEASURING
        start = System.currentTimeMillis();
        solutions = solveNQueens(n);
        end = System.currentTimeMillis();
        // END MEASURING
        
        // Print result
        System.out.println("" + n + "-Queens: Found " + solutions +
            " Solutions in " + (end - start) + "ms!");
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
        int sol     = 0; 
        int med     = n >> 1;       // n/2
        int nMask   = (1 << n) - 1; // n-long bitmask filled with 1s
        int col;

        // For every distinct solution exists another symmetric solution
        // mirrored around the Y-axis (middle).
        // So it is still possible to get all solutions by calculating only 
        // combinations with the first queen placed on the left half.
        for (int y = 0; y < med; y++) {
            col = 1 << y; // set bit on index y
            
            // Count solutions twice (solution + mirrored solution).
            // col >>> 1 : shift col values 1 index to right and discards
            //      remainder (unsigned right shift).
            // col << 1  : shift col values 1 index to left.
            sol += (findPos(nMask, col, col >>> 1, col << 1) << 1);
        }    

        // If n is odd, the middle column must be treaded separately, because
        // no mirrored solution exists
        if (1 == ((n) & 1)) {   // (n) & 1 <=> n%2, 1 if n is odd
            col = 1 << med;     // set bit on index med
            
            // col >>> 1 : shift col values 1 index to right and discards
            //      remainder (unsigendes right shift).
            // col << 1  : shift col values 1 index to left.
            sol += findPos(nMask, col, col >>> 1, col << 1);
        }
        
        return sol;
    }
    
    /**
     * Recursive method to find all possible places for the next queen
     * 
     * @param nMask bitmask filled with 1s and n-length
     * @param bmRow bitmask for rows with existing queens
     * @param bmDL  bitmask for diagonals from (top) left with existing queens
     * @param bmDR  bitmask for diagonals from (top) right with existing queens
     * @return      number of solutions found
     */
    private static int findPos(int nMask, int bmRow, int bmDL, int bmDR) {    
        //reCount++;
        int sol = 0;
        int y, newBmRow;
        
        // bitmask with 1s on index of y-positions where placing a queen is
        // possible
        int yMask = nMask & ~(bmRow | bmDL | bmDR);
        
        // Branch all possible y-positions
        while(yMask > 0) {
            y           = -yMask & yMask;   // Y-position
            newBmRow    = bmRow | y;        // new row-bitmask
            
            // Remove this Y-position from the yMask for the next run
            yMask ^= y;
            
            // Check if there are more queens to be placed
            if (newBmRow < nMask) {
                // More queens must be placed, make a recursive call for the
                // next queen
                sol += findPos(
                    nMask,
                    newBmRow,
                    (bmDL | y) >>> 1,
                    (bmDR | y) << 1
                );
                continue;
            
            } else {
                // No more queens, increment solutions count
                sol++;
            }
        }

        return sol;
    }
}