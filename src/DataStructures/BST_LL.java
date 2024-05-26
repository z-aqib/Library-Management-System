package DataStructures;

public class BST_LL<T extends Comparable<T>> {

    public Node_BST<T> root;
    public int size = 0;
    public MyArrayList<T> array;

    public void insert(T key) {
        // insert key in a tree public void
        if (this.root == null) {
            this.root = new Node_BST<>(key);
        } else {
            Node_BST<T> current = this.root;
            Node_BST<T> previous = this.root;
            while (current != null) {
                previous = current;
                if (key.compareTo(current.data) == -1) {
                    current = current.left;
                } else {
                    current = current.right;
                }
            }
            if (key.compareTo(previous.data) == -1) {
                previous.left = new Node_BST(key);
            } else {
                previous.right = new Node_BST(key);
            }
        }
        size++;
    }

//    public void traverse_BFS(Node_BST n) {
//        // print tree using Breadth first traversal
//        // USE LINKED LIST BASED QUEUE
//        if (this.root == null) {
//            System.out.print("[]");
//            return;
//        }
//        Queue<Node_BST> queue = new Queue();
//        System.out.print("[");
//        queue.enqueue(root);
//        while (queue.isEmpty() != true) {
//            Node_BST dequeuedNode = (Node_BST) queue.dequeue();
//            System.out.print(dequeuedNode.toString());
//            if (dequeuedNode.left != null) {
//                queue.enqueue(dequeuedNode.left);
//            }
//            if (dequeuedNode.right != null) {
//                queue.enqueue(dequeuedNode.right);
//            }
//            if (queue.isEmpty() == false) {
//                System.out.print(" , ");
//            }
//        }
//        System.out.print("]");
//    }
    public void traverse_LNR(Node_BST n) {
        // print tree using Inorder traversal
        if (n == null) {
            return;
        }
        traverse_LNR(n.left);
        System.out.print(n.data + " , ");
        traverse_LNR(n.right);
    }
    
    public MyArrayList<T> getTableOfNodes(){
        this.array=new MyArrayList<>();
        traverse_LNR_array(this.root);
        return this.array;
    }
    
    public void traverse_LNR_array(Node_BST n) {
        // print tree using Inorder traversal
        if (n == null) {
            return;
        }
        traverse_LNR(n.left);
        this.array.add((T) n.data); 
        traverse_LNR(n.right);
    }

    public Node_BST find(T key) {
        // find key in a tree
        Node_BST<T> temp = this.root;
        while (temp != null) {
            if (key.compareTo(temp.data) == 0) {
                break;
            }
            if (key.compareTo(temp.data) == 1) {
                temp = temp.right;
            } else {
                temp = temp.left;
            }
        }
        return temp;
    }

    public void display() {
        System.out.print("\tnodes = " + (calculateNodes(this.root) - 1) + "\n\tusing BFS : ");
        //traverse_BFS(null);
        System.out.print("\n\tusing LNR : [");
        traverse_LNR(root);
        System.out.print("]\n");
    }

    public Node_BST[] find_updated(T key) {
        // returns the node to be found along with its parent
        Node_BST[] nodes = new Node_BST[2];
        if (this.find(key) != null) {
            Node_BST<T> temp = this.root;
            Node_BST<T> prev = this.root;
            while (temp != null) {
                if (key.compareTo(temp.data) == 0) {
                    break;
                }
                prev = temp;
                if (key.compareTo(temp.data) == 1) {
                    temp = temp.right;
                } else {
                    temp = temp.left;
                }
            }
            nodes[0] = prev;
            nodes[1] = temp;
        }
        return nodes;
    }

    public boolean isBST(Node_BST root, T min, T max) {
        if (root == null) { // base condition
            return true;
        } else if (root.data.compareTo(min) == -1 || root.data.compareTo(max) == 1) {
            return false; // check if node is out-of-range
        }
        return isBST(root.left, min, (T) root.data) && isBST(root.right, (T) root.data, max);
    }

    public int calculateNodes(Node_BST root) {
        if (root == null) {
            return 1;
        }
        return calculateNodes(root.left) + calculateNodes(root.right);
    }

    protected void delNoChild(Node_BST parent, Node_BST temp) {
        if (parent.left == temp) {
            parent.left = null;
        } else {
            parent.right = null;
        }
    }

    protected void delOneChild(Node_BST parent, Node_BST temp) {
        Node_BST child;
        if (temp.left != null) {
            child = temp.left;
        } else {
            child = temp.right;
        }
        if (parent.left == temp) {
            parent.left = child;
        } else {
            parent.right = child;
        }
    }

    public boolean delete(T key) {
        Node_BST[] ref = find_updated(key);
        Node_BST<T> temp = ref[1];
        Node_BST<T> parent = ref[0];
        if (temp != null && key.compareTo(temp.data) == 0) {
            if (temp.left == null && temp.right == null) {
                // no child case
                delNoChild(parent, temp);
            } else if ((temp.left != null && temp.right == null) || (temp.left == null && temp.right != null)) {
                // one child case
                delOneChild(parent, temp);
            } else {
                // two child case
                parent = temp;
                temp = temp.right;
                while (temp.left != null) {
                    parent = temp;
                    temp = temp.left;
                }
                ref[1].data = temp.data;
                if (temp.left == null && temp.right == null) {
                    delNoChild(parent, temp);
                } else {
                    delOneChild(parent, temp);
                }
            }
            size--;
            return true;
        } else {
            System.out.println("key not found");
            return false;
        }
    }

}
