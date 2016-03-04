package jp.keio.jfn.wat.controller;

import jp.keio.jfn.wat.domain.AnnotationSet;

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
    private String bkg;
    private SentenceDisplay parentSentenceDisplay;

    /**
     * Initialization with a String object.
     * Default values are for an invalid target.
     */
    public Target (SentenceDisplay parent, String text) {
        this.text = text;
        this.valid = false;
        this.bkg = "#ffffff";
        this.parentSentenceDisplay = parent;
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

    public String getBkg() {
        return bkg;
    }

    public void setBkg(String bkg) {
        this.bkg = bkg;
    }

    public SentenceDisplay getParentSentenceDisplay() {
        return parentSentenceDisplay;
    }
}
