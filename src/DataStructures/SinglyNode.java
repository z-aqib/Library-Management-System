package DataStructures;

public class SinglyNode<T extends Comparable<T>> {

    /*
    this is the Node class of the Singly Linked List data structure. It contains two 
    objects, data and next. next is where we save the memory address of the next node 
    after this node. It contains data of generic (T) type. 
     */
    public T data; // the data to be saved on the node
    public SinglyNode next; // the node after this node

    public SinglyNode(T data) {
        // constructor: save the data on this node, then make the next node null. 
        this.data = data;
        this.next = null;
    }

    @Override
    public String toString() {
        /*
        method: converts the node to a string. 
        if the data is not null, return the data.toString() else return "null"
         */
        if (data != null) {
            return data.toString();
        }
        return "null";
    }

}
