package BrandeisMap.utils;

import java.util.Arrays;

public class CustomArrayList<E> {
    private int size = 0;
    private static int DEFAULT_CAPACITY = 50;
    private Object items[];

    public CustomArrayList() {
        items = new Object[DEFAULT_CAPACITY];
    }

    public void add(E e) {
        if (size == items.length) {
            ensureCapa();
        }
        items[size++] = e;
    }

    public int size() {
        return size;
    }

    private void ensureCapa() {
        int newSize = items.length * 2;
        items = Arrays.copyOf(items, newSize);
    }

    public E get(int i) {
        if (i>= size || i <0) {
            throw new IndexOutOfBoundsException("Index: " + i + ", Size " + i );
        }
        return (E) items[i];
    }

    public void remove() {
        if (size ==  0) {
            throw new NullPointerException();
        }
        items[--size] = null;
    }
}
