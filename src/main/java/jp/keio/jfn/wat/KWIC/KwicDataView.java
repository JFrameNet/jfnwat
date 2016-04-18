package jp.keio.jfn.wat.KWIC;

import jp.keio.jfn.wat.KWIC.controller.KwicController;
import jp.keio.jfn.wat.KWIC.domain.KwicSentence;
import jp.keio.jfn.wat.KWIC.domain.KwicWord;
import jp.keio.jfn.wat.KWIC.repository.KwicSentenceRepository;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.io.Serializable;

/**
 * Created by jfn on 4/12/16.
 */

@Scope("view")
@Component
public class KwicDataView implements Serializable {

    @Autowired
    KwicDataModel lazyModel;

    private String search;

    private KwicSentence selectedSentence;


    public KwicDataView() {}


    public LazyDataModel<KwicSentence> getLazyModel() {
        return lazyModel;
    }

    public KwicSentence getSelectedSentence() {
        return selectedSentence;
    }

    public void setSelectedCar(KwicSentence selectedSentence) {
        this.selectedSentence = selectedSentence;
    }

    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Sentence Selected", String.valueOf(   ( (KwicSentence) event.getObject() ).getId()  ));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void setSearch(String search) throws NoResultsExeption {
        this.search = search;
        lazyModel.setSearch(search);
    }
}
