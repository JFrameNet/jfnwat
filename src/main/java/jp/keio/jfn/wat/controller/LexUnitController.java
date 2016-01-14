package jp.keio.jfn.wat.controller;

import java.io.Serializable;
import java.util.*;
import javax.faces.bean.ManagedBean;

import jp.keio.jfn.wat.domain.*;
import jp.keio.jfn.wat.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@ManagedBean
@RestController
@Scope("view")
public class LexUnitController implements Serializable {
    @Autowired
    LexUnitRepository lexUnitRepository;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    AnnotationSetRepository annotationSetRepository;

    @Autowired
    LayerRepository layerRepository;

    @Autowired
    LabelRepository labelRepository;

    @Autowired
    FrameElementRepository frameElementRepository;

    private int lexUnitId;

    private String filter = "";

    private List<LexUnit> orderedLU = new ArrayList<LexUnit>();

    private LexUnit currentLU;

    private List<Sentence> annotatedSentence;

    private List<FERealization> feRealizations = new ArrayList<FERealization>();

    private List<FEGroupRealization> feGroupRealizations = new ArrayList<FEGroupRealization>();

    private List<AnnotationSet> annotations = new ArrayList<AnnotationSet>();


    public String displayLexUnit () {
        currentLU = lexUnitRepository.findById(lexUnitId);
        for (AnnotationSet annotSet : annotationSetRepository.findAll()) {
            if (annotSet.getLexUnit().getId() == currentLU.getId()) {
                annotations.add(annotSet);
            }
        }
        findSentences();
        findRealizations();
        return currentLU.getName();
    }

    public List<Status> getStatusForLU (LexUnit lu) {
        Iterable<Status> allStatus = statusRepository.findAll();
        List<Status> myList = new ArrayList<Status>();
        for (Status status : allStatus) {
            if (status.getLexUnit().getId() == lu.getId()) {
                myList.add(status);
            }
        }
        return myList;
    }

    public void findFrameElements (AnnotationSet annotationSet, FrameElement fe, LayerTriplet valenceUnit) {
        PatternEntry newPattern = new PatternEntry();
        List<LayerTriplet> unit = new ArrayList<LayerTriplet>();
        unit.add(valenceUnit);
        newPattern.setValenceUnits(unit);
        newPattern.addOccurence(annotationSet);
        boolean insert = true;
        for (FERealization realization : feRealizations) {
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
            feRealizations.add(new FERealization(fe, newPattern));
        }
    }

    public void findGroupRealizations (AnnotationSet annotationSet, List<FrameElement> groupFE, List<LayerTriplet> valenceGroup) {
        PatternEntry newGroupPattern = new PatternEntry();
        newGroupPattern.setValenceUnits(valenceGroup);
        newGroupPattern.addOccurence(annotationSet);
        List<PatternEntry> patternEntries = new ArrayList<PatternEntry>();
        patternEntries.add(newGroupPattern);

        boolean insert = true;
        for (FEGroupRealization realization : feGroupRealizations) {
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
            feGroupRealizations.add( new FEGroupRealization(groupFE, patternEntries));
        }
    }

