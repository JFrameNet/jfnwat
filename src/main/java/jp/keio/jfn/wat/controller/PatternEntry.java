package jp.keio.jfn.wat.controller;

import jp.keio.jfn.wat.domain.AnnotationSet;
import jp.keio.jfn.wat.domain.FrameElement;
import jp.keio.jfn.wat.domain.Label;
import jp.keio.jfn.wat.domain.Layer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jfn on 1/12/16.
 */
public class PatternEntry {

    private List<LayerTriplet> valenceUnits = new ArrayList<LayerTriplet>();

    private List<AnnotationSet> annoSet  = new ArrayList<AnnotationSet>();

    public boolean hasValence (LayerTriplet valence) {
        int instantiationType = valence.getLabelFE().getInstantiationType().getId();
        if (instantiationType == 1) {
            for (LayerTriplet triplet : valenceUnits) {
                if ((valence.getLabelFE().getLabelType().getFrameElement().getId() == triplet.getLabelFE().getLabelType().getFrameElement().getId())
                        && (valence.getLabelPT().getLabelType().getMiscLabel().getId() == triplet.getLabelPT().getLabelType().getMiscLabel().getId())
                        && (valence.getLabelGF().getLabelType().getMiscLabel().getId() == triplet.getLabelGF().getLabelType().getMiscLabel().getId())) {
                    return  true;
                }
            }
        } else  {
            for (LayerTriplet triplet : valenceUnits) {
                if ((valence.getLabelFE().getLabelType().getFrameElement().getId()) == triplet.getLabelFE().getLabelType().getFrameElement().getId()
                        && (triplet.getLabelFE().getInstantiationType().getId() == instantiationType)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasGroupValence (List<LayerTriplet> listValence) {
        for (LayerTriplet valence : listValence) {
            if (! hasValence(valence)) {
                return false;
            }
        }
        return true;
    }

    public String outputFE(FrameElement frameElement) {
        for (LayerTriplet unit : valenceUnits) {
            if (unit.getLabelFE().getLabelType().getFrameElement().getId() == frameElement.getId()) {
                return unit.outputString();
            }
        }
        return "";
    }

    public void addOccurence(AnnotationSet annotationSet) {
        annoSet.add(annotationSet);
    }

    public List<LayerTriplet> getValenceUnits () {
        return valenceUnits;
    }

    public void setValenceUnits(List<LayerTriplet> list) {
        valenceUnits = list;
    }

    public List<AnnotationSet> getAnnoSet() {
        return annoSet;
    }

    public void setAnnoSet(List<AnnotationSet> list) {
        annoSet = list;
    }
}
