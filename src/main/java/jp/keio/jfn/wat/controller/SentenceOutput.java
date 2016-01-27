package jp.keio.jfn.wat.controller;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jfn on 1/13/16.
 */
public class SentenceOutput {

    private List<List<ElementTag>> elements;

    private String text;

    private int id;

    public SentenceOutput(List<List<ElementTag>> list, String text) {
        this.elements = list;
        this.text = text;
    }

    public void newLine (List<ElementTag> list) {
        this.elements.add(list);
    }

    public List<List<ElementTag>> getElements() {
        return elements;
    }

    public String getText() {
        return text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
