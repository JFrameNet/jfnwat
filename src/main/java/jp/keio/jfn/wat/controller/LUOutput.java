package jp.keio.jfn.wat.controller;

import jp.keio.jfn.wat.domain.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This class defines all the elements to be retrieved for a LexicalUnit object.
 */
public class LUOutput {

    private List<FERealization> feCoreRealizations = new ArrayList<FERealization>();

    private List<FERealization> feNonCoreRealizations = new ArrayList<FERealization>();

    private List<FEGroupRealization> feGroupRealizations = new ArrayList<FEGroupRealization>();

    private List<AnnotationSet> annotations = new ArrayList<AnnotationSet>();

    private List<FEGroupRealization> valencePatterns = new ArrayList<FEGroupRealization>();

    private List<SentenceOutput> selectedSentences = new ArrayList<SentenceOutput>();

    private List<String> selectedEl = new ArrayList<String>();

    private LightLU lightLU;

    private List<String> frameElements = new ArrayList<String>();

    private String def = "";

    private String displayCore = "none";
    private String displayNonCore = "none";
    private String hasCore = "";
    private String hasNonCore = "";
    private String hasEl = "";

    /**
     * Initialization.
     *
     * @param real defines if we want to retrieve the group realizations associated with the Lexical Unit or not.
     */
    public LUOutput(LexUnit lexUnit, boolean real) {
        this.def = lexUnit.getSenseDesc();
        this.lightLU = new LightLU(lexUnit.getId(), lexUnit.getName(), lexUnit.getFrame().getName());
        this.annotations = lexUnit.getAnnotationSets();
        if (real) {
            findRealizations();
        }
        findALlFE();
        this.hasCore = this.feCoreRealizations.isEmpty()?"none":"inline";
        this.hasNonCore = this.feNonCoreRealizations.isEmpty()?"none":"inline";
        this.hasEl = this.feGroupRealizations.isEmpty()?"none":"inline";
    }

    /**
     * Finds all frame elements present in the annotation sets associated to the lexical unit.
     */
    private void findALlFE() {
        for (AnnotationSet annoSet : this.annotations) {
            for (Layer layer : annoSet.getLayers()){
                if (layer.getLayerType().getId() == 1) {
                    for (Label label : layer.getLabels()) {
                        String fe = label.getLabelType().getFrameElement().getName();
                        if (!this.frameElements.contains(fe)) {
                            this.frameElements.add(fe);
                        }
                    }
                }
            }
        }
    }

    /**
     * Finds all frame elements realizations and frame element group realizations for the lexical unit.
     */
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

    /**
     * Inserts a frame element realization in this.feCoreRealizations or this.feNonCoreRealizations depending on the
     * frame element type.
     */
    private void findFrameElements (AnnotationSet annotationSet, FrameElement fe, LayerTriplet valenceUnit) {
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

    /**
     * Inserts a frame element group realization in this.fGroupRealizations.
     */
    private void findGroupRealizations (AnnotationSet annotationSet, List<FrameElement> groupFE, List<LayerTriplet> valenceGroup) {
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

    /**
     * Process a list of annotation sets to display a list of annotated sentences (SentenceOutput objects).
     * It takes the screen width as a parameter to decide the size of the lines.
     */
    public List<SentenceOutput> processSentences (List<AnnotationSet> annotationSetList, int breakLine) {
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
                result.add(auxFun(annoSet, allLabels, breakLine));
            }
        }
        addColors(result);
        return result;
    }

