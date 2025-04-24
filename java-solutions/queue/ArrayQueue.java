package queue;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;

public class ArrayQueue extends AbstractQueue {
    private int start = 0;
    private int end = 0;
    private Object[] elements = new Object[5];

    @Override
    public void abstractEnqueue(Object element) {
        Objects.requireNonNull(element);

        ensureCapacity();
        elements[end] = element;
        end = (end + 1) % elements.length;
    }

    @Override
    public Object abstractElement() {
        assert !isEmpty();

        return elements[start];
    }

    @Override
    public void abstractDequeue() {
        assert !isEmpty();

        elements[start] = null;
        start = (start + 1) % elements.length;
    }

    @Override
    public void abstractClear() {
        while (!isEmpty()) {
            dequeue();
        }
        start = 0;
        end = 0;
    }

    public int countIf(Predicate<Object> predicate) {
        int count = 0;
        for (int i = start; i != end; i = (i + 1) % elements.length) {
            if (predicate.test(elements[i])) {
                count++;
            }
        }
        return count;
    }

    public void abstractDedup() {
        int temp = start;
        while (temp != end) {
            int next = (temp + 1) % elements.length;
            if (next == end) {
                break;
            }
            if (elements[temp].equals(elements[next])) {
                while (next != end) {
                    elements[next] = elements[(next + 1) % elements.length];
                    next = (next + 1) % elements.length;
                }
                end = (end - 1 + elements.length) % elements.length;
                size--;
            } else {
                temp = next;
            }
        }
    }

    private void ensureCapacity() {
        if (size() == elements.length - 1) {
            Object[] temp = new Object[elements.length * 2];
            int count = 0;
            for (int i = start; i != end; i = (i + 1) % elements.length) {
                temp[count++] = elements[i];
            }
            start = 0;
            end = count;
            elements = temp;
        }
    }
}
