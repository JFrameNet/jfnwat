package jp.keio.jfn.wat.controller;

import jp.keio.jfn.wat.domain.AnnotationSet;

/**
 * Created by jfn on 2/16/16.
 */
public class Pos {
    private int start;
    private int end;
    private AnnotationSet annotationSet;

    public Pos(int a, int b, AnnotationSet anno) {
        this.start = a;
        this.end = b;
        this.annotationSet = anno;
    }

    public int getEnd() {
        return end;
    }

    public int getStart() {
        return start;
    }

    public AnnotationSet getAnnotationSet() {
        return annotationSet;
    }

    public void setAnnotationSet(AnnotationSet annotationSet) {
        this.annotationSet = annotationSet;
    }
}
