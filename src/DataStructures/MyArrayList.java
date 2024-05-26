package DataStructures;

public class MyArrayList<T extends Comparable<T>> {

    T[] arr;
    int currIndex;

    public MyArrayList() {
        arr = (T[]) new Comparable[10];
        currIndex = -1;
    }

    public MyArrayList(int size) { // constructor to create an array
        arr = (T[]) new Comparable[size];
        currIndex = -1;
    }

    @Override
    public String toString() {
        String str = "";
        for (int i = 0; i < arr.length; i++) {
            str = str + arr[i] + "\n";
        }
        return str;
    }

    public void add(T data) {
        if (currIndex == arr.length - 1) {
            T[] newArray = (T[]) new Comparable[arr.length * 2];
            for (int i = 0; i < arr.length; i++) {
                newArray[i] = arr[i];
            }
            arr = newArray;
        }
        if (currIndex == -1) {
            currIndex++;
            arr[currIndex] = data;
        } else {

            currIndex++;
            arr[currIndex] = data;
        }
    }

    public int Find(T value) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == value) {
                return i;
            }
        }
        return -1;
    }

    public void clear(T value) {
        this.arr = (T[]) new Comparable[2];
        this.currIndex = -1;
        System.out.println(toString());
        System.out.println("size" + Length());
    }

    public int Length() { // return length of occupied list
        return currIndex + 1;
    }

    public T get(int index) {// get element at given index location
        return arr[index];
    }

    public void set(int index, T data) {
        arr[index] = data;
    }

    public void Update(int index, T value) {// update element at given location
        if (index < 0 || index > currIndex) {
            // Check if the index is valid
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
        arr[index] = value;
    }

    public void Remove(T value) {
        int index = -1;
        for (int i = 0; i <= currIndex; i++) {
            if (arr[i]!=null&&arr[i].compareTo(value) == 0) {
                index = i;
            }
        }
        removeIndex(index);

    }

    public void removeIndex(int index) {
        if (index != -1) {
            for (int j = index; j < arr.length - 1; j++) {
                arr[j] = arr[j + 1];
            }
        }
    }

    public T findMax() {

        if (currIndex == -1) {
            return null; // Return null if the list is empty
        }

        T max = arr[0]; // Assume the first element is the maximum
        for (int i = 1; i <= currIndex; i++) {
            if (arr[i].compareTo(max) > 0) {
                max = arr[i]; // Update max if a greater element is found
            }
        }

        return max;
    }

}
