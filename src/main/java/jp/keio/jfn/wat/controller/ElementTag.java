package jp.keio.jfn.wat.controller;

import jp.keio.jfn.wat.domain.AnnotationSet;
import jp.keio.jfn.wat.domain.FrameElement;
import jp.keio.jfn.wat.domain.LexUnit;

/**
 * Created by jfn on 1/13/16.
 */
public class ElementTag {
    private String word;
    private String tag;
    private String tagColor;
    private String wordColor;
    private FrameElement frameElement = null;
    private boolean isLU;
    private AnnotationSet annotationSet = null;

    public ElementTag (String el, String t) {
        this.word = el;
        this.tag = t;
        this.wordColor = "#546E7A";
        this.tagColor = "#FFFFFF";
    }

    public void setFrameElement(FrameElement fe){
        this.frameElement = fe;
    }

    public FrameElement getFrameElement() {
        return frameElement;
    }
    public String getTag () {
        return tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isLU() {
        return isLU;
    }

    public void setLU(boolean LU) {
        isLU = LU;
    }

    public String getWordColor() {
        return wordColor;
    }

    public void setWordColor(String wordColor) {
        this.wordColor = wordColor;
    }

    public void setAnnotationSet(AnnotationSet annotationSet) {
        this.annotationSet = annotationSet;
    }

    public AnnotationSet getAnnotationSet() {
        return annotationSet;
    }

    public String getTagColor() {
        return tagColor;
    }

    public String getWord() {
        return word;
    }

    public void setTagColor(String tagColor) {
        this.tagColor = tagColor;
    }
}
