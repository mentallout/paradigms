package search;

public class BinarySearch {
    // Time Complexity: O(log(n))
    // Contract
    // Pred: array consists of digits if key &&
    //       key is digit &&
    //       forall i = 0...length - 2: array[i] >= array[i + 1]
    // Post: R' = i: i is min &&
    //       array[R'] <= key ||
    //       R' = length if forall i = 0...array.size - 1: key > array[i]
    //                   else: R' in [0, array.size)
    public static int iterative(int[] array, int length, int key) {
        // left is left boundary
        int left = 0;
        // right is right boundary
        int right = length - 1;
        // left <= right
        while (left <= right) {
            // left >= 0 => right >= 0
            int mid = (left + right) / 2;
            // left >= 0 && right >= 0 => mid >= 0
            if (array[mid] <= key) {
                // expanding search to the left
                right = mid - 1;
                // -1 <= right' < array.size
                // a[right' + 1] <= key
            } else {
                // expanding search to the right
                left = mid + 1;
                // 0 <= left' <= length
                // array[left'] > key
            }
        }
        // left = left' > right = right' => search is over
        // 0 <= R' = right + 1 <= length
        return right + 1;
    }

    // Time Complexity: O(log(n))
    // Contract
    // Pred: array consists of digits if key &&
    //       key is digit &&
    //       forall i = 0...length - 2: array[i] >= array[i + 1]
    // Post: R' = i: i is min &&
    //       array[R'] <= key ||
    //       R' = length if forall i = 0...array.size - 1: key > array[i]
    //                   else: R' in [0, array.size)
    public static int recursive(int[] array, int key, int left, int right) {
        if (left <= right) {
            // left >= 0 => right >= 0
            int mid = (left + right) / 2;
            // array[left] > key && array[right] <= key &&
            // (left <= right) => (array[left] >= array[right]) && right - left >= 0
            if (array[mid] > key) {
                // array[right + 1] <= key &&
                left = mid + 1;
                // array[left'] > key
                // left <= right || left - right == 1
            } else {
                // array[right + 1] <= key &&
                // array[left] > key
                right = mid - 1;
                // array[right' + 1] <= key
                // left <= right || left - right == 1
            }
            // if right == left => next call will be the last
            // else key is still not found
            return recursive(array, key, left, right);
        }
        // right >= -1 && left > right => left >= 0
        // right = index of key - 1 if key is found ||
        // right = array.size - 1 if key is not found ||
        return right + 1;
    }

    // Pred: key != null &&
    //       key is digit &&
    //       args[1...n].size > 0 &&
    //       args[1...n] are digits &&
    //       forall i = 0...length - 2: a[i] >= a[i + 1]
    // Post: answer is an index
    //       print(min(answer)) &&
    //       int(a[answer]) <= key &&
    //       if key <= a[answer]: answer in [0, a.size)
    //       else: answer == a.size
    public static void main(String[] args) {
        // in pred: key != null and is digit => no errors
        // x is key
        int x = Integer.parseInt(args[0]);
        // a is an array of digits
        int[] a;
        // n is a size of a
        int n = args.length - 1;
        // key != null => args.size > 0 => n >= 0
        a = new int[n];
        // putting args[1...args.size - 1] to a[0...n - 1]
        for (int i = 0; i < n; i++) {
            // immutable(args)
            // forall i = 0...n - 1: a'[i] = args[j] &&
            // j = 1...args.size - 1
            a[i] = Integer.parseInt(args[i + 1]);
        }
        // a consists of n digits

        /* recursive variant of binary search */
        //System.out.println(recursive(a, x, 0, n - 1));
        // int number is an index of the found element

        /* iterative variant of binary search */
        System.out.println(iterative(a, n, x));
        // int number is an index of the found element
    }
}
