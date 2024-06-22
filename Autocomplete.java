import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

public class Autocomplete {

    // array of terms
    private Term[] array;

    // Initializes the data structure from the given array of terms.
    public Autocomplete(Term[] terms) {
        if (terms == null) throw new IllegalArgumentException(
                "invalid arguments");
        array = new Term[terms.length];
        for (int i = 0; i < terms.length; i++) {
            if (terms[i] == null) throw new IllegalArgumentException(
                    "invalid arguments");

            array[i] = terms[i];
        }
        Arrays.sort(array);
    }

    // Returns all terms that start with the given prefix,
    // in descending order of weight.
    public Term[] allMatches(String prefix) {
        if (prefix == null) throw new IllegalArgumentException(
                "prefix is null");

        Comparator<Term> comp = Term.byPrefixOrder(prefix.length());
        Term term = new Term(prefix, 0);

        int low = BinarySearchDeluxe.firstIndexOf(array, term, comp);
        int high = BinarySearchDeluxe.lastIndexOf(array, term, comp);

        if (low == -1 || high == -1) {
            return new Term[0];
        }

        Term[] allMatches = new Term[high - low + 1];
        int index = 0;
        for (int i = low; i <= high; i++) {
            allMatches[index] = array[i];
            index++;
        }
        Comparator<Term> comp2 = Term.byReverseWeightOrder();
        Arrays.sort(allMatches, comp2);
        return allMatches;
    }

    // Returns the number of terms that start with the given prefix.
    public int numberOfMatches(String prefix) {
        if (prefix == null) throw new IllegalArgumentException(
                "prefix is null");

        Comparator<Term> comp = Term.byPrefixOrder(prefix.length());

        Term term = new Term(prefix, 0);

        int low = BinarySearchDeluxe.firstIndexOf(array, term, comp);
        int high = BinarySearchDeluxe.lastIndexOf(array, term, comp);

        if (low == -1 || high == -1) return 0;
        else return high - low + 1;
    }

    // unit testing (required)
    public static void main(String[] args) {
        // read in the terms from a file
        String filename = args[0];
        In in = new In(filename);
        int n = in.readInt();
        Term[] terms = new Term[n];
        for (int i = 0; i < n; i++) {
            long weight = in.readLong();           // read the next weight
            in.readChar();                         // scan past the tab
            String query = in.readLine();          // read the next query
            terms[i] = new Term(query, weight);    // construct the term
        }

        // read in queries from standard input and print the top k matching terms
        int k = Integer.parseInt(args[1]);
        Autocomplete autocomplete = new Autocomplete(terms);
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            Term[] results = autocomplete.allMatches(prefix);
            StdOut.printf("%d matches\n", autocomplete.numberOfMatches(prefix));
            for (int i = 0; i < Math.min(k, results.length); i++)
                StdOut.println(results[i]);
        }
    }

}
