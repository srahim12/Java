import java.util.HashMap;
//Node@15db9742
//1---->2---->3---->4---->9---->
//6---->7---->3---->4---->9---->
//
public  class LinkedListIntersection{

    public static void main(String[] args){
        LinkedList l1 = new LinkedList();
	    LinkedList l2 = new LinkedList();
        l1.addBack(1);
        l2.addBack(6);
        l1.addBack(2);
        l2.addBack(7);
        Node n = new Node(3,null);
        l1.addNodeBack(n);
        l2.addBack(3);
        l1.addBack(4);
        l2.addBack(9);
        l1.print();
        l2.print();
        int answer;
        Node inter = intersect(l1,l2);
        answer = (inter!=null) ? inter.data : 0;
        System.out.println(answer);
    }
    static Node intersect(LinkedList l1, LinkedList l2){
        if(l1.head == null || l2.head==null){
            return null;
        }
        HashMap<Node,Node> map = new HashMap<>();
        Node ptr = l1.head;
        while(ptr!=null){
            map.put(ptr.next,ptr);
            ptr = ptr.next;
        }
        ptr = l2.head;
        while(ptr.next !=null){
            if(map.get(ptr.next)!=null){
                if(ptr.next == map.get(ptr.next).next){
                    return ptr.next;
                }
            }
            ptr = ptr.next;    
        }
        return null;
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
    public void addNodeBack(Node n){
        if(head == null){
            head = n;
            return;
        }
        Node ptr = head;
        while(ptr.next!=null){
            ptr = ptr.next;
        }
        ptr.next = n;
    }
    public Node getNodeByVal(int val){
        if(head == null) {
            System.out.println("head is null");
            return null;
        }
        Node ptr = head;
        while(ptr != null) {
            if(ptr.data == val){
	        	System.out.println("data is equivalent");
                return ptr;
            }
            ptr = ptr.next;
        }
	    return null;
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

Runtime O(N + M)