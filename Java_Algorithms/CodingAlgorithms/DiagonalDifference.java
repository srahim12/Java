import java.io.*;
import java.util.*;

public class DiagonalDifference {

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int a[][] = new int[n][n];
        int r_dag = 0,l_dag=0,difference=0;
        for(int a_i=0; a_i < n; a_i++){
            for(int a_j=0; a_j < n; a_j++){
                a[a_i][a_j] = in.nextInt();
            //    System.out.print(a[a_i][a_j]+ " ");
            }
            //System.out.println();
        }
        int j =0;
        for(int i=0;i<n;i++){
            r_dag+=a[i][j];
            j++;
        }
        j=n-1;
        for(int i=0;i<n;i++){
            l_dag+=a[i][j];
//            System.out.println(j);
            j--;
        }
        difference = Math.abs(r_dag-l_dag);
        System.out.println(difference);
        
        
        
    }
}
