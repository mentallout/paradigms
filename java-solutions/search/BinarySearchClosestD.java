package search;

public class BinarySearchClosestD {
    // Time Complexity: O(log(n))
    // Contract
    // Pred: array consists of digits &&
    //       key is digit &&
    //       forall i = 0...length - 2: array[i] >= array[i + 1]
    // Post: R' = array[i] &&
    //       abs(array[R'] - key) is min &&
    //       0 <= (array[R'] - key) &&
    //       R' in array[0, array.size)
    public static int iterative(int[] array, int length, int key) {
        // left is left boundary
        int left = 0;
        // right is right boundary
        int right = length - 1;
        // forall i = 0...length - 2: array[i] >= array[i + 1] => array[array.length - 1] is min(array) && array[0] is max(array)
        if (key < array[array.length - 1]) {
            // if key < the min of the array => min difference is between min(array) and key
            return array[array.length - 1];
        } else if (key > array[0]) {
            // if key > the max of the array => min difference is between max(array) and key
            return array[0];
        }
        // before left <= right
        // :NOTE: infinite cycle?
        while (left <= right) {
            // left >= 0 => right >= 0
            int mid = (left + right) / 2;
            // left >= 0 && right >= 0 => mid >= 0
            if (array[mid] == key) {
                // array[mid] - key == 0
                // difference >= 0 => min possible difference is 0
                return array[mid];
            }
            if (array[mid] <= key) {
                // expanding search to the left
                right = mid - 1;
                // -1 <= right' < array.size
            } else {
                // expanding search to the right
                left = mid + 1;
                // 0 <= left' <= length
            }
        }
        // left = left' > right = right' => search is over
        // we compare array[mid] && key => finding minimum in any end terminates the search => min is either in left or right
        if (Math.abs(array[left] - key) < Math.abs(array[right] - key)) {
            // the min difference is array[right]
            return array[left];
        }
        // the min difference is array[left]
        return array[right];
    }

    // Time Complexity: O(log(n))
    // Contract
    // Pred: array consists of digits &&
    //       key is digit &&
    //       forall i = 0...length - 2: array[i] >= array[i + 1]
    // Post: R' = array[i] &&
    //       abs(array[R'] - key) is min &&
    //       0 <= (array[R'] - key) &&
    //       R' in array[0, array.size)
    public static int recursive(int[] array, int key, int left, int right) {
        // forall i = 0...length - 2: array[i] >= array[i + 1] => array[array.length - 1] is min(array) && array[0] is max(array)
        if (key < array[array.length - 1]) {
            // if key < the min of the array => min difference is between min(array) and key
            return array[array.length - 1];
        } else if (key > array[0]) {
            // if key > the max of the array => min difference is between max(array) and key
            return array[0];
        }
        if (left <= right) {
            // left >= 0 => right >= 0
            int mid = (left + right) / 2;
            // left >= 0 && right >= 0 => mid >= 0
            if (array[mid] == key) {
                // array[mid] - key == 0
                // difference >= 0 => min possible difference is 0
                return array[mid];
            }
            if (array[mid] <= key) {
                // expanding search to the left
                right = mid - 1;
                // -1 <= right' < array.size
            } else {
                // expanding search to the right
                left = mid + 1;
                // 0 <= left' <= length
            }
            //
            return recursive(array, key, left, right);
        }
        // left = left' > right = right' => search is over
        // we compare array[mid] && key => finding minimum in any end terminates the search => min is either in left or right
        if (Math.abs(array[left] - key) < Math.abs(array[right] - key)) {
            // the min difference is array[right]
            return array[left];
        }
        // the min difference is array[left]
        return array[right];
    }

    // :NOTE: key?
    // Pred: key != null &&
    //       key is digit &&
    //       args[1...n].size > 0 &&
    //       args[1...n] are digits &&
    //       forall i = 0...length - 2: a[i] >= a[i + 1]
    // Post: answer is an element in a
    //       print(min(abs(key - answer))) &&
    //       recursive if summa is even else iterative
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
        // summa is sum of elements in a
        int summa = 0;
        // putting args[1...args.size - 1] to a[0...n - 1]
        for (int i = 0; i < n; i++) {
            // immutable(args)
            // forall i = 0...n - 1: a'[i] = args[j] &&
            // j = 1...args.size - 1
            a[i] = Integer.parseInt(args[i + 1]);
            // counting summa
            summa += a[i];
        }
        // a consists of n digits
        // recursive if summa is even else iterative
        if (summa % 2 == 0) {
            /* recursive variant of binary search */
            System.out.println(recursive(a, x, 0, n - 1));
        } else {
            /* iterative variant of binary search */
            System.out.println(iterative(a, n, x));
        }
        // int number is an index of the found element
    }
}
