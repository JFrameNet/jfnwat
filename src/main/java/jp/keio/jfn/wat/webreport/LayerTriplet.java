package jp.keio.jfn.wat.webreport;

import jp.keio.jfn.wat.webreport.domain.Label;

/**
 * This class represents the grammatical information associated to a frame element label.
 * PT is its phrase type and GF the grammatical function.
 */
public class LayerTriplet {
    private Label labelFE;
    private Label labelGF;
    private Label labelPT;

    /**
     * Initialization with the frame element label.
     */
    public LayerTriplet(Label label) {
        labelFE = label;
    }

    /**
     * Creates an output string of the format FE.PT.GF with the name of the labels of the triple.
     */
    public String outputString () {
        if (labelFE.getInstantiationType().getId() == 1) {
            String pt = (labelPT == null) ? "" : labelPT.getLabelType().getMiscLabel().getName();
            String gf = (labelGF == null) ? "" :labelGF.getLabelType().getMiscLabel().getName();

            return pt + "." + gf;
        } else if (labelFE.getInstantiationType().getId() == 6){
            String ini = labelFE.getInstantiationType().getName();
            String gf = (labelGF == null) ? "" :labelGF.getLabelType().getMiscLabel().getName();
            return ini + "." + gf;
        } else {
            return labelFE.getInstantiationType().getName();
        }

    }

    public void setLabelPT (Label label) {
        labelPT = label;
    }
    public void setLabelGF (Label label) {
        labelGF = label;
    }

    public Label getLabelFE() {
        return labelFE;
    }
    public Label getLabelPT() {
        return labelPT;
    }
    public Label getLabelGF() {
        return labelGF;
    }


}
