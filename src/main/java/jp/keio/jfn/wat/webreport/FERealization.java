package jp.keio.jfn.wat.webreport;

import jp.keio.jfn.wat.webreport.domain.AnnotationSet;
import jp.keio.jfn.wat.webreport.domain.FrameElement;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a frame element realization. It consists of one FrameElement and a lis of PatterEntry objects.
 */
public class FERealization {
    private FrameElement frameElement;

    private List<PatternEntry> patterns;

    /**
     * Initialization
     */
    public FERealization(FrameElement element, PatternEntry patternEntry) {
        frameElement = element;
        patterns = new ArrayList<PatternEntry>();
        patterns.add(patternEntry);

    }

    public void addPattern (PatternEntry pattern) {
        patterns.add(pattern);
    }

    public FrameElement getFrameElement () {
        return frameElement;
    }

    public void setFrameElement(FrameElement fe) {
        frameElement = fe;
    }

    public List<PatternEntry> getPatterns() {
        return patterns;
    }

    public void setPatterns(List<PatternEntry> list) {
        patterns = list;
    }

    /**
     * Retrieves all the annotations associated to the frame element.
     */
    public List<AnnotationSet> getAllAnnotations() {
        List<AnnotationSet> allAnnotations = new ArrayList<AnnotationSet>();
        for (PatternEntry patternEntry : patterns) {
            allAnnotations.addAll(patternEntry.getAnnoSet());
        }
        return allAnnotations;
    }
}
