package johnhearn;

import org.apache.lucene.queryparser.classic.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class HashMapIndex implements IndexAndSearchText.Index {

    private Map<String, List<String>> index = new HashMap<>();

    public void indexText(BufferedReader text, int lines) throws IOException {
        int lineNo = 1;
        String s = text.readLine();
        while (s != null && lineNo < lines) {
            String[] tokens = s.toLowerCase().split("[\\s\\.]");
            for (String token : tokens) {
                if (index.containsKey(token)) {
                    index.get(token).add(s);
                } else {
                    List<String> list = new ArrayList<>();
                    list.add(s);
                    index.put(token, list);
                }
            }
            lineNo++;
            s = text.readLine();
        }

        //System.out.println("Total lines indexed :: " + lineNo);
    }

    public List<String> findInText(String toFind) throws IOException, ParseException {

        List<String> foundDocs = index.get(toFind);

        //System.out.println("Total Results :: " + foundDocs.size());
/*
        for (String foundDoc : foundDocs) {
            System.out.println(foundDoc);
        }
*/
        return foundDocs;
    }
}