package jp.keio.jfn.wat.controller;

import jp.keio.jfn.wat.domain.AnnotationSet;
import jp.keio.jfn.wat.domain.Label;
import jp.keio.jfn.wat.domain.Layer;
import jp.keio.jfn.wat.domain.Sentence;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jfn on 2/16/16.
 */
public class SentenceDisplay {
    private Sentence sentence;
    private AnnotationSet displayedAnnotationSet;
    private List<Tag> elements;
    private List<Label> focus = new ArrayList<Label>();
    private List<Label> allTargets = new ArrayList<Label>();

    public SentenceDisplay(Sentence sentence, AnnotationSet annotationSet, boolean fullText) {
        this.sentence = sentence;
        this.displayedAnnotationSet = annotationSet;
        getPosFocus(fullText);
    }

    public AnnotationSet getDisplayedAnnotationSet() {
        return displayedAnnotationSet;
    }

    public Sentence getSentence() {
        return sentence;
    }

    private void getPosFocus(boolean fullText) {
        List<Label> allLabels = new ArrayList<Label>();
        if (displayedAnnotationSet != null) {
            for (Layer layer : displayedAnnotationSet.getLayers()){
                if (layer.getLayerType().getId() == 2) {
                    this.focus.addAll(layer.getLabels());
                }
            }
        }
        if (fullText) {
            for (AnnotationSet annoSet : sentence.getAnnotationSets()) {
                for (Layer layer : annoSet.getLayers()){
                    if (layer.getLayerType().getId() == 2) {
                        allLabels.addAll(layer.getLabels());
                    }
                }
            }
        } else {
            allLabels = this.focus;
        }

        List<Label> sorted = new ArrayList<Label>();
        for (Label label : allLabels) {
            insertAtPos(sorted, label);
        }
        this.allTargets = sorted;
    }

    private void insertAtPos (List<Label> labels, Label label) {
        int i = 0;
        while (i < labels.size()) {
            if (labels.get(i).getStartChar() > label.getStartChar()) {
                break;
            }
            i ++;
        }
        labels.add(i,label);
    }

    public void setDisplayedAnnotationSet(AnnotationSet displayedAnnotationSet) {
        this.displayedAnnotationSet = displayedAnnotationSet;
    }

    public void setSentence(Sentence sentence) {
        this.sentence = sentence;
    }

    public List<Tag> getElements() {
        return elements;
    }
    public void setElements(List<Tag> elements) {
        this.elements = elements;
    }


    public List<Label> getAllTargets() {
        return allTargets;
    }

    public void setFocus(List<Label> focus) {
        this.focus = focus;
    }

    public List<Label> getFocus() {
        return focus;
    }
}
