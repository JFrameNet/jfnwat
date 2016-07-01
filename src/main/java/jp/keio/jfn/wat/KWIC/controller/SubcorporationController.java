package jp.keio.jfn.wat.KWIC.controller;

import jp.keio.jfn.wat.domain.LexUnit;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;


import javax.faces.bean.ManagedBean;
import java.io.Serializable;


/**
 * Created by jfn on 6/28/16.
 */
@ManagedBean
@Controller
@Scope("view")
public class SubcorporationController implements Serializable {
    private LexUnit selectedLu;

    public void setSelectedLU(LexUnit lu){
        this.selectedLu = lu;
    }

    public LexUnit getSelectedLu() {
        return selectedLu;
    }

    public void subcorporate() {
        System.out.println("create a new subcorpus");
    }
}
