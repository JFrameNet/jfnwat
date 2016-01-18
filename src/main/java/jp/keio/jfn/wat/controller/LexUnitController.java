package jp.keio.jfn.wat.controller;

import java.io.Serializable;
import java.util.*;
import javax.faces.bean.ManagedBean;

import jp.keio.jfn.wat.domain.*;
import jp.keio.jfn.wat.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@ManagedBean
@RestController
@Scope("view")
public class LexUnitController implements Serializable {
    @Autowired
    LexUnitRepository lexUnitRepository;

    private String filter = "";

    private List<LightLU> orderedLU = new ArrayList<LightLU>();

    public void orderLU () {
        List<LightLU> allLU= new ArrayList<LightLU>();
        for (LexUnit lu : lexUnitRepository.findAll()) {
            if (matchSearch(filter, lu.getName())) {
                allLU.add(new LightLU(lu.getId(), lu.getName(), lu.getFrame().getName()));
            } else if (matchSearch(filter, lu.getFrame().getName())) {
                allLU.add(new LightLU(lu.getId(), lu.getName(), lu.getFrame().getName()));
            }
        }
        orderedLU = allLU;
    }

    public void setFilter (String f) {
        filter = f;
    }

    public String getFilter () {
        return filter;
    }

    public void setOrderedLU (List<LightLU> list) {
        orderedLU = list;
    }

    public List<LightLU> getOrderedLU () {
        if (orderedLU.isEmpty() && filter.isEmpty()) {
            List <LightLU> allLu = new ArrayList<LightLU>();
            for (LexUnit lu : lexUnitRepository.findAll()) {
                allLu.add(new LightLU(lu.getId(), lu.getName(), lu.getFrame().getName()));
            }
            return allLu;
        } else {
            return orderedLU;
        }
    }

    private boolean matchSearch (String query, String name) {
        return ((name.equalsIgnoreCase(query))
                ||(name.toLowerCase().contains(query.toLowerCase()))
                ||(query.toLowerCase().contains(name.toLowerCase())));
    }
}