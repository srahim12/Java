import java.io.*;
import java.util.*;

public class PlusMinus {

    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        int size,neg=0,pos=0,zer=0,k;
        int[] arr;
        float[] val = new float[3];
        size = kb.nextInt();
        arr = new int[size];
        for(int i = 0;i<size;i++){
            arr[i] = kb.nextInt();            
        }
        for(int i = 0;i<size;i++){
            if(arr[i]>0){pos++;}
            else if(arr[i]<0){neg++;}
            else{zer++;}
            if(i==size-1){
                val[0]=(float)pos;
                val[1]=(float)neg;
                val[2]=(float)zer;
            }
        }
        for(int i = 0;i<val.length;i++){
            System.out.printf("%.6f",(val[i]/size));
            System.out.println();
        }                
    }
    
}
