package jp.keio.jfn.wat.controller;

import jp.keio.jfn.wat.domain.FrameElement;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to represent annotation, an annotated sentence is associated with a list of Tag objects.
 * The value of a tag object is the name of the corresponding frame element, can be empty, in this case the frameElement
 * propriety is null.
 */
public class Tag {
    private String value;
    private FrameElement frameElement;
    private List<Target> associated = new ArrayList<Target>();

    private String color;

    /**
     * Initialization.
     * The color of the annotation depends on the display mode (lexical unit entry or fullText).
     *
     * @param value the name of the frame element, can be empty if the FE is null.
     * @param words a list of targets associated
     */
    public Tag (String value, List<Target> words) {
        this.associated = words;
        this.value = value;
        this.color = this.value.equals("LU") ?"#F5F5F5":"#FFFFFF";
    }

    public boolean isEmpty() {
        for (Target target : associated) {
            if (!target.getText().equals(" ")) {
                return false;
            }
        }
        return true;
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
