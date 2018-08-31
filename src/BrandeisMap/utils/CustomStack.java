package BrandeisMap.utils;

import java.util.Arrays;

public class CustomStack<T>{

    private static final int DEFAULT_CAPACITY = 10;
    private T[] store;
    private int size = 0;
    private int capacity;

    public CustomStack() {
        this.capacity = DEFAULT_CAPACITY;
        store = (T[]) new Object[DEFAULT_CAPACITY];
    }

    public boolean push(T value) {
        if (this.size >= store.length) {
            int newSize = size + (size >> 1);
            store = Arrays.copyOf(store, newSize);
        }

        store[size++] = value;
        return true;
    }

    public T pop() {
        if (size <= 0) {
            return null;
        }
        T value = store[--size];
        store[size] = null;
        int reducedSize = size;
        if (size >= capacity && size < (reducedSize + (reducedSize << 1))) {
            System.arraycopy(store, 0, store, 0, size);
        }
        return value;
    }

    public int size() {
        return size;
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            store[i] = null;
        }
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}