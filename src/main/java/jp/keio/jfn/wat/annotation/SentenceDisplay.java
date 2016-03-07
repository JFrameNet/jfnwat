package jp.keio.jfn.wat.annotation;

import jp.keio.jfn.wat.Utils;
import jp.keio.jfn.wat.domain.AnnotationSet;
import jp.keio.jfn.wat.domain.Label;
import jp.keio.jfn.wat.domain.Layer;
import jp.keio.jfn.wat.domain.Sentence;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to represent an annotation. A SentenceDisplay object is associated with one annotation set,
 * from which a list of tags is created. The annotation can be done in "normal" or "fullText" mode.
 */
public class SentenceDisplay {
    private Sentence sentence;
    private AnnotationSet displayedAnnotationSet;
    private List<Tag> elements;
    private List<Label> focus = new ArrayList<Label>();
    private List<Label> allTargets = new ArrayList<Label>();
    private boolean fullText;

    /**
     * Initialization.
     * @param sentence the original sentence, it does not depends on the annotation set
     * @param annotationSet the chosen annotation set, can be null in fullText mode
     * @param fullText set to true for documents
     */
    public SentenceDisplay(Sentence sentence, AnnotationSet annotationSet, boolean fullText) {
        this.sentence = sentence;
        this.displayedAnnotationSet = annotationSet;
        this.fullText = fullText;
        getAllTargetAndFocus();
    }

    /**
     * Creates the list of Tag object corresponding to the chosen annotation.
     * @param allFE is the list of the names of all the frame elements. It is used to keep the colours consistent
     *              within the same page (a frame element in the view will be displayed in the same colour for
     *              every sentence).
     */
    public void getAnnotation(List<String> allFE) {
        getAllTargetAndFocus();
        List<Tag> tags = new ArrayList<Tag>();
        // Creates the list of all the labels related to a frame element (i.e. labels belonging to a layer of type 1)
        List<Label> allLabels = new ArrayList<Label>();
        if (displayedAnnotationSet != null) {
            for (Layer layer : displayedAnnotationSet.getLayers()){
                if (layer.getLayerType().getId() == 1) {
                    allLabels.addAll(layer.getLabels());
                }
            }
        }
        int i = 0;
        int iaux = 0;
        int max = this.sentence.getText().length();

        while (i < max) {
            for (Label label : allLabels) {
                if ((label.getInstantiationType().getId() == 1) && (i == label.getStartChar())) {
                    if (i > iaux) {
                        tags.addAll(singleTags(iaux,i));
                    }
                    String s = label.getLabelType().getFrameElement().getName();
                    // regular tag (associated to a frame element)
                    Tag t = new Tag(this,s, findTargets(i, label.getEndChar() + 1));
                    t.setColor(Utils.allColors.get(allFE.indexOf(s)));
                    t.setFrameElement(label.getLabelType().getFrameElement());
                    tags.add(t);
                    i = label.getEndChar();
                    iaux = i + 1;
                    break;
                }
            }

            i ++;
        }
        if (iaux < max) {
            tags.addAll(singleTags(iaux,max));
        }
        // add all of the frame elements annotated but not instantiated in the sentence
        for (Label label : allLabels) {
            if (label.getInstantiationType().getId() != 1) {
                String word = label.getInstantiationType().getName();
                String el = label.getLabelType().getFrameElement().getName();
                Tag tag = new Tag(this,el, new Target(word));
                tag.setFrameElement(label.getLabelType().getFrameElement());
                tag.setColor(Utils.allColors.get(allFE.indexOf(el)));
                tags.add(tag);
            }
        }
        this.elements = tags;
    }

