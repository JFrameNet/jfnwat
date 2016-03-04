package jp.keio.jfn.wat.controller;

import jp.keio.jfn.wat.domain.FrameElement;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to represent annotation, an annotated sentence is associated with a list of Tag objects.
 * The value of a tag object is the name of the corresponding frame element, can be empty, in this case the frameElement
 * propriety is null.
 * There are three types of tags:
 *  - blank tag : just a plain character in the sentence
 *  - target tag : tag associated to a target Lexical Unit, used only for the fullText mode. The tagged word has a green
 *  background color and is displayed as a link in the text. It has no frame element associated, this type of tag is
 *  just a variant of the "balnk" tag.
 *  - frame element tag : actual tag, with a list of words underscored with a frame element.
 */
public class Tag {
    private String value;
    private FrameElement frameElement;
    private List<Target> associated = new ArrayList<Target>();
    private Target target;
    private String color;
    private SentenceDisplay parentSentenceDisplay;

    /**
     * Initialization with a list of targets ("frameElement type tags).
     * The color of the annotation depends on the display mode (lexical unit entry or fullText).
     *
     * @param value the name of the frame element, can be empty if the FE is null.
     * @param words a list of targets associated
     * @param parent the SentenceDisplay object the tag belongs to.
     */
    public Tag (SentenceDisplay parent, String value, List<Target> words) {
        this.associated = words;
        this.value = value;
        this.parentSentenceDisplay = parent;
    }

    /**
     * Initialization with a unique target ("blank" and "target" type tags).
     * The color of the annotation depends on the display mode (lexical unit entry or fullText).
     *
     * @param value the name of the frame element, can be empty if the FE is null.
     * @param word the unique target associated
     * @param parent the SentenceDisplay object the tag belongs to
     */
    public Tag (SentenceDisplay parent, String value, Target word) {
        this.target = word;
        this.value = value;
        this.parentSentenceDisplay = parent;
    }

    public String myBackgroundColor () {
        if (this.parentSentenceDisplay.isFullText()) {
            return "#FFFFFF";
        } else {
            return "#F5F5F5";
        }
    }

    public boolean isEmpty() {
        return target.getText().equals(" ");
    }

    public Target getTarget() {
        return target;
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

    public SentenceDisplay getParentSentenceDisplay() {
        return parentSentenceDisplay;
    }
}
