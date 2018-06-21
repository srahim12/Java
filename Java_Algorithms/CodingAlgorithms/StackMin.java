public class StackMin {
    
    public static void main(String[] args){
        Stack<Integer> stack = new Stack<Integer>();
        stack.push(8);
        stack.push(99);
        stack.push(3);
        stack.push(7);
        stack.push(55);
        Integer answer = (stack.min()!=null) ? stack.min() : -10000;
        System.out.println(answer);
        }
}
class Stack<T extends Comparable<T>> {
    private static class StackNode<T> {
        private T data;
        private StackNode<T> next;
        
        public StackNode(T data) {
            this.data = data;
        }
    }
    
    private StackNode<T> top;
    private StackNode<T> min;
    
    public T pop(){
        if(top == null) return null;
        T item = top.data;
        top = top.next;
        return item;
    }
    
    public void push(T item){
        StackNode<T> t = new StackNode<T>(item);
        t.next = top;
        top = t;
        if(min==null) min = t;
        else if(t.data.compareTo(min.data)<0){
            min = t;
        }
        
    }
    
    public T peek(){
        if(top == null) return null;
        return top.data;
    }
    
    public boolean isEmpty(){
        return (top == null);
    }
    
    public T min(){
        return min.data;
    }
    
}
