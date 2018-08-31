package BrandeisMap.utils;

import java.util.*;

public class CustomMinHeap<E>{
    private static int DEFAULT_INITIAL_CAPACITY = 10;
    private Object[] queue;
    private int size = 0;
    private Comparator<? super E> comparator;

    private transient int modCount = 0;

    public CustomMinHeap() {
        this.queue = new Object[DEFAULT_INITIAL_CAPACITY];
        this.comparator = null;
    }

    public CustomMinHeap(Comparator<? super E> comparator) {
        this.queue = new Object[DEFAULT_INITIAL_CAPACITY];
        this.comparator = comparator;
    }

    private void resize(int minCapacity) {
        if (minCapacity < 0) // overflow
            throw new OutOfMemoryError();
        int oldCapacity = queue.length;
        // Double size if small; else grow by 50%
        int newCapacity = ((oldCapacity < 64)?
                ((oldCapacity + 1) * 2):
                ((oldCapacity / 2) * 3));
        if (newCapacity < 0) // overflow
            newCapacity = Integer.MAX_VALUE;
        if (newCapacity < minCapacity)
            newCapacity = minCapacity;
        queue = Arrays.copyOf(queue, newCapacity);
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean add(E e) {
        if (e == null)
            throw new NullPointerException();
        modCount++;
        int i = size;
        if (i >= queue.length)
            resize(i + 1);
        size = i + 1;
        if (i == 0)
            queue[0] = e;
        else
            siftUp(i, e);
        return true;
    }

    public int size() {
        return size;
    }

    public E poll() {
        if (size == 0)
            return null;
        int s = --size;
        modCount++;
        E result = (E) queue[0];
        E x = (E) queue[s];
        queue[s] = null;
        if (s != 0)
            siftDown(0, x);
        return result;
    }

    private void siftUp(int k, E x) {
        if (comparator != null) {
            while (k > 0) {
                int parent = (k - 1) >>> 1;
                Object e = queue[parent];
                if (comparator.compare(x, (E) e) >= 0)
                    break;
                queue[k] = e;
                k = parent;
            }
            queue[k] = x;
        } else {
            Comparable<? super E> key = (Comparable<? super E>) x;
            while (k > 0) {
                int parent = (k - 1) >>> 1;
                Object e = queue[parent];
                if (key.compareTo((E) e) >= 0)
                    break;
                queue[k] = e;
                k = parent;
            }
            queue[k] = key;
        }
    }

    private void siftDown(int k, E x) {
        if (comparator != null) {
            int half = size >>> 1;
            while (k < half) {
                int child = (k << 1) + 1;
                Object c = queue[child];
                int right = child + 1;
                if (right < size &&
                        comparator.compare((E) c, (E) queue[right]) > 0)
                    c = queue[child = right];
                if (comparator.compare(x, (E) c) <= 0)
                    break;
                queue[k] = c;
                k = child;
            }
            queue[k] = x;
        } else {
            Comparable<? super E> key = (Comparable<? super E>)x;
            int half = size >>> 1;        // loop while a non-leaf
            while (k < half) {
                int child = (k << 1) + 1; // assume left child is least
                Object c = queue[child];
                int right = child + 1;
                if (right < size &&
                        ((Comparable<? super E>) c).compareTo((E) queue[right]) > 0)
                    c = queue[child = right];
                if (key.compareTo((E) c) <= 0)
                    break;
                queue[k] = c;
                k = child;
            }
            queue[k] = key;
        }
    }
}