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
    private final int DEFAULT_PAGESIZE = 100;
    private int pageSize = DEFAULT_PAGESIZE;

    private boolean kwicView = true;

    private List<DTOSentenceDisplay> selectedSentences;

    public KwicDataView() {
    }

    public List<DTOSentenceDisplay> getSelectedSentences() {
        return selectedSentences;
    }

    public void setSelectedSentences(List<DTOSentenceDisplay> selectedSentences) {
        this.selectedSentences = selectedSentences;
    }


    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void resetPageSize(){
        this.pageSize = DEFAULT_PAGESIZE;
    }

    public boolean isKwicView() {
        return kwicView;
    }

    public boolean isNonKwicView() {
        return !kwicView;
    }

    public void setKwicView(boolean kwicView) {
        this.kwicView = kwicView;
    }



}
