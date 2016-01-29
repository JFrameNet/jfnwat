package jp.keio.jfn.wat.controller;

import java.io.Serializable;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import jp.keio.jfn.wat.domain.*;
import jp.keio.jfn.wat.repository.*;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
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
        if (lu.getSelectedEl().contains("All")) {
            lu.setValencePatterns(sortGroupRealizations(lu.getFeGroupRealizations()));
            return;
        }
        for (FEGroupRealization group : lu.getFeGroupRealizations()) {
            boolean in = false;
            for (FrameElement frameElement : group.getFrameElements()) {
                if ((lu.getSelectedEl().contains(frameElement.getType()))||(lu.getSelectedEl().contains("Non-Core")&&(!frameElement.getType().equals("Core")))||(lu.getSelectedEl().contains(frameElement.getName()))) {
                    result.add(group);
                    in = true;
                    break;
                }
            }
            if (!in) {
                for (PatternEntry patternEntry : group.getPatterns()) {
                    if (!in) {
                        for (LayerTriplet layerTriplet : patternEntry.getValenceUnits()) {
                            String aux = layerTriplet.getLabelFE().getLabelType().getFrameElement().getName() + "." +layerTriplet.outputString();
                            if (lu.getSelectedEl().contains(aux)) {
                                result.add(group);
                                in = true;
                                break;
                            }
                        }
                    }
                }
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

    public void setScreenWidth(String screenWidth) {
        this.screenWidth = screenWidth;
    }

    public String getScreenWidth() {
        return screenWidth;
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