package jp.keio.jfn.wat.controller;

import java.io.Serializable;
import java.util.*;
import javax.faces.bean.ManagedBean;

import jp.keio.jfn.wat.annotation.AnnotationDisplay;
import jp.keio.jfn.wat.domain.*;
import jp.keio.jfn.wat.repository.*;
import jp.keio.jfn.wat.webreport.FEGroupRealization;
import jp.keio.jfn.wat.webreport.LUOutput;
import jp.keio.jfn.wat.webreport.LayerTriplet;
import jp.keio.jfn.wat.webreport.PatternEntry;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;


/**
 * This class is a controller for the lexical unit index. It handles operations on LUOutput objects.
 */
@ManagedBean
@RestController
@Scope("view")
public class LexUnitController implements Serializable {
    @Autowired
    LexUnitRepository lexUnitRepository;

    private String filter = "";

    /**
     * Adds a filter when the user clicks on a button corresponding to the name of a frame element on on the "All",
     * "Core Only" and "Non Core Only" buttons.
     * Sets the boolean values displayCore and displayNonCore of the LUOutput object.
     */
    public void addFilter (LUOutput lu, String string) {
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

    /**
     * Adds a filter when the user clicks on a button correspondong to a realization of a frame element.
     */
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

    /**
     * Removes a filter from the list of the selected filters
     */
    public void removeFilter(LUOutput lu, String string) {
        if (lu.getSelectedEl().contains(string)) {
            lu.getSelectedEl().remove(string);
        }
        filterValencePatterns(lu);
    }

    /**
     * This method is called when a user wants to add all of the sentences of a pattern entry to the list of the
     * selected sentences.
     */
    public void realPatterEntry (LUOutput lu,PatternEntry patternEntry) {
        for (AnnotationSet annotationSet : patternEntry.getAnnoSet()) {
            enableSentence(lu, annotationSet);
        }
    }

    /**
     * This method is called when a user wants to add all of the sentences of a group realization to the list of the
     * selected sentences.
     */
    public void totalGroup (LUOutput lu,FEGroupRealization group) {
        for (AnnotationSet annotationSet : group.getAllAnnotations()) {
            enableSentence(lu, annotationSet);
        }
    }

    /**
     * Adds a new AnnotationDisplay object corresponding to an annotation set to the list of the selected sentences.
     * The insert is not performed if the annotation set has already been selected.
     */
    private void enableSentence (LUOutput lu, AnnotationSet annotationSet) {
        for (AnnotationDisplay s : lu.getAllSentences()) {
            if (s.getAnnotationSet().getId() == annotationSet.getId()) {
                s.setDisplayed(true);
            }
        }
        lu.hasDisplayedSentences();
    }

    /**
     * Removes a sentence from the list of the selected sentences of the LUOutput object.
     */
    public void removeSentence (LUOutput lu, AnnotationDisplay sentence) {
        for (AnnotationDisplay s : lu.getAllSentences()) {
            if (s.getAnnotationSet().getId() == sentence.getAnnotationSet().getId()) {
                s.setDisplayed(false);
            }
        }
        lu.hasDisplayedSentences();
    }


    /**
     * Retrieves the group realizations corresponding to the filters in the selectedEl list.
     * It operates an "and" operation : a group realization is displayed only if its list of frame elements contains all
     * of the elements in the selectedEl list. If the user selected filters of the format FE.PT.GF, a group realization
     * is accepted only if it contains the frame element in this exact realization.
     * If the user selects "All", all group realizations are displayed.
     * If the user selects "Core", all group realizations that contain at least one core frame element in their
     * FrameElements list are displayed.
     * If the user selects "Non-Core", all group realizations that contain at least one non core frame element in their
     * FrameElements list are displayed.
     */
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

    /**
     * Sorts all filtered group realizations by the size of their FrameElement list (shortest first).
     */
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

    /**
     * Gets all AnnotationDisplay objects associated to the annotation sets of one lexical unit.
     */
    public List<AnnotationDisplay> getAnnotations (LUOutput lu) {
        return lu.getAllSentences();
    }

    public List<String> getSelectedEl(LUOutput lu) {
        return lu.getSelectedEl();
    }

    public List<FEGroupRealization> getValencePatterns(LUOutput lu) {
        return lu.getValencePatterns();
    }

    /**
     * Disable all of the sentences for on LUOutput object.
     */
    public void clearAllSentences(LUOutput lu) {
        for (AnnotationDisplay s : lu.getAllSentences()) {
            s.setDisplayed(false);
        }
        lu.hasDisplayedSentences();
    }

    /**
     * Gets all the statuses for the lexical unit.
     */
    @Transactional
    public List<Status> lexUnitStatus (LUOutput luOutput) {
        LexUnit lu = lexUnitRepository.findById(luOutput.getLightLU().getId());
        Hibernate.initialize(lu.getStatuses());
        return lu.getStatuses();
    }

    public void setFilter (String f) {
        filter = f;
    }

    public String getFilter () {
        return filter;
    }

    public void setLexUnitRepository(LexUnitRepository l) {
        lexUnitRepository = l;
    }
}