package jp.keio.jfn.jfnwat.controller;

import jp.keio.jfn.wat.JFNWAT;
import jp.keio.jfn.wat.controller.AnnotationDisplay;
import jp.keio.jfn.wat.controller.Target;
import jp.keio.jfn.wat.domain.Sentence;
import jp.keio.jfn.wat.repository.SentenceRepository;
import org.hibernate.Hibernate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by jfn on 2/3/16.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(JFNWAT.class)
@ActiveProfiles("test")

public class AnnotationDisplayTest {
    @Autowired
    SentenceRepository sentenceRepository;

    @Transactional
    @Test
    public void testFindTargets() {
//        Sentence sentence = sentenceRepository.findById(10072);
//        Hibernate.initialize(sentence.getAnnotationSets());
//        List<Target> targets = AnnotationDisplay.getTargetsSentence(sentence);
//        assertEquals(3, targets.size());
//        assertEquals(false, targets.get(0).isValid());
//        assertEquals(true, targets.get(1).isValid());
//        assertEquals(false, targets.get(2).isValid());
    }
}
