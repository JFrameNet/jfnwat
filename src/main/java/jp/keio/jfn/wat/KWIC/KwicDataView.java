package jp.keio.jfn.wat.KWIC;

import jp.keio.jfn.wat.KWIC.domain.KwicSentence;
import org.primefaces.event.SelectEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;

/**
 * Created by jfn on 4/12/16.
 */

@Scope("view")
@Component
public class KwicDataView implements Serializable {

    private DTOSentenceDisplay selectedSentence;

    public KwicDataView() {
    }

    public DTOSentenceDisplay getSelectedSentence() {
        return selectedSentence;
    }

    public void setSelectedSentence(DTOSentenceDisplay selectedSentence) {
        this.selectedSentence = selectedSentence;
    }

    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Sentence Selected", String.valueOf(   ( (DTOSentenceDisplay) event.getObject() ).kwicSentence.getId()  ));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