    /**
     * Processes one annotation set.
     *
     * @param annotationSet the annotation set to process.
     * @param allLabels list of all labels to consider when processing the sentence.
     * @param breakLine depends on the screen width.
     * @return a SentenceOutput, with as many lines as necessary, and every frame element label associated to its word
     * as an ElementTag object.
     */
    public SentenceOutput auxFun (AnnotationSet annotationSet, List<Label> allLabels, int breakLine) {
        String text = annotationSet.getSentence().getText();
        List<List<ElementTag>> list = new ArrayList<List<ElementTag>>();
        int rank = 0;
        int imin = 0;
        int iaux =0;
        Label newStart = null;
        int imax = text.length();
        List<ElementTag> line;
        while (imin + rank*breakLine < imax) {
            line = new ArrayList<ElementTag>();
            if (newStart != null) {
                String word = text.substring(rank*breakLine, newStart.getEndChar() + 1);
                String tag = "";
                ElementTag elementTag = new ElementTag(word, tag);
                if (newStart.getLabelType().getLayerType().getId() == 1) {
                    elementTag.setTag(newStart.getLabelType().getFrameElement().getName());
                    elementTag.setFrameElement(newStart.getLabelType().getFrameElement());
                } else {
                    elementTag.setLU(true);
                }
                line.add(elementTag);
                imin = newStart.getEndChar() - rank*breakLine;
                iaux = imin;
                newStart = null;
            }
            while ((imin + rank*breakLine < imax)&& (imin < breakLine)) {
                for (Label label : allLabels) {
                    if (label.getInstantiationType().getId() == 1){
                        if (imin + rank*breakLine == label.getStartChar()) {
                            if (imin > iaux) {
                                String wordEmpty = text.substring(iaux + rank*breakLine, imin + rank*breakLine);
                                line.add(new ElementTag(wordEmpty, ""));
                            }
                            int border = label.getEndChar() + 1;
                            if (border > (rank +1)*breakLine) {
                                border = (rank +1)*breakLine;
                                newStart =  label;
                            }
                            String word = text.substring(label.getStartChar(), border);
                            String tag = "";
                            ElementTag elementTag = new ElementTag(word, tag);
                            if (label.getLabelType().getLayerType().getId() == 1) {
                                tag = label.getLabelType().getFrameElement().getName();
                                elementTag.setTag(tag);
                                elementTag.setFrameElement(label.getLabelType().getFrameElement());
                            } else {
                                elementTag.setLU(true);
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
                    String wordEmpty = text.substring(iaux + rank*breakLine, imin + rank*breakLine);
                    line.add(new ElementTag(wordEmpty, ""));
                } else {
                    String wordEmpty = text.substring(iaux + rank*breakLine, imax);
                    line.add(new ElementTag(wordEmpty, ""));
                }
            }
            list.add(line);
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
                List<ElementTag> lastLine = list.get(list.size() -1);
                int size = 0;
                for (ElementTag elementTag1 : lastLine) {
                    size +=  Math.max(elementTag1.getWord().length(), elementTag1.getTag().length());
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
            size +=  Math.max(elementTag1.getWord().length(), elementTag1.getTag().length());
        }
        int complete = breakLine - size;
        String space = "&#160;&#160;&#160;&#160;";
        for (int x = 0; x < complete; x ++ ) {
            space = space.concat("&#160;&#160;&#160;");
        }
        lastLine.add(new ElementTag(space, ""));
        SentenceOutput sentenceOutput = new SentenceOutput(list, text);
        sentenceOutput.setAnnotationSet(annotationSet);
        return sentenceOutput;
    }

    /**
     * Adds colors to a list of SentenceOutput objects. The colors are consistent in all the annotation for one lexical
     * unit (same frame element will always be of the same color for this LU).
     */
    private void addColors (List<SentenceOutput> list) {
        for (SentenceOutput sentenceOutput : list) {
            for (List<ElementTag> elementTagList : sentenceOutput.getElements()) {
                for (ElementTag elementTag : elementTagList) {
                    String tag = elementTag.getTag();
                    if (elementTag.getTag().equals("")) {
                        elementTag.setTagColor("#F5F5F5");
                    } else {
                        elementTag.setTagColor(Utils.allColors.get(this.frameElements.indexOf(tag)));
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

    public void setFeNonCoreRealizations(List<FERealization> feRealizations) {
        this.feNonCoreRealizations = feRealizations;
    }

    public LightLU getLightLU() {
        return lightLU;
    }


    public List<FEGroupRealization> getValencePatterns() {
        return valencePatterns;
    }

    public List<SentenceOutput> getSelectedSentences() {
        return selectedSentences;
    }

    public List<String> getSelectedEl() {
        return selectedEl;
    }

    public void setValencePatterns(List<FEGroupRealization> valencePatterns) {
        this.valencePatterns = valencePatterns;
    }

    public void setSelectedEl(List<String> selectedEl) {
        this.selectedEl = selectedEl;
    }

    public String getDef() {
        return def;
    }

    public void setSelectedSentences(List<SentenceOutput> selectedSentences) {
        this.selectedSentences = selectedSentences;
    }

    public String getDisplayCore() {
        return displayCore;
    }

    public String getDisplayNonCore() {
        return displayNonCore;
    }

    public void setDisplayCore(String displayCore) {
        this.displayCore = displayCore;
    }

    public void setDisplayNonCore(String displayNonCore) {
        this.displayNonCore = displayNonCore;
    }

    public String getHasCore() {
        return hasCore;
    }

    public String getHasNonCore() {
        return hasNonCore;
    }

    public String getHasEl() {
        return hasEl;
    }
}
