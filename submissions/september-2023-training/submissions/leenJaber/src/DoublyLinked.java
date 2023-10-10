public class DoublyLinked {
    Node head;
    Node tail;
    int size;
    DoublyLinked(){
        this.head =null;
        this.tail = null;
        this.size = 0;
    }
    public Node addfirst(Anime NAME){
        Node temp = new Node(NAME);
        if (head==null){
            head = temp;
            tail = temp;
        }
        else{
            head.prev = temp;
            temp.next = head;
            head = temp;
        }
        size++;
        return head;
    }
    public void deleteFirst(){
        if (head==null){
            return;
        }
        else{
            head = head.next;

            size--;
        }
        if(size==1){
            tail = head;
        }
    }
    public Node addLast(Anime ele){
        Node temp = new Node(ele);
        if(tail==null){
            head = temp;
            tail = temp;
        }
        else{
            tail.next = temp;
            temp.prev = tail;
            tail = temp;
        }
        size++;
        return temp;
    }
    public Node deleteLast(){
        if (tail==null){
            return null ;
        }
        if(head==tail){
            Node temp = tail;
            head = tail = null;
            size--;
            return temp;
        }
        Node temp = tail;
        tail = tail.prev;
        tail.next = null;
        size--;
        return temp;
    }
    public void DeleteSpecficNode(Node wanted){
        if (this.head == null) {
            return;
        }
        if (wanted==this.head){
            this.deleteFirst();
        }
        else if (wanted==this.tail){
            this.deleteLast();
        }
        else if (this.size==1) {
            this.head=this.tail = null;

        } else{
            wanted.prev.next = wanted.next;
            wanted.next.prev = wanted.prev;
            wanted.prev = null;
            wanted.next = null;
        }
    }





}
