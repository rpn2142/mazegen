import java.util.Arrays;
import java.util.Random;


public class MazeGenerator {

   public int[][] printMaze(int n) {
       int[] parents = new int[n*n];
       for( int i=0; i<parents.length; i++)
           parents[i] = i;
       int count = parents.length;
       Random random = new Random();
       int max = parents.length*4;
       int[][] cells = new int[n][n];
       for( int[] row : cells)
       Arrays.fill(row, 15);

       while(count > 1 ) {
           int randomWall = random.nextInt(max);

           int cell = randomWall/4;

           int i = cell/n;
           int j = cell - i*n;
           int wall = randomWall%4;

           switch(wall) {
               case 0:
                   if( j-1 >= 0 && find(parents, cell) != find(parents, cell -1)) {
                       count = union(parents, cell, cell - 1, count);
                       cells[i][j] &= 0b1110;
                       cells[i][j-1] &= 0b1011;
                   }
                   break;
               case 1:
                   if( i+1 < n && find(parents, cell) != find(parents, cell +n) ) {
                       count = union(parents, cell, cell + n, count);
                       cells[i][j] &= 0b1101;
                       cells[i+1][j] &= 0b0111;
                   }
                   break;
               case 2:
                   if( j+1 < n && find(parents, cell) != find(parents, cell +1)) {
                       count = union(parents, cell, cell + 1, count);
                       cells[i][j] &= 0b1011;
                       cells[i][j+1] &= 0b1110;
                   }
                   break;
               case 3:
                   if( i-1 >= 0 && find(parents, cell) != find(parents, cell -n)) {
                       count = union(parents, cell, cell - n, count);
                       cells[i][j] &= 0b0111;
                       cells[i-1][j] &= 0b1101;
                   }
                   break;
           }

       }
    return cells;
   }


    private static int union(int[] parent, int i, int j, int count) {
        int parentI = find(parent, i);
        int parentJ = find(parent, j);

        if( parentI != parentJ )
            count--;

        parent[parentJ] = parentI;

        return count;
    }

    private static int find(int[] parent, int i) {
        if( parent[i] != i )
            parent[i] = find(parent, parent[i]);

        return parent[i];
    }

}
