package jp.keio.jfn.wat.webreport;

import jp.keio.jfn.wat.annotation.AnnotationDisplay;
import jp.keio.jfn.wat.domain.AnnotationSet;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import javax.faces.bean.ManagedBean;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jfn on 3/11/16.
 */
@ManagedBean
@RestController
@Component
@Scope("session")
public class DialogManager {
    private List<AnnotationDisplay> selectedSentences = new ArrayList<AnnotationDisplay>();

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
                if (!this.selectedSentences.contains(s)) {
                    this.selectedSentences.add(s);
                }
            }
        }
    }

    public String hasSentences() {
        if (this.selectedSentences.size() > 0) {
            return "";
        } else {
            return "none";
        }
    }

    /**
     * Removes a sentence from the list of the selected sentences of the LUOutput object.
     */
    public void removeSentence (AnnotationDisplay sentence) {
        this.selectedSentences.remove(sentence);
    }

    /**
     * Delete all sentences
     */
    public void clearAllSentences() {
        this.selectedSentences = new ArrayList<AnnotationDisplay>();
    }

    public void setSelectedSentences(List<AnnotationDisplay> selectedSentences) {
        this.selectedSentences = selectedSentences;
    }

    public List<AnnotationDisplay> getSelectedSentences() {
        return selectedSentences;
    }
}
