package jp.keio.jfn.wat.controller;

import java.io.Serializable;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import jp.keio.jfn.wat.domain.*;
import jp.keio.jfn.wat.repository.*;
import org.hibernate.Hibernate;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.RequestContext;
import sun.security.x509.AttributeNameEnumeration;

@ManagedBean
@RestController
@Scope("view")
public class LexUnitController implements Serializable {
    @Autowired
    LexUnitRepository lexUnitRepository;

    private String filter = "";

    private String screenWidth = "";

    private LazyDataModel<LightLU> model;

    public void setFilter (String f) {
        filter = f;
    }

    public String getFilter () {
        return filter;
    }

    public List<SentenceOutput> showAnnotation (LUOutput lu) {
        return lu.getSelectedSentences();
    }


    public void realPatterEntry (LUOutput lu,PatternEntry patternEntry) {
        double breakline = (float) (Integer.parseInt(screenWidth)  * 0.0625);
        for (SentenceOutput sentence : lu.processSentences(patternEntry.getAnnoSet(), (int) breakline, false)) {
            boolean insert = true;
            for (SentenceOutput in : lu.getSelectedSentences()) {
                if (sentence.getText().equals(in.getText())) {
                    insert = false;
                    break;
                }
            }
            if (insert) {
                lu.getSelectedSentences().add(sentence);
            }
        }
    }

    public void totalGroup (LUOutput lu,FEGroupRealization group) {
        double breakline = (float) (Integer.parseInt(screenWidth)  * 0.0625);
        for (SentenceOutput sentence : lu.processSentences(group.getAllAnnotations(), (int)breakline, false)) {
            boolean insert = true;
            for (SentenceOutput in : lu.getSelectedSentences()) {
                if (sentence.getText().equals(in.getText())) {
                    insert = false;
                    break;
                }
            }
            if (insert) {
                lu.getSelectedSentences().add(sentence);
            }
        }
    }

    public void removeSentence (LUOutput lu, SentenceOutput sentenceOutput) {
        lu.getSelectedSentences().remove(sentenceOutput);
    }


    private void filterValencePatterns (LUOutput lu) {
        List<FEGroupRealization> result = new ArrayList<FEGroupRealization>();
        if (lu.getSelectedEl().isEmpty()) {
            lu.setValencePatterns( sortGroupRealizations(result));
            return;
        }
        if (lu.getSelectedEl().contains("All")) {
            lu.setValencePatterns(sortGroupRealizations(lu.getFeGroupRealizations()));
            return;
        }
        if (lu.getSelectedEl().contains("Core")) {
            for (FEGroupRealization group : lu.getFeGroupRealizations()) {
                for (FrameElement frameElement : group.getFrameElements()) {
                    if (frameElement.getType().equals("Core")) {
                        result.add(group);
                        break;
                    }
                }
            }
            lu.setValencePatterns( sortGroupRealizations(result));
            return;
        }
        if (lu.getSelectedEl().contains("Non-Core")) {
            for (FEGroupRealization group : lu.getFeGroupRealizations()) {
                for (FrameElement frameElement : group.getFrameElements()) {
                    if (!frameElement.getType().equals("Core")) {
                        result.add(group);
                        break;
                    }
                }
            }
            lu.setValencePatterns( sortGroupRealizations(result));
            return;
        }
        for (FEGroupRealization group : lu.getFeGroupRealizations()) {
            boolean good = true;
            for (String filter : lu.getSelectedEl()) {
                boolean contains = false;
                for (FrameElement frameElement : group.getFrameElements()) {
                    if (filter.equals(frameElement.getName())) {
                        contains = true;
                        break;
                    }
                }
                if (!contains) {
                    for (PatternEntry patternEntry : group.getPatterns()) {
                        if (!contains) {
                            for (LayerTriplet layerTriplet : patternEntry.getValenceUnits()) {
                                String aux = layerTriplet.getLabelFE().getLabelType().getFrameElement().getName() + "." +layerTriplet.outputString();
                                if (filter.equals(aux)) {
                                    contains = true;
                                    break;
                                }
                            }
                        } else {
                            break;
                        }
                    }
                    if (!contains) {
                        good = false;
                        break;
                    }
                }
            }
            if (good) {
                result.add(group);
            }
        }
        lu.setValencePatterns( sortGroupRealizations(result));
    }

