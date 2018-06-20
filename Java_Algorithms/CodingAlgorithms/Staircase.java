import java.io.*;
import java.util.*;

public class Staircase {

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner kb = new Scanner(System.in);
        int n = kb.nextInt();
        String[] hash = new String[n];
        
        for(int i = 0;i<n;i++){
            if(i==n-1){hash[i] = "#";}
            else{hash[i] = " ";}
        }
        /*for(int j = 0;j<n;j++){
            System.out.print(hash[j]);
            
        }*/
        System.out.println();
        for(int i = n-1;i>=0;i--){
            hash[i] = "#";
            for(int j = 0;j<n;j++){
                System.out.print(hash[j]);
            }
            System.out.println();
        }       
        
    } 
    
}
