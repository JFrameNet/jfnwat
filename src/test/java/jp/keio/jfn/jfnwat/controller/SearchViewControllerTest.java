package jp.keio.jfn.jfnwat.controller;

import jp.keio.jfn.wat.JFNWAT;

import jp.keio.jfn.wat.controller.SearchViewController;
import jp.keio.jfn.wat.webreport.domain.*;
import jp.keio.jfn.wat.webreport.repository.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;

import static org.junit.Assert.*;
/**
 * Created by jfn on 2/12/16.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(JFNWAT.class)
@ActiveProfiles("test")
public class SearchViewControllerTest {

    private SearchViewController controller;

    @Autowired
    FrameRepository frameRepository;

    @Autowired
    LexUnitRepository lexUnitRepository;

    @Autowired
    DocumentRepository documentRepository;

    @Autowired
    CorpusRepository corpusRepository;

    @Autowired
    LemmaRepository lemmaRepository;

    @Autowired
    PartOfSpchRepository partOfSpchRepository;

    private Frame frame;
    private Lemma lemma;

    @Before
    public void setup(){
        controller = new SearchViewController();
        controller.setLexUnitRepository(lexUnitRepository);
        controller.setDocumentRepository(documentRepository);
        controller.setCorpusRepository(corpusRepository);
        controller.setFrameRepository(frameRepository);

        frame = new Frame();
        frame.setName("frame");
        frame.setCreatedBy("test");
        frame.setModifiedDate(new Timestamp(new java.util.Date().getTime()));
        frameRepository.save(frame);

        PartOfSpch partOfSpch = new PartOfSpch();
        partOfSpch.setName("name");
        partOfSpchRepository.save(partOfSpch);

        lemma = new Lemma();
        lemma.setPartOfSpch(partOfSpch);
        lemma.setCreatedBy("test");
        lemma.setModifiedDate(new Timestamp(new java.util.Date().getTime()));
        lemmaRepository.save(lemma);

        LexUnit lexUnit = new LexUnit();
        lexUnit.setLemma(lemma);
        lexUnit.setName("lexical unit");
        lexUnit.setFrame(frame);
        lexUnit.setCreatedBy("test");
        lexUnit.setModifiedDate(new Timestamp(new java.util.Date().getTime()));

        lexUnitRepository.save(lexUnit);

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
    }

    @After
    public void after() {
        lexUnitRepository.deleteAll();
        frameRepository.deleteAll();
        documentRepository.deleteAll();
        corpusRepository.deleteAll();
        lemmaRepository.deleteAll();
        partOfSpchRepository.deleteAll();
    }

    @Test
    public void testNavigateWithSearch() {
        controller.setSearch("mysearch");
        assertEquals("searchPage?faces-redirect=true&search=mysearch", controller.navigateWithSearch());
    }

    @Test
    public void testCompleteText() {
        assertEquals(4, controller.completeText("").size());
        assertEquals(1,controller.completeText("frame").size());
        assertEquals(1, controller.completeText("lexical unit").size());
        assertEquals(1, controller.completeText("corpus").size());
        assertEquals(1, controller.completeText("foo").size());
        assertEquals(2, controller.completeText("f").size());
    }

    @Test
    public void testFindFrameKeyword() {
        controller.setSearch("");
        assertEquals(0, controller.findFrameKeyword().size());

        Frame frame1 = new Frame();
        frame1.setName("Activity_Start");
        frame1.setCreatedBy("test");
        frame1.setModifiedDate(new Timestamp(new java.util.Date().getTime()));
        frameRepository.save(frame1);

        Frame frame2 = new Frame();
        frame2.setName("Activity_Stop");
        frame2.setCreatedBy("test");
        frame2.setModifiedDate(new Timestamp(new java.util.Date().getTime()));
        frameRepository.save(frame2);

        controller.setSearch("a");
        assertEquals(3, controller.findFrameKeyword().size());

        controller.setSearch("start");
        assertEquals(1, controller.findFrameKeyword().size());

        controller.setSearch("activity");
        assertEquals(2, controller.findFrameKeyword().size());

        frameRepository.delete(frame1);
        frameRepository.delete(frame2);
    }

    @Test
    public void testFindLUKeyword() {
        controller.setSearch("");
        assertEquals(0, controller.findLUKeyword().size());

        LexUnit lexUnit1 = new LexUnit();
        lexUnit1.setId(1);
        lexUnit1.setLemma(lemma);
        lexUnit1.setName("run.v");
        lexUnit1.setFrame(frame);
        lexUnit1.setCreatedBy("test");
        lexUnit1.setModifiedDate(new Timestamp(new java.util.Date().getTime()));
        lexUnitRepository.save(lexUnit1);

        LexUnit lexUnit2 = new LexUnit();
        lexUnit2.setId(2);
        lexUnit2.setLemma(lemma);
        lexUnit2.setName("tell.v");
        lexUnit2.setFrame(frame);
        lexUnit2.setCreatedBy("test");
        lexUnit2.setModifiedDate(new Timestamp(new java.util.Date().getTime()));
        lexUnitRepository.save(lexUnit2);

        controller.setSearch("run");
        assertEquals(1, controller.findLUKeyword().size());

        controller.setSearch("v");
        assertEquals(2, controller.findLUKeyword().size());

        // Filter on frame name
        controller.setSearch("frame");
        assertEquals(3, controller.findLUKeyword().size());

        lexUnitRepository.delete(lexUnit1);
        lexUnitRepository.delete(lexUnit2);
    }

    @Test
    public void testFindCorpusKeyword() {
        controller.setSearch("");
        assertEquals(0, controller.findCorpusKeyword().size());

        Corpus corpus1 = new Corpus();
        corpus1.setId(1);
        corpus1.setName("japan");
        corpus1.setCreatedBy("test");
        corpus1.setModifiedDate(new Timestamp(new java.util.Date().getTime()));
        corpusRepository.save(corpus1);

        Corpus corpus2 = new Corpus();
        corpus2.setId(2);
        corpus2.setName("france");
        corpus2.setCreatedBy("test");
        corpus2.setModifiedDate(new Timestamp(new java.util.Date().getTime()));
        corpusRepository.save(corpus2);

        controller.setSearch("japan");
        assertEquals(1, controller.findCorpusKeyword().size());

        controller.setSearch("anything");
        assertEquals(0, controller.findCorpusKeyword().size());

        controller.setSearch("A");
        assertEquals(2, controller.findCorpusKeyword().size());

        // Filter on document name
        controller.setSearch("foo");
        assertEquals(1, controller.findCorpusKeyword().size());

        corpusRepository.delete(corpus1);
        corpusRepository.delete(corpus2);
    }
}
