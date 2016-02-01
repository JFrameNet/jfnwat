package jp.keio.jfn.wat.controller;

import jp.keio.jfn.wat.domain.*;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by jfn on 1/27/16.
 */
public class DocumentOutput {
    private List<Sentence> sentences  = new ArrayList<Sentence>();

    private List<SentenceOutput> selectedSentences = new ArrayList<SentenceOutput>();

    private String name;

    public DocumentOutput(Document document) {
        for (Paragraph paragraph : document.getParagraphs()) {
            sentences.addAll(paragraph.getSentences());
        }
        this.name = document.getName();
    }

    private List<String> allFE = new ArrayList<String>();

    public String getName() {
        return name;
    }

    private void findALlFESentences() {
        for (Sentence sentence : this.sentences) {
            for (AnnotationSet annoSet :sentence.getAnnotationSets()) {
                for (Layer layer : annoSet.getLayers()){
                    if (layer.getLayerType().getId() == 1) {
                        for (Label label : layer.getLabels()) {
                            String fe = label.getLabelType().getFrameElement().getName();
                            if (!this.allFE.contains(fe)) {
                                this.allFE.add(fe);
                            }
                        }
                    }
                }
            }
        }
    }

    public SentenceOutput processSentences (int breakLine) {
        findALlFESentences();
        String allText = "";
        List<Label> allLabels = new ArrayList<Label>();
        List<List<ElementTag>> list = new ArrayList<List<ElementTag>>();
        for (Sentence sentence : this.sentences) {
            allText = allText + sentence.getText();
            for (AnnotationSet annoSet :sentence.getAnnotationSets()) {
                for (Layer layer : annoSet.getLayers()){
                    if (layer.getLayerType().getId() == 2) {
                        allLabels.addAll(layer.getLabels());
                    }
                }
            }
        }
        int rank = 0;
        int imin = 0;
        int iaux =0;
        int imax = allText.length();
        List<ElementTag> line;
        while (imin + rank*breakLine < imax) {
            line = new ArrayList<ElementTag>();
            while ((imin + rank*breakLine < imax)&& (imin < breakLine)) {
                for (Label label : allLabels) {
                    if (label.getInstantiationType().getId() == 1){
                        int index = this.sentences.indexOf(label.getLayer().getAnnotationSet().getSentence());
                        int previousSentence = 0;
                        for (int i = 0; i < index; i ++) {
                            previousSentence += this.sentences.get(i).getText().length();
                        }
                        if (imin + rank*breakLine == label.getStartChar() + previousSentence) {
                            if (imin > iaux) {
                                String wordEmpty = allText.substring(iaux + rank*breakLine, imin + rank*breakLine);
                                ElementTag el = new ElementTag(wordEmpty, "");
                                el.setLU(false);
                                line.add(el);
                            }
                            int border = label.getEndChar() + previousSentence+ 1;
                            String word = allText.substring(label.getStartChar() + previousSentence, border);
                            ElementTag el = new ElementTag(word, label.getLayer().getAnnotationSet().getLexUnit().getFrame().getName());
                            el.setAnnotationSet(label.getLayer().getAnnotationSet());
                            el.setLU(true);
                            line.add(el);

                            imin = border - 1 - rank*breakLine;
                            iaux = imin + 1;
                            break;
                        }
                    }
                }
                imin ++;
            }
            if (imin > iaux) {
                if (imin + rank*breakLine < imax) {
                    String wordEmpty = allText.substring(iaux + rank*breakLine, imin + rank*breakLine);
                    ElementTag el = new ElementTag(wordEmpty, "");
                    el.setLU(false);
                    line.add(el);
                } else {
                    String wordEmpty = allText.substring(iaux + rank*breakLine, imax);
                    ElementTag el = new ElementTag(wordEmpty, "");
                    el.setLU(false);
                    line.add(el);
                }
            }
            list.add(line);
            rank ++;
            imin = 0;
            iaux = 0;
        }
        List<ElementTag> lastLine = list.get(list.size() -1);
        int size = 0;
        for (ElementTag elementTag1 : lastLine) {
            size +=  Math.max(elementTag1.getWord().length(), elementTag1.getTag().length());
        }
        int complete = breakLine - size;
        String space = "__";
        for (int x = 0; x < complete; x ++ ) {
            space = space.concat("_");
        }
        ElementTag el = new ElementTag(space, "");
        el.setWordColor("white");
        el.setLU(false);
        lastLine.add(el);
        return new SentenceOutput(list,allText);
    }

    public List<SentenceOutput> getSelectedSentences() {
        return selectedSentences;
    }

    public List<String> getAllFE() {
        return allFE;
    }
}
