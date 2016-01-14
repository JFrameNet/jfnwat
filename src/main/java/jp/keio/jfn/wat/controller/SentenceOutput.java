package jp.keio.jfn.wat.controller;

import java.util.List;

/**
 * Created by jfn on 1/13/16.
 */
public class SentenceOutput {

    private List<ElementTag> elements;

    public SentenceOutput(List<ElementTag> list) {
        this.elements = list;
    }

    public List<ElementTag> getElements () {
        return elements;
    }
}