    private List<FEGroupRealization> sortGroupRealizations (List<FEGroupRealization> list) {
        List<FEGroupRealization> sortedList = new ArrayList<FEGroupRealization>();
        for (FEGroupRealization group : list) {
            int size = group.getFrameElements().size();
            int index = 0;
            while (index < sortedList.size()) {
                if (size <= sortedList.get(index).getFrameElements().size()) {
                    break;
                }
                index ++;
            }
            sortedList.add(index, group);
        }
        return sortedList;
    }

    public List<String> getSelectedEl(LUOutput lu) {
        return lu.getSelectedEl();
    }

    public List<FEGroupRealization> getValencePatterns(LUOutput lu) {
        return lu.getValencePatterns();
    }

    public void addFilter (LUOutput lu,String string) {
        if (string.equals("Core") || string.equals("Non-Core") ||string.equals("All")) {
            List<String> selectedEl = new ArrayList<String>();
            selectedEl.add(string);
            lu.setSelectedEl(selectedEl);
            filterValencePatterns(lu);
            if (string.equals("Core")) {
                lu.setDisplayCore("block");
                lu.setDisplayNonCore("none");
            } else if (string.equals("Non-Core")) {
                lu.setDisplayNonCore("block");
                lu.setDisplayNonCore("none");
            }else if (string.equals("All")) {
                lu.setDisplayNonCore("block");
                lu.setDisplayNonCore("block");
            }
            return;
        }
        if (!lu.getSelectedEl().contains(string)) {
            List<String> toRemove = new ArrayList<String>();
            toRemove.add("All");
            toRemove.add("Core");
            toRemove.add("Non-Core");
            for (String filters : lu.getSelectedEl()) {
                if (filters.contains(string)) {
                    toRemove.add(filters);
                }
            }
            for (String delete : toRemove) {
                lu.getSelectedEl().remove(delete);
            }
            lu.getSelectedEl().add(string);
        }
        filterValencePatterns(lu);
    }

    public void addFilterReal (LUOutput lu, FrameElement el, String string) {
        String output = el.getName() + "." + string;
        if (!lu.getSelectedEl().contains(output)) {
            lu.getSelectedEl().add(output);
        }
        lu.getSelectedEl().remove(el.getName());
        lu.getSelectedEl().remove("All");
        lu.getSelectedEl().remove("Core");
        lu.getSelectedEl().remove("Non-Core");
        filterValencePatterns(lu);
    }

    public void removeFilter(LUOutput lu, String string) {
        if (lu.getSelectedEl().contains(string)) {
            lu.getSelectedEl().remove(string);
        }
        filterValencePatterns(lu);
    }

    public void clearAllSentences(LUOutput lu) {
        lu.setSelectedSentences(new ArrayList<SentenceOutput>());
    }

    public void setScreenWidth(String screenWidth) {
        this.screenWidth = screenWidth;
    }

    public String getScreenWidth() {
        return screenWidth;
    }

    @Transactional
    public List<Status> lexUnitStatus (LUOutput luOutput) {
        LexUnit lu = lexUnitRepository.findById(luOutput.getLightLU().getId());
        Hibernate.initialize(lu.getStatuses());
        return lu.getStatuses();
    }

    //    public LazyDataModel<LightLU> getModel() {
//        this.model = new LazyDataModel<LightLU>() {
//            @Override
//            public List<LightLU> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
//                List<LightLU> result = getOrderedLU();
//                return result;
//            }
//        };
//        return model;
//    }
}