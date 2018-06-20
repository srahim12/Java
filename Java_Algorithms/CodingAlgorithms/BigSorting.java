import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class BigSorting {
    private static BigInteger[] arr;
    private static BigInteger[] help;
    private static int num;
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        num=in.nextInt();
        String[] unsorted = new String[num];
        arr = new BigInteger[num];
        for(int unsorted_i=0; unsorted_i < num; unsorted_i++){
            unsorted[unsorted_i] = in.next();
        }

        for(int i = 0;i<num;i++){
            arr[i]= new BigInteger(unsorted[i]);
        }
        printArr(arr);
        
        //mergeSort(arr,0,n-1);
        


    }

    public static void mergeSort(int[] arr, int low, int high){
		
		
    }



    public static void printArr(BigInteger[] arr){
        for(int i = 0;i<arr.length;i++){
            //array[i] = Integer.parseInt(arr[i]);
            System.out.println(arr[i]);
        }
    }

}
