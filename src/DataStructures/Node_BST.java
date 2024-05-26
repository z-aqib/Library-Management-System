package DataStructures;


public class Node_BST<T extends Comparable<T>> {

    public T data;
    public Node_BST left;
    public Node_BST right;

    public Node_BST(T d) {
        this.data = d;
        this.left = null;
        this.right = null;
    }

    @Override
    public String toString() {
        return this.data.toString();
    }

}
