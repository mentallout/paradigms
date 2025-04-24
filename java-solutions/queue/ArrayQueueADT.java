package queue;

import java.util.Objects;
import java.util.function.Predicate;

// Model: queue[start]..a[end % elements.length]
// Inv: queue.length >= 0 && forall i=0..size: a[i] != null
// Let: immutable(k): forall i=0..k: a'[i] = a[i]

public class ArrayQueueADT {
    private int start = 0;
    private int end = 0;
    private Object[] elements = new Object[5];

    public ArrayQueueADT() {
    }

    // Pre: element != null
    // Post: elements[end] = element && end' = (end + 1) % elements.length && immutable(start)
    public static void enqueue(ArrayQueueADT queue, Object element) {
        Objects.requireNonNull(element);

        ensureCapacity(queue, size(queue) + 1);
        queue.elements[queue.end] = element;
        queue.end = (queue.end + 1) % queue.elements.length;
    }

    // Pre: queue.length > 0
    // Post: R = elements[start] && immutable(start)
    public static Object element(ArrayQueueADT queue) {
        assert !isEmpty(queue);

        return queue.elements[queue.start];
    }

    // Pre: queue.length > 0
    // Post: R = elements[start] && start' = (start + 1) % elements.length && immutable(start')
    public static Object dequeue(ArrayQueueADT queue) {
        assert !isEmpty(queue);

        Object element = element(queue);
        queue.start = (queue.start + 1) % queue.elements.length;
        return element;
    }

    // Pre: true
    // Post: R = queue.length && queue.length' = queue.length && immutable(start, end)
    public static int size(ArrayQueueADT queue) {
        if (queue.start <= queue.end) return queue.end - queue.start;
        return queue.elements.length - (queue.start - queue.end);
    }

    // Pre: true
    // Post: R = (queue.length = 0) && queue.length' = queue.length && immutable(start, end)
    public static boolean isEmpty(ArrayQueueADT queue) {
        return size(queue) == 0;
    }

    // Pre: true
    // Post: queue.length = 0 && start == end == 0
    public static void clear(ArrayQueueADT queue) {
        queue.start = 0;
        queue.end = 0;
    }

    // Pre: true
    // Post: R = amount of elements that are suitable for predicate && immutable(start, end, elements)
    public static int countIf(ArrayQueueADT queue, Predicate<Object> predicate) {
        int count = 0;
        for (int i = queue.start; i != queue.end; i = (i + 1) % queue.elements.length) {
            if (predicate.test(queue.elements[i])) {
                count++;
            }
        }
        return count;
    }

    // Pre: true
    // Post: queue.length' = queue.length && immutable(queue.length)
    private static void ensureCapacity(ArrayQueueADT queue, int capacity) {
        if (queue.elements.length <= capacity) {
            Object[] temp = new Object[2 * capacity];
            if (queue.start < queue.end) {
                System.arraycopy(queue.elements, queue.start, temp, 0, size(queue));
            } else {
                System.arraycopy(queue.elements, queue.start, temp, 0, queue.elements.length - queue.start);
                System.arraycopy(queue.elements, 0, temp, queue.elements.length - queue.start, queue.end);
            }
            queue.start = 0;
            queue.end = queue.elements.length - 1;
            queue.elements = temp;
        }
    }
}
