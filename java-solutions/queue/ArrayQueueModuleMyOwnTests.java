package queue;

public class ArrayQueueModuleMyOwnTests {
    public static void fill(int[] args) {
        for (int arg : args) {
            ArrayQueueModule.enqueue(arg);
        }
    }

    public static void dump() {
        while (!ArrayQueueModule.isEmpty()) {
            System.out.println("size = " + ArrayQueueModule.size() + ", element dequeued:" + ArrayQueueModule.dequeue());
        }
    }

    public static void main(String[] args) {
        int[] temp = new int[]{1, 2, 3};
        fill(temp);
        dump();
    }
}
