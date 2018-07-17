import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LuceneWriteIndexExample {
    private static final String INDEX_DIR = "./lucene6index";

    public static void main(String[] args) throws Exception {
        List<Document> documents = new ArrayList<>();
        documents.add(createDocument(1, "Lokesh", "Gupta", "howtodoinjava.com"));
        documents.add(createDocument(2, "Brian", "Schultz", "example.com"));

        IndexWriter writer = createWriter();
        writer.deleteAll(); //Let's clean everything first

        writer.addDocuments(documents);
        writer.commit();
        writer.close();
    }

    private static Document createDocument(Integer id, String firstName, String lastName, String website) {
        Document document = new Document();
        document.add(new StringField("id", id.toString(), Field.Store.YES));
        document.add(new TextField("firstName", firstName, Field.Store.YES));
        document.add(new TextField("lastName", lastName, Field.Store.YES));
        document.add(new TextField("website", website, Field.Store.YES));
        document.add(new LatLonPoint("location", 6.821994,79.886208));
        return document;
    }

    private static IndexWriter createWriter() throws IOException {
        //Directory dir = new RAMDirectory(); //FSDirectory.open(Paths.get(INDEX_DIR));
        Directory dir = FSDirectory.open(Paths.get(INDEX_DIR));
        IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
        return new IndexWriter(dir, config);
    }
}