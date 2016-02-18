package jp.keio.jfn.wat.controller;

import jp.keio.jfn.wat.domain.AnnotationSet;
import jp.keio.jfn.wat.domain.Label;
import jp.keio.jfn.wat.domain.Layer;
import jp.keio.jfn.wat.domain.LexUnit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jfn on 2/16/16.
 */
public class AnnotationDisplay {

    public static List<Tag> getAnnotation(AnnotationSet annotationSet, List<String> allFE, boolean single) {
        SentenceDisplay display = new SentenceDisplay(annotationSet.getSentence());
        display.setDisplayedAnnotationSet(annotationSet);
        List<Pos> positions = getPosTargets(display, single);
        List<Tag> tags = new ArrayList<Tag>();
        List<Label> allTargets = new ArrayList<Label>();
        List<Label> allLabels = new ArrayList<Label>();
        for (Layer layer : annotationSet.getLayers()){
            if (layer.getLayerType().getId() == 1) {
                allLabels.addAll(layer.getLabels());
            } else  if (layer.getLayerType().getId() == 2) {
                allTargets.addAll(layer.getLabels());
            }
        }
        int i = 0;
        int iaux = 0;
        int max = annotationSet.getSentence().getText().length();

        while (i < max) {
            for (Label label : allLabels) {
                if ((label.getInstantiationType().getId() == 1) && (i == label.getStartChar())) {
                    if (i > iaux) {
                        tags.add(getTagMultipleTargets(annotationSet.getSentence().getText(), iaux, i, positions));
                    }
                    String s = label.getLabelType().getFrameElement().getName();
                    Tag t = new Tag(s, findTargets(annotationSet.getSentence().getText(), i, label.getEndChar() + 1, positions, allTargets));
                    t.setColor(Utils.allColors.get(allFE.indexOf(s)));
                    t.setFrameElement(label.getLabelType().getFrameElement());
                    tags.add(t);
                    i = label.getEndChar();
                    iaux = i + 1;
                    break;
                }
            }
            for (Label label : allTargets) {
                if ((label.getInstantiationType().getId() == 1) && (i == label.getStartChar())) {
                    if (i > iaux) {
                        tags.add(getTagMultipleTargets(annotationSet.getSentence().getText(), iaux, i, positions));
                    }
                    tags.add(new Tag("", findTargets(annotationSet.getSentence().getText(), i, label.getEndChar() + 1, positions, allTargets)));
                    i = label.getEndChar();
                    iaux = i + 1;
                    break;
                }
            }

            i ++;
        }
        if (iaux < max) {
            tags.add(getTagMultipleTargets(annotationSet.getSentence().getText(), iaux, max, positions));
        }
        for (Label label : allLabels) {
            if (label.getInstantiationType().getId() != 1) {
                String word = label.getInstantiationType().getName();
                String el = label.getLabelType().getFrameElement().getName();
                Tag tag = new Tag(el, new ArrayList<Target>(Arrays.asList(new Target(word))));
                tag.setFrameElement(label.getLabelType().getFrameElement());
                tag.setColor(Utils.allColors.get(allFE.indexOf(el)));
                tags.add(tag);
            }
        }
        return tags;
    }

    public static List<Pos> getPosTargets(SentenceDisplay sentenceDisplay, boolean single) {
        List<Pos> list = new ArrayList<Pos>();
        List<Label> allLabels = new ArrayList<Label>();
        if (single) {
            for (Layer layer : sentenceDisplay.getDisplayedAnnotationSet().getLayers()){
                if (layer.getLayerType().getId() == 2) {
                    allLabels.addAll(layer.getLabels());
                }
            }
        } else {
            for (AnnotationSet annoSet : sentenceDisplay.getSentence().getAnnotationSets()) {
                for (Layer layer : annoSet.getLayers()){
                    if (layer.getLayerType().getId() == 2) {
                        allLabels.addAll(layer.getLabels());
                    }
                }
            }
        }

        for (Label label : allLabels) {
            Pos pos = new Pos(label.getStartChar(), label.getEndChar(), label.getLayer().getAnnotationSet());
            insertPos(list,pos);
        }
        return list;
    }

    private static void insertPos (List<Pos> positions, Pos x) {
        int i = 0;
        while (i < positions.size()) {
            if (positions.get(i).getStart() > x.getStart()) {
                break;
            }
            i ++;
        }
        positions.add(i,x);
    }

    private static List<Target> findTargets (String text, int start, int end, List<Pos> pos, List<Label> focus) {
        List<Target> result = new ArrayList<Target>();
        int i = start;
        int aux = i;
        while (i < end) {
            for (Pos position : pos) {
                if (position.getStart() == i) {
                    if (i > aux) {
                        result.add(new Target(text.substring(aux,i)));
                    }
                    Target t = new Target(text.substring(i,position.getEnd() + 1));
                    t.setValid(true);
                    for (Label label : focus) {
                        if (label.getStartChar() == position.getStart() && position.getEnd() == label.getEndChar()) {
                            t.setBkg("#66BB6A");
                        }
                    }
                    t.setAnnotationSet(position.getAnnotationSet());
                    result.add(t);
                    i = position.getEnd();
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

    public static Tag getTagMultipleTargets(String text, int start, int end, List<Pos> pos) {
        List<Target> result = new ArrayList<Target>();
        int i = start;
        int aux = i;
        while (i < end) {
            for (Pos position : pos) {
                if (position.getStart() == i) {
                    if (i > aux) {
                        Target t = new Target(text.substring(aux,i));
                        result.add(t);
                    }
                    Target t = new Target(text.substring(i,position.getEnd() + 1));
                    t.setValid(true);
                    t.setAnnotationSet(position.getAnnotationSet());
                    result.add(t);
                    i = position.getEnd();
                    aux = i + 1;
                }
            }
            i ++;
        }
        if (aux < end) {
            Target t = new Target(text.substring(aux,end));
            result.add(t);
        }
        return new Tag("",result);
    }
}
