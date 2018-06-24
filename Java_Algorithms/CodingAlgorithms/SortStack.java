//Programmed by Shah Rahim



class Sort{
    
    private Stack sortStack;

    public Sort(){
        sortStack = new Stack();    
    }
    
    public void sort(Stack origStack){
        if(origStack.getSize() ==0)return;
        int focus = 0;
        boolean first = true;
        boolean sorting = true;
        int iter = 0;
        while(sorting){
            if(first){
                sortStack.push(origStack.pop());
                first = false;
            } else if(origStack.getSize()==0){
                sorting = true;
                break;
            } else {
                focus = origStack.pop();
                if(focus>sortStack.peek()){
                    boolean less = true;
                    int pops = 0;
                    while(less){
                        if(sortStack.peek() > focus || sortStack.getSize()==0){
                            less = false;
                        } else {
                            origStack.push(sortStack.pop());
                            pops++;
                        }
                    }
                    sortStack.push(focus);
                    for(int i = 0;i<pops;i++){
                        sortStack.push(origStack.pop());
                    }
                } else {
                    sortStack.push(focus);
                }
            }
        }
        return;           
    }
    
    public Stack getSortedStack(){
        return sortStack;
    }
    
    public void printStack(Stack s){
        int stack_size = s.getSize();
        for(int i = 0;i < stack_size;i++){
            System.out.println(s.pop());
        }
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

