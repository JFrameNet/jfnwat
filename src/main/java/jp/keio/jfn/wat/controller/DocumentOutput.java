package jp.keio.jfn.wat.controller;

import jp.keio.jfn.wat.domain.*;

import java.util.ArrayList;
import java.util.List;


/**
 * This class defines all the elements to be retrieved for a Document object.
 */
public class DocumentOutput {

    private List<Sentence> sentences  = new ArrayList<Sentence>();

    private List<SentenceDisplay> sentenceDisplay = new ArrayList<SentenceDisplay>();

    private List<SentenceOutput> selectedSentences = new ArrayList<SentenceOutput>();

    private String name;

    private List<String> allFE = new ArrayList<String>();

    /**
     * Instantiation with a Document object.
     */
    public DocumentOutput(Document document) {
        for (Paragraph paragraph : document.getParagraphs()) {
            sentences.addAll(paragraph.getSentences());
        }
        for (Sentence sentence : sentences) {
            sentenceDisplay.add(new SentenceDisplay(sentence));
        }
        this.name = document.getName();
        findALlFESentences();
    }

    /**
     * Creates a list of all the possible frame elements for any annotation set for all the sentences in the
     * document.
     * This list will be used to set frame elements colors when displaying annotations.
     */
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

    /**
     * Returns one SentenceOuput composed of all the sentences of the document concatenated. Looks for all the target
     * LUs in the sentences.
     *
     * @param breakLine this variable sets the line break. It depends on the screen width.
     * @return a unique SentenceOutput in which all the targets LU are a separated ElementTag.
     */
    public SentenceOutput processSentences (int breakLine) {
        findALlFESentences();
        String allText = "";
        List<Label> allLabels = new ArrayList<Label>();
        List<List<ElementTag>> list = new ArrayList<List<ElementTag>>();
        // Concatenates all sentences for this document and lists all target LUs
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
        // rank is the line number
        int rank = 0;
        int imin = 0;
        int iaux =0;
        int imax = allText.length();
        List<ElementTag> line;
        // Loop on the whole text
        while (imin + rank*breakLine < imax) {
            line = new ArrayList<ElementTag>();
            // Loop for one line
            while ((imin + rank*breakLine < imax)&& (imin < breakLine)) {
                for (Label label : allLabels) {
                    if (label.getInstantiationType().getId() == 1){
                        // Adds the length of all previous sentences to the sentence from which the label is extracted
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

    public String getName() {
        return name;
    }

    public List<SentenceDisplay> getSentenceDisplay() {
        return sentenceDisplay;
    }
}
