package jp.keio.jfn.wat.webreport;

import jp.keio.jfn.wat.domain.AnnotationSet;
import jp.keio.jfn.wat.domain.FrameElement;
import jp.keio.jfn.wat.domain.Label;
import jp.keio.jfn.wat.webreport.LayerTriplet;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a pattern entry, which consists in a list of LayerTriplet (labels in the format FE.PT.GF) and
 * a list of annotation sets.
 */
public class PatternEntry {

    private List<LayerTriplet> valenceUnits = new ArrayList<LayerTriplet>();

    private List<AnnotationSet> annoSet  = new ArrayList<AnnotationSet>();

    /**
     * Checks if an input LayerTriplet object is already present in the valenceUnits list.
     *
     * @return true if it founds the LayerTriplet object in valenceUnits list, false otherwise.
     */
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

    /**
     * Two labels are the same if they are both null or if their associated name (the frame element name or the MiscLabel
     * name depending on the type of the label) are identical.
     */
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

    /**
     * Checks if an input list of LayerTriplet objects contains the same elements as the current LayerTriplet list.
     *
     * @return true if every LayerTriplet object is contained in valenceUnits list, false otherwise.
     */
    public boolean hasGroupValence (List<LayerTriplet> listValence) {
        for (LayerTriplet valence : listValence) {
            if (! hasValence(valence)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the output string in the format FE.PT.GF associated to a frame element
     */
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

}
