package DataStructures;

// COMPLETED - ALL METHODS DONE
public class SinglyLLH<T extends Comparable<T>> {

    /*
    this is a LINKED LIST data structure of SINGLY type i.e. all the nodes move in 
    one direction. It is of generic type, i.e. any sort of data can be stored on 
    the SinglyNode. It only saves the starting address of the nodes i.e. the HEAD NODE 
    to save memory but increases time complexity in insertion. 
     */
    private SinglyNode headNode; // saves the starting address of the linked list
    private int size; // svaes the size of the linked list

    public SinglyLLH() {
        // constructor: starts the linked list and intialises the head node to be null. 
//        System.out.println("Welcome to My Singly Linked List! ");
        this.headNode = null;
    }

    @Override
    public String toString() {
        // method: converts the linked list into a string. returns a string with comma node values.
        String str = "{";
        SinglyNode duplicateHead = this.headNode;
        while (duplicateHead.next != null) {
            str += duplicateHead.data.toString() + ",";
            duplicateHead = duplicateHead.next;
        }
        return "size=" + size + str + duplicateHead.data.toString() + "}";
    }

    public void display() {
        // method: this method displays the singly linked list
        System.out.println(toString());
    }

    public void insertStart(T valueToBeInserted) {
        /*
        method: inserts a value at the start of the linked list
        this node traverses the linked list to the end by creating a temporary head
        then inserts the node at the end of the linked list. 
         */
        if (isEmpty() == true) { // if the head is null, make the valueToBeInserted the head
            this.headNode = new SinglyNode(valueToBeInserted);
            size++;
        } else {
            SinglyNode temporaryHead = this.headNode;
            this.headNode = new SinglyNode(valueToBeInserted);
            this.headNode.next = temporaryHead;
            size++;
        }
//        System.out.println("SUCCESS: '" + valueToBeInserted.toString() + "' inserted at the start successfully. ");
    }

    public void insertEnd(T valueToBeInserted) {
        /*
        method: inserts a value at the end of the linked list
        this node traverses the linked list to the end by creating a temporary head
        then inserts the node at the end of the linked list. 
         */
        if (isEmpty() == true) { // if the head is null, make the valueToBeInserted the head
            this.headNode = new SinglyNode(valueToBeInserted);
            size++;
        } else {
            SinglyNode duplicateHead = this.headNode;
            while (duplicateHead.next != null) {
                duplicateHead = duplicateHead.next;
            }
            duplicateHead.next = new SinglyNode(valueToBeInserted);
            size++;
        }
//        System.out.println("SUCCESS: '" + valueToBeInserted.toString() + "' inserted at the end successfully. ");
    }

    public boolean insertBefore(SinglyNode nodeBefore, T valueToBeInserted) {
        /*
        method: inserts a value before a specific node. 
        first it checks, is the list empty or the node given is null? return the 
        method immediately. otherwise, implement the cases: 
        CASE 1 -> the node to be inserted before is the head. hence make the head the 
        inserting Node and the .next of the head the rest of the nodes
        CASE 2 -> the node is in the rest of the linked list, save its previous and 
        currentNode and insert between them. 
         */
        if (isEmpty() == true) {
            System.out.println("ERROR: MyLinkedList is empty. ");
            return false;
        } else if (nodeBefore == null || find((T) nodeBefore.data) == false) {
            System.out.println("ERROR: Node '" + nodeBefore + "' does not exist in the linked list. ");
            return false;
        }
        SinglyNode insertingNode = new SinglyNode(valueToBeInserted);
        SinglyNode duplicateHead = this.headNode;
        // CASE ONE
        if (this.headNode == nodeBefore) {
            this.headNode = insertingNode;
            insertingNode.next = duplicateHead;
            size++;
//            System.out.println("SUCCESS: '" + valueToBeInserted.toString() + "' inserted before "
//                    + "'" + nodeBefore.toString() + "' successfully. ");
            return true;
        }
        SinglyNode previousNode = duplicateHead;
        // CASE TWO
        while (duplicateHead != null) {
            if (duplicateHead == nodeBefore) {
                previousNode.next = insertingNode;
                insertingNode.next = duplicateHead;
                size++;
//                System.out.println("SUCCESS: '" + valueToBeInserted.toString()
//                        + "' inserted before '" + nodeBefore.toString() + "' successfully. ");
                return true;
            }
            previousNode = duplicateHead;
            duplicateHead = duplicateHead.next;
        }
        System.out.println("ERROR: '" + valueToBeInserted.toString()
                + "' could not be inserted before '" + nodeBefore.toString() + "'. ");
        return false;
    }

