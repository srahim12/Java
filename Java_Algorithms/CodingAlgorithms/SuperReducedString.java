import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

    static String super_reduced_string(String s){
        if(s.equals("")){
            return "";
        }
        int letter_count = 1;
        char letter = s.charAt(0);
        String fin="";
        int len = s.length();
        if(len==2){
            if(s.charAt(0)==s.charAt(1)){
                return "";
            }
        }                                                //012345678
        for(int i = 0;i<len;i++){                       //aaabbcccd
                //System.out.print("i= "+ i + " " + "lc= " + letter_count + " " + "l= " + letter + " " + "fin= " + fin);
                if(s.charAt(i+1)==letter){
                    letter_count++;
                    if(i==len-2){
                        fin +=letter;
                        if(fin.length()==2){
                           if(s.charAt(0)==s.charAt(1)){
                                return "";
                            }   
                        }
                        return fin;
                    }
                }
                else{
                    if(i==len-2){
                        if(letter_count==2){
                            letter='\0';
                        }
                        fin += letter +"" + s.charAt(i+1);
                        if(fin.length()==2){
                           if(s.charAt(0)==s.charAt(1)){
                                return "";
                           }
                        }
                        return fin;
                    }
                    else{
                        if(letter_count==2){
                            letter='\0';
                        }
                        fin += letter;
                        letter=s.charAt(i+1);
                        letter_count=1;
                    }
                }
            //System.out.println();
        }
        if(fin.length()==2){
            if(s.charAt(0)==s.charAt(1)){
                return "";
            }
        }
        
        //System.out.println();
        return fin;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String s = in.next();
        String result = super_reduced_string(s);
        //System.out.println();
        System.out.println(result);
    }
}
