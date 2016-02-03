package jp.keio.jfn.wat.controller;

import jp.keio.jfn.wat.domain.AnnotationSet;
import jp.keio.jfn.wat.domain.FrameElement;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a frame elements group realization. It consists of a list of frame elements and a list
 * of PatterEntry objects, which correspond to the different realizations of the frame elements.
 */
public class FEGroupRealization {
    private List<FrameElement> frameElements;

    private List<PatternEntry> patterns = new ArrayList<PatternEntry>();

    /**
     * Initialization
     */
    public FEGroupRealization(List<FrameElement> listFE, List<PatternEntry> listPatterns) {
        frameElements = listFE;
        patterns = listPatterns;
    }

    /**
     * Checks is two lists of frame elements contains the same elements.
     *
     * @param group a list of frame elements to be compared to the group realization list.
     * @return true if all the frame elements of the input list are in the group list.
     */
    public boolean equalsFEGroup (List<FrameElement> group) {
        if (group.size() == frameElements.size()) {
            for (FrameElement frameElement : group) {
                boolean hasFE = false;
                for (FrameElement actualfe : frameElements) {
                    if (actualfe.getName().equals(frameElement.getName())) {
                        hasFE = true;
                        break;
                    }
                }
                if (!hasFE) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Retrieves all the annotation sets associated to every patterEntry object of the group realization
     */
    public List<AnnotationSet> getAllAnnotations() {
        List<AnnotationSet> allAnnotations = new ArrayList<AnnotationSet>();
        for (PatternEntry patternEntry : patterns) {
            allAnnotations.addAll(patternEntry.getAnnoSet());
        }
        return allAnnotations;
    }

    /**
     * Retrieves the total number of realizations for the group (addind the total realizations for every pattern entry).
     */
    public int getTotalOccurences () {
        int result = 0;
        for (PatternEntry patternEntry : patterns) {
            result += patternEntry.getAnnoSet().size();
        }
        return result;
    }

    public void addPattern (PatternEntry pattern) {
        patterns.add(pattern);
    }

    public List<FrameElement> getFrameElements () {
        return frameElements;
    }

    public void setFrameElements(List<FrameElement> list) {
        frameElements = list;
    }

    public List<PatternEntry> getPatterns() {
        return patterns;
    }
}
