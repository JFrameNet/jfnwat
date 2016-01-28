package jp.keio.jfn.wat.controller;

import jp.keio.jfn.wat.domain.FrameElement;
import jp.keio.jfn.wat.domain.Label;

/**
 * Created by jfn on 1/8/16.
 */
public class LayerTriplet {
    private Label labelFE;
    private Label labelGF;
    private Label labelPT;

    public LayerTriplet(Label label) {
        labelFE = label;
    }

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
