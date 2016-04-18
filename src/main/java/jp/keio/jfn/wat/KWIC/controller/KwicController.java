package jp.keio.jfn.wat.KWIC.controller;

import javax.faces.bean.ManagedBean;

import jp.keio.jfn.wat.KWIC.NoResultsExeption;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import jp.keio.jfn.wat.KWIC.KwicDataView;

import jp.keio.jfn.wat.domain.LexUnit;
import jp.keio.jfn.wat.repository.LexUnitRepository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Quiri on 3-2-2016.
 */

@ManagedBean
@RestController
@Scope("view")
public class KwicController implements Serializable {

    @Autowired
    LexUnitRepository lexUnitRepository;

    @Autowired
    KwicDataView kwicDataView;

    private String search;
    private String colloquial;
    private Boolean end;


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

    public void setColloquial(String colloquial) {
        this.colloquial = colloquial;
    }

    public String getColloquial() {
        return colloquial;
    }

    public void setEnd(Boolean end) {
        this.end = end;
    }

    public Boolean getEnd() {
        return end;
    }


    @Transactional(transactionManager = "kwicTransactionManager")
    public void findMatchingSentences() {
        try {
            kwicDataView.setSearch(search);
        } catch (NoResultsExeption e){
            System.out.println("No matching sentences found for " + search);
        }
    }

    public KwicDataView getView() {
        return kwicDataView;
    }
}

