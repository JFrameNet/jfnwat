package jp.keio.jfn.jfnwat.webreport.controller;

import jp.keio.jfn.wat.JFNWAT;
import jp.keio.jfn.wat.webreport.controller.DocumentController;
import jp.keio.jfn.wat.domain.Corpus;
import jp.keio.jfn.wat.domain.Document;
import jp.keio.jfn.wat.repository.CorpusRepository;
import jp.keio.jfn.wat.repository.DocumentRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by jfn on 2/3/16.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(JFNWAT.class)
@ActiveProfiles("test")
public class DocumentControllerTest {

    @Autowired
    DocumentRepository documentRepository;

    @Autowired
    CorpusRepository corpusRepository;

    private DocumentController controller;

    @Before
    public void setup() {
        controller = new DocumentController();

        Corpus corpus = new Corpus();
        corpus.setName("corpus");
        corpus.setCreatedBy("test");
        corpus.setModifiedDate(new Timestamp(new java.util.Date().getTime()));

        corpusRepository.save(corpus);

        Document document = new Document();
        document.setName("foo");
        document.setCreatedBy("test");
        document.setCorpus(corpus);
        document.setModifiedDate(new Timestamp(new java.util.Date().getTime()));

        documentRepository.save(document);

        controller.setDocumentRepository(documentRepository);
    }

    @After
    public void after() {
        documentRepository.deleteAll();
        corpusRepository.deleteAll();
        controller = null;
    }

    @Test
    public void testEmptyFilterCorpora() {
        controller.setFilter("");
        controller.filterCorpora();
        assertEquals(1,controller.getAllDocs().size());
    }

    @Test
    public void testFilterCorpora() {
        Document document = new Document();
        document.setId(1);
        document.setName("document name");
        document.setCreatedBy("test");
        document.setCorpus(corpusRepository.findById(0));
        document.setModifiedDate(new Timestamp(new java.util.Date().getTime()));
        documentRepository.save(document);

        controller.setFilter("document name");
        controller.filterCorpora();
        assertEquals(1,controller.getAllDocs().size());

        controller.setFilter("document");
        controller.filterCorpora();
        assertEquals(1,controller.getAllDocs().size());

        controller.setFilter("oCuMeNt");
        controller.filterCorpora();
        assertEquals(1,controller.getAllDocs().size());

        // Filter also on corpus name. For this test, all documents refer to a corpus called "corpus"
        controller.setFilter("corpus");
        controller.filterCorpora();
        assertEquals(2,controller.getAllDocs().size());

        documentRepository.delete(document);
    }

    @Test
    public void testGetAllDocs() {
        controller.setFilter("");
        controller.setAllDocs(new ArrayList<Document>());
        controller.filterCorpora();
        assertEquals(1,controller.getAllDocs().size());
    }
}
