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

        MiscLabel miscLabel2 = new MiscLabel();
        miscLabel2.setName("pt");
        miscLabel2.setId(2);
        miscLabelRepository.save(miscLabel2);

        MiscLabel miscLabel3 = new MiscLabel();
        miscLabel3.setName("gf");
        miscLabel3.setId(3);
        miscLabelRepository.save(miscLabel3);

        LabelType labelType1 = new LabelType();
        labelType1.setColor1(color);
        labelType1.setColor2(color);
        labelType1.setColor3(color);
        labelType1.setColor4(color);
        labelType1.setLayerType(layerType);
        labelTypeRepository.save(labelType1);

        LabelType labelType2 = new LabelType();
        labelType2.setColor1(color);
        labelType2.setColor2(color);
        labelType2.setColor3(color);
        labelType2.setColor4(color);
        labelType2.setLayerType(layerType);
        labelType2.setMiscLabel(miscLabel2);
        labelType2.setId(2);
        labelTypeRepository.save(labelType2);

        LabelType labelType3 = new LabelType();
        labelType3.setColor1(color);
        labelType3.setColor2(color);
        labelType3.setColor3(color);
        labelType3.setColor4(color);
        labelType3.setLayerType(layerType);
        labelType3.setMiscLabel(miscLabel3);
        labelType3.setId(3);
        labelTypeRepository.save(labelType3);

        Sentence sentence = new Sentence();
        sentence.setText("text");
        sentence.setCreatedBy("test");
        sentenceRepository.save(sentence);

        AnnotationStatus annotationStatus = new AnnotationStatus();
        annotationStatus.setName("status");
        annotationStatusRepository.save(annotationStatus);

        AnnotationSet annotationSet = new AnnotationSet();
        annotationSet.setSentence(sentence);
        annotationSet.setAnnotationStatus(annotationStatus);
        annotationSet.setCreatedBy("test");
        annotationSetRepository.save(annotationSet);

        Layer layer = new Layer();
        layer.setAnnotationSet(annotationSet);
        layer.setCreatedBy("test");
        layer.setLayerType(layerType);
        layerRepository.save(layer);

        Label labelFE = new Label();
        labelFE.setInstantiationType(instantiationType);
        labelFE.setCreatedBy("test");
        labelFE.setLabelType(labelType1);
        labelFE.setLayer(layer);
        labelRepository.save(labelFE);

        labelPT = new Label();
        labelPT.setInstantiationType(instantiationType);
        labelPT.setCreatedBy("test");
        labelPT.setLabelType(labelType2);
        labelPT.setLayer(layer);
        labelPT.setId(2);
        labelRepository.save(labelPT);

        labelGF = new Label();
        labelGF.setInstantiationType(instantiationType);
        labelGF.setCreatedBy("test");
        labelGF.setLabelType(labelType3);
        labelGF.setLayer(layer);
        labelGF.setId(3);
        labelRepository.save(labelGF);

        layerTriplet = new LayerTriplet(labelFE);
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
        layerTriplet = null;
    }

    @Test
    public void testOutputString1() {
        instantiationType.setId((byte)1);

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
