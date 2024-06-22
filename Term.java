import java.util.Arrays;
import java.util.Comparator;

public class Term implements Comparable<Term> {
    private String query; // string associated with each term
    private long weight; // the weight of each term

    // Initializes a term with the given query string and weight.
    public Term(String query, long weight) {
        if (query == null || weight < 0) {
            throw new IllegalArgumentException("inadmissable arguments");
        }
        this.query = query;
        this.weight = weight;
    }

    private static class ReverseWeightComparator implements Comparator<Term> {
        public int compare(Term first, Term second) {
            return Long.compare(second.weight, first.weight);
        }
    }

    private static class ByPrefixOrder implements Comparator<Term> {

        private int r; // length of prefix to check by

        // initializes r to private variable of the class
        private ByPrefixOrder(int r) {
            if (r < 0) throw new IllegalArgumentException("invalid r value");
            this.r = r;
        }

        // compares two terms by the first r letters of each query
        public int compare(Term first, Term second) {
            int max = Math.max(first.query.length(), second.query.length());
            int min = Math.min(first.query.length(), second.query.length());

            if (r >= max) {
                return first.query.compareTo(second.query);
            }

            if (r < min) {
                for (int i = 0; i < r; i++) {
                    char x = first.query.charAt(i);
                    char y = second.query.charAt(i);

                    if (x > y) return 1;
                    if (x < y) return -1;
                }
                return 0;
            }
            else {
                for (int i = 0; i < min; i++) {
                    char x = first.query.charAt(i);
                    char y = second.query.charAt(i);

                    if (x > y) return 1;
                    if (x < y) return -1;
                }
                if (r == min) return 0;
                return first.query.length() - second.query.length();
            }
        }
    }


    // Compares the two terms in descending order by weight.
    public static Comparator<Term> byReverseWeightOrder() {
        return new ReverseWeightComparator();
    }

    // Compares the two terms in lexicographic order,
    // but using only the first r characters of each query.
    public static Comparator<Term> byPrefixOrder(int r) {
        return new ByPrefixOrder(r);
    }


    // Compares the two terms in lexicographic order by query.
    @Override
    public int compareTo(Term that) {
        return query.compareTo(that.query);
    }

    // Returns a string representation of this term in the following format:
    // the weight, followed by a tab, followed by the query.
    public String toString() {
        return (weight + "\t" + query);
    }

    // unit testing (required)
    public static void main(String[] args) {


        Term[] terms = new Term[5];
        terms[0] = new Term("sun", 4);
        terms[1] = new Term("sunset", 5);
        terms[2] = new Term("hello", 2);
        terms[3] = new Term("sunday", 100);
        terms[4] = new Term("poop", 0);

        Arrays.sort(terms, byPrefixOrder(3));

        for (int i = 0; i < terms.length; i++) {
            System.out.println(terms[i].query);
        }

        Arrays.sort(terms, byReverseWeightOrder());
        System.out.println();

        for (int i = 0; i < terms.length; i++) {
            System.out.println(terms[i].query);


        }

    }

}
