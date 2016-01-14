package jp.keio.jfn.wat.controller;

/**
 * Created by jfn on 1/13/16.
 */
public class ElementTag {
    private String element;
    private String tag;
    private String color;

    public ElementTag (String el, String t) {
        this.element = el;
        this.tag = t;
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
