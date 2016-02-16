package jp.keio.jfn.wat.controller;

import jp.keio.jfn.wat.domain.AnnotationSet;

/**
 * Created by jfn on 2/16/16.
 */
public class Target {

    private String text;
    private AnnotationSet annotationSet;
    private boolean valid;
    private boolean focus;

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
