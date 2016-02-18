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

    public static void getAnnotation(SentenceDisplay display, List<String> allFE) {
        AnnotationSet annotationSet = display.getDisplayedAnnotationSet();
        String sentenceText = display.getSentence().getText();
        List<Tag> tags = new ArrayList<Tag>();
        List<Label> allLabels = new ArrayList<Label>();
        if (annotationSet != null) {
            for (Layer layer : annotationSet.getLayers()){
                if (layer.getLayerType().getId() == 1) {
                    allLabels.addAll(layer.getLabels());
                }
            }
        }
        int i = 0;
        int iaux = 0;
        int max = sentenceText.length();

        while (i < max) {
            for (Label label : allLabels) {
                if ((label.getInstantiationType().getId() == 1) && (i == label.getStartChar())) {
                    if (i > iaux) {
                        tags.add(new Tag("", findTargets(sentenceText, iaux, i, display.getAllTargets(), display.getFocus())));
                    }
                    String s = label.getLabelType().getFrameElement().getName();
                    Tag t = new Tag(s, findTargets(sentenceText, i, label.getEndChar() + 1, display.getAllTargets(), display.getFocus()));
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
            tags.add(new Tag("",findTargets(sentenceText, iaux, max, display.getAllTargets(), display.getFocus())));
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
        display.setElements(tags);
    }

    private static List<Target> findTargets (String text, int start, int end, List<Label> all, List<Label> focus) {
        List<Target> result = new ArrayList<Target>();
        int i = start;
        int aux = i;
        while (i < end) {
            for (Label label : all) {
                if (label.getStartChar() == i) {
                    if (i > aux) {
                        result.add(new Target(text.substring(aux,i)));
                    }
                    Target t = new Target(text.substring(i,label.getEndChar() + 1));
                    t.setValid(true);
                    for (Label l : focus) {
                        if (label.getStartChar() == l.getEndChar() && l.getEndChar() == label.getEndChar()) {
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
}
