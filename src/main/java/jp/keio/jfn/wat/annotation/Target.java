package jp.keio.jfn.wat.annotation;

import jp.keio.jfn.wat.webreport.domain.AnnotationSet;

/**
 * This class is used to represent annotation, every Tag object is associated with a list of Target objects.
 * The target is valid if the text is the target LU of an annotation set. Otherwise, the annotation set is null, and the
 * target is just a plain text.
 * The background color depends on the type of target, a non-valid target has a white background whereas a valid target
 * is displayed with a green background.
 */
public class Target {

    private String text;
    private AnnotationSet annotationSet;
    private boolean valid;
    private boolean focus;

    /**
     * Initialization with a String object.
     * Default values are for an invalid target.
     */
    public Target (String text) {
        this.text = text;
        this.valid = false;
        this.focus = false;
    }

    public AnnotationSet getAnnotationSet() {
        return annotationSet;
    }

    public boolean isValid() {
        return valid;
    }

    public String getText() {
        return text;
    }

    public void setAnnotationSet(AnnotationSet annotationSet) {
        this.annotationSet = annotationSet;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }


    public boolean isFocus() {
        return focus;
    }

    public void setFocus(boolean focus) {
        this.focus = focus;
    }
}
