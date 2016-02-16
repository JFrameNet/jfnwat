package jp.keio.jfn.wat.controller;

import jp.keio.jfn.wat.domain.AnnotationSet;
import jp.keio.jfn.wat.domain.Label;
import jp.keio.jfn.wat.domain.Layer;
import jp.keio.jfn.wat.domain.Sentence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jfn on 2/16/16.
 */
public class AnnotationDisplay {

    public static List<Target> getTargetsSentence(Sentence sentence) {
        List<Target> targets = new ArrayList<Target>();
        List<Label> allLabels = new ArrayList<Label>();
        for (AnnotationSet annoSet :sentence.getAnnotationSets()) {
            for (Layer layer : annoSet.getLayers()){
                if (layer.getLayerType().getId() == 2) {
                    allLabels.addAll(layer.getLabels());
                }
            }
        }
        int i = 0;
        int aux = 0;
        int max = sentence.getText().length();

        while (i < max) {
            for (Label label : allLabels) {
                if (i == label.getStartChar()) {
                    if (i > aux) {
                        String wordEmpty = sentence.getText().substring(aux, i);
                        Target target = new Target(wordEmpty);
                        targets.add(target);
                    }
                    String word = sentence.getText().substring(i, label.getEndChar() + 1);
                    Target target = new Target(word);
                    target.setValid(true);
                    target.setAnnotationSet(label.getLayer().getAnnotationSet());
                    targets.add(target);
                    i = label.getEndChar();
                    aux = i + 1;
                    break;
                }
            }
            i ++;
        }
        if (aux < max) {
            String wordEmpty = sentence.getText().substring(aux, max);
            Target target = new Target(wordEmpty);
            targets.add(target);
        }
        return targets;
    }

    public static List<Tag> getAnnotation(AnnotationSet annotationSet, List<String> allFE) {
        List<Pos> positions = getPosTargets(annotationSet.getSentence());
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
                        tags.add(new Tag("", findTargets(annotationSet.getSentence().getText(), iaux, i,positions, false )));
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
            tags.add(new Tag("", findTargets(annotationSet.getSentence().getText(), iaux, max,positions, false)));
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

    private static List<Pos> getPosTargets(Sentence sentence) {
        List<Pos> list = new ArrayList<Pos>();
        List<Label> allLabels = new ArrayList<Label>();
        for (AnnotationSet annoSet :sentence.getAnnotationSets()) {
            for (Layer layer : annoSet.getLayers()){
                if (layer.getLayerType().getId() == 2) {
                    allLabels.addAll(layer.getLabels());
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
                    t.setFocus(target);
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
}
