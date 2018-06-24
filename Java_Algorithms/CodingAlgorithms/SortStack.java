 public void sort(Stack origStack) {
     if (origStack.getSize() == 0) return;
     int focus = 0;
     boolean first = true;
     while (!origStack.isEmpty()) {
         if (first) {
             sortStack.push(origStack.pop());
             first = false;
         } else {
             focus = origStack.pop();
             if (focus > sortStack.peek()) {
                 boolean less = true;
                 int pops = 0;
                 while (sortStack.peek() > focus || sortStack.getSize() == 0) {
                         origStack.push(sortStack.pop());
                         pops++;
                     }
                 }
                 sortStack.push(focus);
                 for (int i = 0; i < pops; i++) {
                     sortStack.push(origStack.pop());
                 }
             } else {
                 sortStack.push(focus);
             }
         }
     }
 }