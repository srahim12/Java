import java.util.List;
import java.util.ArrayList;

public class StackOfPlates {

    public static void main(String[] args){
        SetOfStacks ss = new SetOfStacks(3);
        ss.push(2); 
        ss.push(3);
        ss.push(4);
        ss.push(5);
        ss.push(6);
        ss.push(7);
        ss.push(8);
        ss.push(9);
        System.out.println(ss.getSetSize());
        System.out.println(ss.popAt(3));
    }   
}
class SetOfStacks{
    
    private static List<Stack> stackSet = new ArrayList<>();    
    private static int threshold;
    private static int setSize;
    
    public SetOfStacks(int threshold){
        this.threshold = threshold;
        setSize = 0;
    }
    
    public static void push(int item){
        if(setSize == 0 ||stackSet.get(setSize-1).getSize()==threshold){
            Stack s = new Stack();
            s.push(item);
            stackSet.add(s);
            setSize++;
        }
        else if(stackSet.get(setSize-1).getSize()<threshold){
            stackSet.get(setSize-1).push(item);        
        }
    }
    
    public static int[] pop(){
        int[] arr = new int[setSize];
        for(int i = 0;i<setSize;i++){
            arr[i] = stackSet.get(i).pop();
        }
        return arr;
    }
    
    public void printArr(int[] arr){
        for(int i = 0;i<arr.length;i++){
            System.out.print(arr[i] + ",");
        }
        System.out.println();    
    }
    
    public int popAt(int index){
        if(index >= setSize+1) return -1000;
        return stackSet.get(index-1).pop();
    }
    
    public static int getSetSize(){
        return setSize;
    }
}
class Stack{
    private static class StackNode{
        private int data;
        private StackNode next;
        
        public StackNode(int data) {
            this.data = data;
        }
    }
    
    private StackNode top;
    private StackNode min;
    private int size = 0;    

    public int pop(){
        if(top == null) return -1;
        int item = top.data;
        top = top.next;
        size--;
        return item;
    }
    
    public void push(int item){
        StackNode t = new StackNode(item);
        t.next = top;
        size++;
        top = t;
        if(min==null) min = t;
        else if(t.data<min.data){
            min = t;
        }
    }
    
    public int peek(){
        if(top == null) return -1;
        return top.data;
    }
    
    public boolean isEmpty(){
        return (top == null);
    }
    
    public int min(){
        return min.data;
    }
    
    public int getSize(){
        return size;
    }   
}
