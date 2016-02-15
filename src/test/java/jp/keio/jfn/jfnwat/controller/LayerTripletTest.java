package jp.keio.jfn.jfnwat.controller;

import jp.keio.jfn.wat.JFNWAT;

import jp.keio.jfn.wat.controller.LayerTriplet;
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

import static org.junit.Assert.*;
/**
 * Created by jfn on 2/12/16.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(JFNWAT.class)
@ActiveProfiles("test")
public class LayerTripletTest {

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

    private LayerTriplet layerTriplet;

    private Label labelPT;

    private Label labelGF;

    private InstantiationType instantiationType;

    @Before
    public void setup() {
        Color color = new Color();
        color.setName("name");
        color.setRgb("rgb");
        colorRepository.save(color);

        instantiationType = new InstantiationType();
        instantiationTypeRepository.save(instantiationType);

        LayerType layerType = new LayerType();
        layerType.setName("name");
        layerTypeRepository.save(layerType);

        Frame frame = new Frame();
        frame.setName("frame");
        frame.setCreatedBy("test");
        frameRepository.save(frame);

        layerTriplet = createLayerTriplet(0,"frame element", "pt", "gf", frameElementRepository,
                miscLabelRepository,
                labelTypeRepository,
                sentenceRepository,
                annotationSetRepository,
                layerRepository,
                labelRepository,
                annotationStatusRepository,
                frame,
                color,
                layerType,
                instantiationType);

        labelGF = layerTriplet.getLabelGF();
        labelPT = layerTriplet.getLabelPT();
    }


    public static LayerTriplet createLayerTriplet(int index, String fe, String pt, String gf,
                                           FrameElementRepository frameElementRepository,
                                           MiscLabelRepository miscLabelRepository,
                                           LabelTypeRepository labelTypeRepository,
                                           SentenceRepository sentenceRepository,
                                           AnnotationSetRepository annotationSetRepository,
                                           LayerRepository layerRepository,
                                           LabelRepository labelRepository,
                                           AnnotationStatusRepository annotationStatusRepository,
                                           Frame frame,
                                           Color color,
                                           LayerType layerType,
                                           InstantiationType instantiationType) {
        FrameElement frameElement = new FrameElement();
        frameElement.setName(fe);
        frameElement.setId(index*10);
        frameElement.setFrame(frame);
        frameElementRepository.save(frameElement);

        MiscLabel miscLabel2 = new MiscLabel();
        miscLabel2.setName(pt);
        miscLabel2.setId(index*10 + 2);
        miscLabelRepository.save(miscLabel2);

        MiscLabel miscLabel3 = new MiscLabel();
        miscLabel3.setName(gf);
        miscLabel3.setId(index*10 + 3);
        miscLabelRepository.save(miscLabel3);

        LabelType labelType1 = new LabelType();
        labelType1.setColor1(color);
        labelType1.setColor2(color);
        labelType1.setColor3(color);
        labelType1.setColor4(color);
        labelType1.setLayerType(layerType);
        labelType1.setFrameElement(frameElement);
        labelType1.setId(index*10 + 1);
        labelTypeRepository.save(labelType1);

        LabelType labelType2 = new LabelType();
        labelType2.setColor1(color);
        labelType2.setColor2(color);
        labelType2.setColor3(color);
        labelType2.setColor4(color);
        labelType2.setLayerType(layerType);
        labelType2.setMiscLabel(miscLabel2);
        labelType2.setId(index * 10 + 2);
        labelTypeRepository.save(labelType2);

        LabelType labelType3 = new LabelType();
        labelType3.setColor1(color);
        labelType3.setColor2(color);
        labelType3.setColor3(color);
        labelType3.setColor4(color);
        labelType3.setLayerType(layerType);
        labelType3.setMiscLabel(miscLabel3);
        labelType3.setId(index * 10 + 3);
        labelTypeRepository.save(labelType3);

        Sentence sentence = new Sentence();
        sentence.setText("text");
        sentence.setCreatedBy("test");
        sentence.setId(index);
        sentenceRepository.save(sentence);

        AnnotationStatus annotationStatus = new AnnotationStatus();
        annotationStatus.setName("status");
        annotationStatus.setId(index);
        annotationStatusRepository.save(annotationStatus);

        AnnotationSet annotationSet = new AnnotationSet();
        annotationSet.setSentence(sentence);
        annotationSet.setAnnotationStatus(annotationStatus);
        annotationSet.setCreatedBy("test");
        annotationSet.setId(index);
        annotationSetRepository.save(annotationSet);

        Layer layer = new Layer();
        layer.setAnnotationSet(annotationSet);
        layer.setCreatedBy("test");
        layer.setLayerType(layerType);
        layer.setId(index);
        layerRepository.save(layer);

        Label labelFE = new Label();
        labelFE.setInstantiationType(instantiationType);
        labelFE.setCreatedBy("test");
        labelFE.setLabelType(labelType1);
        labelFE.setLayer(layer);
        labelFE.setId(index*10 + 1);
        labelRepository.save(labelFE);

        Label labelPT = new Label();
        labelPT.setInstantiationType(instantiationType);
        labelPT.setCreatedBy("test");
        labelPT.setLabelType(labelType2);
        labelPT.setLayer(layer);
        labelPT.setId(index*10 +2);
        labelRepository.save(labelPT);

        Label labelGF = new Label();
        labelGF.setInstantiationType(instantiationType);
        labelGF.setCreatedBy("test");
        labelGF.setLabelType(labelType3);
        labelGF.setLayer(layer);
        labelGF.setId(index*10 +3);
        labelRepository.save(labelGF);

        LayerTriplet layerTriplet = new LayerTriplet(labelFE);
        layerTriplet.setLabelGF(labelGF);
        layerTriplet.setLabelPT(labelPT);

        return layerTriplet;
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
        layerTriplet = null;
    }

    @Test
    public void testOutputString1() {
        instantiationType.setId((byte)1);

        layerTriplet.setLabelPT(null);
        layerTriplet.setLabelGF(null);
        assertEquals(".", layerTriplet.outputString());

        layerTriplet.setLabelPT(labelPT);
        layerTriplet.setLabelGF(null);
        assertEquals("pt.", layerTriplet.outputString());

        layerTriplet.setLabelPT(null);
        layerTriplet.setLabelGF(labelGF);
        assertEquals(".gf", layerTriplet.outputString());

        layerTriplet.setLabelPT(labelPT);
        assertEquals("pt.gf", layerTriplet.outputString());
    }

    @Test
    public void testOutputString2() {
        instantiationType.setId((byte)6);
        instantiationType.setName("typename");

        layerTriplet.setLabelPT(null);
        layerTriplet.setLabelGF(null);
        assertEquals("typename.", layerTriplet.outputString());

        layerTriplet.setLabelPT(null);
        layerTriplet.setLabelGF(labelGF);
        assertEquals("typename.gf", layerTriplet.outputString());
    }

    @Test
    public void testOutputString3() {
        instantiationType.setId((byte)4);
        instantiationType.setName("other");
        assertEquals("other", layerTriplet.outputString());
    }
}