    public boolean insertAfter(SinglyNode nodeAfter, T valueToBeInserted) {
        /*
        method: inserts a value after a specific node
        this method inserts data after this specific node. first it checks, if the head is 
        empty, it returns. if the specific node is null or not in the linked list, it returns. 
        otherwise, it creates a node of the data to be inserted. then it inserts between 
        the node nodeAfter and the node nodeAfter.next. 
         */
        if (isEmpty() == true) {
            System.out.println("ERROR: MyLinkedList is empty. ");
            return false;
        } else if (nodeAfter == null || find((T) nodeAfter.data) == false) {
            System.out.println("ERROR: Node '" + nodeAfter + "' does not exist in the linked list. ");
            return false;
        }
        SinglyNode insertingNode = new SinglyNode(valueToBeInserted);
        insertingNode.next = nodeAfter.next;
        nodeAfter.next = new SinglyNode(valueToBeInserted);
        size++;
//        System.out.println("SUCCESS: '" + valueToBeInserted.toString()
//                + "' inserted after '" + nodeAfter.toString() + "' successfully. ");
        return true;
    }

    public boolean insertAt(int indexToInsertAt, T valueToBeInserted) {
        /*
        method: inserts a value at a specific indexToInsertAt
        this method inserts data at a specific indexToInsertAt in the linked list. if the LL is empty, 
        it returns, otherwise, it does it inserts the element just before the indexToInsertAt 
        given i.e. it inserts after indexToInsertAt-1;
         */
        if (size == 0) {
            System.out.println("ERROR: My linked list is empty. ");
            return false;
        } else {
            return insertAfter(getNode(indexToInsertAt - 1), valueToBeInserted);
        }
    }

    public void insertInOrder(T valueToBeInserted) {
        /*
        method: inserts values in order in the linked list. first it inserts at the start 
        and then sorts it so that when sort is called, the starting head (the newly inserted 
        value) is checked firat. 
         */
        insertStart(valueToBeInserted);
        sortLowToHigh();
//        System.out.println("SUCCESS: '" + valueToBeInserted + "' inserted in order successfully. ");
    }

    public boolean update(int indexToBeUpdated, T valueToUpdate) {
        /*
        method: updates a specific indexToBeUpdated's node to a value. 
        this method gets the node at the specific indexToBeUpdated given. if that node is null, 
        it means no node exists at that indexToBeUpdated and hence it cannot be updated. 
        if that node is not null, the value at that node is updated. 
         */
        SinglyNode updatingNode = getNode(indexToBeUpdated);
        if (updatingNode == null) {
//            System.out.println("ERROR: Index cannot be updated as the linked list does not contain that index. ");
            return false;
        } else {
            updatingNode.data = valueToUpdate;
//            System.out.println("SUCCESS: index " + indexToBeUpdated
//                    + " updated to '" + valueToUpdate + "' successfully. ");
            return true;
        }
    }

    public boolean find(T valueToBeFound) {
        /*
        method: finds a specific value. 
        it uses the get method: if it is null, it is not found, otherwise it is found and true.
         */
        if (getNode(valueToBeFound) == null) {
//            System.out.println("ERROR: '" + valueToBeFound.toString() + "' not found in the linked list. ");
            return false;
        } else {
//            System.out.println("SUCCESS: '" + valueToBeFound.toString() + "' found successfully. ");
            return true;
        }
    }

