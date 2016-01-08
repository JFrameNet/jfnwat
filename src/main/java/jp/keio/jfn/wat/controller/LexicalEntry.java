package jp.keio.jfn.wat.controller;

import jp.keio.jfn.wat.domain.FrameElement;
import jp.keio.jfn.wat.domain.Sentence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jfn on 1/7/16.
 */
public class LexicalEntry {

    private FrameElement frameElement;
    private List<Sentence> occurence;
    private HashMap<String, List<Sentence>> realizations;

    public LexicalEntry(FrameElement n) {
        frameElement = n;
        occurence = new ArrayList<Sentence>();
        realizations = new HashMap<String, List<Sentence>>();
    }

    public void setFrameElement (FrameElement fe) {
        frameElement = fe;
    }

    public FrameElement getFrameElement() {
        return frameElement;
    }

    public void setOccurence(List<Sentence> list){
        occurence = list;
    }

    public List<Sentence> getOccurence() {
        return occurence;
    }

    public HashMap<String, List<Sentence>> getRealizations() {
        return realizations;
    }

    public void setRealizations(HashMap<String , List<Sentence>> list) {
        realizations = list;
    }

    public void addOccurence (Sentence sentence) {
        occurence.add(sentence);
    }
}
