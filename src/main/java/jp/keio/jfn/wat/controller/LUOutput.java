package jp.keio.jfn.wat.controller;

import jp.keio.jfn.wat.domain.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jfn on 1/18/16.
 */
public class LUOutput {

    private List<FERealization> feCoreRealizations = new ArrayList<FERealization>();

    private List<FERealization> feNonCoreRealizations = new ArrayList<FERealization>();

    private List<FEGroupRealization> feGroupRealizations = new ArrayList<FEGroupRealization>();

    private List<AnnotationSet> annotations = new ArrayList<AnnotationSet>();

    private List<SentenceOutput> sentences = new ArrayList<SentenceOutput>();

    private LightLU lightLU;

    public LUOutput(LexUnit lexUnit) {
        this.lightLU = new LightLU(lexUnit.getId(), lexUnit.getName(), lexUnit.getFrame().getName());
        this.annotations = lexUnit.getAnnotationSets();
        findRealizations();
//        this.sentences = processSentences(this.annotations, 68, true);
    }

    public void findFrameElements (AnnotationSet annotationSet, FrameElement fe, LayerTriplet valenceUnit) {
        PatternEntry newPattern = new PatternEntry();
        List<LayerTriplet> unit = new ArrayList<LayerTriplet>();
        unit.add(valenceUnit);
        newPattern.setValenceUnits(unit);
        newPattern.addOccurence(annotationSet);
        boolean insert = true;
        List<FERealization> list = fe.getType().equals("Core") ? this.feCoreRealizations : this.feNonCoreRealizations;
        for (FERealization realization : list) {
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
            list.add(new FERealization(fe, newPattern));
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
            if (labelsFE.size() != 0) {
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
    }


    public List<SentenceOutput> processSentences (List<AnnotationSet> annotationSetList, int breakLine, boolean btn) {
        List<String> allFE = new ArrayList<String>();
        List<SentenceOutput> result = new ArrayList<SentenceOutput>();
        for (AnnotationSet annoSet : annotationSetList) {
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
            if (layerFE != null) {
                List<Label> allLabels = layerFE.getLabels();
                allLabels.addAll(layerTarget.getLabels());
                auxFun(result, annoSet, allFE, allLabels, breakLine, btn);
            }
        }
        addColors(result, allFE);
        return result;
    }

    private void auxFun (List<SentenceOutput> sentenceOutputs, AnnotationSet annoSet, List<String> allFE, List<Label> allLabels, int breakLine, boolean btn) {
        Sentence sentence = annoSet.getSentence();
        List<List<ElementTag>> list = new ArrayList<List<ElementTag>>();
        int rank = 0;
        int imin = 0;
        int iaux =0;
        Label newStart = null;
        int imax = sentence.getText().length();
        List<ElementTag> line;
        while (imin + rank*breakLine < imax) {
            line = new ArrayList<ElementTag>();
            if (newStart != null) {
                int offset = (btn && (rank == 1)) ? 5 : 0;
                String word = sentence.getText().substring(rank*breakLine - offset, newStart.getEndChar() + 1);
                String tag = "Target";
                ElementTag elementTag = new ElementTag(word, tag);
                if (newStart.getLabelType().getLayerType().getId() == 1) {
                    elementTag.setTag(newStart.getLabelType().getFrameElement().getName());
                    elementTag.setFrameElement(newStart.getLabelType().getFrameElement());
                } else {
                    elementTag.setColor("#546E7A");
                }
                line.add(elementTag);
                imin = newStart.getEndChar() - rank*breakLine + offset;
                iaux = imin;
                newStart = null;
            }
            while ((imin + rank*breakLine < imax)&& (imin < breakLine)) {
                for (Label label : allLabels) {
                    if (label.getInstantiationType().getId() == 1){
                        if (imin + rank*breakLine == label.getStartChar()) {
                            if (imin > iaux) {
                                String wordEmpty = sentence.getText().substring(iaux + rank*breakLine, imin + rank*breakLine);
                                line.add(new ElementTag(wordEmpty, ""));
                            }
                            int border = label.getEndChar() + 1;
                            if (border > (rank +1)*breakLine) {
                                border = (rank +1)*breakLine;
                                newStart =  label;
                            }
                            String word = sentence.getText().substring(label.getStartChar(), border);
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
                            line.add(elementTag);

                            imin = border - 1 - rank*breakLine;
                            iaux = imin + 1;
                            break;
                        }
                    }
                }
                imin ++;
            }
            if (imin > iaux) {
                if (imin + rank*breakLine < imax) {
                    String wordEmpty = sentence.getText().substring(iaux + rank*breakLine, imin + rank*breakLine);
                    line.add(new ElementTag(wordEmpty, ""));
                } else {
                    String wordEmpty = sentence.getText().substring(iaux + rank*breakLine, imax);
                    line.add(new ElementTag(wordEmpty, ""));
                }
            }
            list.add(line);
            if (rank == 0) {
                breakLine += 5;
            }
            rank ++;
            imin = 0;
            iaux = 0;
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
                List<ElementTag> lastLine = list.get(list.size() -1);
                int size = 0;
                for (ElementTag elementTag1 : lastLine) {
                    size +=  Math.max(elementTag1.getElement().length(), elementTag1.getTag().length());
                }
                if (size < breakLine - Math.max(word.length(), el.getName().length()/2)) {
                    list.get(list.size() -1).add(elementTag);
                } else {
                    List<ElementTag> newLine = new ArrayList<ElementTag>();
                    newLine.add(elementTag);
                    list.add(newLine);
                }

            }
        }
        List<ElementTag> lastLine = list.get(list.size() -1);
        int size = 0;
        for (ElementTag elementTag1 : lastLine) {
            size +=  Math.max(elementTag1.getElement().length(), elementTag1.getTag().length());
        }
        int complete = breakLine - size;
        String space = "&#160;&#160;&#160;&#160;";
        for (int x = 0; x < complete; x ++ ) {
            space = space.concat("&#160;&#160;&#160;&#160;");
        }
        lastLine.add(new ElementTag(space, ""));
        sentenceOutputs.add(new SentenceOutput(sentenceOutputs.size(),list, sentence.getText()));
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
            for (List<ElementTag> elementTagList : sentenceOutput.getElements()) {
                for (ElementTag elementTag : elementTagList) {
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
    }

    public List<FEGroupRealization> getFeGroupRealizations() {
        return feGroupRealizations;
    }

    public List<FERealization> getFeCoreRealizations() {
        return feCoreRealizations;
    }

    public List<FERealization> getFeNonCoreRealizations() {
        return feNonCoreRealizations;
    }

    public List<SentenceOutput> getSentences() {
        return sentences;
    }

    public void setFeGroupRealizations(List<FEGroupRealization> feGroupRealizations) {
        this.feGroupRealizations = feGroupRealizations;
    }

    public void setFeCorRealizations(List<FERealization> feRealizations) {
        this.feCoreRealizations = feRealizations;
    }

    public void setFeNonCoreRealizations(List<FERealization> feRealizations) {
        this.feNonCoreRealizations = feRealizations;
    }

    public void setSentences(List<SentenceOutput> sentences) {
        this.sentences = sentences;
    }

    public LightLU getLightLU() {
        return lightLU;
    }

    public List<FERealization> getAllFERealizations () {
        List<FERealization> list = new ArrayList<FERealization>();
        list.addAll(feCoreRealizations);
        list.addAll(feNonCoreRealizations);
        return list;
    }
}
