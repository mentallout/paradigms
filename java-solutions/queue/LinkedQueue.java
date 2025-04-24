package queue;

public class LinkedQueue extends AbstractQueue {
    private Node start;
    private Node end;

    @Override
    public void abstractEnqueue(Object element) {
        Node newNode = new Node(element, null);
        if (start == null) {
            start = newNode;
        } else {
            end.next = newNode;
        }
        end = newNode;
    }

    @Override
    public Object abstractElement() {
        assert !isEmpty();

        return start.value;
    }

    @Override
    public void abstractDequeue() {
        assert !isEmpty();

        start = start.next;
        if (start == null) {
            end = null;
        }
    }

    @Override
    public void abstractClear() {
        start = null;
        end = null;
    }


    public void abstractDedup() {
        Node temp = start;
        while (temp.next != null) {
            if (temp.value.equals(temp.next.value)) {
                temp.next = temp.next.next;
                size--;
            } else {
                temp = temp.next;
            }
        }
        end = temp;
    }

    private static class Node {
        private final Object value;
        private Node next;

        public Node(Object value, Node next) {
            assert value != null;

            this.value = value;
            this.next = next;
        }
    }
}