    public SinglyNode getNode(T valueToGet) {
        /*
        method: gets the node with the specific value
        this method checks each node by traversing a duplicate head node and see's
        if it is equal to the data that we are finding. if yes, it stops running and 
        stops at that node. if not, it traverses until null. it returns what we stopped 
        on. if null, not found, if not null, found. 
         */
        if (isEmpty() == true) {
//            System.out.println("ERROR: My linked list is empty. ");
            return null;
        }
        SinglyNode duplicateHead = this.headNode;
        while (duplicateHead != null && duplicateHead.data.compareTo(valueToGet) != 0) {
            duplicateHead = duplicateHead.next;
        }
        return duplicateHead;
    }

    public SinglyNode getNode(int indexNeeded) {
        /*
        method: gets the node at a specific indexNeeded
        this method returns the node on the indexNeeded given by traversing the linked list 
        until we reach that indexNeeded. 
         */
        if (isEmpty() == true) {
//            System.out.println("ERROR: My linked list is empty. ");
            return null;
        } else if (indexNeeded > size) {
//            System.out.println("ERROR: The linked list has " + size + " elements and none at index " + indexNeeded);
            return null;
        }
        SinglyNode duplicateNode = this.headNode;
        int counter = 0;
        while (duplicateNode != null && counter++ != indexNeeded) {
            duplicateNode = duplicateNode.next;
        }
        return duplicateNode;
    }

    public int getIndex(T valueToGet) {
        /*
        method: gets the index at which the specific value exists. 
        this method gets the index of a specific data. it will return what index is 
        this data on. it will frist check for an empty list. then it will triverse until '
        we find the data - incrementing a counter. if the data is not found then it 
        returns -1, else returns the index it was found on. 
         */
        if (isEmpty() == true) {
//            System.out.println("ERROR: My linked list is empty. ");
            return -1;
        }
        SinglyNode duplicateHead = this.headNode;
        int counter = 0;
        while (duplicateHead != null && duplicateHead.data.compareTo(valueToGet) != 0) {
            duplicateHead = duplicateHead.next;
            counter++;
        }
        if (duplicateHead != null && duplicateHead.data.compareTo(valueToGet) == 0) {
            return counter;
        }
//        System.out.println("ERROR: '" + valueToGet + "' cannot be found in the linked list. ");
        return -1;
    }

    public SinglyNode peekHead() {
        // method: returns the node at the start of the linked list
        return this.headNode;
    }

    public SinglyNode peekTail() {
        // method: returns the node at the end of the linked list by traversing it and getting the last value
        SinglyNode duplicateHead = this.headNode;
        while (duplicateHead != null && duplicateHead.next != null) {
            duplicateHead = duplicateHead.next;
        }
        return duplicateHead;
    }

    public boolean clear() {
        // method: clears the linked list make list empty by making the start address null
        this.headNode = null;
        size = 0;
//        System.out.println("SUCCESS: Linked list cleared successfully. ");
        return true;
    }

    public int size() {
        // method: returns the size of the linked list, i.e. how many elements it contains
        return size;
    }

    public boolean isEmpty() {
        // method: returns whether or not the linked list is empty or not
        return this.headNode == null;
    }

    public SinglyNode deleteIndex(int indexToBeDeleted) {
        /*
        method: deletes the node at a specific index
        first it gets the node at the index-1 i.e. the node before the node we are 
        deleting. if that node is null OR the node.next is null, it means that index 
        does not exist - return. but if not, make the node's next equal to node.next.next
        which means we skipped the node in between which is the index we are deleting. 
         */
        SinglyNode previousNode = getNode(indexToBeDeleted - 1);
        if (previousNode == null || previousNode.next == null) {
//            System.out.println("ERROR: Index cann/ot be deleted as it does not exist. ");
            return null;
        }
        SinglyNode deletingNode = previousNode.next;
        previousNode.next = previousNode.next.next;
        size--;
//        System.out.println("SUCCESS: Value at index " + indexToBeDeleted + " has been deleted successfully. ");
        return deletingNode;
    }

