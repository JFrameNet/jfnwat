package jp.keio.jfn.jfnwat.webreport.controller;

import jp.keio.jfn.wat.JFNWAT;

import jp.keio.jfn.wat.webreport.controller.TabController;
import jp.keio.jfn.wat.domain.*;
import jp.keio.jfn.wat.repository.*;
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
public class TabControllerTest {

    @Autowired
    FrameRepository frameRepository;

    @Autowired
    LexUnitRepository lexUnitRepository;

    @Autowired
    DocumentRepository documentRepository;

    @Autowired
    LemmaRepository lemmaRepository;

    @Autowired
    PartOfSpchRepository partOfSpchRepository;

    private TabController controller;

    private  Frame frame;
    private Lemma lemma;

    @Before
    public void setup() {
        controller = new TabController();
        controller.setLexUnitRepository(lexUnitRepository);

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
    }

    @After
    public void after() {
        lexUnitRepository.deleteAll();
        frameRepository.deleteAll();
        lemmaRepository.deleteAll();
        partOfSpchRepository.deleteAll();
    }

    @Test
    public void testEmptyOrderLU() {
        assertEquals(1, controller.getOrderedLU("").size());
    }

    @Test
    public void testOrderLU() {
        LexUnit lexUnit = new LexUnit();
        lexUnit.setId(1);
        lexUnit.setName("tell.v");
        lexUnit.setCreatedBy("test");
        lexUnit.setModifiedDate(new Timestamp(new java.util.Date().getTime()));
        lexUnit.setFrame(frame);
        lexUnit.setLemma(lemma);
        lexUnitRepository.save(lexUnit);

        controller.orderLU("tell");
        assertEquals(1, controller.getOrderedLU("foo").size());

        controller.orderLU("V");
        assertEquals(1, controller.getOrderedLU("V").size());

        controller.orderLU("footell.v");
        assertEquals(1, controller.getOrderedLU("footell.v").size());

        // filters also on frame name
        controller.orderLU("frame");
        assertEquals(2, controller.getOrderedLU("frame").size());

        lexUnitRepository.delete(lexUnit);
    }
}
