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
        solutions = solve(n, board);
        end = System.currentTimeMillis();
        // END MEASURING

        // Print result
        System.out.println("" + n + "-Queens: Found " + solutions + " Solutions in " + (end-start) + "ms!");
        //System.out.println("" + n + "-Queens: Found " + solutions + " Solutions in " + (end-start) + "ms! ["+reCount+" executions]");
    }

    public static int solve(int i, int[] board) {
        //reCount++;
        int max = board.length; // faster as field
        int sol = 0;
        //int y, x, col;


        if(i == max) {
            int odd     = (max) &1; // max%2
            int half    = max>>1;   // max/2

            // For every distinct solution exists another symmetric solution
            // mirrored around the Y-axis (middle).
            // So it is still possible to get all solutions by calculating only
            // combinations with the first queen placed on the left half.
            for(int y=0; y < half; y++) {
                board[0] = y;
                //System.out.println("1 " + y);
                sol += 2*solve(i-1, board.clone());
            }

            // If n is odd, the middle column must be treaded separately, because
            // no mirrored solution exists
            if(1 == odd) {
                board[0] = half;
                //System.out.println("h " + half);
                sol += solve(i-1, board);
            }
        } else {
            int y, x, col;
            int num = max - i;

            out: for(y=0; y < max; y++) {
                // Check for all existing Queens if they threaten the current
                // positions
                for(x=0; x < num; x++) {
                    col = board[x];
                    // same pos || num-x == abs(y-col) -> diagonal
                    if(col == y || num-x == y-col || num-x == -(y-col))
                        continue out;
                }

                if(i == 1) {
                    sol++;
                } else {
                    board[num] = y;
                    sol += solve(i-1, board.clone());
                }
                /* Slower :(
                } else if(y == max-1) {
                    board[num] = y;
                    sol += solve(i-1, board);
                } else {
                    int[] newBoard = new int[max];
                    System.arraycopy(board, 0, newBoard, 0, max);
                    //int[] newBoard = (y == max-1) ? board: board.clone();
                    newBoard[num] = y;
                    sol += solve(i-1, newBoard);
                }*/
            }
        }

        return sol;
    }
}
