package jp.keio.jfn.wat.controller;

import jp.keio.jfn.wat.domain.AnnotationSet;
import jp.keio.jfn.wat.domain.FrameElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jfn on 1/12/16.
 */
public class FEGroupRealization {
    private List<FrameElement> frameElements;

    private List<PatternEntry> patterns = new ArrayList<PatternEntry>();

    public FEGroupRealization(List<FrameElement> listFE, List<PatternEntry> listPatterns) {
        frameElements = listFE;
        patterns = listPatterns;
    }

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

    public void setPatterns(List<PatternEntry> list) {
        patterns = list;
    }

    public List<AnnotationSet> getAllAnnotations() {
        List<AnnotationSet> allAnnotations = new ArrayList<AnnotationSet>();
        for (PatternEntry patternEntry : patterns) {
            allAnnotations.addAll(patternEntry.getAnnoSet());
        }
        return allAnnotations;
    }
}
