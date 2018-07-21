import org.apache.lucene.queryparser.classic.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class NonIndex implements IndexAndSearchText.Index {

    private List<String> index = new ArrayList<>();

    public void indexText(BufferedReader text, int lines) throws Exception {
        int lineNo = 1;
        String s = text.readLine();
        while (s != null && lineNo < lines) {
            index.add(s.toLowerCase());
            lineNo++;
            s = text.readLine();
        }

        //System.out.println("Total lines indexed :: " + lineNo);
    }

    public List<String> findInText(String toFind) throws Exception {

        List<String> foundDocs = new ArrayList<>();

        for (String s : index) {
            if (matcher(toFind, s))
                foundDocs.add(s);
        }

        //System.out.println("Total Results :: " + foundDocs.size());
/*
        for (String foundDoc : foundDocs) {
            System.out.println(foundDoc);
        }
*/
        return foundDocs;
    }

    protected boolean matcher(String toFind, String s) {
        return s.contains(toFind);
    }
}