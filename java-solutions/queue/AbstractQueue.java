package queue;

import java.util.Objects;

public abstract class AbstractQueue implements Queue {
    public int size = 0;

    public abstract void abstractEnqueue(Object element);

    public abstract Object abstractElement();

    public abstract void abstractDequeue();

    public abstract void abstractClear();

    public abstract void abstractDedup();

    public void enqueue(Object element) {
        Objects.requireNonNull(element);

        size++;
        abstractEnqueue(element);
    }

    public Object element() {
        assert !isEmpty();

        return abstractElement();
    }

    public Object dequeue() {
        assert !isEmpty();

        Object element = element();
        abstractDequeue();
        size--;
        return element;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        size = 0;
        abstractClear();
    }

    public void dedup() {
        if (size > 1) {
            abstractDedup();
        }
    }
}