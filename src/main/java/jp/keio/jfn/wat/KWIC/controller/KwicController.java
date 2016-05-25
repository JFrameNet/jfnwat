package jp.keio.jfn.wat.KWIC.controller;

import javax.faces.bean.ManagedBean;

import jp.keio.jfn.wat.KWIC.*;
import org.primefaces.model.LazyDataModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import org.springframework.beans.factory.annotation.Autowired;

import jp.keio.jfn.wat.domain.LexUnit;
import jp.keio.jfn.wat.repository.LexUnitRepository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Quiri on 3-2-2016.
 */

@ManagedBean
@Controller
@Scope("view")
public class KwicController implements Serializable {

    @Autowired
    LexUnitRepository lexUnitRepository;

    @Autowired
    KwicTransactions kwicTransactions;

    private LazyDataModel<DTOSentenceDisplay> lazyKwicData;


    private String search;
    private String collocate;
    private Boolean end;
    private int preScope;
    private int postScope;


    public String toKwic() { return "kwicPage?faces-redirect=true";}


    public List<String> completeText(String query) { //todo doesnt get called to first time after pageload

        List<String> results = new ArrayList<String>();

        while(results.size()<=5) {// TODO make 5 a parameter linked to the 5 in max results in kwicform.xhtml
            for (LexUnit lu : lexUnitRepository.findByNameStartingWith(query)) {
                String word = lu.getName().split("\\.")[0];
                if(!results.contains(word)){ results.add(word);}
            }
            break;
        }
        return results;
    }



    public void setSearch(String search) {
        this.search = search;
    }

    public String getSearch() {return search;}

    public void setCollocate(String collocate) {
        this.collocate = collocate;
    }

    public String getCollocate() {
        return collocate;
    }

    public void setEnd(Boolean end) {
        this.end = end;
    }

    public Boolean getEnd() {
        return end;
    }

    public void setPreScope(int preScope) {
        this.preScope = preScope;
    }

    public int getPreScope() {
        return preScope;
    }

    public void setPostScope(int postScope) {
        this.postScope = postScope;
    }

    public int getPostScope() {
        return postScope;
    }


    public void findMatchingSentences() {
        try {
            if(search != null){
                lazyKwicData = new LazyKwicData(new DTOKwicSearch(search, collocate, end), kwicTransactions);
            }
        } catch (NoResultsExeption noResultsExeption) {
            noResultsExeption.printStackTrace();
        }
    }

    public LazyDataModel<DTOSentenceDisplay> getLazyData(){
        if(lazyKwicData == null){
            findMatchingSentences();
        }
        return lazyKwicData;
    }


}

