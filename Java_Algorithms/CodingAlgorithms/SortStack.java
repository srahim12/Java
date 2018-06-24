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
    
}

