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
                if (triplet.getLabelFE().getInstantiationType().getId() == instantiationType) {
                    boolean fe = sameLabel(valence.getLabelFE(), triplet.getLabelFE(), true);
                    boolean pt = sameLabel(valence.getLabelPT(), triplet.getLabelPT(), false);
                    boolean gf = sameLabel(valence.getLabelGF(), triplet.getLabelGF(), false);
                    if (fe && pt && gf) {
                        return true;
                    }
                }
            }
        } else if (instantiationType == 6){
            for (LayerTriplet triplet : valenceUnits) {
                if (triplet.getLabelFE().getInstantiationType().getId() == instantiationType) {
                    boolean fe = sameLabel(valence.getLabelFE(), triplet.getLabelFE(), true);
                    boolean pt = false;
                    boolean gf = sameLabel(valence.getLabelGF(), triplet.getLabelGF(), false);
                    if (valence.getLabelPT() == null) {
                        if (triplet.getLabelPT() == null) {
                            pt = true;
                        }
                    } else {
                        if (triplet.getLabelPT() != null) {
                            if (valence.getLabelFE().getInstantiationType().getId() == triplet.getLabelFE().getInstantiationType().getId()) {
                                pt = true;
                            }
                        }
                    }
                    if (fe && pt && gf) {
                        return true;
                    }
                }
            }
        } else {
            for (LayerTriplet triplet : valenceUnits) {
                if ((valence.getLabelFE().getLabelType().getFrameElement().getId()) == triplet.getLabelFE().getLabelType().getFrameElement().getId()
                        && (triplet.getLabelFE().getInstantiationType().getId() == instantiationType)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean sameLabel (Label label1, Label label2, boolean fe) {
        if (label1 == null) {
            if (label2 == null) {
                return true;
            }
        } else {
            if (label2 != null) {
                if (fe) {
                    if (label1.getLabelType().getFrameElement().getId() == label2.getLabelType().getFrameElement().getId()) {
                        return true;
                    }
                } else {
                    if (label1.getLabelType().getMiscLabel().getId() == label2.getLabelType().getMiscLabel().getId()) {
                        return true;
                    }
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
