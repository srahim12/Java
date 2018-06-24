public class QueueViaStacks{
    public static void main(String[] args){
        MyQueue m = new MyQueue();
        m.getSize();
        m.enqueue(2);
        m.enqueue(3);
        m.enqueue(4);
        m.enqueue(5);
        System.out.println(m.dequeue());
        System.out.println(m.dequeue());
        System.out.println(m.dequeue());
    }
}

class MyQueue{
    private Stack s1;
    private Stack s2;
    
    public MyQueue(){
        s1 = new Stack();
        s2 = new Stack();
    }

    public void enqueue(int item){
        if(s1.getSize()==0 && s2.getSize() == 0){
            s1.push(item);
        } else if(s1.getSize() >0){
            s1.push(item);
        } else if(s2.getSize()>0){
            s2.push(item);
        }
    }

    public int dequeue(){
        int val=-1;
        int i= 0;
        if(s1.getSize() == 0 && s2.getSize() == 0){
            val = -1;
        }
        if(s1.getSize() >0){
            int s1_size = s1.getSize();
            for(i = 0; i <s1_size-1 ; i++){
                val = s1.pop();
                s2.push(val);
            }
            val = s1.pop();                
        } else if(s2.getSize()>0) {
            int s2_val = s2.pop();
            int s2_size = s2.getSize();
            for(i = 0; i <s2_size-1 ; i++){
                val = s2.pop();
                s1.push(val);
            }
            return s2_val;
        }
        return val;
    }
    
    public void getSize(){
        System.out.println("s1: " + s1.getSize());
        System.out.println("s2: " + s2.getSize());
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

