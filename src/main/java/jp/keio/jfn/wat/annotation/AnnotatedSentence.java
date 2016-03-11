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
    private AnnotationSet currentAnnoSet = null;
    private String bkgColor;

    /**
     * Initialization with the list of annotated objects. By default, enable the "null" annotation set (when no LU
     * target has been selected).
     */
    public AnnotatedSentence(List<AnnotationDisplay> annotations, int type) {
        this.allAnnotations = annotations;
        enableAnnoSet();
        if (type == 0) {
            this.bkgColor = "#ffffff";
        } else {
            this.bkgColor = "#f5f5f5";
        }
    }

    /**
     * Switch the annotation set to be displayed. If a user clicks on the LU target that is already selected, the
     * enabled annotation set becomes null (no annotation is displayed).
     */
    public void switchAnnoSet (AnnotationSet annotationSet) {
        if (annotationSet == null) {
            return;
        }
        if ((currentAnnoSet != null) && (annotationSet.getId() == currentAnnoSet.getId())){
            currentAnnoSet = null;
        } else {
            currentAnnoSet = annotationSet;
        }
        enableAnnoSet();
    }

    private void enableAnnoSet () {
        for (AnnotationDisplay annotationDisplay : allAnnotations) {
            if (currentAnnoSet == null) {
                annotationDisplay.setDisplayed(annotationDisplay.getAnnotationSet() == null);
            } else {
                int id = currentAnnoSet.getId();
                if (annotationDisplay.getAnnotationSet() == null) {
                    annotationDisplay.setDisplayed(false);
                } else {
                    annotationDisplay.setDisplayed(annotationDisplay.getAnnotationSet().getId() == id);
                }
            }
        }
    }

    public List<AnnotationDisplay> getAllAnnotations() {
        return allAnnotations;
    }

    public String getBkgColor() {
        return bkgColor;
    }
}
