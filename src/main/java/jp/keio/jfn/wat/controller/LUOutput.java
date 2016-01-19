package jp.keio.jfn.wat.controller;

import jp.keio.jfn.wat.domain.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jfn on 1/18/16.
 */
public class LUOutput {

    private List<FERealization> feRealizations = new ArrayList<FERealization>();

    private List<FEGroupRealization> feGroupRealizations = new ArrayList<FEGroupRealization>();

    private List<AnnotationSet> annotations = new ArrayList<AnnotationSet>();

    private List<SentenceOutput> sentences = new ArrayList<SentenceOutput>();

    private LightLU lightLU;

    public LUOutput(LexUnit lexUnit) {
        this.lightLU = new LightLU(lexUnit.getId(), lexUnit.getName(), lexUnit.getFrame().getName());
        this.annotations = lexUnit.getAnnotationSets();
        findRealizations();
        processSentences();
    }

    public void findFrameElements (AnnotationSet annotationSet, FrameElement fe, LayerTriplet valenceUnit) {
        PatternEntry newPattern = new PatternEntry();
        List<LayerTriplet> unit = new ArrayList<LayerTriplet>();
        unit.add(valenceUnit);
        newPattern.setValenceUnits(unit);
        newPattern.addOccurence(annotationSet);
        boolean insert = true;
        for (FERealization realization : this.feRealizations) {
            if (realization.getFrameElement().getId() == fe.getId()) {
                insert = false;
                boolean insertPattern = true;
                for (PatternEntry patternEntry : realization.getPatterns()) {
                    if (patternEntry.hasValence (valenceUnit)) {
                        insertPattern = false;
                        patternEntry.addOccurence(annotationSet);
                        break;
                    }
                }
                if (insertPattern) {
                    realization.addPattern(newPattern);
                }
            }
        }
        if (insert) {
            this.feRealizations.add(new FERealization(fe, newPattern));
        }
    }

    public void findGroupRealizations (AnnotationSet annotationSet, List<FrameElement> groupFE, List<LayerTriplet> valenceGroup) {
        PatternEntry newGroupPattern = new PatternEntry();
        newGroupPattern.setValenceUnits(valenceGroup);
        newGroupPattern.addOccurence(annotationSet);
        List<PatternEntry> patternEntries = new ArrayList<PatternEntry>();
        patternEntries.add(newGroupPattern);

        boolean insert = true;
        for (FEGroupRealization realization : this.feGroupRealizations) {
            if (realization.equalsFEGroup (groupFE)){
                insert = false;
                boolean insertPattern = true;
                for (PatternEntry patternEntry : realization.getPatterns()){
                    if (patternEntry.hasGroupValence (valenceGroup)) {
                        insertPattern = false;
                        patternEntry.addOccurence(annotationSet);
                        break;
                    }
                }
                if (insertPattern) {
                    realization.addPattern(newGroupPattern);
                }
            }
        }
        if (insert) {
            this.feGroupRealizations.add( new FEGroupRealization(groupFE, patternEntries));
        }
    }

    public void findRealizations () {
        for (AnnotationSet annoSet : this.annotations) {
            Layer layerFE = null;
            Layer layerPT = null;
            Layer layerGF = null;
            for (Layer layer : annoSet.getLayers()) {
                if (layer.getLayerType().getId() == 1) {
                    layerFE = layer;
                } else if (layer.getLayerType().getId() == 4) {
                    layerPT = layer;
                } else if (layer.getLayerType().getId() == 3){
                    layerGF = layer;
                }
                if ((layerFE != null) && (layerGF != null) && (layerPT != null)) {
                    break;
                }
            }
            List<Label> labelsFE = layerFE.getLabels();
            List<Label> labelsPT = layerPT.getLabels();
            List<Label> labelsGF = layerGF.getLabels();
            List<FrameElement> groupFE = new ArrayList<FrameElement>();
            List<LayerTriplet> valenceGroup = new ArrayList<LayerTriplet>();
            for (Label label : labelsFE) {
                FrameElement fe = label.getLabelType().getFrameElement();
                groupFE.add(fe);
                LayerTriplet valenceUnit = new LayerTriplet(label);
                if (label.getInstantiationType().getId() == 1) {
                    int start = label.getStartChar();
                    int end = label.getEndChar();
                    for (Label labelPT : labelsPT) {
                        if ((labelPT.getStartChar() == start) && (labelPT.getEndChar() == end)) {
                            valenceUnit.setLabelPT(labelPT);
                            break;
                        }
                    }
                    for (Label labelGF : labelsGF) {
                        if ((labelGF.getStartChar() == start) && (labelGF.getEndChar() == end)) {
                            valenceUnit.setLabelGF(labelGF);
                            break;
                        }
                    }
                }
                valenceGroup.add(valenceUnit);
                findFrameElements(annoSet, fe, valenceUnit);
            }
            findGroupRealizations(annoSet, groupFE, valenceGroup);
        }
    }

