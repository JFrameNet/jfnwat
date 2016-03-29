package jp.keio.jfn.jfnwat.controller;

import jp.keio.jfn.wat.JFNWAT;

import jp.keio.jfn.wat.webreport.FEGroupRealization;
import jp.keio.jfn.wat.webreport.PatternEntry;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jfn on 2/12/16.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(JFNWAT.class)
@ActiveProfiles("test")
public class FEGroupRealizationTest {

    @Autowired
    LabelRepository labelRepository;

    @Autowired
    InstantiationTypeRepository instantiationTypeRepository;

    @Autowired
    LabelTypeRepository labelTypeRepository;

    @Autowired
    ColorRepository colorRepository;

    @Autowired
    LayerTypeRepository layerTypeRepository;

    @Autowired
    LayerRepository layerRepository;

    @Autowired
    AnnotationSetRepository annotationSetRepository;

    @Autowired
    AnnotationStatusRepository annotationStatusRepository;

    @Autowired
    SentenceRepository sentenceRepository;

    @Autowired
    MiscLabelRepository miscLabelRepository;

    @Autowired
    FrameElementRepository frameElementRepository;

    @Autowired
    FrameRepository frameRepository;

    private FEGroupRealization feGroupRealization;

    @Before
    public void setup() {
        List<FrameElement> frameElements = new ArrayList<FrameElement>();
        List<PatternEntry> patternEntryList = new ArrayList<PatternEntry>();
        patternEntryList.add(PatternEntryTest.createPatterEntry(2,frameElementRepository,
                miscLabelRepository,
                labelTypeRepository,
                sentenceRepository,
                annotationSetRepository,
                layerRepository,
                labelRepository,
                annotationStatusRepository,
                colorRepository,
                instantiationTypeRepository,
                layerTypeRepository,
                frameRepository));
        patternEntryList.add(PatternEntryTest.createPatterEntry(3,frameElementRepository,
                miscLabelRepository,
                labelTypeRepository,
                sentenceRepository,
                annotationSetRepository,
                layerRepository,
                labelRepository,
                annotationStatusRepository,
                colorRepository,
                instantiationTypeRepository,
                layerTypeRepository,
                frameRepository));
        patternEntryList.add(PatternEntryTest.createPatterEntry(4,frameElementRepository,
                miscLabelRepository,
                labelTypeRepository,
                sentenceRepository,
                annotationSetRepository,
                layerRepository,
                labelRepository,
                annotationStatusRepository,
                colorRepository,
                instantiationTypeRepository,
                layerTypeRepository,
                frameRepository));
        feGroupRealization = new FEGroupRealization(frameElements, patternEntryList);
    }

    @After
    public void after() {
        labelRepository.deleteAll();
        instantiationTypeRepository.deleteAll();
        labelTypeRepository.deleteAll();
        colorRepository.deleteAll();
        layerRepository.deleteAll();
        annotationSetRepository.deleteAll();
        annotationStatusRepository.deleteAll();
        layerTypeRepository.deleteAll();
        sentenceRepository.deleteAll();
        miscLabelRepository.deleteAll();
        frameElementRepository.deleteAll();
        frameRepository.deleteAll();
        feGroupRealization = null;
    }

    @Test
    public void testEqualsFEGroup() {

    }

    @Test
    public void testGetAllAnnotations () {

    }

    @Test
    public void testGetTotalOccurences() {

    }
}
