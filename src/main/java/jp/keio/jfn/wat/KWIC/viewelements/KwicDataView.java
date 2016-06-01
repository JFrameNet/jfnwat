package jp.keio.jfn.wat.KWIC.viewelements;

import jp.keio.jfn.wat.KWIC.DTOSentenceDisplay;
import jp.keio.jfn.wat.KWIC.domain.KwicSentence;
import org.primefaces.event.SelectEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;

/**
 * Created by jfn on 4/12/16.
 */

@Scope("view")
@Component
@ManagedBean
public class KwicDataView implements Serializable {

    private List<DTOSentenceDisplay> selectedSentences;

    public KwicDataView() {
    }

    public List<DTOSentenceDisplay> getSelectedSentences() {
        return selectedSentences;
    }

    public void setSelectedSentences(List<DTOSentenceDisplay> selectedSentences) {
        this.selectedSentences = selectedSentences;
    }
}
