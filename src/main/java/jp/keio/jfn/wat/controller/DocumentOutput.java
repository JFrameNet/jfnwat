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
            sentenceDisplay.add(new SentenceDisplay(sentence, null, true));
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
