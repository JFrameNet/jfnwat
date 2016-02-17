package jp.keio.jfn.wat.controller;

import jp.keio.jfn.wat.domain.AnnotationSet;
import jp.keio.jfn.wat.domain.Label;
import jp.keio.jfn.wat.domain.Layer;

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
        Layer layerFE = null;
        Layer layerTarget = null;
        for (Layer layer : annotationSet.getLayers()){
            if (layer.getLayerType().getId() == 1) {
                layerFE = layer;
            } else  if (layer.getLayerType().getId() == 2) {
                layerTarget = layer;
            }
            if ((layerFE != null) && (layerTarget != null)) {
                break;
            }
        }
        List<Label> allLabels;
        try {
            assert layerFE != null;
            allLabels = layerFE.getLabels();
            allLabels.addAll(layerTarget.getLabels());
        } catch (NullPointerException e) {
            System.err.print("No frame elements");
            return new ArrayList<Tag>();
        }

        int i = 0;
        int iaux = 0;
        int max = annotationSet.getSentence().getText().length();

        while (i < max) {
            for (Label label : allLabels) {
                if ((label.getInstantiationType().getId() == 1) && (i == label.getStartChar())) {
                    if (i > iaux) {
                        tags.addAll(getTargets(annotationSet.getSentence().getText(), iaux, i, positions));
                    }
                    if (label.getLabelType().getLayerType().getId() == 1) {
                        String s = label.getLabelType().getFrameElement().getName();
                        Tag t = new Tag(s, findTargets(annotationSet.getSentence().getText(), i, label.getEndChar() + 1, positions, false));
                        t.setColor(Utils.allColors.get(allFE.indexOf(s)));
                        t.setFrameElement(label.getLabelType().getFrameElement());
                        tags.add(t);
                    } else {
                        tags.add(new Tag("", findTargets(annotationSet.getSentence().getText(), i, label.getEndChar() + 1, positions, true)));
                    }

                    i = label.getEndChar();
                    iaux = i + 1;
                    break;
                }
            }
            i ++;
        }
        if (iaux < max) {
            tags.addAll(getTargets(annotationSet.getSentence().getText(), iaux, max, positions));
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

    private static List<Target> findTargets (String text, int start, int end, List<Pos> pos, boolean target) {
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
                    t.setBkg("#66BB6A");
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

    public static List<Tag> getTargets(String text, int start, int end, List<Pos> pos) {
        List<Tag> result = new ArrayList<Tag>();
        int i = start;
        int aux = i;
        while (i < end) {
            for (Pos position : pos) {
                if (position.getStart() == i) {
                    if (i > aux) {
                        Target t = new Target(text.substring(aux,i));
                        result.add(new Tag("", new ArrayList<Target>(Arrays.asList(t))));
                    }
                    Target t = new Target(text.substring(i,position.getEnd() + 1));
                    t.setValid(true);
                    t.setAnnotationSet(position.getAnnotationSet());
                    result.add(new Tag("", new ArrayList<Target>(Arrays.asList(t))));
                    i = position.getEnd();
                    aux = i + 1;
                }
            }
            i ++;
        }
        if (aux < end) {
            Target t = new Target(text.substring(aux,end));
            result.add(new Tag("", new ArrayList<Target>(Arrays.asList(t))));
        }
        return result;
    }
}
