package jp.keio.jfn.wat.controller;

import jp.keio.jfn.wat.domain.FrameElement;

/**
 * Created by jfn on 1/13/16.
 */
public class ElementTag {
    private String element;
    private String tag;
    private String color;
    private FrameElement frameElement = null;

    public ElementTag (String el, String t) {
        this.element = el;
        this.tag = t;
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
}
