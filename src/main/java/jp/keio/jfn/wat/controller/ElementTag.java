package jp.keio.jfn.wat.controller;

import jp.keio.jfn.wat.domain.AnnotationSet;
import jp.keio.jfn.wat.domain.FrameElement;
import jp.keio.jfn.wat.domain.LexUnit;

/**
 * This is an helper to display annotation.
 * An ElementTag object consists of a word and an associated tag. A color can be specified for the word and the tag.
 */
public class ElementTag {
    private String word;
    private String tag;
    private String tagColor;
    private String wordColor;
    private FrameElement frameElement = null;
    private boolean isLU;
    private AnnotationSet annotationSet = null;

    /**
     * Initialisation of an ElementTag with a word and an associated tag.
     */
    public ElementTag (String w, String t) {
        this.word = w;
        this.tag = t;
        this.wordColor = "#546E7A";
        this.tagColor = "#FFFFFF";
        this.isLU = false;
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
