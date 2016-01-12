package jp.keio.jfn.wat.controller;

import jp.keio.jfn.wat.domain.FrameElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jfn on 1/12/16.
 */
public class FERealization {

    public FERealization(FrameElement element, PatternEntry patternEntry) {
        frameElement = element;
        patterns = new ArrayList<PatternEntry>();
        patterns.add(patternEntry);

    }

    public int getTotalOccurences () {
        int result = 0;
        for (PatternEntry patternEntry : patterns) {
            result += patternEntry.getAnnoSet().size();
        }
        return result;
    }

    private FrameElement frameElement;

    private List<PatternEntry> patterns;

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
}
