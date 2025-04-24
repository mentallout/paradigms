package queue;

public class ArrayQueueMyOwnTests {
    public static void fill(ArrayQueue queue, int[] args) {
        for (int arg : args) {
            queue.enqueue(arg);
        }
    }

    public static void dump(ArrayQueue queue) {
        while (!queue.isEmpty()) {
            System.out.println("size = " + queue.size() + ", element dequeued:" + queue.dequeue());
        }
    }

    public static void main(String[] args) {
        ArrayQueue queue1 = new ArrayQueue();
        ArrayQueue queue2 = new ArrayQueue();
        int[] temp1 = new int[]{1, 2, 3};
        int[] temp2 = new int[]{4, 5, 6, 7};
        fill(queue1, temp1);
        fill(queue2, temp2);
        dump(queue1);
        dump(queue2);
    }
}
