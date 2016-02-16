package jp.keio.jfn.wat.controller;

import jp.keio.jfn.wat.domain.AnnotationSet;
import jp.keio.jfn.wat.domain.Sentence;

/**
 * Created by jfn on 2/16/16.
 */
public class SentenceDisplay {
    private Sentence sentence;
    private AnnotationSet displayedAnnotationSet;

    public SentenceDisplay(Sentence sentence) {
        this.sentence = sentence;
    }

    public AnnotationSet getDisplayedAnnotationSet() {
        return displayedAnnotationSet;
    }

    public Sentence getSentence() {
        return sentence;
    }

    public void setDisplayedAnnotationSet(AnnotationSet displayedAnnotationSet) {
        this.displayedAnnotationSet = displayedAnnotationSet;
    }

    public void setSentence(Sentence sentence) {
        this.sentence = sentence;
    }
}
