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

    private LazyDataModel<LightLU> model;

//    private List<AnnotationSet> annotationSetsDialog = new ArrayList<AnnotationSet>();

    private List<LightLU> orderedLU = new ArrayList<LightLU>();

    private List<String> selectedEl = new ArrayList<String>();

    private List<FEGroupRealization> valencePatterns = new ArrayList<FEGroupRealization>();

    private List<SentenceOutput> selectedSentences = new ArrayList<SentenceOutput>();

    public void orderLU () {
        List<LightLU> allLU= new ArrayList<LightLU>();
        for (LexUnit lu : lexUnitRepository.findAll()) {
            if (matchSearch(filter, lu.getName())) {
                allLU.add(new LightLU(lu.getId(), lu.getName(), lu.getFrame().getName()));
            } else if (matchSearch(filter, lu.getFrame().getName())) {
                allLU.add(new LightLU(lu.getId(), lu.getName(), lu.getFrame().getName()));
            }
        }
        orderedLU = allLU;
    }

    public void setFilter (String f) {
        filter = f;
    }

    public String getFilter () {
        return filter;
    }


    public List<LightLU> getOrderedLU () {
        if (orderedLU.isEmpty() && filter.isEmpty()) {
            List <LightLU> allLu = new ArrayList<LightLU>();
            for (LexUnit lu : lexUnitRepository.findAll()) {
                allLu.add(new LightLU(lu.getId(), lu.getName(), lu.getFrame().getName()));
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

    public List<SentenceOutput> showAnnotation () {
        return selectedSentences;
    }


    public void realPatterEntry (LUOutput lu,PatternEntry patternEntry) {
        selectedSentences.addAll(lu.processSentences(patternEntry.getAnnoSet(), 78, false));
    }

    public void totalGroup (LUOutput lu,FEGroupRealization group) {
        selectedSentences.addAll(lu.processSentences(group.getAllAnnotations(), 78, false));
    }

    public void removeSentence (SentenceOutput sentenceOutput) {
        selectedSentences.remove(sentenceOutput);
    }


    private void filterValencePatterns (LUOutput lu) {
        List<FEGroupRealization> result = new ArrayList<FEGroupRealization>();
        if (selectedEl.contains("All")) {
            valencePatterns = sortGroupRealizations(lu.getFeGroupRealizations());
            return;
        }
        for (FEGroupRealization group : lu.getFeGroupRealizations()) {
            boolean in = false;
            for (FrameElement frameElement : group.getFrameElements()) {
                if ((selectedEl.contains(frameElement.getType()))||(selectedEl.contains("Non-Core")&&(!frameElement.getType().equals("Core")))||(selectedEl.contains(frameElement.getName()))) {
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
                            if (selectedEl.contains(aux)) {
                                result.add(group);
                                in = true;
                                break;
                            }
                        }
                    }
                }
            }
        }
        valencePatterns = sortGroupRealizations(result);
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

    public List<String> getSelectedEl() {
        return selectedEl;
    }

    public void addFilter (LUOutput lu,String string) {
        if (string.equals("Core") || string.equals("Non-Core") ||string.equals("All")) {
            selectedEl = new ArrayList<String>();
            selectedEl.add(string);
            filterValencePatterns(lu);
            return;
        }
        if (!selectedEl.contains(string)) {
            List<String> toRemove = new ArrayList<String>();
            toRemove.add("All");
            toRemove.add("Core");
            toRemove.add("Non-Core");
            for (String filters : selectedEl) {
                if (filters.contains(string)) {
                    toRemove.add(filters);
                }
            }
            for (String delete : toRemove) {
                selectedEl.remove(delete);
            }
            selectedEl.add(string);
        }
        filterValencePatterns(lu);
    }

    public void addFilterReal (LUOutput lu, FrameElement el, String string) {
        String output = el.getName() + "." + string;
        if (!selectedEl.contains(output)) {
            selectedEl.add(output);
        }
        selectedEl.remove(el.getName());
        selectedEl.remove("All");
        selectedEl.remove("Core");
        selectedEl.remove("Non-Core");
        filterValencePatterns(lu);
    }

    public void removeFilter(LUOutput lu, String string) {
        if (selectedEl.contains(string)) {
            selectedEl.remove(string);
        }
        filterValencePatterns(lu);
    }

    public List<FEGroupRealization> getValencePatterns() {
        return valencePatterns;
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