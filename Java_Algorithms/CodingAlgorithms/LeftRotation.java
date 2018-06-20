import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

class Node{
    public Node next;
    public int data;
    
    public Node(Node next,int data){
        this.next = next;
        this.data = data;
    }
}
class CircleList{
    public Node tail;
    public CircleList(){
        this.tail = null;
    }
    public void insert(int val){
        Node n = new Node(null,val);
        Node temp;
        if(tail==null){
            tail=n;
            tail.next = tail;
            return;
        }
        else{
            temp = tail.next;
            tail.next = n;
            n.next = temp;
            tail = n;
        }
        return;
    }
    public Node getTail(){
        return tail;
    }
    public void printList(Node pr){
        Node ptr = pr.next;
        System.out.print(ptr.data + " ");
        ptr = ptr.next;
        while(ptr!=pr.next){
            System.out.print(ptr.data + " ");
            ptr = ptr.next;
        }
    }
 }
public class LeftRotation {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int k = in.nextInt();
        int a[] = new int[n];
        for(int a_i=0; a_i < n; a_i++){
            a[a_i] = in.nextInt();
        }
        CircleList cl = new CircleList();
        for(int i = 0;i<n;i++){
            cl.insert(a[i]);
        }
        Node ptr = cl.getTail();
        int iteration = n-k;
        for(int i =0;i<k;i++){
            ptr = ptr.next;
        }
        cl.printList(ptr);
    }
}
