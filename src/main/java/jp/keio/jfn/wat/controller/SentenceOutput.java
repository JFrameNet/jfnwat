package jp.keio.jfn.wat.controller;

import java.util.List;

/**
 * This class is a representation of an annotated sentence.
 * Every line of the sentence is associated with a list of ElementTag objects.
 */
public class SentenceOutput {

    private List<Tag> elements;

    private SentenceDisplay sentenceDisplay;

    /**
     * Initialization
     */
    public SentenceOutput(SentenceDisplay sentence, List<Tag> elements) {
        this.elements = elements;
        this.sentenceDisplay = sentence;
    }


    public List<Tag> getElements() {
        return elements;
    }

    public SentenceDisplay getSentenceDisplay() {
        return sentenceDisplay;
    }

    public void setElements(List<Tag> elements) {
        this.elements = elements;
    }

    public void setSentenceDisplay(SentenceDisplay sentenceDisplay) {
        this.sentenceDisplay = sentenceDisplay;
    }
}
