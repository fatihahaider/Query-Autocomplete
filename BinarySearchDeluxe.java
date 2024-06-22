import java.util.Arrays;
import java.util.Comparator;

public class BinarySearchDeluxe {

    // Returns the index of the first key in the sorted array a[]
    // that is equal to the search key, or -1 if no such key.
    public static <Key> int firstIndexOf(Key[] a, Key key,
                                         Comparator<Key> comparator) {
        if (key == null || a == null || comparator == null) throw new
                IllegalArgumentException("Null Argument");

        int low = 0;
        int high = a.length - 1;
        int index = -1;

        while (low <= high) {
            int mid = (high + low) >>> 1;

            int comp = comparator.compare(key, a[mid]);
            if (comp > 0) low = mid + 1;
            else if (comp < 0) high = mid - 1;
            else {
                index = mid;
                high = mid - 1;
            }
        }
        return index;
    }

    // Returns the index of the last key in the sorted array a[]
    // that is equal to the search key, or -1 if no such key.
    public static <Key> int lastIndexOf(Key[] a, Key key,
                                        Comparator<Key> comparator) {
        if (key == null || a == null || comparator == null) throw new
                IllegalArgumentException("Null Arguments");

        int low = 0;
        int high = a.length - 1;
        int index = -1;

        while (low <= high) {
            int mid = (high + low) >>> 1;

            int comp = comparator.compare(key, a[mid]);

            if (comp > 0) low = mid + 1;
            else if (comp < 0) high = mid - 1;
            else {
                index = mid;
                low = mid + 1;
            }
        }
        return index;
    }

    public static void main(String[] args) {
        String query = "hola";
        String[] a = {
                "hey", "hi", "hello", "hello", "hello",
                "whats up", "hola", "hola", "sup"
        };
        Arrays.sort(a);

        Comparator<String> x = Comparator.naturalOrder();
        System.out.println(firstIndexOf(a, query, x));
        System.out.println(lastIndexOf(a, query, x));

    }
}
