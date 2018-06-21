public class LinkedListIntersection{

    public static void main(String[] args){
        LinkedList l1 = new LinkedList();
        l1.addFront(1);
        l1.addFront(2);
        l1.addFront(3);
        l1.addFront(4);
        LinkedList l2 = new LinkedList();
        l2.addFront(6);
        l2.addFront(7);
        l2.addFront(3);
        l2.addFront(9);     
    }
}
class Node{
    public Node next;
    int data;
    
    public Node(int data, Node next){
        this.next = next;
        this.data = data;
    }
    public Node(){}
}
class LinkedList{
    public Node head;
    int size;
    
    public LinkedList(){
        head = null;
        size = 0;
    }
    public void addFront(int data){
        Node n = new Node(data,null);
        if(head == null){
            head = n;
            return;
        }
        n.next = this.head;
        head = n;
    }
    public void addBack(int data){
        Node n = new Node(data, null);
        if(head==null){
            head = n;
            return;
        }
        Node ptr = head;
        while(ptr!=null){
            if(ptr.next == null){
                ptr.next = n;
                return;
            }
            ptr = ptr.next;
        }
    }

    public void print(){
        if(head==null)
            return;
        Node ptr = this.head;
        while(ptr!=null){
            System.out.print(ptr.data+ "---->");
            ptr = ptr.next;
        }
        System.out.println();
    }
}
