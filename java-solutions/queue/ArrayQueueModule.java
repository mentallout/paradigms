package queue;

import java.util.Objects;
import java.util.function.Predicate;

// Model: queue[start]..a[end % elements.length]
// Inv: queue.length >= 0 && forall i=0..size: a[i] != null
// Let: immutable(k): forall i=0..k: a'[i] = a[i]

public class ArrayQueueModule {
    private static int start = 0;
    private static int end = 0;
    private static Object[] elements = new Object[5];

    // Pre: element != null
    // Post: elements[end] = element && end' = (end + 1) % elements.length && immutable(start)
    public static void enqueue(Object element) {
        Objects.requireNonNull(element);

        ensureCapacity(size() + 1);
        elements[end] = element;
        end = (end + 1) % elements.length;
    }

    // Pre: queue.length > 0
    // Post: R = elements[start] && immutable(start)
    public static Object element() {
        assert !isEmpty();

        return elements[start];
    }

    // Pre: queue.length > 0
    // Post: R = elements[start] && start' = (start + 1) % elements.length && immutable(start')
    public static Object dequeue() {
        assert !isEmpty();

        Object element = element();
        start = (start + 1) % elements.length;
        return element;
    }

    // Pre: true
    // Post: R = queue.length && queue.length' = queue.length && immutable(start, end)
    public static int size() {
        if (start <= end) return end - start;
        else return elements.length - (start - end);
    }

    // Pre: true
    // Post: R = (queue.length = 0) && queue.length' = queue.length && immutable(start, end)
    public static boolean isEmpty() {
        return size() == 0;
    }

    // Pre: true
    // Post: queue.length = 0 && start == end == 0
    public static void clear() {
        start = 0;
        end = 0;
    }

    // Pre: true
    // Post: R = amount of elements that are suitable for predicate && immutable(start, end, elements)
    public static int countIf(Predicate<Object> predicate) {
        int count = 0;
        for (int i = start; i != end; i = (i + 1) % elements.length) {
            if (predicate.test(elements[i])) {
                count++;
            }
        }
        return count;
    }

    // Pre: true
    // Post: queue.length' = queue.length && immutable(queue.length)
    private static void ensureCapacity(int capacity) {
        if (elements.length <= capacity) {
            Object[] temp = new Object[2 * capacity];
            if (start < end) {
                System.arraycopy(elements, start, temp, 0, size());
            } else {
                System.arraycopy(elements, start, temp, 0, elements.length - start);
                System.arraycopy(elements, 0, temp, elements.length - start, end);
            }
            start = 0;
            end = elements.length - 1;
            elements = temp;
        }
    }
}
