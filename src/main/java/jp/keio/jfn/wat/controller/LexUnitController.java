package jp.keio.jfn.wat.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;

import jp.keio.jfn.wat.domain.LexUnit;
import jp.keio.jfn.wat.domain.Status;
import jp.keio.jfn.wat.repository.LexUnitRepository;
import jp.keio.jfn.wat.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@ManagedBean
@Controller
public class LexUnitController implements Serializable {
    @Autowired
    LexUnitRepository lexUnitRepository;

    @Autowired
    StatusRepository statusRepository;

    public Iterable<LexUnit> getAll () {
        return lexUnitRepository.findAll();
    }

    public List<String> getStatusForLU (LexUnit lu) {
        Iterable<Status> allStatus = statusRepository.findAll();
        List<String> myList = new ArrayList<String>();
        for (Status status : allStatus) {
            if (status.getLexUnit().getId() == lu.getId()) {
                myList.add(status.getStatusType().getName());
            }
        }
        return myList;
    }
}