    public void findRealizations () {
        for (AnnotationSet annoSet : annotations) {
            Layer layerFE = null;
            Layer layerPT = null;
            Layer layerGF = null;
            for (Layer layer : getAllLayers(annoSet)) {
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
            List<Label> labelsFE = getAllLabels(layerFE);
            List<Label> labelsPT = getAllLabels(layerPT);
            List<Label> labelsGF = getAllLabels(layerGF);
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

    public List<SentenceOutput> processSentences () {
        List<SentenceOutput> sentences = new ArrayList<SentenceOutput>();
        List<String> allFE = new ArrayList<String>();
        for (AnnotationSet annoSet : annotations) {
            Sentence sentence = annoSet.getSentence();
            Layer layerFE = null;
            Layer layerTarget = null;
            for (Layer layer : getAllLayers(annoSet)){
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
                List<Label> allLabels = getAllLabels(layerFE);
                allLabels.addAll(getAllLabels(layerTarget));
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
                                String tag = "";
                                if (label.getLabelType().getLayerType().getId() == 1) {
                                    tag = label.getLabelType().getFrameElement().getName();
                                    if (! allFE.contains(tag)) {
                                        allFE.add(tag);
                                    }
                                } else if (label.getLabelType().getLayerType().getId() == 2) {
                                    tag = "Target";
                                }
                                list.add(new ElementTag(word, tag));

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
                        String tag = label.getLabelType().getFrameElement().getName();
                        list.add(new ElementTag(word, tag));
                    }
                }
                sentences.add(new SentenceOutput(list));
            }
        }
        addColors(sentences, allFE);
        return sentences;
    }

    private void addColors (List<SentenceOutput> list, List<String> allFE) {
        List<String> colors = new ArrayList<String>();
        colors.add("#4db6ac");
        colors.add("#F7941E");
        colors.add("#EA5753");
        colors.add("#EF9A9A");


        for (SentenceOutput sentenceOutput : list) {
            for (ElementTag elementTag : sentenceOutput.getElements()) {
                String tag = elementTag.getTag();
                if (allFE.contains(tag)) {
                    elementTag.setColor(colors.get(allFE.indexOf(tag)));
                } else if (tag.equals("Target")) {
                    elementTag.setColor("#546E7A");
                } else {
                    elementTag.setColor("#F5F5F5");
                }
            }
        }
    }

    public void orderLU () {
        List<LexUnit> allLU= new ArrayList<LexUnit>();
        for (LexUnit lu : lexUnitRepository.findAll()) {
            if (matchSearch(filter, lu.getName())) {
                allLU.add(lu);
            } else if (matchSearch(filter, lu.getFrame().getName())) {
                allLU.add(lu);
            }
        }
        orderedLU = allLU;
    }

    private void findSentences () {
        List<Sentence> allSenteces = new ArrayList<Sentence>();
        for (AnnotationSet annotSet : annotations) {
            allSenteces.add(annotSet.getSentence());
        }
        annotatedSentence = allSenteces;
    }
    public int getLexUnitId() {
        return lexUnitId;
    }

    public void setLexUnitId (int id) {
        lexUnitId = id;
    }

    public LexUnit getCurrentLU () {
        return currentLU;
    }

    public List<Sentence> getAnnotatedSentence () {
        return annotatedSentence;
    }

    public void setFilter (String f) {
        filter = f;
    }

    public String getFilter () {
        return filter;
    }

    public void setOrderedLU (List<LexUnit> list) {
        orderedLU = list;
    }

    public List<LexUnit> getOrderedLU () {
        if (orderedLU.isEmpty() && filter.isEmpty()) {
            List <LexUnit> allLu = new ArrayList<LexUnit>();
            for (LexUnit lu : lexUnitRepository.findAll()) {
                allLu.add(lu);
            }
            return allLu;
        } else {
            return orderedLU;
        }
    }

    private boolean matchSearch (String query, String name) {
        return ((name.equalsIgnoreCase(query))
                ||(name.toLowerCase().contains(query.toLowerCase()))
                ||(query.toLowerCase().contains(name.toLowerCase())));
    }

    private List<Layer> getAllLayers(AnnotationSet annotationSet) {
        List<Layer> result = new ArrayList<Layer>();
        for (Layer layer : layerRepository.findAll()) {
            if (layer.getAnnotationSet().getId() == annotationSet.getId()) {
                result.add(layer);
            }
        }
        return result;
    }

    private List<Label> getAllLabels(Layer layer) {
        List<Label> result = new ArrayList<Label>();
        for (Label label : labelRepository.findAll()){
            if (label.getLayer().getId() == layer.getId()) {
                result.add(label);
            }
        }
        return result;
    }

    public List<FERealization> getFeRealizations() {
        return feRealizations;
    }

    public List<FEGroupRealization> getFeGroupRealizations() {
        return feGroupRealizations;
    }
}