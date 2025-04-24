package queue;

public class ArrayQueueADTMyOwnTests {
    public static void fill(ArrayQueueADT queue, int[] args) {
        for (int arg : args) {
            ArrayQueueADT.enqueue(queue, arg);
        }
    }

    public static void dump(ArrayQueueADT queue) {
        while (!ArrayQueueADT.isEmpty(queue)) {
            System.out.println("size = " + ArrayQueueADT.size(queue) + ", element dequeued:" + ArrayQueueADT.dequeue(queue));
        }
    }

    public static void main(String[] args) {
        ArrayQueueADT queue1 = new ArrayQueueADT();
        ArrayQueueADT queue2 = new ArrayQueueADT();
        int[] temp1 = new int[]{1, 2, 3};
        int[] temp2 = new int[]{4, 5, 6, 7};
        fill(queue1, temp1);
        fill(queue2, temp2);
        dump(queue1);
        dump(queue2);
    }
}
