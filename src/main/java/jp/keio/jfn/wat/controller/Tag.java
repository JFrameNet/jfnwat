package jp.keio.jfn.wat.controller;

import jp.keio.jfn.wat.domain.FrameElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jfn on 2/16/16.
 */
public class Tag {
    private String value;
    private FrameElement frameElement;
    private List<Target> associated = new ArrayList<Target>();

    private String color;

    public Tag (String value, List<Target> words) {
        this.associated = words;
        this.value = value;
        this.color = "#FFFFFF";
    }

    public List<Target> getAssociated() {
        return associated;
    }

    public String getValue() {
        return value;
    }

    public void setAssociated(List<Target> associated) {
        this.associated = associated;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public FrameElement getFrameElement() {
        return frameElement;
    }

    public void setFrameElement(FrameElement frameElement) {
        this.frameElement = frameElement;
    }
}
