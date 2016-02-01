package jp.keio.jfn.wat.controller;

import jp.keio.jfn.wat.domain.AnnotationSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jfn on 1/13/16.
 */
public class SentenceOutput {

    private List<List<ElementTag>> elements;

    private String text;

    private AnnotationSet annotationSet;

    public SentenceOutput(List<List<ElementTag>> list, String text) {
        this.elements = list;
        this.text = text;
    }

    public List<List<ElementTag>> getElements() {
        return elements;
    }

    public String getText() {
        return text;
    }

    public AnnotationSet getAnnotationSet() {
        return annotationSet;
    }

    public void setAnnotationSet(AnnotationSet annotationSet) {
        this.annotationSet = annotationSet;
    }
}