    /**
     * Creates the focus list, a list of all the target labels for the chosen annotation set.
     * If the annotation is "fullText", allTarget list contains all of the label targets for every annotation set
     * associated to the sentence. Otherwise, allTarget list equals the focus list.
     */
    private void getAllTargetAndFocus() {
        List<Label> allLabels = new ArrayList<Label>();
        this.focus = new ArrayList<Label>();
        if (displayedAnnotationSet != null) {
            for (Layer layer : displayedAnnotationSet.getLayers()){
                if (layer.getLayerType().getId() == 2) {
                    this.focus.addAll(layer.getLabels());
                }
            }
        }
        if (this.fullText) {
            for (AnnotationSet annoSet : sentence.getAnnotationSets()) {
                if (!isEmptyAnnoSet(annoSet)) {
                    for (Layer layer : annoSet.getLayers()){
                        if (layer.getLayerType().getId() == 2) {
                            allLabels.addAll(layer.getLabels());
                        }
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

    /**
     * Checks if an annotation set is empty.
     */
    private boolean isEmptyAnnoSet (AnnotationSet annotationSet) {
        for (Layer layer : annotationSet.getLayers()){
            if (layer.getLayerType().getId() == 1) {
               if (layer.getLabels().size() > 0) {
                   return false;
               }
            }
        }
        return true;
    }

    /**
     * Inserts a label in a list in ascending order for the startChar parameter.
     */
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

    /**
     * Creates a list of targets for frame elements type tags.
     * For every label in the focus list, the background color is changed.
     */
    private List<Target> findTargets (int start, int end) {
        String text = this.sentence.getText();
        List<Target> result = new ArrayList<Target>();
        int i = start;
        int aux = i;
        while (i < end) {
            for (Label label : this.allTargets) {
                if (label.getStartChar() == i) {
                    if (i > aux) {
                        result.add(new Target(text.substring(aux,i)));
                    }
                    Target t = new Target(text.substring(i,label.getEndChar() + 1));
                    t.setValid(true);
                    for (Label on : this.focus) {
                        if (label.getStartChar() == on.getStartChar() && on.getEndChar() == label.getEndChar()) {
                            t.setBkg("#66BB6A");
                        }
                    }
                    t.setAnnotationSet(label.getLayer().getAnnotationSet());
                    result.add(t);
                    i = label.getEndChar();
                    aux = i + 1;
                }
            }
            i ++;
        }
        if (aux < end) {
            result.add(new Target(text.substring(aux,end)));
        }
        return result;
    }

    /**
     * Creates a list of tags that can be "blank" or "target" tags.
     */
    private List<Tag> singleTags (int start, int end) {
        List<Tag> tags = new ArrayList<Tag>();

        String text = this.sentence.getText();
        int i = start;
        int aux = i;
        while (i < end) {
            for (Label label : this.allTargets) {
                if (label.getStartChar() == i) {
                    if (i > aux) {
                        for (int x = aux; x < i; x ++) {
                            Tag tag = new Tag(this,".", new Target(sentence.getText().substring(x, x+1)));
                            if (!tag.isEmpty()) {
                                tags.add(tag);
                            }
                        }
                    }
                    Target t = new Target(text.substring(i,label.getEndChar() + 1));
                    t.setValid(true);
                    for (Label on : this.focus) {
                        if (label.getStartChar() == on.getStartChar() && on.getEndChar() == label.getEndChar()) {
                            t.setBkg("#66BB6A");
                        }
                    }
                    t.setAnnotationSet(label.getLayer().getAnnotationSet());
                    tags.add(new Tag(this,".",t));
                    i = label.getEndChar();
                    aux = i + 1;
                }
            }
            i ++;
        }
        if (aux < end) {
            for (int x = aux; x < end; x ++) {
                Tag tag = new Tag(this,".", new Target(sentence.getText().substring(x, x+1)));
                if (!tag.isEmpty()) {
                    tags.add(tag);
                }
            }
        }
        return tags;
    }

    public AnnotationSet getDisplayedAnnotationSet() {
        return displayedAnnotationSet;
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

    public Sentence getSentence() {
        return sentence;
    }

    public boolean isFullText() {
        return fullText;
    }
}