    public void processSentences () {
        List<SentenceOutput> sentences = new ArrayList<SentenceOutput>();
        List<String> allFE = new ArrayList<String>();
        for (AnnotationSet annoSet : this.annotations) {
            Sentence sentence = annoSet.getSentence();
            Layer layerFE = null;
            Layer layerTarget = null;
            for (Layer layer : annoSet.getLayers()){
                if (layer.getLayerType().getId() == 1) {
                    layerFE = layer;
                } else  if (layer.getLayerType().getId() == 2) {
                    layerTarget = layer;
                }
                if ((layerFE != null) && (layerTarget != null)) {
                    break;
                }
            }
            List<ElementTag> list = new ArrayList<ElementTag>();
            if (layerFE != null) {
                List<Label> allLabels = layerFE.getLabels();
                allLabels.addAll(layerTarget.getLabels());
                int imin = 0;
                int iaux = 0;
                int imax = sentence.getText().length();

                while (imin < imax) {
                    for (Label label : allLabels) {
                        if (label.getInstantiationType().getId() == 1){
                            if (imin == label.getStartChar()) {
                                if (imin > iaux) {
                                    String wordEmpty = sentence.getText().substring(iaux, imin);
                                    list.add(new ElementTag(wordEmpty, ""));
                                }
                                String word = sentence.getText().substring(label.getStartChar(), label.getEndChar() + 1);
                                String tag = "Target";
                                ElementTag elementTag = new ElementTag(word, tag);
                                if (label.getLabelType().getLayerType().getId() == 1) {
                                    tag = label.getLabelType().getFrameElement().getName();
                                    if (! allFE.contains(tag)) {
                                        allFE.add(tag);
                                    }
                                    elementTag.setTag(tag);
                                    elementTag.setFrameElement(label.getLabelType().getFrameElement());
                                } else {
                                    elementTag.setColor("#546E7A");
                                }
                                list.add(elementTag);

                                imin = label.getEndChar();
                                iaux = imin + 1;
                                break;
                            }
                        }
                    }
                    imin ++;

                }
                if (imin > iaux) {
                    String wordEmpty = sentence.getText().substring(iaux, imin);
                    list.add(new ElementTag(wordEmpty, ""));
                }
                for (Label label : allLabels) {
                    if (label.getInstantiationType().getId() != 1) {
                        String word = label.getInstantiationType().getName();
                        FrameElement el = label.getLabelType().getFrameElement();
                        ElementTag elementTag = new ElementTag(word, el.getName());
                        elementTag.setFrameElement(el);
                        if (! allFE.contains(el.getName())) {
                            allFE.add(el.getName());
                        }
                        list.add(elementTag);
                    }
                }
                sentences.add(new SentenceOutput(list, sentence.getText()));
            }
        }
        addColors(sentences, allFE);
        this.sentences = sentences;
    }

    private void addColors (List<SentenceOutput> list, List<String> allFE) {
        List<String> colors = new ArrayList<String>();
        colors.add("#4db6ac");
        colors.add("#F7941E");
        colors.add("#EA5753");
        colors.add("#EF9A9A");
        colors.add("#F7D100");
        colors.add("darkblue");
        colors.add("darkmagenta");
        colors.add("peru");
        colors.add("sandybrown");
        colors.add("steelblue");
        colors.add("tomato");
        colors.add("plum");
        colors.add("olive");
        colors.add("darkgoldenrod");
        colors.add("firebrick");

        for (SentenceOutput sentenceOutput : list) {
            for (ElementTag elementTag : sentenceOutput.getElements()) {
                String tag = elementTag.getTag();
                if (elementTag.getColor() == null) {
                    if (allFE.contains(tag)) {
                        elementTag.setColor(colors.get(allFE.indexOf(tag)));
                    } else {
                        elementTag.setColor("#F5F5F5");
                    }
                }

            }
        }
    }

    public List<FEGroupRealization> getFeGroupRealizations() {
        return feGroupRealizations;
    }

    public List<FERealization> getFeRealizations() {
        return feRealizations;
    }

    public List<SentenceOutput> getSentences() {
        return sentences;
    }

    public void setFeGroupRealizations(List<FEGroupRealization> feGroupRealizations) {
        this.feGroupRealizations = feGroupRealizations;
    }

    public void setFeRealizations(List<FERealization> feRealizations) {
        this.feRealizations = feRealizations;
    }

    public void setSentences(List<SentenceOutput> sentences) {
        this.sentences = sentences;
    }

    public LightLU getLightLU() {
        return lightLU;
    }
}
