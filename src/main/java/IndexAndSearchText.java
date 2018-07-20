import java.io.BufferedReader;
import java.io.FileReader;

public class IndexAndSearchText {

    public static void main(String[] args) throws Exception {
        int lines = 20_000;

        Index luceneIndex = initIndex(lines, new LuceneIndex(), "Lucene");
        Index hashMapIndex = initIndex(lines, new HashMapIndex(), "Lucene");
        Index nonIndex = initIndex(lines, new NonIndex(), "Lucene");
        System.out.println();

        for (int i = 0; i < 10; i++) {
            runIndex(luceneIndex, "Lucene");
            runIndex(hashMapIndex, "HashMap");
            runIndex(nonIndex, "Full scan");
            System.out.println();
        }
    }

    private static Index initIndex(int lines, Index index, String name) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader("moby dick.txt"));

        StopWatch indexing = new StopWatch(name + " create index");
        index.indexText(reader, lines);
        indexing.split();

        return index;
    }

    private static void runIndex(Index index, String name) throws Exception {
        StopWatch find = new StopWatch(name + " lookup word");
        index.findInText("hyena");
        find.split();
    }

    interface Index {
        void indexText(BufferedReader text, int lines) throws Exception;

        Object findInText(String toFind) throws Exception;
    }

    private static class StopWatch {
        private final String name;
        private long start = System.nanoTime();

        StopWatch(String name) {
            this.name = name;
        }

        void split() {
            System.out.println(name + " took: " + (System.nanoTime() - start) / 1_000_000.0);
        }
    }
}
