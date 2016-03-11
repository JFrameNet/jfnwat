package jp.keio.jfn.wat.webreport;

import jp.keio.jfn.wat.annotation.AnnotatedSentence;
import jp.keio.jfn.wat.annotation.AnnotationDisplay;
import jp.keio.jfn.wat.domain.*;

import java.util.ArrayList;
import java.util.List;


/**
 * This class defines all the elements to be retrieved for a Document object.
 */
public class DocumentOutput {

    private List<Sentence> sentences  = new ArrayList<Sentence>();

    private List<AnnotatedSentence> annotatedSentences = new ArrayList<AnnotatedSentence>();

    private String name;

    private List<String> allFE = new ArrayList<String>();

    /**
     * Instantiation with a Document object.
     * It creates a list of AnnotatedSentence objects, associated to every sentence of the document.
     */
    public DocumentOutput(Document document) {
        for (Paragraph paragraph : document.getParagraphs()) {
            sentences.addAll(paragraph.getSentences());
        }
        findALlFESentences();
        for (Sentence sentence : sentences) {
            List<AnnotationDisplay> annotationDisplays = new ArrayList<AnnotationDisplay>();
            annotationDisplays.add(new AnnotationDisplay(sentence, null, true, allFE));
            for (AnnotationSet annotationSet : sentence.getAnnotationSets()) {
                boolean empty = true;
                for (Layer layer : annotationSet.getLayers()){
                    if (layer.getLayerType().getId() == 1) {
                        if (layer.getLabels().size() > 0) {
                            empty = false;
                            break;
                        }
                    }
                }
                if (!empty) {
                    annotationDisplays.add(new AnnotationDisplay(sentence, annotationSet, true, allFE));
                }
            }
            annotatedSentences.add(new AnnotatedSentence(annotationDisplays, (sentences.indexOf(sentence) % 2)));
        }
        this.name = document.getName();
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

    public String getName() {
        return name;
    }

    public List<AnnotatedSentence> getAnnotatedSentences() {
        return annotatedSentences;
    }
}
