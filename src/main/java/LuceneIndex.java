import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

import java.io.BufferedReader;
import java.io.IOException;

class LuceneIndex implements IndexAndSearchText.Index {

    private Directory index = new RAMDirectory();
    private IndexSearcher searcher;

    public void indexText(BufferedReader text, int lines) throws IOException {
        IndexWriter writer = createWriter();
        writer.deleteAll(); //Let's clean everything first

        int lineNo = 1;
        String s = text.readLine();
        while(s != null && lineNo < lines) {
            writer.addDocument(createDocument(lineNo++, s));
            s = text.readLine();
        }
        writer.commit();
        writer.close();

        //System.out.println("Total lines indexed :: " + lineNo);

        searcher = createSearcher();
    }

    private Document createDocument(Integer lineNo, String line) {
        Document document = new Document();
        document.add(new StringField("lineNo", lineNo.toString(), Field.Store.YES));
        document.add(new TextField("line", line, Field.Store.YES));
        return document;
    }

    private IndexWriter createWriter() throws IOException {
        IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
        return new IndexWriter(index, config);
    }

    private IndexSearcher createSearcher() throws IOException {
        IndexReader reader = DirectoryReader.open(index);
        return new IndexSearcher(reader);
    }

    public TopDocs findInText(String toFind) throws IOException, ParseException {
        //Search by ID
        TopDocs foundDocs = search(toFind, searcher);

        //System.out.println("Total Results :: " + foundDocs.totalHits);
/*
        for (ScoreDoc sd : foundDocs.scoreDocs) {
            Document d = searcher.doc(sd.doc);
            System.out.println(d.get("lineNo") + " " + d.get("line"));
        }
*/
        return foundDocs;
    }

    private TopDocs search(String toFind, IndexSearcher searcher) throws IOException, ParseException {
        QueryParser qp = new QueryParser("line", new StandardAnalyzer());
        Query firstNameQuery = qp.parse(toFind);
        return searcher.search(firstNameQuery, 10);
    }
}