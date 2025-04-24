package queue;

// Model: queue[start]..a[end - 1]
// Inv: queue.length >= 0 && forall i=0..size: a[i] != null
// Let: immutable(k): forall i=0..k: a'[i] = a[i]

public interface Queue {
    // Pre: element != null
    // Post: elements[end] = element && end' = (end + 1) % elements.length && immutable(start)
    void enqueue(Object element);

    // Pre: queue.length > 0
    // Post: R = elements[start] && immutable(start)
    Object element();

    // Pre: queue.length > 0
    // Post: R = elements[start] && start' = (start + 1) % elements.length && immutable(start')
    Object dequeue();

    // Pre: true
    // Post: R = queue.length && queue.length' = queue.length && immutable(start, end)
    int size();

    // Pre: true
    // Post: R = (queue.length = 0) && queue.length' = queue.length && immutable(start, end)
    boolean isEmpty();

    // Pre: true
    // Post: queue.length = 0 && start == end == 0
    void clear();

    // Pre: size > 1
    // :NOTE: Порядок + элементы?
    // Post: forall i = 1...queue.size: elements[i - 1] != elements[i] && queue.length' = queue.length - count(duplicates)
    // count(duplicates) = amount of elements[i - 1] == elements[i]
    void dedup();
}
