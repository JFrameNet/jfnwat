package jp.keio.jfn.jfnwat.controller;

import jp.keio.jfn.wat.JFNWAT;

import jp.keio.jfn.wat.webreport.LayerTriplet;
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

    @Before
    public void setup() {
        patternEntry = createPatterEntry(1,
                frameElementRepository,
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
                frameRepository);
    }


    public static PatternEntry createPatterEntry (int index, FrameElementRepository frameElementRepository,
                                                  MiscLabelRepository miscLabelRepository,
                                                  LabelTypeRepository labelTypeRepository,
                                                  SentenceRepository sentenceRepository,
                                                  AnnotationSetRepository annotationSetRepository,
                                                  LayerRepository layerRepository,
                                                  LabelRepository labelRepository,
                                                  AnnotationStatusRepository annotationStatusRepository,
                                                  ColorRepository colorRepository,
                                                  InstantiationTypeRepository instantiationTypeRepository,
                                                  LayerTypeRepository layerTypeRepository,
                                                  FrameRepository frameRepository) {
        Color color = new Color();
        color.setName("name");
        color.setRgb("rgb");
        color.setId((byte)index);
        colorRepository.save(color);

        InstantiationType instantiationType = new InstantiationType();
        instantiationType.setId((byte) index);
        instantiationTypeRepository.save(instantiationType);

        LayerType layerType = new LayerType();
        layerType.setId(index);
        layerType.setName("name");
        layerTypeRepository.save(layerType);

        Frame frame = new Frame();
        frame.setName("frame" + index);
        frame.setCreatedBy("test");
        frameRepository.save(frame);

        LayerTriplet layerTriplet1 = LayerTripletTest.createLayerTriplet(index * 10 + 1,"fe1","pt1","gf1", frameElementRepository,
                miscLabelRepository,
                labelTypeRepository,
                sentenceRepository,
                annotationSetRepository,
                layerRepository,
                labelRepository,
                annotationStatusRepository,
                frame, color, layerType, instantiationType);
        LayerTriplet layerTriplet2 = LayerTripletTest.createLayerTriplet(index * 10 + 2,"fe2","pt2","gf2", frameElementRepository,
                miscLabelRepository,
                labelTypeRepository,
                sentenceRepository,
                annotationSetRepository,
                layerRepository,
                labelRepository,
                annotationStatusRepository,
                frame, color, layerType, instantiationType);
        LayerTriplet layerTriplet3 = LayerTripletTest.createLayerTriplet(index * 10 + 3,"fe3","pt3","gf3", frameElementRepository,
                miscLabelRepository,
                labelTypeRepository,
                sentenceRepository,
                annotationSetRepository,
                layerRepository,
                labelRepository,
                annotationStatusRepository,
                frame, color, layerType, instantiationType);

        PatternEntry patternEntry = new PatternEntry();
        patternEntry.setValenceUnits(new ArrayList<LayerTriplet>(Arrays.asList(layerTriplet1,layerTriplet2,layerTriplet3)));

        return patternEntry;
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
        Color color = new Color();
        color.setName("name");
        color.setRgb("rgb");
        colorRepository.save(color);

        InstantiationType instantiationType = new InstantiationType();
        instantiationTypeRepository.save(instantiationType);

        LayerType layerType = new LayerType();
        layerType.setName("name");
        layerTypeRepository.save(layerType);

        Frame frame = new Frame();
        frame.setName("frame2");
        frame.setCreatedBy("test");
        frameRepository.save(frame);

        LayerTriplet testTriplet = LayerTripletTest.createLayerTriplet(40,"fetest","pttest","gftest", frameElementRepository,
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
    public void testHasGroupValenceEmpty() {
        assertEquals(true, patternEntry.hasGroupValence(new ArrayList<LayerTriplet>()));
    }


    @Test
    public void testHasGroupValenceFalse() {
        Color color = new Color();
        color.setName("name");
        color.setRgb("rgb");
        colorRepository.save(color);

        InstantiationType instantiationType = new InstantiationType();
        instantiationTypeRepository.save(instantiationType);

        LayerType layerType = new LayerType();
        layerType.setName("name");
        layerTypeRepository.save(layerType);

        Frame frame = new Frame();
        frame.setName("frame3");
        frame.setCreatedBy("test");
        frameRepository.save(frame);

        LayerTriplet testTriplet = LayerTripletTest.createLayerTriplet(50,"fetest","pttest","gftest", frameElementRepository,
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
    public void testOutputFE1() {
        LayerTriplet layerTriplet1 = patternEntry.getValenceUnits().get(0);
        FrameElement frameElement1 = layerTriplet1.getLabelFE().getLabelType().getFrameElement();
        layerTriplet1.getLabelFE().getInstantiationType().setId((byte)1);

        LayerTriplet layerTriplet2 = patternEntry.getValenceUnits().get(1);
        FrameElement frameElement2 = layerTriplet2.getLabelFE().getLabelType().getFrameElement();
        layerTriplet2.getLabelFE().getInstantiationType().setId((byte)1);

        LayerTriplet layerTriplet3 = patternEntry.getValenceUnits().get(2);
        FrameElement frameElement3 = layerTriplet3.getLabelFE().getLabelType().getFrameElement();
        layerTriplet3.getLabelFE().getInstantiationType().setId((byte)1);

        assertEquals("pt1.gf1", patternEntry.outputFE(frameElement1));
        assertEquals("pt2.gf2",patternEntry.outputFE(frameElement2));
        assertEquals("pt3.gf3",patternEntry.outputFE(frameElement3));

        Label label1 = layerTriplet1.getLabelPT();
        layerTriplet1.setLabelPT(null);
        Label label2 = layerTriplet2.getLabelPT();
        layerTriplet2.setLabelPT(null);
        Label label3 = layerTriplet3.getLabelPT();
        layerTriplet3.setLabelPT(null);

        assertEquals(".gf1", patternEntry.outputFE(frameElement1));
        assertEquals(".gf2",patternEntry.outputFE(frameElement2));
        assertEquals(".gf3",patternEntry.outputFE(frameElement3));

        layerTriplet1.setLabelPT(label1);
        layerTriplet2.setLabelPT(label2);
        layerTriplet3.setLabelPT(label3);
    }

    @Test
    public void testOutputFE2() {
        FrameElement frameElement = patternEntry.getValenceUnits().get(0).getLabelFE().getLabelType().getFrameElement();
        InstantiationType in = patternEntry.getValenceUnits().get(0).getLabelFE().getInstantiationType();
        in.setId((byte)4);
        in.setName("test name");
        assertEquals("test name", patternEntry.outputFE(frameElement));
    }
}
