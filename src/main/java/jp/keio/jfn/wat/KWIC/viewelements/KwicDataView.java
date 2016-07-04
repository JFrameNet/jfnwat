package jp.keio.jfn.wat.KWIC.viewelements;

import jp.keio.jfn.wat.KWIC.DTOSentenceDisplay;
import jp.keio.jfn.wat.KWIC.LazyKwicData;
import jp.keio.jfn.wat.KWIC.domain.KwicSentence;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.ToggleSelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.LazyDataModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
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

    private LazyDataModel<DTOSentenceDisplay> lazyDataModel;

    private boolean kwicView = true;

    private List<DTOSentenceDisplay> selectedSentences = new ArrayList<>();
    private DTOSentenceDisplay contextSentence;

    public KwicDataView() {
    }

    public List<DTOSentenceDisplay> getSelectedSentences() {
        return selectedSentences;
    }

    public void setSelectedSentences(List<DTOSentenceDisplay> selectedSentences) {
       // empty
    }

    public void rowSelectCheckbox(SelectEvent event){
        selectedSentences.add((DTOSentenceDisplay) event.getObject());
    }

    public void rowUnselectCheckbox(UnselectEvent event){
        int idToRemove = ((DTOSentenceDisplay) event.getObject()).getKwicsID();
        List<DTOSentenceDisplay> toRemove = new ArrayList<>();
        for (DTOSentenceDisplay selected : selectedSentences){
            if(selected.getKwicsID() == idToRemove){
                toRemove.add(selected);
            }
        }
        selectedSentences.removeAll(toRemove);
    }

    public void toggleSelect(ToggleSelectEvent event){
        if(!event.isSelected()){
            selectedSentences.clear();
        } else {
        }
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

    public void setLazyKwicData(LazyDataModel<DTOSentenceDisplay> lazyKwicData) {
        this.lazyDataModel = lazyKwicData;
    }

    public void setContextSentence(DTOSentenceDisplay sentence) {
        this.contextSentence = sentence;
    }

    public DTOSentenceDisplay getContextSentence() {
        return this.contextSentence;
    }
}