    public SinglyNode deleteFirstOccurence(T valueToBeDeleted) {
        // delete a node with value valueToBeDeleted (first occurrence)
        /*
        method: deletes the first occurence of a specific value
        so if my head is null or my linked list doesnt contain this data, i will return 
        false that the value cannot be deleted. otherwise, i will traverse two nodes 
        from the head, one to save the currentNode and one to save the previousNode
        if i find the value, i make the previousNodes next the currentNodes next so that 
        the currentNode is skipped (deleted). otherwise i keep traversing until i find it. 
         */
        if (isEmpty() == true) {
//            System.out.println("ERROR: MyLinkedList is empty. ");
            return null;
        } else if (find(valueToBeDeleted) == false) {
//            System.out.println("ERROR: '" + valueToBeDeleted.toString()
//                    + "' cannot be deleted as it does not exist. ");
            return null;
        }
        SinglyNode duplicateHead = this.headNode;
        SinglyNode previousNode = duplicateHead;
        while (duplicateHead != null) {
            if (duplicateHead.data.compareTo(valueToBeDeleted) == 0) {
                SinglyNode nodeToBeDeleted = duplicateHead;
                previousNode.next = duplicateHead.next;
                size--;
//                System.out.println("SUCCESS: '" + valueToBeDeleted.toString() + "' deleted successfully. ");
                return nodeToBeDeleted;
            }
            previousNode = duplicateHead;
            duplicateHead = duplicateHead.next;
        }
//        System.out.println("ERROR: '" + valueToBeDeleted.toString() + "' could not be deleted. ");
        return null;
    }

    public void deleteAllOccurences(T valueToBeDeleted) {
        // delete all the nodes with value valueToBeDeleted (all occurrence)
        /*
        method: deletes all the occurences of a specific value
        it just runs a loop by first finding the data, if its found, call delete
        then run again, if its found, call delete. keep deleting until its not found. 
         */
        while (find(valueToBeDeleted) == true) {
            deleteFirstOccurence(valueToBeDeleted);
        }
//        System.out.println("SUCCESS: '" + valueToBeDeleted + "'s all occurences deleted successfully. ");
    }

    public SinglyNode deleteHead() {
        /*
        method: removes the head of the linked list
        this method removes the head. if the list is empty, it returns. otherwise, it 
        removes the head and returns the node that was removed. 
         */
        if (isEmpty() == true) {
//            System.out.println("ERROR: Linked list is empty. ");
            return null;
        }
        SinglyNode headToBeRemoved = peekHead();
        this.headNode = peekHead().next;
        size--;
//        System.out.println("SUCCESS: The head has been removed successfully. ");
        return headToBeRemoved;
    }

    public SinglyNode deleteTail() {
        // method: removes the last node in the linked list by traversing till the second last
        if (isEmpty() == true) {
//            System.out.println("ERROR: Linked list is empty. ");
            return null;
        }
        SinglyNode duplicateHead = this.headNode;
        while (duplicateHead.next.next != null) {
            duplicateHead = duplicateHead.next;
        }
        SinglyNode removingNode = duplicateHead.next;
        duplicateHead.next = null;
        size--;
//        System.out.println("SUCCESS: The last node has been removed successfully. ");
        return removingNode;
    }

