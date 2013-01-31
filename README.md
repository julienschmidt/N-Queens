N-Queens
========

Submitted version of my program for the *N-Queens speed-challange* as part of the programmer internship at Technische Universität München

Progress from trivial to fast
-----------------------------
Since this was the winning submission (category "non threaded") I added a [`progress` branch](https://github.com/JulienSchmidt/N-Queens/commits/progress) where you can relive the way from the trivial algorithm to the optimized (fast) algoritm.

You can browse the changes here: [`progress`](https://github.com/JulienSchmidt/N-Queens/commits/progress)


Known bugs
----------
* Typo at src/nqueens/NQueens.java line 28


Bytecode generated from the performance-critical function
--------------------------------------------------------------
    private static int findPos(int, int, int, int);
      Code:
         0: iconst_0
         1: istore        4
         3: iload_0
         4: iload_1
         5: iload_2
         6: ior
         7: iload_3
         8: ior
         9: iconst_m1
        10: ixor
        11: iand
        12: istore        7
        14: iload         7
        16: ifle          78
        19: iload         7
        21: ineg
        22: iload         7
        24: iand
        25: istore        5
        27: iload_1
        28: iload         5
        30: ior
        31: istore        6
        33: iload         7
        35: iload         5
        37: ixor
        38: istore        7
        40: iload         6
        42: iload_0
        43: if_icmpge     72
        46: iload         4
        48: iload_0
        49: iload         6
        51: iload_2
        52: iload         5
        54: ior
        55: iconst_1
        56: iushr
        57: iload_3
        58: iload         5
        60: ior
        61: iconst_1
        62: ishl
        63: invokestatic  #31                 // Method findPos:(IIII)I
        66: iadd
        67: istore        4
        69: goto          14
        72: iinc          4, 1
        75: goto          14
        78: iload         4
        80: ireturn
        
