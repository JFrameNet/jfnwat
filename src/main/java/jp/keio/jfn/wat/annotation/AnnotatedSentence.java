package jp.keio.jfn.wat.annotation;

import jp.keio.jfn.wat.domain.AnnotationSet;


import java.util.ArrayList;
import java.util.List;

/**
 * This class is used in fullText mode. An AnnotatatedSentence object contains all the existing AnnotationDisplay
 * objects associated with the annotation sets of the sentence.
 */
public class AnnotatedSentence {
    private List<AnnotationDisplay> allAnnotations = new ArrayList<AnnotationDisplay>();
    private AnnotationDisplay currentAnnoSet;
    private String bkgColor;
    private int rank;

    /**
     * Initialization with the list of annotated objects. By default, enable the "null" annotation set (when no LU
     * target has been selected).
     */
    public AnnotatedSentence(List<AnnotationDisplay> annotations, int type, int rank) {
        this.allAnnotations = annotations;
        if (type == 0) {
            this.bkgColor = "#ffffff";
        } else {
            this.bkgColor = "#f5f5f5";
        }
        this.rank = rank;
        currentAnnoSet = allAnnotations.get(0);
    }

    /**
     * Switch the annotation set to be displayed. If a user clicks on the LU target that is already selected, the
     * enabled annotation set becomes null (no annotation is displayed).
     */
    public void switchAnnoSet (Target word) {
        AnnotationSet annotationSet = word.getAnnotationSet();
        if (annotationSet == null) {
            return;
        }
        AnnotationDisplay annotationDisplay = null;
        for (AnnotationDisplay display : allAnnotations) {
            if (display.getAnnotationSet() != null) {
                if (display.getAnnotationSet().getId() == annotationSet.getId()) {
                    annotationDisplay = display;
                    break;
                }
            }
        }
        if (annotationDisplay == null) {
            return;
        }
        if ((currentAnnoSet.getAnnotationSet() != null) && (annotationDisplay.getAnnotationSet().getId() == currentAnnoSet.getAnnotationSet().getId())){
            currentAnnoSet = allAnnotations.get(0);
        } else {
            currentAnnoSet = annotationDisplay;
        }
    }

    public boolean noAnnotation() {
        return this.allAnnotations.size() == 1;
    }

    public AnnotationDisplay getCurrentAnnoSet() {
        return currentAnnoSet;
    }

    public int getRank() {
        return rank;
    }

    public void setCurrentAnnoSet(AnnotationDisplay currentAnnoSet) {
        this.currentAnnoSet = currentAnnoSet;
    }

    public List<AnnotationDisplay> getAllAnnotations() {
        return allAnnotations;
    }

    public String getBkgColor() {
        return bkgColor;
    }
}
