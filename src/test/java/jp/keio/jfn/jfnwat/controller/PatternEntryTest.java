package jp.keio.jfn.jfnwat.controller;

import jp.keio.jfn.wat.JFNWAT;

import jp.keio.jfn.wat.controller.LayerTriplet;
import jp.keio.jfn.wat.controller.PatternEntry;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
/**
 * Created by jfn on 2/12/16.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(JFNWAT.class)
@ActiveProfiles("test")
public class PatternEntryTest {

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

    private PatternEntry patternEntry;

    private Color color;

    private Frame frame;

    private LayerType layerType;

    private InstantiationType instantiationType;

    @Before
    public void setup() {

        color = new Color();
        color.setName("name");
        color.setRgb("rgb");
        colorRepository.save(color);

        instantiationType = new InstantiationType();
        instantiationTypeRepository.save(instantiationType);

        layerType = new LayerType();
        layerType.setName("name");
        layerTypeRepository.save(layerType);

        frame = new Frame();
        frame.setName("frame");
        frame.setCreatedBy("test");
        frameRepository.save(frame);

        LayerTriplet layerTriplet1 = LayerTripletTest.createLayerTriplet(1,"fe1","pt1","gf1", frameElementRepository,
                miscLabelRepository,
                labelTypeRepository,
                sentenceRepository,
                annotationSetRepository,
                layerRepository,
                labelRepository,
                annotationStatusRepository,
                frame, color, layerType, instantiationType);
        LayerTriplet layerTriplet2 = LayerTripletTest.createLayerTriplet(2,"fe2","pt2","gf2", frameElementRepository,
                miscLabelRepository,
                labelTypeRepository,
                sentenceRepository,
                annotationSetRepository,
                layerRepository,
                labelRepository,
                annotationStatusRepository,
                frame, color, layerType, instantiationType);
        LayerTriplet layerTriplet3 = LayerTripletTest.createLayerTriplet(3,"fe3","pt3","gf3", frameElementRepository,
                miscLabelRepository,
                labelTypeRepository,
                sentenceRepository,
                annotationSetRepository,
                layerRepository,
                labelRepository,
                annotationStatusRepository,
                frame, color, layerType, instantiationType);
        patternEntry = new PatternEntry();
        patternEntry.setValenceUnits(new ArrayList<LayerTriplet>(Arrays.asList(layerTriplet1,layerTriplet2,layerTriplet3)));
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
        patternEntry = null;
    }

    @Test
    public void testHasValenceFalse() {
        LayerTriplet testTriplet = LayerTripletTest.createLayerTriplet(4,"fetest","pttest","gftest", frameElementRepository,
                miscLabelRepository,
                labelTypeRepository,
                sentenceRepository,
                annotationSetRepository,
                layerRepository,
                labelRepository,
                annotationStatusRepository,
                frame, color, layerType, instantiationType);
        testTriplet.getLabelFE().getInstantiationType().setId((byte)1);
        assertEquals(false,patternEntry.hasValence(testTriplet));
    }

    @Test
    public void testHasValenceTrue() {
        LayerTriplet testTriplet = patternEntry.getValenceUnits().get(0);
        assertEquals(true,patternEntry.hasValence(testTriplet));
    }

    @Test
    public void testHasGroupValenceFalse() {
        LayerTriplet testTriplet = LayerTripletTest.createLayerTriplet(5,"fetest","pttest","gftest", frameElementRepository,
                miscLabelRepository,
                labelTypeRepository,
                sentenceRepository,
                annotationSetRepository,
                layerRepository,
                labelRepository,
                annotationStatusRepository,
                frame, color, layerType, instantiationType);
        List<LayerTriplet> testList = new ArrayList<LayerTriplet>();
        testList.add(testTriplet);
        assertEquals(false, patternEntry.hasGroupValence(testList));

        testList.add(patternEntry.getValenceUnits().get(0));
        assertEquals(false, patternEntry.hasGroupValence(testList));
    }

    @Test
    public void testHasGroupValenceTrue() {
        List<LayerTriplet> testList = new ArrayList<LayerTriplet>();
        testList.add(patternEntry.getValenceUnits().get(0));
        assertEquals(true, patternEntry.hasGroupValence(testList));

        testList.add(patternEntry.getValenceUnits().get(1));
        assertEquals(true, patternEntry.hasGroupValence(testList));
    }

    @Test
    public void testOutputFE() {
        FrameElement frameElement = patternEntry.getValenceUnits().get(0).getLabelFE().getLabelType().getFrameElement();
        patternEntry.getValenceUnits().get(0).getLabelFE().getInstantiationType().setId((byte)1);
        assertEquals("pt1.gf1", patternEntry.outputFE(frameElement));
    }
}
