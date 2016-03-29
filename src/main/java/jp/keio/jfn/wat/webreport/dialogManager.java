package jp.keio.jfn.wat.webreport;

import jp.keio.jfn.wat.annotation.AnnotationDisplay;
import jp.keio.jfn.wat.annotation.Tag;
import jp.keio.jfn.wat.annotation.Target;
import jp.keio.jfn.wat.webreport.domain.AnnotationSet;
import jp.keio.jfn.wat.webreport.domain.LexUnit;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import javax.faces.bean.ManagedBean;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
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
    private boolean mini = false;

    public StreamedContent downloadSentencesFile() {
        String exampleString = "";
        int i = 0;
        for (AnnotationDisplay annotationDisplay : selectedSentences) {
            i ++;
            exampleString += i + ") " + annotationToString(annotationDisplay);
        }
        InputStream stream = new ByteArrayInputStream(exampleString.getBytes(StandardCharsets.UTF_8));
        return  new DefaultStreamedContent(stream, "txt", "web-report.txt");
    }

    private String annotationToString(AnnotationDisplay annotationDisplay) {
        String result = "";
        for (Tag tag : annotationDisplay.getElements()) {
            if (!tag.getValue().isEmpty()) {
                result += "[";
            }
            for (Target target : tag.getAssociated()) {
                if (target.isFocus()) {
                    result += "{";
                }
                result += target.getText();
                if (target.isFocus()) {
                    result += " TargetLU}";
                }
            }
            if (!tag.getValue().isEmpty()) {
                result += " " +tag.getValue() + "]";
            }
        }
        LexUnit lu = annotationDisplay.getAnnotationSet().getLexUnit();
        result += " " + lu.getFrame().getName()+"."+lu.getName();
        return result + "\n\n";
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
                if (!this.selectedSentences.contains(s)) {
                    this.selectedSentences.add(s);
                }
            }
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
        this.mini = false;
    }

    public void setSelectedSentences(List<AnnotationDisplay> selectedSentences) {
        this.selectedSentences = selectedSentences;
    }

    public void toggleMini() {
        this.mini = !this.mini;
    }

    public String getBkgColor(AnnotationDisplay line) {
        int type = selectedSentences.indexOf(line) % 2;
        if (type == 0) {
            return "#ffffff";
        } else {
            return "#f5f5f5";
        }
    }

    public String getTagColor(Tag tag, AnnotationDisplay line) {
        if (tag.getFrameElement() == null) {
            return getBkgColor(line);
        } else {
            return tag.getColor();
        }
    }

    public List<AnnotationDisplay> getSelectedSentences() {
        return selectedSentences;
    }

    public boolean isMini() {
        return mini;
    }

    public void setMini(boolean mini) {
        this.mini = mini;
    }
}
