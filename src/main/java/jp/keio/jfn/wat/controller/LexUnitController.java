package jp.keio.jfn.wat.controller;

import java.io.Serializable;
import java.util.*;
import javax.faces.bean.ManagedBean;

import com.sun.xml.internal.bind.v2.TODO;
import javassist.compiler.Lex;
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

    private int lexUnitId;

    private String filter = "";

    private List<LexUnit> orderedLU = new ArrayList<LexUnit>();

    private LexUnit currentLU;

    private List<Sentence> annotatedSentence;

    private List<LexicalEntry> lexicalEntries = new ArrayList<LexicalEntry>();

    private List<AnnotationSet> annotations = new ArrayList<AnnotationSet>();

    public String displayLexUnit () {
        currentLU = lexUnitRepository.findById(lexUnitId);
        for (AnnotationSet annotSet : annotationSetRepository.findAll()) {
            if (annotSet.getLexUnit().getId() == currentLU.getId()) {
                annotations.add(annotSet);
            }
        }
        findSentences();
        findAnnotatedFE();
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

    public void findAnnotatedFE () {
        for (AnnotationSet annoSet : annotations) {
            Sentence sentence = annoSet.getSentence();
            List<Label> allLabels = new ArrayList<Label>();
            for (Layer layer : getAllLayers(annoSet)) {
                for (Label label : getAllLabels(layer)) {
                    allLabels.add(label);
                }
            }
            for (Label label : allLabels) {
                if (label.getLabelType().getLayerType().getId() == 1) {
                    FrameElement fe = label.getLabelType().getFrameElement();
                    boolean insert = true;
                    for (LexicalEntry lex : lexicalEntries) {
                        if (lex.getFrameElement().getId() == fe.getId()) {
                            lex.addOccurence(sentence);
                            insert = false;
                            break;
                        }
                    }
                    if (insert) {
                        lexicalEntries.add(new LexicalEntry(fe));
                    }
                }
            }
            for (LexicalEntry lexicalEntry : lexicalEntries) {
                FrameElement fe = lexicalEntry.getFrameElement();
                HashMap<String , List<Sentence>> list = lexicalEntry.getRealizations();
                for (Label labelRef : allLabels) {
                    if (labelRef.getLabelType().getLayerType().getId() == 1) {
                        if (labelRef.getInstantiationType().getId() == 1) {
                            if (labelRef.getLabelType().getFrameElement().getId() == fe.getId()) {
                                int start = labelRef.getStartChar();
                                int end = labelRef.getEndChar();
                                for (Label label : allLabels) {
                                    if (label.getLabelType().getLayerType().getId() == 3) {
                                        if ((label.getStartChar() == start) && (label.getEndChar() == end)) {
                                            String pt = label.getLabelType().getMiscLabel().getName();
                                            for (Label labelGF : allLabels) {
                                                if (labelGF.getLabelType().getLayerType().getId() == 4) {
                                                    if ((labelGF.getStartChar() == start) && (labelGF.getEndChar() == end)) {
                                                        String gf = labelGF.getLabelType().getMiscLabel().getName();
                                                        String result = gf + "." + pt;
                                                        List<Sentence> realizations = new ArrayList<Sentence>();
                                                        if (list.containsKey(result)) {
                                                            realizations = list.get(result);
                                                        }
                                                        realizations.add(sentence);
                                                        list.put(result, realizations);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }   else if ((labelRef.getInstantiationType().getId() == 4) && (labelRef.getLabelType().getFrameElement().getId() == fe.getId())) {
                            String result = labelRef.getInstantiationType().getName() + ".--";
                            List<Sentence> realizations = new ArrayList<Sentence>();
                            if (list.containsKey(result)) {
                                realizations = list.get(result);
                            }
                            realizations.add(sentence);
                            list.put(result, realizations);
                        }
                    }
                }
                lexicalEntry.setRealizations(list);
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

    public void setLexicalEntries (List<LexicalEntry> list) {
        lexicalEntries = list;
    }

    public List<LexicalEntry> getLexicalEntries () {
        return lexicalEntries;
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

    public List<Map.Entry<String, List<Sentence>>> convertMapToList (LexicalEntry el) {
        return new ArrayList(el.getRealizations().entrySet());
    }
}