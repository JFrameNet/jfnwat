package jp.keio.jfn.wat.controller;

import jp.keio.jfn.wat.domain.FrameElement;
import jp.keio.jfn.wat.domain.LexUnit;

/**
 * Created by jfn on 1/13/16.
 */
public class ElementTag {
    private String element;
    private String tag;
    private String color;
    private String wordColor;
    private FrameElement frameElement = null;
    private boolean isLU;

    public ElementTag (String el, String t) {
        this.element = el;
        this.tag = t;
        this.wordColor = "#546E7A";
    }

    public void setFrameElement(FrameElement fe){
        this.frameElement = fe;
    }

    public FrameElement getFrameElement() {
        return frameElement;
    }
    public void setColor(String color) {
        this.color = color;
    }

    public String getColor () {
        return color;
    }

    public String getElement () {
        return element;
    }
    public String getTag () {
        return tag;
    }

    public void setElement(String elememt) {
        this.element = elememt;
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
}