    public void sortLowToHigh() {
        /*
        method: sorts the singly linked list by using a fixed node and checking it with each 
        node in the linked list. then it checks the next node after the fixed node with 
        every node in the linked list and so on. 
        if the fixed node is greater than a node after it (changing node), it swaps the 
        data of the two nodes. 
         */
        for (SinglyNode fN = this.headNode; fN != null; fN = fN.next) {
            for (SinglyNode cN = fN.next; cN != null; cN = cN.next) {
                if (fN.data.compareTo(cN.data) == 1) {
                    swap(fN, cN);
                }
            }
        }
//        System.out.println("SUCCESS: The linked list has been sorted from low to high successfully. ");
    }

    public void removeDuplicates() {
        /*
        method: removes duplicates. first it sorts the entire linked list and then 
        runs a loop where we get a node, (FORWARD CHECKING)  check with its next node, is it same? then 
        remove the next node. then we (BACKWARD CHECKING) check with its previous node, 
        is it same? if yes, remove the current node. then proceed to other node and 
        repeat forward and backward checking. 
        each time a node is removed, increment a counter to know how many duplicates were 
        removed in the linked list. 
         */
        sortLowToHigh();
        int counter = 0;
        for (SinglyNode duplicateHead = this.headNode, prevDH = this.headNode;
                duplicateHead != null; prevDH = duplicateHead, duplicateHead = duplicateHead.next) {
            if (duplicateHead.next != null && duplicateHead.data.compareTo(duplicateHead.next.data) == 0) {
                duplicateHead.next = duplicateHead.next.next;
                size--;
                counter++;
            }
            if (prevDH != duplicateHead && prevDH.data.compareTo(duplicateHead.data) == 0) {
                prevDH.next = duplicateHead.next;
                duplicateHead = prevDH;
                size--;
                counter++;
            }
        }
//        System.out.println("SUCCESS: All the duplicates in the linked list have been removed successfully. ");
//        System.out.println("Number of duplicates removed = " + counter);
    }

    public Comparable<T> findMax() {
        // method: returns the highest value in the linkedlist by sorting it and returning the last value
        sortLowToHigh();
        SinglyNode duplicateHead = this.headNode;
        while (duplicateHead.next != null) {
            duplicateHead = duplicateHead.next;
        }
        return duplicateHead.data;
    }

    public Comparable<T> findMin() {
        // method: returns the smallest value in the linkedlist by sorting it and returning the first value
        sortLowToHigh();
        return this.headNode.data;
    }

    public Comparable<T>[] toArray() {
        /*
        method: changes the linked list to an array
        this method converts the linked list to an array by traversing the linked list 
        and adding each node's data to the array. 
         */
        Comparable<T>[] array = new Comparable[size()];
        SinglyNode duplicateHead = this.headNode;
        for (int i = 0; duplicateHead != null && i < array.length; i++, duplicateHead = duplicateHead.next) {
            array[i] = duplicateHead.data;
        }
        return array;
    }

    public void reverse() {
        /*
        method: reverses the linked list
        this method reverses the singly linked list. it gets each node and reverses 
        its link i.e. if A is linked to B , it links B to A so that the links are reveresed 
        then it makes the last node the head node. 
         */
        SinglyNode previousNode = null;
        SinglyNode duplicateHead = this.headNode;
        SinglyNode temporaryNode;
        while (duplicateHead != null) {
            temporaryNode = duplicateHead.next; // first it saves head's next somewhere
            duplicateHead.next = previousNode; //and makes the head's next null
            previousNode = duplicateHead; // then it makes previous the head (which is only a node)
            duplicateHead = temporaryNode; // and then makes the head equal to head.next
        }
        this.headNode = previousNode;
//        System.out.println("SUCCESS: The linked list has been reversed successfully. ");
    }

    private void swap(SinglyNode firstNode, SinglyNode secondNode) {
        /*
        method: swaps two nodes. this method is repeatedly used when sorting a singly 
        linked list. it swaps the data of secondNode nodes. 
         */
        Comparable<T> tempVariable = firstNode.data;
        firstNode.data = secondNode.data;
        secondNode.data = tempVariable;
    }

}
