package jp.keio.jfn.wat.webreport;

import jp.keio.jfn.wat.annotation.AnnotationDisplay;
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

    private List<AnnotationDisplay> allSentences = new ArrayList<AnnotationDisplay>();

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
     */
    public LUOutput(LexUnit lexUnit) {
        this.def = lexUnit.getSenseDesc();
        this.lightLU = new LightLU(lexUnit.getId(), lexUnit.getName(), lexUnit.getFrame().getName());
        this.annotations = lexUnit.getAnnotationSets();
        findRealizations();
        findALlFE();
        this.hasCore = this.feCoreRealizations.isEmpty()?"none":"inline";
        this.hasNonCore = this.feNonCoreRealizations.isEmpty()?"none":"inline";
        this.hasEl = this.feGroupRealizations.isEmpty()?"none":"inline";
        for (AnnotationSet annotationSet : this.annotations) {
            AnnotationDisplay annotationDisplay = new AnnotationDisplay(annotationSet.getSentence(), annotationSet, false, frameElements);
            annotationDisplay.setDisplayed(false);
            allSentences.add(annotationDisplay);
        }
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

    public List<AnnotationDisplay> getAllSentences() {
        return allSentences;
    }

    public void setAllSentences(List<AnnotationDisplay> allSentences) {
        this.allSentences = allSentences;
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

    public List<String> getFrameElements() {
        return frameElements;
    }
}
