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
    public List<Sentence> getSentences() {
        return sentences;
    }

    public void setSentences(List<Sentence> sentences) {
        this.sentences = sentences;
    }

    public String getName() {
        return name;
    }

    public List<SentenceOutput> processSentences () {
        List<SentenceOutput> sentenceOutputs = new ArrayList<SentenceOutput>();
        for (Sentence sentence : this.sentences) {
            List<Label> allLabels = new ArrayList<Label>();
            for (AnnotationSet annoSet :sentence.getAnnotationSets()) {
                for (Layer layer : annoSet.getLayers()){
                    if (layer.getLayerType().getId() == 2) {
                        allLabels.addAll(layer.getLabels());
                    }
                }
            }
            List<List<ElementTag>> list = new ArrayList<List<ElementTag>>();
            int breakLine = 85;
            int rank = 0;
            int imin = 0;
            int iaux =0;
            int imax = sentence.getText().length();
            List<ElementTag> line;
            while (imin + rank*breakLine < imax) {
                line = new ArrayList<ElementTag>();
                while ((imin + rank*breakLine < imax)&& (imin < breakLine)) {
                    for (Label label : allLabels) {
                        if (label.getInstantiationType().getId() == 1){
                            if (imin + rank*breakLine == label.getStartChar()) {
                                if (imin > iaux) {
                                    String wordEmpty = sentence.getText().substring(iaux + rank*breakLine, imin + rank*breakLine);
                                    ElementTag el = new ElementTag(wordEmpty, "");
                                    el.setLU(false);
                                    line.add(el);
                                }
                                int border = label.getEndChar() + 1;
                                String word = sentence.getText().substring(label.getStartChar(), border);
                                ElementTag el = new ElementTag(word, label.getLayer().getAnnotationSet().getLexUnit().getFrame().getName());
                                el.setAnnotationSet(label.getLayer().getAnnotationSet());
                                el.setLU(true);
                                el.setTagColor("#66BB6A");
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
                        String wordEmpty = sentence.getText().substring(iaux + rank*breakLine, imin + rank*breakLine);
                        ElementTag el = new ElementTag(wordEmpty, "");
                        el.setLU(false);
                        line.add(el);
                    } else {
                        String wordEmpty = sentence.getText().substring(iaux + rank*breakLine, imax);
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
            SentenceOutput output = new SentenceOutput(list, sentence.getText());
            output.setId(sentenceOutputs.size());
            sentenceOutputs.add(output);
        }
        return sentenceOutputs;
    }

    public List<SentenceOutput> getSelectedSentences() {
        return selectedSentences;
    }
}
