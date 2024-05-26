package DataStructures;

import Books.*;

public class HashingOpenAddQuadratic<T extends Comparable<T>> {

    private Comparable<T>[] table;
    private BST_LL<Book>[] BSTtable;
    private int countOccupied;

    public HashingOpenAddQuadratic(int size) {
        // constructor: creates a table of 1.33 times of given size and intializes count as 0
        table = (T[]) new Comparable[size + (size / 3)];
        // table size should be a prime number and 1/3 extra.
        this.countOccupied = 0;
    }

    public HashingOpenAddQuadratic() {
        table = (T[]) new Comparable[27];
        for (int i = 0; i < table.length; i++) {
            table[i] = (Comparable<T>) new BST_LL<Book>();
        }
    }

    public int strToIntBK(String stringToBeChanged) {
        String a = String.valueOf(stringToBeChanged.charAt(0));
        return switch (a.toLowerCase()) {
            case "a" ->
                0;
            case "b" ->
                1;
            case "c" ->
                2;
            case "d" ->
                3;
            case "e" ->
                4;
            case "f" ->
                5;
            case "g" ->
                6;
            case "h" ->
                7;
            case "i" ->
                8;
            case "j" ->
                9;
            case "k" ->
                10;
            case "l" ->
                11;
            case "m" ->
                12;
            case "n" ->
                13;
            case "o" ->
                14;
            case "p" ->
                15;
            case "q" ->
                16;
            case "r" ->
                17;
            case "s" ->
                18;
            case "t" ->
                19;
            case "u" ->
                20;
            case "v" ->
                21;
            case "w" ->
                22;
            case "x" ->
                23;
            case "y" ->
                24;
            case "z" ->
                25;
            default ->
                26;
        };
    }

    public void SCInsert(Comparable<T> valueToBeInserted) {
        int index = strToIntBK(valueToBeInserted.toString());
        BSTtable[index].insert((Book) valueToBeInserted);
        countOccupied++;
    }

    public Comparable<T> SCsearch(Comparable<T> valueToSearch) {
        int index = strToIntBK(valueToSearch.toString());
        Node_BST b = BSTtable[index].find((Book) valueToSearch);
        if (b != null) {
            return b.data;
        } else {
            return null;
        }
    }

    public boolean SCDelete (Comparable<T> valueToDelete){
        int index = strToIntBK(valueToDelete.toString());
        return BSTtable[index].delete((Book) valueToDelete); 
    }

    @Override

    public String toString() {
        // method: converts the hashtable into a string
        String str = "";
        for (int i = 0; i < table.length; i++) {
            str += (i + 1) + ":'" + table[i] + "'";
            if (i != table.length - 1) {
                str = str + " , \n";
            }
        }
        return "[" + str + "]";
    }

    public void display() {
        // method: displays hash table
        System.out.println(toString());
    }

    public int strToInt(String stringToBeChanged) {
        // method: convert string into integer by summing the ASCII values
        int sum = 0;
        for (int i = 0; i < stringToBeChanged.length(); i++) {
            sum += (int) stringToBeChanged.charAt(i);
        }
        return sum;
    }

    public int Hash(int sum) {
        //method: hash value calculator. compute hash value by taking mod on table length
        return sum % table.length;
    }

    public int Rehash(int sum, int i) {
        /* 
        method: rehash function. 
        in quadratic addressing, we will square 'i' which is the number of times we 
        encounter a collision. then we will call hash() function and find the hash of the new 
        value (value + i^2)
         */
        return Hash(sum + (int) (Math.pow(i, 2)));
    }

    public int insert(Comparable<T> valueToBeInserted) {
        /*
        method: inserts a value in the table using its hash() value. 
        call strToInt(v) and change the value to string, save return value in sum.
        call Hash(sum) and save return value in sum
        if (no collision on hash value) then place value
        else call rehash function until empty cell found
        also compute number of collisions on insertion of each word
         */
        if (countOccupied == table.length) {
//            System.out.println("ERROR: Cannot be inserted as hash table is full. ");
            return -1;
        }
        int sum = strToInt(valueToBeInserted.toString());
        sum = Hash(sum);
        int i = 0; // counts the number of times re-hashed i.e. no. of collisions faced
        while (this.table[sum] != null) {
            sum = Rehash(sum, ++i);
        }
        this.table[sum] = valueToBeInserted;
        countOccupied++;
        return i;
    }

    public boolean delete(Comparable<T> valueToBeDeleted) {
        /*
        method: delete word from hash table
        first search for the value. if found, then proceed deletion. 
        run a loop and keep checking if the hash() and rehash() is equal to the value 
        that is being deleted. if yes, make that value null. 
         */
        if (search(valueToBeDeleted) != null) {
            int sum = strToInt(valueToBeDeleted.toString());
            sum = Hash(sum);
            int i = 0;
            while (table[sum] != null && table[sum].compareTo((T) valueToBeDeleted) != 0) {
                sum = Rehash(sum, ++i);
            }
            if (table[sum] != null && table[sum].compareTo((T) valueToBeDeleted) == 0) {
                table[sum] = null;
                countOccupied--;
//                System.out.println("SUCCESS: '" + valueToBeDeleted + "' is deleted successfully. ");
                return true;
            }
        }
//        System.out.println("ERROR: '" + valueToBeDeleted.toString() + "' cannot be deleted as it is not found. ");
        return false;

    }

    public Comparable<T> search(Comparable<T> valueToBeSearched) {
        /*
        method: search word in a hash table
        first it changes the object to string, computes its sum, then computes its 
        hash() value. then it iteratively searches for that value at 
         */
        int sum = strToInt(valueToBeSearched.toString());
        sum = Hash(sum);
        int i = 0;
        while (table[sum] != null && table[sum].compareTo((T) valueToBeSearched) != 0) {
            sum = Rehash(sum, ++i);
        }
        if (table[sum] != null && table[sum].compareTo((T) valueToBeSearched) == 0) {
//            System.out.println("SUCCESS: '" + valueToBeSearched.toString() + "' is found at index "+sum);
            return table[sum];
        }
//        System.out.println("ERROR: '" + valueToBeSearched.toString() + "' could not be found. ");
        return null;
    }

    public Comparable<T>[] getTable() {
        return table;
    }
    
    public MyArrayList<T> getSCTable() {
        MyArrayList<T> tablee = new MyArrayList<>();
        for (int i = 0; i < BSTtable.length; i++) {
            MyArrayList<T> smallTable = (MyArrayList<T>) BSTtable[i].getTableOfNodes();
            for (int j = 0; j < smallTable.Length(); j++) {
                tablee.add(smallTable.get(i)); 
            }
        }
        return tablee;
    }

    public int getElements() {
        return countOccupied;
    }

}
